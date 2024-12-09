package org.example.scheduleapp.v1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleRequestDto {
    private String task;
    private String authorName;
    private String password;
}
