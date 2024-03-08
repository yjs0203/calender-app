package com.example.calandertodolist.event;

import com.example.calandertodolist.event.update.AbstractAuditableEvent;
import com.example.calandertodolist.event.update.UpdateNoDisturbance;
import com.example.calandertodolist.event.update.UpdateOutOfOffice;

import java.time.ZonedDateTime;

public class OutOfOffice extends AbstractEvent {
    public OutOfOffice(int id, String title,
                       ZonedDateTime startAt, ZonedDateTime endAt) {
        super(id, title, startAt, endAt);
    }

    @Override
    protected void update(AbstractAuditableEvent update) {
        UpdateOutOfOffice updateOutOfOffice = (UpdateOutOfOffice) update;
    }

    @Override
    public void print() {
        System.out.printf("[자리비움] %s : %s, %s%n", getTitle(), getStartAt(), getEndAt());
    }

    @Override
    public boolean support(EventType type) {
        return type == EventType.OUT_OF_OFFICE;
    }
}
