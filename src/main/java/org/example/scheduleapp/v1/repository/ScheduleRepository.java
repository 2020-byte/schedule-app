package org.example.scheduleapp.v1.repository;

import org.example.scheduleapp.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.LocalDate;


@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Schedule> rowMapper = (rs, rowNum) -> {
        Schedule schedule = new Schedule();
        schedule.setId(rs.getString("id"));
        schedule.setTask(rs.getString("task"));
        schedule.setAuthorId(rs.getString("author_id"));
        schedule.setAuthorName(rs.getString("author_name"));
        schedule.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        schedule.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
        return schedule;
    };

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(Schedule schedule) {
        String sql = "INSERT INTO schedule (id, task, author_id, author_name, created_at, modified_at) VALUES (?, ?, ?, ?, ?, ?)";
        schedule.setId(UUID.randomUUID().toString());
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setModifiedAt(LocalDateTime.now());

        jdbcTemplate.update(sql,
                schedule.getId(),
                schedule.getTask(),
                schedule.getAuthorId(),
                schedule.getAuthorName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );

        return schedule;
    }

    public List<Schedule> findAll(LocalDate modifiedAt, String authorName) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        if (modifiedAt != null) {
            sql.append(" AND DATE(modified_at) = DATE(?)");
        }
        if (authorName != null && !authorName.isEmpty()) {
            sql.append(" AND author_name = ?");
        }

        sql.append(" ORDER BY modified_at DESC");

        if (modifiedAt != null && authorName != null && !authorName.isEmpty()) {
            return jdbcTemplate.query(sql.toString(), rowMapper, modifiedAt, authorName);
        } else if (modifiedAt != null) {
            return jdbcTemplate.query(sql.toString(), rowMapper, modifiedAt);
        } else if (authorName != null && !authorName.isEmpty()) {
            return jdbcTemplate.query(sql.toString(), rowMapper, authorName);
        }
        return jdbcTemplate.query(sql.toString(), rowMapper);
    }

    public Optional<Schedule> findById(String id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}