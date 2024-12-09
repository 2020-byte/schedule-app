package org.example.scheduleapp.v5.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.v5.dto.PatchScheduleDto;
import org.example.scheduleapp.v5.dto.ScheduleDto;
import org.example.scheduleapp.v5.exception.InvalidPasswordException;
import org.example.scheduleapp.v5.exception.ScheduleNotFoundException;
import org.example.scheduleapp.v5.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("v5ScheduleService")
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleDto updateSchedule(String scheduleId, PatchScheduleDto request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException(scheduleId));

        if (!scheduleRepository.validatePassword(schedule.getAuthorId(), request.getPassword())) {
            throw new InvalidPasswordException();
        }

        Schedule updateSchedule = request.toEntity(scheduleId, schedule.getAuthorId());
        Schedule savedSchedule = scheduleRepository.update(updateSchedule);
        return ScheduleDto.from(savedSchedule);
    }

    public void deleteSchedule(String scheduleId, String password) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException(scheduleId));

        if (!scheduleRepository.validatePassword(schedule.getAuthorId(), password)) {
            throw new InvalidPasswordException();
        }

        scheduleRepository.delete(scheduleId);
    }

    public Schedule getScheduleById(String id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));
    }
}