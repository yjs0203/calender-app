package com.example.calenderapp.event;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.time.ZonedDateTime;
import java.util.Set;

public class Todo extends AbstractEvent{

    private String description;

    public Todo(int id, String title,
                ZonedDateTime startAt, ZonedDateTime endAt,
                String description) {
        super(id, title, startAt, endAt);
        this.description = description;
    }

    @Override
    public void print() {
        System.out.printf("[할 일] %s : %s%n", getTitle(), description);
    }
}
