package org.example.scheduleapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private String id;
    private String task;
    private String authorId;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

