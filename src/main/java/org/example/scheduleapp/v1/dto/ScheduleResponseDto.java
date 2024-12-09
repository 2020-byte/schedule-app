package org.example.scheduleapp.v1.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.scheduleapp.entity.Schedule;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleResponseDto {
    private String id;
    private String task;
    private String authorId;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ScheduleResponseDto from(Schedule schedule) {
        ScheduleResponseDto dto = new ScheduleResponseDto();
        dto.setId(schedule.getId());
        dto.setTask(schedule.getTask());
        dto.setAuthorId(schedule.getAuthorId());
        dto.setAuthorName(schedule.getAuthorName());
        dto.setCreatedAt(schedule.getCreatedAt());
        dto.setModifiedAt(schedule.getModifiedAt());
        return dto;
    }
}

