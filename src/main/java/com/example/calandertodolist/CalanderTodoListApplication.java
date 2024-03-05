package com.example.calandertodolist;

import com.example.calandertodolist.event.*;
import com.example.calandertodolist.event.update.UpdateMeeting;
import com.example.calandertodolist.reader.EventCsvReader;
import com.example.calandertodolist.reader.RawCsvReader;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class CalanderTodoListApplication {

    public static void main(String[] args) throws IOException {
        Schedule schedule = new Schedule();

//        HashSet<String> participants = new HashSet<>();
//        participants.add("jimmy.jang");
//
//        Meeting meeting1 = new Meeting(
//                1, "meeting1",
//                ZonedDateTime.now(), ZonedDateTime.now().plusHours(1),
//                participants, "meetingRoomA", "스터디"
//        );
//        schedule.add(meeting1);
//
//        Todo todo1 = new Todo(
//                2, "todo1",
//                ZonedDateTime.now().plusHours(3), ZonedDateTime.now().plusHours(4),
//                "할 일 적기"
//        );
//        schedule.add(todo1);
//
//        Todo todo2 = new Todo(
//                3, "todo2",
//                ZonedDateTime.now().plusHours(5), ZonedDateTime.now().plusHours(4),
//                "할 일 적기"
//        );
//        schedule.add(todo2);

        EventCsvReader csvReader = new EventCsvReader(new RawCsvReader());
        String meetingCsvPath = "/data/meeting.csv";
        String noDisturbanceCsvPath = "/data/no_disturbance.csv";
        String outOfOfficeCsvPath = "/data/out_of_office.csv";
        String toDoCsvPath = "/data/to_do.csv";

        List<Meeting> meetings = csvReader.readMeetings(meetingCsvPath);
        meetings.forEach(schedule::add);

        List<NoDisturbance> noDisturbances = csvReader.readNoDisturbance(noDisturbanceCsvPath);
        noDisturbances.forEach(schedule::add);

        List<OutOfOffice> outOfOffices = csvReader.readOutOfOffice(outOfOfficeCsvPath);
        outOfOffices.forEach(schedule::add);

        List<Todo> toDo = csvReader.readToDo(toDoCsvPath);
        toDo.forEach(schedule::add);

        schedule.printAll();

        System.out.println("------------------------------------------");

        Meeting meeting = meetings.get(0);
        meeting.print();

        System.out.println("수정 후 ...");
        meetings.get(0).validateAndUpdate(
                new UpdateMeeting(
                        "new title",
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusHours(1),
                        null,
                        "A",
                        "new agenda"
                )
        );

        meeting.delete(true);
        System.out.println("삭제 후 수정 시도 ...");
        meetings.get(0).validateAndUpdate(
                new UpdateMeeting(
                        "new title 2",
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusHours(1),
                        null,
                        "B",
                        "new agenda 2"
                )
        );

        meeting.print(); //삭제된 data 수정불가.

        System.out.println("전체 일정 ...");
        schedule.printAll();
    }
}
