package com.afterpay.service;

import com.afterpay.model.Transaction;

import java.time.Instant;
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
     * @param transactions list of transaction records
     * @param startDate start date of the window.
     * @param endDate end date of the window.
     * @param thresholdPrice threshold price.
     * @return List of hexed account numbers
     */
    List<String> findFraudulentTransactions(final List<Transaction> transactions, final Instant startDate,
                                            final Instant endDate, final Double thresholdPrice);

}