package com.calendar.core.domain.entity;

import com.calendar.core.domain.Event;
import com.calendar.core.domain.Notification;
import com.calendar.core.domain.ScheduleType;
import com.calendar.core.domain.Task;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "schedules")
@Entity
public class Schedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public static Schedule event(String title,
                                 String description, LocalDateTime startAt,
                                 LocalDateTime endAt, User writer) {
        return Schedule.builder()
                .startAt(startAt)
                .endAt(endAt)
                .title(title)
                .description(description)
                .writer(writer)
                .scheduleType(ScheduleType.EVENT)
                .build();
    }

    public static Schedule task(String title, String description, LocalDateTime taskAt,
                                User writer) {
        return Schedule.builder()
                .startAt(taskAt)
                .title(title)
                .description(description)
                .writer(writer)
                .scheduleType(ScheduleType.TASK)
                .build();
    }

    public static Schedule notification(String title, LocalDateTime notifyAt, User writer) {
        return Schedule.builder()
                .startAt(notifyAt)
                .title(title)
                .writer(writer)
                .scheduleType(ScheduleType.NOTIFICATION)
                .build();
    }

    public Task toTask() {
        return new Task(this);
    }

    public Event toEvent() {
        return new Event(this);
    }

    public Notification toNotification() {
        return new Notification(this);
    }

}
