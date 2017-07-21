package database;

import model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 7/21/17.
 */
public class DataBase {
    private static Map<Long, User> users = new HashMap<>();

    public static Map<Long, User> getUsers() {
        return users;
    }

}
