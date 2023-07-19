package com.interview.accounts.service;

import com.interview.accounts.config.JwtAuthenticationFilter;
import com.interview.accounts.customException.BeanNotFoundException;
import com.interview.accounts.domain.Account;
import com.interview.accounts.mapper.AccountsMapper;
import com.interview.accounts.model.AccountDTO;
import com.interview.accounts.model.AccountRequestDTO;
import com.interview.accounts.model.GetAccountsResponseBody;
import com.interview.accounts.model.Pagination;
import com.interview.accounts.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class AccountService {

    @Autowired
    private final AccountRepository repository;

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    public GetAccountsResponseBody getAccounts(Pagination pagination) {
        Iterable<Account> accountIterablble;
        if (pagination == null) {
            accountIterablble = repository.findAll();
        } else {
            accountIterablble = repository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize()));
        }
        List<Account> mutableList = StreamSupport
                .stream(accountIterablble.spliterator(), false)
                .collect(Collectors.toList());
        return new GetAccountsResponseBody(repository.count(), AccountsMapper.map(mutableList));
    }

    public GetAccountsResponseBody filter(String queryParameter) {
        int number = 0;
        try {
            number = Integer.parseInt(queryParameter);
        } catch (NumberFormatException e) {
            logger.info("can not pharse to number filter() AccountService{}");
        }

        Optional<Account> account = repository.findByNameOrAccount(queryParameter, number);
        List<Account> accountList = new ArrayList<>(1);
        account.ifPresent(accountList::add);
        return new GetAccountsResponseBody(account.isPresent() ? 0 : 1, AccountsMapper.map(accountList));
    }

    public GetAccountsResponseBody update(AccountRequestDTO accountRequestDTO) {
        Optional<Account> accountOptional = repository.findById(accountRequestDTO.getId());
        if (accountOptional.isPresent()) {
            logger.info("accountOptional Present update() AccountService{}");

            Account account = accountOptional.get();
            account.setBalance(accountRequestDTO.getBalance());
            account.setName(accountRequestDTO.getName());
            account.setNumber(accountRequestDTO.getNumber());

            return GetAccountsResponseBody.builder().accounts(AccountsMapper.map(Arrays.asList(repository.save(account)))).total(1).build();
        } else {
            throw new BeanNotFoundException("account does not exists for this id");

        }

    }

    public Account save(AccountDTO accountDTO) {
        if (accountDTO != null) {
            return repository.save(Account.builder().number(accountDTO.getNumber()).name(accountDTO.getName()).balance(accountDTO.getBalance()).build());
        } else {
            logger.info("invalid input save() AccountService{}");
            throw new ConstraintViolationException("inputs are not valid", null);
        }
    }


}
