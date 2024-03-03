package com.example.calenderapp.event;

import java.time.ZonedDateTime;

public class OutOfOffice extends AbstractEvent {
    public OutOfOffice(int id, String title,
                       ZonedDateTime startAt, ZonedDateTime endAt) {
        super(id, title, startAt, endAt);
    }

    @Override
    public void print() {

    }
}
