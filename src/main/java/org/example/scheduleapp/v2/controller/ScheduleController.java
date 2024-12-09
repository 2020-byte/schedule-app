package org.example.scheduleapp.v2.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapp.v2.dto.PatchScheduleDto;
import org.example.scheduleapp.v2.dto.DeleteScheduleDto;
import org.example.scheduleapp.v2.dto.ScheduleDto;
import org.example.scheduleapp.v2.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("v2ScheduleController")
@RequestMapping("/api/v2/schedules")
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
}

