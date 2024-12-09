package org.example.scheduleapp.v4.controller;

import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.v4.dto.ScheduleResponseDto;
import org.example.scheduleapp.v4.service.ScheduleService;
import org.example.scheduleapp.v4.common.Paging;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController("v4ScheduleController")
@RequestMapping("/api/v4/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate modifiedAt,
            @RequestParam(required = false) String authorName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Paging paging = new Paging(page, size);
        List<Schedule> schedules = scheduleService.getAllSchedules(modifiedAt, authorName, paging);
        return ResponseEntity.ok(schedules.stream().map(ScheduleResponseDto::from).toList());
    }


}