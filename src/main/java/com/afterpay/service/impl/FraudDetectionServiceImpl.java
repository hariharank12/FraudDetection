package com.afterpay.service.impl;

import com.afterpay.model.Transaction;
import com.afterpay.service.FraudDetectionService;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class containing algorithm to find fradulent transactions for a given window.
 * Created by hariharank12 on 17/05/20.
 */
public class FraudDetectionServiceImpl implements FraudDetectionService {

    /**
     * Finds suspicious transactions for a given 24 hour date range and grouping them on credit card number.
     * The algorithm time is linear.
     * The method is executed after being validated by FraudHandler.
     *
     * @param transactions transaction list.
     * @param startDate start date of the window
     * @param endDate end date of the window
     * @param thresholdPrice threshold price for analysis.
     * @return List of hashed account numbers
     */
    public List<String> findFraudulentTransactions(final List<Transaction> transactions, final Instant startDate, final Instant endDate,
                                                   final Double thresholdPrice) {

        Map<String, Double> transactionsWithTotalAmount = groupByAmountAndFindSum(transactions, startDate, endDate);
        return filterTransactionsByThreshold(thresholdPrice, transactionsWithTotalAmount);
    }

    /**
     * Filter the transactions beyond the threshold.
     *
     * @param thresholdPrice threshold price for analysis
     * @param transactionsWithTotalAmount transactions with summed amount
     * @return List
     */
    private List<String> filterTransactionsByThreshold(Double thresholdPrice, Map<String, Double> transactionsWithTotalAmount) {
        return transactionsWithTotalAmount.entrySet().stream().filter(t -> t.getValue() > thresholdPrice).map(Map.Entry::getKey).
                collect(Collectors.toList());
    }

    /**
     * Finds the sum of transactions based on credit card number.
     *
     * @param transactions list of transactions
     * @param startDate start date of the analysis
     * @param endDate end timestamp of the analysis
     * @return Map
     */
    private Map<String, Double> groupByAmountAndFindSum(List<Transaction> transactions, Instant startDate, Instant endDate) {
        return transactions.stream().
                filter(transaction -> transaction.getTimestamp().isAfter(startDate) &&
                        transaction.getTimestamp().isBefore(endDate)).collect(
                Collectors.groupingBy(Transaction::getHexCreditCardNumber,
                        Collectors.summingDouble(Transaction::getAmount)));
    }
}