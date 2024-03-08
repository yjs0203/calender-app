package com.example.calandertodolist.event.update;

import java.time.ZonedDateTime;

public class UpdateOutOfOffice extends AbstractAuditableEvent{

    public UpdateOutOfOffice(String title, ZonedDateTime startAt, ZonedDateTime endAt) {
        super(title, startAt, endAt);
    }
}
