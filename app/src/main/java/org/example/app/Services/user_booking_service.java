


package org.example.app.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.app.Users;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class user_booking_service {
    private static final String USERS_PATH = "src/main/java/org/example/app/LocalDB/users.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private Users user;

    public user_booking_service(Users user) {
        this.user = user;
    }

    public void saveUser() {
        try {
            File file = new File(USERS_PATH);

            List<Users> userList = new ArrayList<>();
            if (file.exists()) {
                userList = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Users.class));
            }

            userList.add(user);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, userList);
        } catch (IOException e) {
            System.out.println("Error saving new user: " + e.getMessage());
        }
    }
}
