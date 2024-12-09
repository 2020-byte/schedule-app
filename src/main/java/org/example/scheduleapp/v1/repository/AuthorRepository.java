package org.example.scheduleapp.v1.repository;

import org.example.scheduleapp.entity.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthorRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Author> rowMapper = (rs, rowNum) -> {
        Author author = new Author();
        author.setId(rs.getString("id"));
        author.setName(rs.getString("name"));
        author.setPassword(rs.getString("password"));
        author.setEmail(rs.getString("email"));
        author.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        author.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
        return author;
    };

    public AuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Author> findByNameAndPassword(String name, String password) {
        String sql = "SELECT * FROM author WHERE name = ? AND password = ?";
        var results = jdbcTemplate.query(sql, rowMapper, name, password);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Author save(Author author) {
        String sql = "INSERT INTO author (id, name, password, email, created_at, modified_at) VALUES (?, ?, ?, ?, ?, ?)";
        author.setId(UUID.randomUUID().toString());
        author.setCreatedAt(LocalDateTime.now());
        author.setModifiedAt(LocalDateTime.now());

        jdbcTemplate.update(sql,
                author.getId(),
                author.getName(),
                author.getPassword(),
                author.getEmail(),
                author.getCreatedAt(),
                author.getModifiedAt()
        );

        return author;
    }
}