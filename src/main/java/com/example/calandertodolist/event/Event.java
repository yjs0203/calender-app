package com.example.calandertodolist.event;

public interface Event {
    /*

     */
    void print();

    boolean support(EventType type);
}
