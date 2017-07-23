package database;

import model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 7/21/17.
 */
public class DataBase {
    private static Map<String, User> users = new HashMap<>();

    public static Map<String, User> getUsers() {
        return users;
    }

}
