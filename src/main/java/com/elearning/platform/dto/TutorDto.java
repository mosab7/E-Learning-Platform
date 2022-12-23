package com.elearning.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorDto {
    private String name;
    private String surname;
    private String email;
    private String description;
    private String imgUrl;
}
