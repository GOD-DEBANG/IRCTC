package org.example.app;

import java.util.List;

public class Users {
    private String name;
    private String password;
    private String hashpassword;
    private List<Ticket> ticketsbooked;
    private String userid;

    // Constructor
    public Users() {}

    // Getters
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getHashpassword() {
        return hashpassword;
    }

    public List<Ticket> getTicketsbooked() {
        return ticketsbooked;
    }

    public String getUserid() {
        return userid;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHashpassword(String hashpassword) {
        this.hashpassword = hashpassword;
    }

    public void setTicketsbooked(List<Ticket> ticketsbooked) {
        this.ticketsbooked = ticketsbooked;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    // Method to print all tickets
    public void printTickets() {
        if (ticketsbooked != null) {
            for (Ticket ticket : ticketsbooked) {
                System.out.println(ticket.getTicketInfo());
            }
        } else {
            System.out.println("No tickets booked.");
        }
    }
}
