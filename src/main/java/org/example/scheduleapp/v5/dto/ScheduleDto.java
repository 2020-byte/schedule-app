package org.example.scheduleapp.v5.dto;

import lombok.Data;
import org.example.scheduleapp.entity.Schedule;

import java.time.LocalDateTime;

@Data
public class ScheduleDto {
    private String id;
    private String task;
    private String authorId;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ScheduleDto from(Schedule schedule) {
        ScheduleDto dto = new ScheduleDto();
        dto.setId(schedule.getId());
        dto.setTask(schedule.getTask());
        dto.setAuthorId(schedule.getAuthorId());
        dto.setAuthorName(schedule.getAuthorName());
        dto.setCreatedAt(schedule.getCreatedAt());
        dto.setModifiedAt(schedule.getModifiedAt());
        return dto;
    }
}
