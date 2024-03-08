package com.example.calandertodolist;

import com.example.calandertodolist.event.*;
import com.example.calandertodolist.event.update.UpdateMeeting;
import com.example.calandertodolist.event.update.UpdateNoDisturbance;
import com.example.calandertodolist.event.update.UpdateOutOfOffice;
import com.example.calandertodolist.event.update.UpdateTodo;
import com.example.calandertodolist.exception.InvalidEventException;
import com.example.calandertodolist.reader.EventCsvReader;
import com.example.calandertodolist.reader.RawCsvReader;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.management.RuntimeMBeanException;
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

        List<Todo> toDos = csvReader.readToDo(toDoCsvPath);
        toDos.forEach(schedule::add);

        System.out.println("==========Meeting CRUD==========");
        System.out.println("--- CSV READER Origin DATA ---");
        schedule.printAll();
        System.out.println();

        System.out.println("=== 수정 전 ===");
        Meeting meeting = meetings.get(0);
        meeting.print();
        System.out.println();

        System.out.println("=== 수정 후 ===");
        meetings.get(0).validateAndUpdate(
                new UpdateMeeting(
                        "Meeting Update Success",
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusHours(1),
                        null,
                        "A",
                        "new agenda"
                )
        );
        meeting.print();
        System.out.println();

        System.out.println("=== 삭제 전 ===");
        meeting.print();
        System.out.println();

        meeting.delete(true);
        System.out.println("=== 삭제 후 수정 시도 ===");

        try{
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
        } catch (RuntimeException e) {
            System.out.println("!!! 오류 발생, 다음 코드 진행 !!!");
            System.out.println();
        }

        System.out.println("=== 전체 일정 재출력 ===");
        schedule.printAll();
        System.out.println();
        schedule.resetSchedule();


        System.out.println("==========NoDisturbance CRUD==========");
        System.out.println("--- CSV READER Origin DATA ---");
        schedule.printAll();
        System.out.println();

        System.out.println("=== 수정 전 ===");
        NoDisturbance noDisturbance = noDisturbances.get(0);
        noDisturbance.print();
        System.out.println();

        System.out.println("=== 수정 후 ===");
        noDisturbances.get(0).validateAndUpdate(
                new UpdateNoDisturbance(
                        "NoDisturbance Update Success",
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusHours(1)
                )
        );
        noDisturbance.print();
        System.out.println();

        System.out.println("=== 삭제 전 ===");
        noDisturbance.print();
        System.out.println();

        noDisturbance.delete(true);
        System.out.println("=== 삭제 후 수정 시도 ===");

        try {
            noDisturbances.get(0).validateAndUpdate(
                    new UpdateNoDisturbance(
                            "NoDisturbance Delete Success",
                            ZonedDateTime.now(),
                            ZonedDateTime.now().plusHours(1)
                    )
            );
        } catch (RuntimeException e) {
            System.out.println("!!! 오류 발생, 다음 코드 진행 !!!");
            System.out.println();
        }

        System.out.println("=== 전체 일정 재출력 ===");
        schedule.printAll();
        System.out.println();
        schedule.resetSchedule();


        System.out.println("==========OutOfOffice CRUD==========");
        System.out.println("--- CSV READER Origin DATA ---");
        schedule.printAll();
        System.out.println();

        System.out.println("=== 수정 전 ===");
        OutOfOffice outOfOffice = outOfOffices.get(0);
        outOfOffice.print();
        System.out.println();

        System.out.println("=== 수정 후 ===");
        outOfOffices.get(0).validateAndUpdate(
                new UpdateOutOfOffice(
                        "OutOfOffice Update Success",
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusHours(1)
                )
        );
        outOfOffice.print();
        System.out.println();

        System.out.println("=== 삭제 전 ===");
        outOfOffice.print();
        System.out.println();

        outOfOffice.delete(true);
        System.out.println("=== 삭제 후 수정 시도 ===");

        try {
            outOfOffices.get(0).validateAndUpdate(
                    new UpdateOutOfOffice(
                            "NoDisturbance Delete Success",
                            ZonedDateTime.now(),
                            ZonedDateTime.now().plusHours(1)
                    )
            );
        } catch (RuntimeException e) {
            System.out.println("!!! 오류 발생, 다음 코드 진행 !!!");
            System.out.println();
        }

        System.out.println("=== 전체 일정 재출력 ===");
        schedule.printAll();
        System.out.println();
        schedule.resetSchedule();


        System.out.println("==========ToDo CRUD==========");
        System.out.println("--- CSV READER Origin DATA ---");
        schedule.printAll();
        System.out.println();

        System.out.println("=== 수정 전 ===");
        Todo toDo = toDos.get(0);
        toDo.print();
        System.out.println();

        System.out.println("=== 수정 후 ===");
        toDos.get(0).validateAndUpdate(
                new UpdateTodo(
                        "ToDo Update Success",
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusHours(1),
                        "ToDo Update Description"
                )
        );
        toDo.print();
        System.out.println();

        System.out.println("=== 삭제 전 ===");
        toDo.print();
        System.out.println();

        toDo.delete(true);
        System.out.println("=== 삭제 후 수정 시도 ===");

        try {
            outOfOffices.get(0).validateAndUpdate(
                    new UpdateTodo(
                            "ToDo Delete Success",
                            ZonedDateTime.now(),
                            ZonedDateTime.now().plusHours(1),
                            "ToDo Delete Success1"
                    )
            );
        } catch (RuntimeException e) {
            System.out.println("!!! 오류 발생, 다음 코드 진행 !!!");
            System.out.println();
        }

        System.out.println("=== 전체 일정 재출력 ===");
        schedule.printAll();
        System.out.println();
        schedule.resetSchedule();

    }
}
