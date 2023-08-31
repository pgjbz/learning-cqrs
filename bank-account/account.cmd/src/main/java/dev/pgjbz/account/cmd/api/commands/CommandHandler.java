package dev.pgjbz.account.cmd.api.commands;

public interface CommandHandler {
    void handle(final OpenAccountCommand command);
    void handle(final DepositFundsCommand command);
    void handle(final WithdrawFundsCommand command);
    void handle(final CloseAccountCommand command);
}
