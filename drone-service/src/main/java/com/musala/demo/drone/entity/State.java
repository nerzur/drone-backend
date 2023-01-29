package com.musala.demo.drone.entity;

/**
 * This enum contains the states in which a drone can be found.
 * @author Gabriel
 * @version 1.0
 */
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
