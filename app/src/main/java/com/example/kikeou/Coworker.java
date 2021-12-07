package com.example.kikeou;

import java.io.Serializable;
import java.util.List;

public class Coworker implements Serializable {

    private String name;
    private List<String> contacts;
    private List<String> localization;

    public Coworker(String name, List<String> contacts, List<String> localization) {
        this.name = name;
        this.contacts = contacts;
        this.localization = localization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public List<String> getLocalization() {
        return localization;
    }

    public void setLocalization(List<String> localization) {
        this.localization = localization;
    }

    @Override
    public String toString() {
        return "Coworker{" +
                "name='" + name + '\'' +
                ", contacts=" + contacts +
                ", localization=" + localization +
                '}';
    }
}
