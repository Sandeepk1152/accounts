package com.interview.accounts.domain;

import lombok.*;

import javax.persistence.*;

@Table(name = "accounts")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name="account_generator", sequenceName = "account_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    @Column(name = "number")
    private int number;
    @Column(name = "name")
    private String name;
    @Column(name = "balance")
    private double balance;

}
