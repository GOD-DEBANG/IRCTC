package org.example.app;

import org.example.app.Services.user_booking_service;
import org.example.app.util.user_service_util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            // Get user details
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            System.out.print("Enter user ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // Hash password
            String hashedPassword = user_service_util.hashPassword(password);

            // Create User object
            Users newUser = new Users();
            newUser.setName(name);
            newUser.setUserId(userId);
            newUser.setHashedPassword(hashedPassword);

            // Ticket input
            System.out.print("Enter ticket source: ");
            String source = scanner.nextLine();

            System.out.print("Enter ticket destination: ");
            String destination = scanner.nextLine();

            System.out.print("Enter ticket ID: ");
            String ticketId = scanner.nextLine();

            // Create ticket and add to list
            Ticket ticket = new Ticket(source, destination, ticketId);
            List<Ticket> ticketList = new ArrayList<>();
            ticketList.add(ticket);

            newUser.setTicketsBooked(ticketList);

            // Save user to JSON
            user_booking_service saver = new user_booking_service(newUser);
            saver.saveUser();

            System.out.println("\n✅ User saved successfully!");

        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
            e.printStackTrace(); // for debugging
        }
    }
}
