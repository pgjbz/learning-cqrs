package dev.pgjbz.account.query.api.controllers;

import dev.pgjbz.account.common.dto.BaseResponse;
import dev.pgjbz.account.query.api.dto.AccountLookupResponse;
import dev.pgjbz.account.query.api.dto.EqualityType;
import dev.pgjbz.account.query.api.queries.FindAccountByHolderQuery;
import dev.pgjbz.account.query.api.queries.FindAccountByIdQuery;
import dev.pgjbz.account.query.api.queries.FindAccountWithBalanceQuery;
import dev.pgjbz.account.query.api.queries.FindAllAccountsQuery;
import dev.pgjbz.cqrs.core.domain.BaseEntity;
import dev.pgjbz.cqrs.core.infrastructure.QueryDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/bankAccountLookup")
public class AccountLookupController {

    private final QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<BaseResponse> findAllAccounts() {
        final List<BaseEntity> accounts = queryDispatcher.send(new FindAllAccountsQuery());
        return buildMultipleAccountResponse(accounts);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> findAccountById(@PathVariable(value = "id") final String id) {
        final List<BaseEntity> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
        return buildSingleAccountResponse(accounts);
    }

    @GetMapping(value = "/holder")
    public ResponseEntity<BaseResponse> findAccountByHolder(@RequestParam(value = "holder") final String holder) {
        final List<BaseEntity> accounts = queryDispatcher.send(new FindAccountByHolderQuery(holder));
        return buildSingleAccountResponse(accounts);
    }

    @GetMapping(value = "/balance")
    public ResponseEntity<BaseResponse> findByBalance(@RequestParam(value = "equalityType") final EqualityType equalityType,
                                                      @RequestParam(value = "balance") final BigDecimal balance) {
        final List<BaseEntity> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(equalityType, balance));
        return buildMultipleAccountResponse(accounts);
    }

    private static ResponseEntity<BaseResponse> buildSingleAccountResponse(final List<BaseEntity> accounts) {
        if (CollectionUtils.isEmpty(accounts))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(new AccountLookupResponse("Successfully returned bank account", accounts));
    }

    private static ResponseEntity<BaseResponse> buildMultipleAccountResponse(final List<BaseEntity> accounts) {
        if (CollectionUtils.isEmpty(accounts))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(new AccountLookupResponse("Successfully returned %s bank account(s)".formatted(accounts.size()), accounts));
    }

}
