package com.example.calandertodolist.event.update;

import java.time.ZonedDateTime;

public class UpdateOutOfOffice extends AbstractAuditableEvent{

    protected UpdateOutOfOffice(String title, ZonedDateTime startAt, ZonedDateTime endAt) {
        super(title, startAt, endAt);
    }
}
