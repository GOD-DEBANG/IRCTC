package ticket.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.app.Train;
import org.example.app.Users;
import org.example.app.TrainService;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserBookingService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String USER_FILE_PATH = "app/src/main/java/ticket/booking/localDb/users.json";

    private List<Users> userList;
    private Users currentUser; // holds the logged-in user

    // Constructors
    public UserBookingService(Users user) throws IOException {
        this.currentUser = user;
        loadUserListFromFile();
    }

    public UserBookingService() throws IOException {
        loadUserListFromFile();
    }

    // Load all users from JSON file
    private void loadUserListFromFile() throws IOException {
        File userFile = new File(USER_FILE_PATH);
        if (userFile.exists()) {
            userList = objectMapper.readValue(userFile, new TypeReference<List<Users>>() {});
        } else {
            userList = new ArrayList<>();
        }
    }

    // Save updated user list to file
    private void saveUserListToFile() throws IOException {
        objectMapper.writeValue(new File(USER_FILE_PATH), userList);
    }

    // Login check
    public Boolean loginUser() {
        Optional<Users> foundUser = userList.stream()
                .filter(u -> u.getName().equals(currentUser.getName())
                        && UserServiceUtil.checkPassword(currentUser.getPassword(), u.getHashpassword()))
                .findFirst();

        foundUser.ifPresent(user -> this.currentUser = user); // set the matched user as currentUser
        return foundUser.isPresent();
    }

    // Sign up new user
    public Boolean signUp(Users newUser) {
        try {
            userList.add(newUser);
            saveUserListToFile();
            return true;
        } catch (IOException e) {
            System.err.println("Error saving new user: " + e.getMessage());
            return false;
        }
    }

    // Fetch and print current user's booked tickets
    public void fetchBookings() {
        if (currentUser != null) {
            currentUser.printTickets();
        } else {
            System.out.println("User not logged in.");
        }
    }

    // Cancel booking
    public Boolean cancelBooking(String ticketId) {
        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be null or empty.");
            return false;
        }

        if (currentUser == null) {
            System.out.println("No user logged in.");
            return false;
        }

        boolean removed = currentUser.getTicketsBooked()
                .removeIf(ticket -> ticket.getTicketId().equals(ticketId));

        if (removed) {
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            try {
                saveUserListToFile(); // persist change
            } catch (IOException e) {
                System.err.println("Failed to save ticket changes: " + e.getMessage());
                return false;
            }
            return true;
        } else {
            System.out.println("No ticket found with ID " + ticketId);
            return false;
        }
    }

    // Fetch trains between source and destination
    public List<Train> getTrains(String source, String destination) {
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        } catch (IOException ex) {
            System.err.println("Error fetching trains: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    // Get seat matrix from train
    public List<List<Integer>> fetchSeats(Train train) {
        return train.getSeats();
    }

    // Book a seat
    public Boolean bookTrainSeat(Train train, int row, int seat) {
        try {
            List<List<Integer>> seats = train.getSeats();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    new TrainService().addTrain(train); // update train info
                    return true;
                }
                System.out.println("Seat already booked.");
                return false;
            } else {
                System.out.println("Invalid row or seat number.");
                return false;
            }
        } catch (IOException ex) {
            System.err.println("Booking failed: " + ex.getMessage());
            return false;
        }
    }
}
