package com.afterpay;

import com.afterpay.model.test.TransactionTest;
import com.afterpay.service.test.FraudDetectionServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite for Fraud detection application.
 * Created by hariharank12 on 17/05/20.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        FraudDetectionServiceTest.class,
        TransactionTest.class
})
public class FraudDetectionAppTest {

}
