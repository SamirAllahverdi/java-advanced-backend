package org.example.exchange_application.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Currency> currencies;

    public User(String name, List<Currency> currencies) {
        this.name = name;
        this.currencies = currencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", currencies=" + currencies +
                '}';
    }
}
