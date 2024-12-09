package org.example.scheduleapp.v5.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.v1.dto.ScheduleResponseDto;
import org.example.scheduleapp.v5.dto.DeleteScheduleDto;
import org.example.scheduleapp.v5.dto.PatchScheduleDto;
import org.example.scheduleapp.v5.dto.ScheduleDto;
import org.example.scheduleapp.v5.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("v5ScheduleController")
@RequestMapping("/api/v5/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDto> updateSchedule(
            @PathVariable String scheduleId,
            @RequestBody PatchScheduleDto request) {
        return ResponseEntity.ok(
                scheduleService.updateSchedule(scheduleId, request)
        );
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable String scheduleId,
            @RequestBody DeleteScheduleDto request) {
        scheduleService.deleteSchedule(scheduleId, request.getPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable String id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(ScheduleResponseDto.from(schedule));
    }
}

