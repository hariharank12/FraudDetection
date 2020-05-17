package com.afterpay;

import com.afterpay.model.Transaction;
import com.afterpay.util.FraudDetectionUtil;

import java.util.Arrays;
import java.util.List;

/**
 * A base test class containing test data preparation.
 * Created by hariharank12 on 17/05/20.
 */
public class FraudDetectionBaseTest {
    /**
     * Method that prepares transaction record.
     *
     * @param accountNumber account number.
     * @param date date
     * @param amount transaction amount
     * @return Transaction record.
     */
    protected Transaction prepareTransaction(String accountNumber, String date, double amount) {
        return new Transaction(FraudDetectionUtil.md5Hash(accountNumber),
                FraudDetectionUtil.parseDate(date), amount);
    }

    /**
     * Prepares Test Data to test fraud detection application.
     *
     * @return
     */
    protected List<Transaction> prepareTransactionTestData() {
        return Arrays.asList(prepareTransaction("12345",
                "2014-04-29T13:15:54.00Z", 10.00), prepareTransaction("12345",
                "2014-04-29T13:16:54.00Z", 20.00), prepareTransaction("62345",
                "2014-04-29T13:15:54.00Z", 11.11), prepareTransaction("62345",
                "2014-04-29T13:16:54.00Z", 12.12), prepareTransaction("72345",
                "2014-05-29T13:15:54.00Z", 25.89), prepareTransaction("72345",
                "2014-05-29T13:16:54.00Z", 45.10), prepareTransaction("82345",
                "2014-04-29T13:15:54.00Z", 10.10), prepareTransaction("82345",
                "2014-04-29T13:18:54.00Z", 10.10), prepareTransaction("92345",
                "2014-04-29T13:15:54.00Z", 5.67), prepareTransaction("92345",
                "2014-04-29T13:17:54.00Z", -3.25));
    }

}
