package com.musala.demo.drone.entity;

public enum State {
    IDLE("IDLE"),
    LOADING("LOADING"),
    LOADED ("LOADED"),
    DELIVERING("DELIVERING"),
    DELIVERED("DELIVERED"),
    RETURNING("RETURNING"),
    ;

    private final String text;

    State(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
