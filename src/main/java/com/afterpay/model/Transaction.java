package com.afterpay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**
 * Transaction class containing transaction id and amount
 * Created by hariharank12 on 17/05/20.
 */
@Getter
@AllArgsConstructor
public class Transaction {
    String hexCreditCardNumber;
    Date timestamp;
    Double amount;
}
