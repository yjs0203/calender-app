package com.example.calenderapp.event;

public interface Event {
    void print();

    boolean support(EventType type);
}
