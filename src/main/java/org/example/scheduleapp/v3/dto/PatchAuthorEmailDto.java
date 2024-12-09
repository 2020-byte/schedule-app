package org.example.scheduleapp.v3.dto;

import lombok.Data;

@Data
public class PatchAuthorEmailDto {
    private String authorName;
    private String password;
    private String email;
}