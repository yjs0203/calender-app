package com.example.calandertodolist.event;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<AbstractEvent> events = new ArrayList<>();

    public void add(AbstractEvent event) {
        if (hasScheduleConflictWith(event)) {
            throw new RuntimeException(
                    String.format(
                            "이미 스케줄이 있는 시간에는 추가할 수 없습니다. %s : %s%n",
                            event.getTitle(), event.getStartAt()
                    )
            );
        }
        this.events.add(event);
    }

    public void printAll() {
        events.stream()
                .filter(event -> !event.getDelteYn())
                .forEach(Event::print);
    }

    public void printBy(EventType type) {
        events.stream()
                .filter(event -> event.support(type))
                .forEach(Event::print);
    }

    private boolean hasScheduleConflictWith(AbstractEvent event) {
        return events.stream()
                .anyMatch(each ->
                        (event.getStartAt().isBefore(each.getEndAt()) && event.getStartAt().isAfter(each.getStartAt()))
                            || (event.getEndAt().isAfter(each.getStartAt())) && event.getEndAt().isBefore(each.getEndAt())
                            || event.getStartAt().equals(each.getStartAt()) && event.getEndAt().equals(each.getEndAt()));
    }

    public void resetSchedule() {
        events.stream()
                .filter(AbstractEvent::getDelteYn)
                .forEach(event -> event.setDeleteYn(false));
    }
}
