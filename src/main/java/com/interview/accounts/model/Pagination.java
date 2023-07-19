package com.interview.accounts.model;

import lombok.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {
    int page;
    int size;
}
