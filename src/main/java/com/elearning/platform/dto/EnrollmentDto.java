package com.elearning.platform.dto;

import com.elearning.platform.auth.User;
import com.elearning.platform.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnrollmentDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private User user;
    private Course course;
}
