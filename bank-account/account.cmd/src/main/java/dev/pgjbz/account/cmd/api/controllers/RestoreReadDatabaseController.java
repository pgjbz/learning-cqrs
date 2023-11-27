package dev.pgjbz.account.cmd.api.controllers;

import dev.pgjbz.account.cmd.api.commands.RestoreDatabaseCommand;
import dev.pgjbz.account.cmd.dto.response.RestoreDatabaseResponse;
import dev.pgjbz.account.common.dto.BaseResponse;
import dev.pgjbz.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/restoreReadDatabase")
public class RestoreReadDatabaseController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restore() {
        final String restoreId = UUID.randomUUID().toString();
        commandDispatcher.send(new RestoreDatabaseCommand(restoreId));
        log.info("read database call with id {}", restoreId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestoreDatabaseResponse("Read database restore request successfully with restore id: %s".formatted(restoreId)));
    }


}
