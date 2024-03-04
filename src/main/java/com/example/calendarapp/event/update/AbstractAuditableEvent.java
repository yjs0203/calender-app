package com.example.calendarapp.event.update;

import java.time.ZonedDateTime;

public class AbstractAuditableEvent {
    private final String title;
    private final ZonedDateTime startAt;
    private final ZonedDateTime endAt;

    protected AbstractAuditableEvent(String title, ZonedDateTime startAt, ZonedDateTime endAt) {
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public String getTitle() {return title;}
    public ZonedDateTime getStartAt() {return startAt;}
    public ZonedDateTime getEndAt() {return endAt;}

}
