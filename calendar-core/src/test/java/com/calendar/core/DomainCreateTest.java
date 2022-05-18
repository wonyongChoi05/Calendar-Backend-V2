package com.calendar.core;

import com.calendar.core.domain.ScheduleType;
import com.calendar.core.domain.entity.Schedule;
import com.calendar.core.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainCreateTest {

    @Test
    void eventCreate() {
        final User me = new User("meme", "email", "pw", LocalDate.now());
        final Schedule taskSchedule = Schedule.task("할일", "개발하기", LocalDateTime.now(), me);
        assertEquals(taskSchedule.getScheduleType(), ScheduleType.TASK);
        assertEquals(taskSchedule.toTask().getTitle(), "할일");
    }

}
