package com.example.gymapp.model;

public class Gym {
    private int gymID;
    private String gymName;
    private String location;

    public Gym() {
    }

    public Gym(int gymID, String gymName, String location) {
        this.gymID = gymID;
        this.gymName = gymName;
        this.location = location;
    }

    public int getGymID() {
        return gymID;
    }

    public void setGymID(int gymID) {
        this.gymID = gymID;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "Gym{" +
                "gymID=" + gymID +
                ", gymName='" + gymName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
