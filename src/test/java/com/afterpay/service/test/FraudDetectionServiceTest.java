package com.afterpay.service.test;

import com.afterpay.FraudDetectionBaseTest;
import com.afterpay.aspect.FraudHandler;
import com.afterpay.exception.FraudDetectionException;
import com.afterpay.service.FraudDetectionService;
import com.afterpay.service.impl.FraudDetectionServiceImpl;
import com.afterpay.util.FraudDetectionUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Proxy;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

/**
 * Class to test various scenarios of FraudDetection Service class.
 * Created by hariharank12 on 17/05/20.
 */
public class FraudDetectionServiceTest extends FraudDetectionBaseTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private FraudDetectionService fraudDetectionService;

    /**
     * Initialize stubs and proxy handlers to test.
     */
    @Before
    public void init() {
        FraudDetectionServiceImpl fraudDetectionServiceImpl = new FraudDetectionServiceImpl();
        FraudHandler handler = new FraudHandler(fraudDetectionServiceImpl);
        fraudDetectionService = (FraudDetectionService) Proxy.newProxyInstance(FraudDetectionService.class.getClassLoader(),
                new Class[]{FraudDetectionService.class}, handler);
    }

    /**
     * Test a valid positive scenario, three accounts exceeding threshold
     * namely - 697cf48ee6ac8cfd42b4b5138200cb55, aa2e180b06272d9d38fd2869108fab6d, 827ccb0eea8a706c4c34a16891f84e7b.
     */
    @Test
    public void suspiciousAccountsHasSizeThree() {
        List<String> suspiciousAccountIds = fraudDetectionService.findFraudulentTransactions(
                prepareTransactionTestData(), FraudDetectionUtil.parseDate("2014-04-29T00:00:00.000000"),
                FraudDetectionUtil.parseDate("2014-04-30T00:00:00.000000"), 15.00);
        assertThat(suspiciousAccountIds, hasSize(3));
    }

    /**
     * Exception scenario when From date is after two date.
     */
    @Test
    public void throwFraudDetectionExceptionWhenFromDateisAfterToDate() {
        exception.expect(FraudDetectionException.class);
        exception.expectMessage("From Date cannot be greater than To Date");
        List<String> suspiciousAccountIds = fraudDetectionService.findFraudulentTransactions(
                prepareTransactionTestData(), FraudDetectionUtil.parseDate("2014-04-30T00:00:00.000000"),
                FraudDetectionUtil.parseDate("2014-04-29T00:00:00.000000"), 15.00);
    }

    /**
     * Exception scenario when given date range is more than 24 hours window.
     */
    @Test
    public void throwFraudDetectionExceptionWhenDateRangeisGreaterThan24Hours() {
        exception.expect(FraudDetectionException.class);
        exception.expectMessage("Number of hours is greater than 24 hours sliding window");
        List<String> suspiciousAccountIds = fraudDetectionService.findFraudulentTransactions(
                prepareTransactionTestData(), FraudDetectionUtil.parseDate("2014-04-29T00:00:00.000000"),
                FraudDetectionUtil.parseDate("2014-04-30T10:00:00.000000"), 15.00);
    }

    /**
     * Teardown cleanup.
     */
    @After
    public void destroy() {
        fraudDetectionService = null;
    }

}
