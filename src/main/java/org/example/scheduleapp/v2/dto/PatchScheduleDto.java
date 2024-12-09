package org.example.scheduleapp.v2.dto;

import lombok.Data;
import org.example.scheduleapp.entity.Schedule;
import java.time.LocalDateTime;

@Data
public class PatchScheduleDto {
    private String task;
    private String authorName;
    private String password;

    public Schedule toEntity(String id, String authorId) {
        Schedule schedule = new Schedule();
        schedule.setId(id);
        schedule.setTask(this.task);
        schedule.setAuthorId(authorId);
        schedule.setAuthorName(this.authorName);
        schedule.setModifiedAt(LocalDateTime.now());
        return schedule;
    }
}