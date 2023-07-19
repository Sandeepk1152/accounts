package com.interview.accounts.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class CustomError {
    private HttpStatus status;
    private String message;
    //private Set<ConstraintViolation<?>> constraintViolations;

}
