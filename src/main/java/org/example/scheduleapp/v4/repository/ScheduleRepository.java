package org.example.scheduleapp.v4.repository;

import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.v4.common.Paging;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Repository("v4ScheduleRepository")
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


    public List<Schedule> findAll(LocalDate modifiedAt, String authorName, Paging paging) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (modifiedAt != null) {
            sql.append(" AND DATE(modified_at) = DATE(?)");
            params.add(modifiedAt);
        }
        if (authorName != null && !authorName.isEmpty()) {
            sql.append(" AND author_name = ?");
            params.add(authorName);
        }

        sql.append(" ORDER BY modified_at DESC");
        sql.append(" LIMIT ? OFFSET ?");
        params.add(paging.getSize());
        params.add(paging.getOffset());

        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }


}