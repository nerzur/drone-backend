package com.musala.demo.drone.entity;

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
