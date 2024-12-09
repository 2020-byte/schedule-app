package org.example.scheduleapp.v5.repository;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapp.entity.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("v5ScheduleRepository")
@RequiredArgsConstructor
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Schedule> scheduleRowMapper = (rs, rowNum) -> {
        Schedule schedule = new Schedule();
        schedule.setId(rs.getString("id"));
        schedule.setTask(rs.getString("task"));
        schedule.setAuthorId(rs.getString("author_id"));
        schedule.setAuthorName(rs.getString("author_name"));
        schedule.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        schedule.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
        return schedule;
    };

    public Optional<Schedule> findById(String scheduleId) {
        String sql = """
            SELECT s.*, a.name as author_name 
            FROM schedule s 
            JOIN author a ON s.author_id = a.id 
            WHERE s.id = ?
            """;

        try {
            Schedule schedule = jdbcTemplate.queryForObject(sql, scheduleRowMapper, scheduleId);
            return Optional.ofNullable(schedule);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Schedule update(Schedule schedule) {
        String sql = """
            UPDATE schedule 
            SET task = ?, modified_at = ? 
            WHERE id = ?
            """;

        jdbcTemplate.update(sql,
                schedule.getTask(),
                schedule.getModifiedAt(),
                schedule.getId()
        );

        String authorSql = """
            UPDATE author 
            SET name = ? 
            WHERE id = ?
            """;

        jdbcTemplate.update(authorSql,
                schedule.getAuthorName(),
                schedule.getAuthorId()
        );

        return findById(schedule.getId()).orElseThrow();
    }

    public void delete(String scheduleId) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, scheduleId);
    }

    public boolean validatePassword(String authorId, String password) {
        String sql = "SELECT COUNT(*) FROM author WHERE id = ? AND password = ?";

        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, authorId, password);
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}