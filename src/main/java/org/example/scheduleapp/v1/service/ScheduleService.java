package org.example.scheduleapp.v1.service;

import org.example.scheduleapp.entity.Author;
import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.v1.dto.ScheduleRequestDto;
import org.example.scheduleapp.v1.dto.ScheduleResponseDto;
import org.example.scheduleapp.v1.repository.AuthorRepository;
import org.example.scheduleapp.v1.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, AuthorRepository authorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.authorRepository = authorRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Author author = authorRepository.findByNameAndPassword(requestDto.getAuthorName(), requestDto.getPassword())
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(requestDto.getAuthorName());
                    newAuthor.setPassword(requestDto.getPassword());
                    return authorRepository.save(newAuthor);
                });

        Schedule schedule = new Schedule();
        schedule.setTask(requestDto.getTask());
        schedule.setAuthorName(author.getName());
        schedule.setAuthorId(author.getId());

        return ScheduleResponseDto.from(scheduleRepository.save(schedule));
    }

    public List<Schedule> getAllSchedules(LocalDate modifiedAt, String authorName) {
        return scheduleRepository.findAll(modifiedAt, authorName);
    }

    public Schedule getScheduleById(String id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }
}