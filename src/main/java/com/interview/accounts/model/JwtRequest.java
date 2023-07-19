package com.interview.accounts.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@EqualsAndHashCode
public class JwtRequest {
    @NotBlank(message = "userName can not be blank")
    private String userName;
    @NotBlank(message = "password can not be blank")
    private String password;
}
