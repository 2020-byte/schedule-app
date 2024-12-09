package org.example.scheduleapp.v3.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapp.entity.Author;
import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.v3.dto.AuthorDto;
import org.example.scheduleapp.v3.dto.PatchAuthorEmailDto;
import org.example.scheduleapp.v3.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorDto updateEmail(PatchAuthorEmailDto request) {
        Author author = authorRepository.findByNameAndPassword(request.getAuthorName(), request.getPassword())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        author.setEmail(request.getEmail());
        author.setModifiedAt(LocalDateTime.now());

        Author updatedAuthor = authorRepository.update(author);
        return AuthorDto.from(updatedAuthor);
    }

    public List<Schedule> getSchedulesByAuthorId(String authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }
        return authorRepository.findSchedulesByAuthorId(authorId);
    }
}
