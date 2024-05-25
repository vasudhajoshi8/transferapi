package com.example.transferapi;

import com.example.transferapi.model.Account;
import com.example.transferapi.model.TransferRequest;
import com.example.transferapi.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferapiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Account account1;
    private Account account2;

    @BeforeEach
    public void setup() {
        accountRepository.deleteAll();

        account1 = new Account();
        account1.setOwner("John Doe");
        account1.setBalance(BigDecimal.valueOf(1000));
        accountRepository.save(account1);

        account2 = new Account();
        account2.setOwner("Jane Doe");
        account2.setBalance(BigDecimal.valueOf(500));
        accountRepository.save(account2);
    }

    @Test
    @Transactional
    public void testTransferSuccess() throws Exception {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromAccountId(account1.getId());
        transferRequest.setToAccountId(account2.getId());
        transferRequest.setAmount(BigDecimal.valueOf(200));

        mockMvc.perform(post("/api/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testTransferInsufficientFunds() throws Exception {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromAccountId(account1.getId());
        transferRequest.setToAccountId(account2.getId());
        transferRequest.setAmount(BigDecimal.valueOf(2000));

        mockMvc.perform(post("/api/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void contextLoads() {
        // Simple context load test
    }
}