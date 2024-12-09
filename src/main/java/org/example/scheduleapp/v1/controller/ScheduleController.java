package org.example.scheduleapp.v1.controller;

import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.v1.dto.ScheduleRequestDto;
import org.example.scheduleapp.v1.dto.ScheduleResponseDto;
import org.example.scheduleapp.v1.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return ResponseEntity.ok(scheduleService.createSchedule(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate modifiedAt,
            @RequestParam(required = false) String authorName) {
        List<Schedule> schedules = scheduleService.getAllSchedules(modifiedAt, authorName);
        return ResponseEntity.ok(schedules.stream().map(ScheduleResponseDto::from).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable String id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(ScheduleResponseDto.from(schedule));
    }
}