package dev.pgjbz.account.cmd.api.controllers;

import dev.pgjbz.account.cmd.api.commands.DepositFundsCommand;
import dev.pgjbz.account.cmd.dto.DepositFundsRequest;
import dev.pgjbz.account.cmd.dto.DepositFundsResponse;
import dev.pgjbz.account.common.dto.BaseResponse;
import dev.pgjbz.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/depositFunds")
@RequiredArgsConstructor
public class DepositFundsController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping(value = "/{id}")

    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") final String id,
                                                     @RequestBody final DepositFundsRequest depositFundsRequest) {
        final DepositFundsCommand depositFundsCommand = new DepositFundsCommand(id, depositFundsRequest.amount());
        commandDispatcher.send(depositFundsCommand);
        final var openAccountResponse = new DepositFundsResponse("Deposit funds request completed successfully!");
        return ResponseEntity.ok(openAccountResponse);
    }
}

