package com.example.transferapi.service;

import com.example.transferapi.model.Account;
import com.example.transferapi.model.Transfer;
import com.example.transferapi.repository.AccountRepository;
import com.example.transferapi.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid from account ID"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid to account ID"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transfer transfer = new Transfer();
        transfer.setFromAccountId(fromAccountId);
        transfer.setToAccountId(toAccountId);
        transfer.setAmount(amount);
        transfer.setTimestamp(LocalDateTime.now());

        transferRepository.save(transfer);
    }
}
