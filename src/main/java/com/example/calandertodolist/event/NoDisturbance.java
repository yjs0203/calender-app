package com.example.calandertodolist.event;

import com.example.calandertodolist.event.update.AbstractAuditableEvent;
import com.example.calandertodolist.event.update.UpdateMeeting;
import com.example.calandertodolist.event.update.UpdateNoDisturbance;

import java.time.ZonedDateTime;

public class NoDisturbance extends AbstractEvent {
    public NoDisturbance(int id, String title,
                         ZonedDateTime startAt, ZonedDateTime endAt) {
        super(id, title, startAt, endAt);
    }

    @Override
    public void print() {
        System.out.printf("[방해금지] %s : %s, %s%n", getTitle(),getStartAt(),getEndAt());
    }

    @Override
    public boolean support(EventType type) {
        return type == EventType.NO_DISTURBANCE;
    }

    @Override
    protected void update(AbstractAuditableEvent update) {
        UpdateNoDisturbance updateNoDisturbance = (UpdateNoDisturbance) update;
    }
}
