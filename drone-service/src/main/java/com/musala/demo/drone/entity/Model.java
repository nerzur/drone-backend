package com.musala.demo.drone.entity;

/**
 * This enum contains the drone models.
 * @author Gabriel
 * @version 1.0
 */
public enum Model {
    LIGHTWEIGHT("Lightweight"),
    Middleweight("Middleweight"),
    CRUISERWEIGHT ("Cruiserweight"),
    HEAVYWEIGHT("Heavyweight");

    private final String text;

    Model(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
