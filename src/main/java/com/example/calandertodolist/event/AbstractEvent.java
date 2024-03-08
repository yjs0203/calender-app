package com.example.calandertodolist.event;

import com.example.calandertodolist.event.update.AbstractAuditableEvent;
import com.example.calandertodolist.exception.InvalidEventException;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

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

    public void validateAndUpdate(AbstractAuditableEvent update) {
        if (deleteYn == true) {
            throw new RuntimeException("이미 삭제된 이벤트는 수정할 수 없음");
        }

        defaultUpdate(update);
        update(update);
    }

    private void defaultUpdate(AbstractAuditableEvent update) {
        this.title = update.getTitle();
        this.startAt = update.getStartAt();
        this.endAt = update.getEndAt();
        this.duration = Duration.between(this.startAt, this.endAt);
        this.modifiedAt = ZonedDateTime.now();
    }

    protected abstract void update(AbstractAuditableEvent update);

    public void delete(boolean deleteYn) {
        this.deleteYn = deleteYn;
    }

    public String getTitle() { return this.title; }

    public ZonedDateTime getStartAt() { return startAt; }

    public ZonedDateTime getEndAt() { return endAt; }

    public boolean getDelteYn() { return deleteYn; }

    public boolean setDeleteYn(boolean deleteYn) {
        this.deleteYn = deleteYn;
        return deleteYn;
    }


}