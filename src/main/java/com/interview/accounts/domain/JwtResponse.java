package com.interview.accounts.domain;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class JwtResponse {
    private String jwtToken;
    private String username;
}
