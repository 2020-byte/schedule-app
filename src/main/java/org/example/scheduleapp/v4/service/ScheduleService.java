package org.example.scheduleapp.v4.service;

import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.v4.repository.ScheduleRepository;
import org.example.scheduleapp.v4.common.Paging;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("v4ScheduleService")
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    public List<Schedule> getAllSchedules(LocalDate modifiedAt, String authorName, Paging paging) {
        return scheduleRepository.findAll(modifiedAt, authorName, paging);
    }

}