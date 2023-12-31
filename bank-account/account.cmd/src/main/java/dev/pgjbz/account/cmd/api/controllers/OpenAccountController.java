package dev.pgjbz.account.cmd.api.controllers;

import dev.pgjbz.account.cmd.api.commands.OpenAccountCommand;
import dev.pgjbz.account.cmd.dto.request.OpenAccountRequest;
import dev.pgjbz.account.cmd.dto.response.OpenAccountResponse;
import dev.pgjbz.account.common.dto.BaseResponse;
import dev.pgjbz.cqrs.core.infrastructure.CommandDispatcher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/openBankAccount")
public class OpenAccountController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openBankAccount(
            @Valid @RequestBody final OpenAccountRequest openAccountRequest
            ) {
        final var openAccountCommand = new OpenAccountCommand(openAccountRequest);
        commandDispatcher.send(openAccountCommand);
        final var openAccountResponse = new OpenAccountResponse("Bank account creation request completed successfully!",
                openAccountCommand.id());
        return ResponseEntity.ok(openAccountResponse);
    }
}
