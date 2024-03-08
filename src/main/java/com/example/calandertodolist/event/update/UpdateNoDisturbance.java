package com.example.calandertodolist.event.update;

import java.time.ZonedDateTime;
import java.util.Set;

public class UpdateNoDisturbance extends AbstractAuditableEvent {

    public UpdateNoDisturbance(String title, ZonedDateTime startAt, ZonedDateTime endAt) {
        super(title, startAt, endAt);
    }
}
