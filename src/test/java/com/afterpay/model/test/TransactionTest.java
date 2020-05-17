package com.afterpay.model.test;

import com.afterpay.FraudDetectionBaseTest;
import com.afterpay.model.Transaction;
import com.afterpay.util.FraudDetectionUtil;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit test for Transaction record.
 * Created by hariharank12 on 17/05/20.
 */
public class TransactionTest extends FraudDetectionBaseTest {

    /**
     * Test transaction values.
     */
    @Test
    public void assignAndTestTransactionValues() {
        String accountNumber = "12345";
        String date = "2014-04-29T13:15:54.000000";
        double amount = 10.00;
        Transaction transaction = prepareTransaction(accountNumber, date, amount);
        assertThat(transaction.getHexCreditCardNumber(), equalTo(FraudDetectionUtil.md5Hash(accountNumber)));
    }

}
