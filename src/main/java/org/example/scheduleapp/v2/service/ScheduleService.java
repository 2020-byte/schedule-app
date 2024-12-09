package org.example.scheduleapp.v2.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.v2.dto.PatchScheduleDto;
import org.example.scheduleapp.v2.dto.ScheduleDto;
import org.example.scheduleapp.v2.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service("v2ScheduleService")
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleDto updateSchedule(String scheduleId, PatchScheduleDto request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        if (!scheduleRepository.validatePassword(schedule.getAuthorId(), request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        Schedule updateSchedule = request.toEntity(scheduleId, schedule.getAuthorId());
        Schedule savedSchedule = scheduleRepository.update(updateSchedule);
        return ScheduleDto.from(savedSchedule);
    }

    public void deleteSchedule(String scheduleId, String password) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        if (!scheduleRepository.validatePassword(schedule.getAuthorId(), password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        scheduleRepository.delete(scheduleId);
    }
}