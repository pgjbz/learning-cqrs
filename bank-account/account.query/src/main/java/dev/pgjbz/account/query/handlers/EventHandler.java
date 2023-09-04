package dev.pgjbz.account.query.handlers;

import dev.pgjbz.account.common.events.AccountClosedEvent;
import dev.pgjbz.account.common.events.AccountOpenedEvent;
import dev.pgjbz.account.common.events.FundsDepositedEvent;
import dev.pgjbz.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(final AccountOpenedEvent event);
    void on(final FundsDepositedEvent event);
    void on(final FundsWithdrawnEvent event);
    void on(final AccountClosedEvent event);

}
