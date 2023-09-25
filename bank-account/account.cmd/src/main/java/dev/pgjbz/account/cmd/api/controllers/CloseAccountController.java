package dev.pgjbz.account.cmd.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pgjbz.account.cmd.api.commands.CloseAccountCommand;
import dev.pgjbz.account.cmd.dto.CloseAccountResponse;
import dev.pgjbz.account.common.dto.BaseResponse;
import dev.pgjbz.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/closeAccount")
public class CloseAccountController {
    
    private final CommandDispatcher commandDispatcher;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable final String id) {
        final CloseAccountCommand closeAccountCommand = new CloseAccountCommand(id);
        commandDispatcher.send(closeAccountCommand);
        return ResponseEntity.ok(new CloseAccountResponse("Close account request completed successfully!"));
    }
}
