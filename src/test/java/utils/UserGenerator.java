package utils;

import java.util.concurrent.ThreadLocalRandom;

public class UserGenerator {
    public static User getRandomUser() {
        User user = new User();
        user.setName("User" + System.currentTimeMillis());
        user.setEmail("user" + System.currentTimeMillis() + "@example.com");
        user.setPassword("password" + ThreadLocalRandom.current().nextInt(1000, 9999));
        return user;
    }
}
