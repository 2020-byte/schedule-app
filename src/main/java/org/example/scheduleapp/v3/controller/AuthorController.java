package org.example.scheduleapp.v3.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.v3.dto.AuthorDto;
import org.example.scheduleapp.v3.dto.PatchAuthorEmailDto;
import org.example.scheduleapp.v3.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v3/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PatchMapping("/email")
    public ResponseEntity<AuthorDto> updateEmail(@RequestBody PatchAuthorEmailDto request) {
        return ResponseEntity.ok(authorService.updateEmail(request));
    }

    @GetMapping("/{authorId}/schedules")
    public ResponseEntity<List<Schedule>> getSchedulesByAuthorId(@PathVariable("authorId") String authorId) {
        return ResponseEntity.ok(authorService.getSchedulesByAuthorId(authorId));
    }
}