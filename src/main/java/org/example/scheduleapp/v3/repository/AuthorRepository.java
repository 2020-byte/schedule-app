package org.example.scheduleapp.v3.repository;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapp.entity.Author;
import org.example.scheduleapp.entity.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("v3AuthorRepository")
@RequiredArgsConstructor
public class AuthorRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Author> authorRowMapper = (rs, rowNum) -> {
        Author author = new Author();
        author.setId(rs.getString("id"));
        author.setName(rs.getString("name"));
        author.setEmail(rs.getString("email"));
        author.setPassword(rs.getString("password"));
        author.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        author.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
        return author;
    };

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

    public Optional<Author> findByNameAndPassword(String name, String password) {
        String sql = """
           SELECT * FROM author 
           WHERE name = ? AND password = ?
           """;

        try {
            Author author = jdbcTemplate.queryForObject(sql, authorRowMapper, name, password);
            return Optional.ofNullable(author);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Author update(Author author) {
        String sql = """
           UPDATE author 
           SET email = ?, modified_at = ? 
           WHERE id = ?
           """;

        jdbcTemplate.update(sql,
                author.getEmail(),
                author.getModifiedAt(),
                author.getId()
        );

        return findById(author.getId()).orElseThrow();
    }

    public Optional<Author> findById(String id) {
        String sql = "SELECT * FROM author WHERE id = ?";

        try {
            Author author = jdbcTemplate.queryForObject(sql, authorRowMapper, id);
            return Optional.ofNullable(author);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM author WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public List<Schedule> findSchedulesByAuthorId(String authorId) {
        String sql = """
           SELECT s.*, a.name as author_name 
           FROM schedule s 
           JOIN author a ON s.author_id = a.id 
           WHERE s.author_id = ? 
           ORDER BY s.created_at DESC
           """;

        return jdbcTemplate.query(sql, scheduleRowMapper, authorId);
    }
}