package org.example.scheduleapp.v3.dto;

import lombok.Data;
import org.example.scheduleapp.entity.Author;
import java.time.LocalDateTime;

@Data
public class AuthorDto {
    private String id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static AuthorDto from(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setEmail(author.getEmail());
        dto.setCreatedAt(author.getCreatedAt());
        dto.setModifiedAt(author.getModifiedAt());
        return dto;
    }
}
