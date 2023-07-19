package com.interview.accounts.controller;

import com.interview.accounts.domain.Account;
import com.interview.accounts.model.AccountDTO;
import com.interview.accounts.model.AccountRequestDTO;
import com.interview.accounts.model.GetAccountsResponseBody;
import com.interview.accounts.model.Pagination;
import com.interview.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@RestController
@RequestMapping("/accounts")
@Validated
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<GetAccountsResponseBody> getAccounts() {
        return ResponseEntity.ok(accountService.getAccounts(null));
    }

    @GetMapping("/findPaginated")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseEntity<GetAccountsResponseBody> findPaginated(@RequestParam @Valid @Min(value = 1,message = "Page number can not be zero") int page,
                                                                 @RequestParam @Valid @Min(value = 1,message = "size can not be zero") int size) {
        return ResponseEntity.ok(accountService.getAccounts(Pagination.builder().page(page).size(size).build()));
    }

    @GetMapping("/filter")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseEntity<GetAccountsResponseBody> filter(@RequestParam @Valid @NotBlank(message = "queryParameter can not be blank or null") String queryParameter) {
        return ResponseEntity.ok(accountService.filter(queryParameter));
    }

    @PutMapping
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseEntity<GetAccountsResponseBody> update( @RequestBody AccountRequestDTO accountRequestDTO){
        return ResponseEntity.ok(accountService.update(accountRequestDTO));
    }

    @PostMapping
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseEntity<Account> save(@RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.save(accountDTO));
    }

}
