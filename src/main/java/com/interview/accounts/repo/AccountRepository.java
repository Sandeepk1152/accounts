package com.interview.accounts.repo;

import com.interview.accounts.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE a.name = ?1 OR a.number = ?2")
    Optional<Account> findByNameOrAccount(String name, int number);
}
