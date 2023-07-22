package com.example.LibraryManagementSystem.dtos;

import com.example.LibraryManagementSystem.model.TransactionType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiateTransactionRequest {

    @NotNull
    private Integer studentId;

    @NotNull
    private Integer bookId;

    @NotNull
    private TransactionType transactionType;
}
