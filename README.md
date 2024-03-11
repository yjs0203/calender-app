
# 일정관리 프로그램

- 구글 캘린더 등 일정을 관리할 수 있는 서비스입니다.
-  Java 기반으로 작성되었으며 콘솔에서 결과를 확인할 수 있습니다.

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

# 소개. Calander Service

- 캘린더 서비스를 제공합니다.
- 사용자는 회의, 방해금지, 자리비움, 할 일 4가지 이벤트를 작성할 수 있습니다.
- 작성한 파일들은 화면으로 출력됩니다.
- 수정, 삭제 가능합니다.

# 환경 소개

- JAVA 17
- SpringBoot 3.2.3
- OS : Mac Os
- 개발환경 : Intellij IDEA
- 저장소 : Github

# 예외처리

- 등록한 이벤트와 중복된 시간은 중복처리로 인해 등록이 안됩니다.
- 등록된 이벤트를 삭제 후 해당 이벤트를 수정할 시 오류메시지 출력합니다.

# 이미지

![alternative message](url) 형식으로 작성
예
![Linux mascot](/assets/images/linux.png)
