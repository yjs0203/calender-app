package com.example.calandertodolist.event;

import com.example.calandertodolist.exception.InvalidEventException;

import java.time.Duration;
import java.time.ZonedDateTime;

public abstract class AbstractEvent implements Event{
    private final int id;
    private String title;

    private ZonedDateTime startAt;
    private ZonedDateTime endAt;
    private Duration duration;

    private final ZonedDateTime createAt;
    private ZonedDateTime modifiedAt;

    private boolean deleteYn;

    protected AbstractEvent(int id, String title,
                            ZonedDateTime startAt, ZonedDateTime endAt) {
        if (startAt.isAfter(endAt)) {
            throw new InvalidEventException(
                    String.format("시작일은 종료일보다 이전이여야 합니다. 시작일=%s, 종료일=%s", startAt, endAt)
            );
        }

        this.id = id;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.duration = Duration.between(startAt, endAt);

        ZonedDateTime now = ZonedDateTime.now();
        this.createAt = now;
        this.modifiedAt = now;

        this.deleteYn = false;
    }

    public String getTitle() {
        return this.title;
    }
}
