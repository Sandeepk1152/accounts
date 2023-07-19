package com.interview.accounts.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class AccountRequestDTO {
    @Positive( message = "number should be valid value")
    @NotNull(message = "id can not be null")
    private Integer id;

    @Positive( message = "number should be valid value")
    @NotNull(message = "id can not be null")
    private Integer number;

    @NotBlank(message = "name should not be empty or null")
    private String name;

    private double balance;
}
