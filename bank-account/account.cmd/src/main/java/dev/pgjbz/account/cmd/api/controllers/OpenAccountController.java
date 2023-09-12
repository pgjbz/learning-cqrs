package dev.pgjbz.account.cmd.api.controllers;

import dev.pgjbz.account.cmd.api.commands.OpenAccountCommand;
import dev.pgjbz.account.cmd.dto.OpenAccountRequest;
import dev.pgjbz.account.cmd.dto.OpenAccountResponse;
import dev.pgjbz.account.common.dto.BaseResponse;
import dev.pgjbz.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/openBankAccount")
@RequiredArgsConstructor
public class OpenAccountController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openBankAccount(
            @RequestBody final OpenAccountRequest openAccountRequest
            ) {
        final var openAccountCommand = new OpenAccountCommand(openAccountRequest);
        commandDispatcher.send(openAccountCommand);
        final var openAccountResponse = new OpenAccountResponse("Bank account creation request completed successfully!",
                openAccountCommand.id());
        return ResponseEntity.ok(openAccountResponse);
    }
}
