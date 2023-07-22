package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dtos.InitiateTransactionRequest;
import com.example.LibraryManagementSystem.model.SecuredUser;
import com.example.LibraryManagementSystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user = (SecuredUser) authentication.getPrincipal();
        Integer adminId = user.getAdmin().getId();
        return transactionService.initiateTxn(initiateTransactionRequest, adminId);
    }

    @PostMapping("/transaction/payment")
    public void makePayment(@RequestParam("amount") Integer amount,
                            @RequestParam("transactionId") String txnId) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user = (SecuredUser) authentication.getPrincipal();
        Integer studentId = user.getStudent().getId();
        transactionService.payFine(amount, studentId, txnId);
    }
}
