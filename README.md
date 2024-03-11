
# 일정관리 프로그램

- 구글 캘린더 등 일정을 관리할 수 있는 서비스입니다.
  - 캘린더 서비스를 제공합니다.
  - 사용자는 회의, 방해금지, 자리비움, 할 일 4가지 이벤트를 작성할 수 있습니다.
  - 작성한 파일들은 화면으로 출력됩니다.
  - 수정, 삭제 가능합니다.<br>
-  Java 기반으로 작성되었으며 콘솔에서 결과를 확인할 수 있습니다.

# 개발 환경

- JAVA 17
- SpringBoot 3.2.3
- OS : Mac Os
- 개발환경 : Intellij IDEA
- 저장소 : Github

# 요구사항

### 1.일정의 유형
- "미팅", "방해 금지 시간", "할 일","외출"에 대한 일정을 관리 할 수 있다.
    - 미팅:`meeting`
    - 방해 금지 시간:`no-disturbance`
    - 외출:`out-of-office`
    - 할 일:`to-do`

### 2.등록 (Create)
- 일정을 등록할 수 있다.
    - 겹치는 일정이 존재하면 일정을 등록할 수 없다.
- 일정을 `.csv`파일로 대량 등록할 수 있다.

### 3.검색/조회 (Read)
- 일정을 수정할 수 있다.
- 삭제된 일정은 수정할 수 없다.

### 4.삭제 (Delete)
- 일정을 삭제할 수 있다.
- Soft-delete 로 처리한다.  


# 시스템 디자인 및 설계

### 도메인
```java
import java.time.ZonedDateTime;

public enum EventType {
    MEETING,
    TO_DO,
    NO_DISTURBANCE,
    OUT_OF_OFFICE,
    ;
   }
public abstract class AbstractEvent implements Event {
    private in id;
    private String title;
    private ZonedDateTime startAt;
    private ZonedDateTime endAt;

    private boolean deletedYn;

    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    protected abstract void update(); // 각 세부 이벤트 타입에서 필요한 수정을 하기 위함
}

public class Meeting extends AbstractEvent{
    private String description;
    private Set<String> participants;
}

public class Todo extends AbstractEvent{
    
}

public class OutOfOffice extends AbstractEvent {
    
}

public class NoDisturbance extends AbstractEvent{
    
}
```
# 여러 Event 를 관리하는 객체는 Schedule

### CSV 읽기
- open-csv
- https://www.baeldung.com/opencsv

```java
public class EventCsvReader {

    private final RawCsvReader rawCsvReader;

    public EventCsvReader(RawCsvReader rawCsvReader) {
        this.rawCsvReader = rawCsvReader;
    }

    public List<Meeting> readMeetings(String path) throws IOException {
        List<Meeting> result = new ArrayList<>();

        // 데이터를 읽는 부분
        List<String[]> read = rawCsvReader.readAll(path);
        for (int i = 0; i < read.size(); i++) {
            if (skipHeader(i)) {
                continue;
            }

            String[] each = read.get(i);

            // Meeting 으로 변환 부분
            result.add(
                    new Meeting(
                            Integer.parseInt(each[0]),
                            each[2],
                            ZonedDateTime.of(
                                    LocalDateTime.parse(
                                            each[6],
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                    ),

                                    ZoneId.of("Asia/Seoul")
                            ),
                            ZonedDateTime.of(
                                    LocalDateTime.parse(
                                            each[7],
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                    ),

                                    ZoneId.of("Asia/Seoul")
                            ),
                            new HashSet<>(Arrays.asList(each[3].split(","))),
                            each[4],
                            each[5]

                    )
            );
        }

        return result;
    }
    
    private static boolean skipHeader(int i) {
        return i == 0;
    }
    
}

public class RawCsvReader {
  public List<String[]> readAll(String path) throws IOException {
    InputStream in = getClass().getResourceAsStream(path);
    InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);

    CSVReader csvReader = new CSVReader(reader);
    return csvReader.readAll();
  }
}
```


# 예외처리

- 등록한 이벤트와 중복된 시간은 중복처리로 인해 등록이 안됩니다.
```java

    private boolean hasScheduleConflictWith(AbstractEvent event) {
      return events.stream()
              .anyMatch(each ->
                      (event.getStartAt().isBefore(each.getEndAt()) && event.getStartAt().isAfter(each.getStartAt()))
                              || (event.getEndAt().isAfter(each.getStartAt())) && event.getEndAt().isBefore(each.getEndAt())
                              || event.getStartAt().equals(each.getStartAt()) && event.getEndAt().equals(each.getEndAt()));
    }

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

```
![Exception1](https://github.com/KeeHeung/calander-todo-list/blob/main/src/main/resources/image/sameTimeException.png?raw=true)

- 등록된 이벤트를 삭제 후 해당 이벤트를 수정할 시 오류메시지 출력합니다.
```java
    public void validateAndUpdate(AbstractAuditableEvent update) {
        if (deleteYn == true) {
            throw new RuntimeException("이미 삭제된 이벤트는 수정할 수 없음");
        }

        defaultUpdate(update);
        update(update);
    }
```
![Exception2](https://github.com/KeeHeung/calander-todo-list/blob/main/src/main/resources/image/updateException.png?raw=true)

# 출력 결과

![Console1](https://github.com/KeeHeung/calander-todo-list/blob/main/src/main/resources/image/consoleSuccess.png?raw=true)
