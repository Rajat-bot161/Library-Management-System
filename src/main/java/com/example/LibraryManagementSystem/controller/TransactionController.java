package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dtos.InitiateTransactionRequest;
import com.example.LibraryManagementSystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @PostMapping("/transaction")
    public String initiateTxn(@RequestBody @Valid InitiateTransactionRequest initiateTransactionRequest) throws Exception {

        return transactionService.initiateTxn(initiateTransactionRequest);
    }

    @PostMapping("/transaction/payment")
    public void makePayment(@RequestParam("amount") Integer amount,
                            @RequestParam("studentId") Integer studentId,
                            @RequestParam("transactionId") String txnId) throws Exception {

        transactionService.payFine(amount, studentId, txnId);
    }
}
