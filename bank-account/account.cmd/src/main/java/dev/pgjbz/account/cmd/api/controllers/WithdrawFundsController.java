package dev.pgjbz.account.cmd.api.controllers;

import dev.pgjbz.account.cmd.api.commands.WithdrawFundsCommand;
import dev.pgjbz.account.cmd.dto.request.WithdrawFundsRequest;
import dev.pgjbz.account.cmd.dto.response.WithdrawFundsResponse;
import dev.pgjbz.account.common.dto.BaseResponse;
import dev.pgjbz.cqrs.core.infrastructure.CommandDispatcher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/withdrawFunds")
public class WithdrawFundsController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> withDraw(@PathVariable(value = "id") final String id,
                                                 @Valid @RequestBody final WithdrawFundsRequest withdrawFundsRequest) {
        final WithdrawFundsCommand withdrawFundsCommand = new WithdrawFundsCommand(id, withdrawFundsRequest.amount());
        commandDispatcher.send(withdrawFundsCommand);
        final var openAccountResponse = new WithdrawFundsResponse("Deposit funds request completed successfully!");
        return ResponseEntity.ok(openAccountResponse);
    }
}
