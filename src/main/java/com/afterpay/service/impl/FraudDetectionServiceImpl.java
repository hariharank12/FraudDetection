package com.afterpay.service.impl;

import com.afterpay.model.Transaction;
import com.afterpay.service.FraudDetectionService;

import java.util.Date;
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
     * @param transactions
     * @param startDate
     * @param endDate
     * @param thresholdPrice
     * @return List of hashed account numbers
     */
    public List<String> findFraudulentTransactions(final List<Transaction> transactions, final Date startDate, final Date endDate,
                                                   final Double thresholdPrice) {

        Map<String, Double> transactionsWithTotalAmount = groupByAmountAndFindSum(transactions, startDate, endDate);
        return filterTransactionsByThreshold(thresholdPrice, transactionsWithTotalAmount);
    }

    /**
     * Filter the transactions beyond the threshold.
     *
     * @param thresholdPrice
     * @param transactionsWithTotalAmount
     * @return
     */
    private List<String> filterTransactionsByThreshold(Double thresholdPrice, Map<String, Double> transactionsWithTotalAmount) {
        return transactionsWithTotalAmount.entrySet().stream().filter(t -> t.getValue() > thresholdPrice).map(e -> e.getKey()).
                collect(Collectors.toList());
    }

    /**
     * Finds the sum of transactions based on credit card number.
     *
     * @param transactions
     * @param startDate
     * @param endDate
     * @return
     */
    private Map<String, Double> groupByAmountAndFindSum(List<Transaction> transactions, Date startDate, Date endDate) {
        return transactions.stream().
                filter(transaction -> transaction.getTimestamp().after(startDate) &&
                        transaction.getTimestamp().before(endDate)).collect(
                Collectors.groupingBy(Transaction::getHexCreditCardNumber,
                        Collectors.summingDouble(Transaction::getAmount)));
    }
}