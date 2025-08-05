package org.example.app;

import java.util.List;

public class Users {
    private String name;
    private String userId;
    private String hashedPassword;
    private List<Ticket> ticketsBooked;

    // Getters
    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }
}
