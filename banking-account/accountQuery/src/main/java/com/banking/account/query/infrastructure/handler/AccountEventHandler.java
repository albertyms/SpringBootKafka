package com.banking.account.query.infrastructure.handler;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawEvent;
import com.banking.account.query.domain.AccountRepository;
import com.banking.account.query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler {

    @Autowired
    private AccountRepository repository;

    @Override
    public void on(AccountOpenedEvent event) {

        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreateDate())
                .accountType(event.getAccountType())
                    .balance(event.getOpeningBalance())
                .build();

        repository.save(bankAccount);

    }

    @Override
    public void on(FundsDepositedEvent event) {

        var bankAccount = repository.findById(event.getId());

        if(bankAccount.isEmpty()) {
            return;
        }

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + event.getAmount();

        bankAccount.get().setBalance(latestBalance);

        repository.save(bankAccount.get());
    }

    @Override
    public void on(FundsWithdrawEvent event) {
        var bankAccount = repository.findById(event.getId());

        if(bankAccount.isEmpty()) {
            return;
        }

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - event.getAmount();

        bankAccount.get().setBalance(latestBalance);

        repository.save(bankAccount.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        repository.deleteById(event.getId());
    }
}
