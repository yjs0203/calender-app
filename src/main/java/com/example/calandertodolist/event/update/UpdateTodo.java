package com.example.calandertodolist.event.update;

import java.time.ZonedDateTime;

public class UpdateTodo extends AbstractAuditableEvent {

    private final String description;

    public UpdateTodo(String title, ZonedDateTime startAt, ZonedDateTime endAt,
                            String description) {
        super(title, startAt, endAt);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
