package com.afterpay.service;

import com.afterpay.model.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Interface containing fraud detection API contract.
 * Created by hariharank12 on 17/05/20.
 */
public interface FraudDetectionService {

    /**
     * Contract for find fraudulent transactions.
     * The finding operation is almost linear time in execution.
     *
     * @param transactions
     * @param startDate
     * @param endDate
     * @param thresholdPrice
     * @return List of hexed account numbers
     */
    public List<String> findFraudulentTransactions(final List<Transaction> transactions, final Date startDate,
                                                   final Date endDate, final Double thresholdPrice);

}