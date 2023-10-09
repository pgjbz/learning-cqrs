package dev.pgjbz.account.cmd.api.controllers;

import dev.pgjbz.account.cmd.api.commands.DepositFundsCommand;
import dev.pgjbz.account.cmd.dto.request.DepositFundsRequest;
import dev.pgjbz.account.cmd.dto.response.DepositFundsResponse;
import dev.pgjbz.account.common.dto.BaseResponse;
import dev.pgjbz.cqrs.core.infrastructure.CommandDispatcher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/depositFunds")
public class DepositFundsController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping(value = "/{id}")

    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") final String id,
                                                     @Valid @RequestBody final DepositFundsRequest depositFundsRequest) {
        final DepositFundsCommand depositFundsCommand = new DepositFundsCommand(id, depositFundsRequest.amount());
        commandDispatcher.send(depositFundsCommand);
        final var openAccountResponse = new DepositFundsResponse("Deposit funds request completed successfully!");
        return ResponseEntity.ok(openAccountResponse);
    }
}

