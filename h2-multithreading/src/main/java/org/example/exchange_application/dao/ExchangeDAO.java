package org.example.exchange_application.dao;


import org.example.exchange_application.model.User;

import java.io.*;

public class ExchangeDAO {

    public static final String PATH = "h7_concurrency/src/main/java/exchange_application/user1.txt";


    public void createSampleData(User user) {
        write(user);
    }

    public void write(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(PATH)))) {
            oos.writeObject(user);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("DAO:write:IOException", ex);
        }
    }

    public User read() {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(PATH)))) {
            Object read = ois.readObject();
            return (User) read;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }

    }
}
