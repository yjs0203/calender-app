package com.example.calandertodolist.reader;

import com.example.calandertodolist.event.Meeting;
import com.example.calandertodolist.event.NoDisturbance;
import com.example.calandertodolist.event.OutOfOffice;
import com.example.calandertodolist.event.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventCsvReaderTest {

    private final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    @Mock
    private RawCsvReader rawCsvReader;

    @InjectMocks
    private EventCsvReader sut;

    @Test
    public void readerMeeting() throws IOException {
        // given
        String path = "";
//        EventCsvReader sut = new EventCsvReader(rawCsvReader);

        List<String[]> mockData = new ArrayList<>();
        mockData.add(new String[8]);

        int mockSize = 5;
        for (int i = 0; i < mockSize; i++) {
            mockData.add(meetingMock(i));
        }

        when(rawCsvReader.readAll(path)).thenReturn(mockData);

        // when
        List<Meeting> meetings = sut.readMeetings(path);

        // then
        assertEquals(mockSize, meetings.size());
        assertEquals("title0", meetings.get(0).getTitle());
    }

    @Test
    public void readerNoDisturbance() throws IOException {
        // given
        String path = "";

        List<String[]> mockData = new ArrayList<>();
        mockData.add(new String[5]);

        int mockSize = 5;
        for (int i = 0; i < mockSize; i++) {
            mockData.add(noDisturbanceMock(i));
        }

        when(rawCsvReader.readAll(path)).thenReturn(mockData);

        // when
        List<NoDisturbance> noDisturbances = sut.readNoDisturbance(path);

        // then
        assertEquals(mockSize, noDisturbances.size());
        assertEquals("title0", noDisturbances.get(0).getTitle());
    }

    @Test
    public void readerOutOfOffice() throws IOException {
        // given
        String path = "";

        List<String[]> mockData = new ArrayList<>();
        mockData.add(new String[5]);

        int mockSize = 5;
        for (int i = 0; i < mockSize; i++) {
            mockData.add(outOfOfficeMock(i));
        }

        when(rawCsvReader.readAll(path)).thenReturn(mockData);

        // when
        List<OutOfOffice> outOfOffices = sut.readOutOfOffice(path);

        // then
        assertEquals(mockSize, outOfOffices.size());
        assertEquals("title0", outOfOffices.get(0).getTitle());
    }

    @Test
    public void readerToDo() throws IOException {
        // given
        String path = "";

        List<String[]> mockData = new ArrayList<>();
        mockData.add(new String[6]);

        int mockSize = 5;
        for (int i = 0; i < mockSize; i++) {
            mockData.add(toDoMock(i));
        }

        when(rawCsvReader.readAll(path)).thenReturn(mockData);

        // when
        List<Todo> toDo = sut.readToDo(path);

        // then
        assertEquals(mockSize, toDo.size());
        assertEquals("title0", toDo.get(0).getTitle());
    }

    private String[] meetingMock(int id) {
        String[] mock = new String[8];
        mock[0] = String.valueOf(id);
        mock[1] = "MEETING"+id;
        mock[2] = "title"+id;
        mock[3] = "A,B,C"+id;
        mock[4] = "A1"+id;
        mock[5] = "test"+id;
        mock[6] = of(ZonedDateTime.now().plusHours(id));
        mock[7] = of(ZonedDateTime.now().plusHours(id+1));

        return  mock;
    }

    private String[] noDisturbanceMock(int id) {
        String[] mock = new String[5];
        mock[0] = String.valueOf(id);
        mock[1] = "NO_DISTURBANCE"+id;
        mock[2] = "title"+id;
        mock[3] = of(ZonedDateTime.now().plusHours(id));
        mock[4] = of(ZonedDateTime.now().plusHours(id+1));

        return  mock;
    }

    private String[] outOfOfficeMock(int id) {
        String[] mock = new String[5];
        mock[0] = String.valueOf(id);
        mock[1] = "OUT_OF_OFFICE"+id;
        mock[2] = "title"+id;
        mock[3] = of(ZonedDateTime.now().plusHours(id));
        mock[4] = of(ZonedDateTime.now().plusHours(id+1));

        return  mock;
    }

    private String[] toDoMock(int id) {
        String[] mock = new String[6];
        mock[0] = String.valueOf(id);
        mock[1] = "TO_DO"+id;
        mock[2] = "title"+id;
        mock[3] = "description"+id;
        mock[4] = of(ZonedDateTime.now().plusHours(id));
        mock[5] = of(ZonedDateTime.now().plusHours(id+1));

        return  mock;
    }

    private static String of(ZonedDateTime dataTime) {
        return dataTime.format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
    }
}