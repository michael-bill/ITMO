package org.michaelb.lab3.story.exceptions;

public class NoMoreFuelException extends RuntimeException {
    public NoMoreFuelException() {
        super("у воздушного шара законичлось топливо и он не может лететь");
    }
}
