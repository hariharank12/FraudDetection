package com.afterpay.aspect;

import com.afterpay.exception.FraudDetectionException;
import com.afterpay.service.FraudDetectionService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Aspect validation to validate inputs before invoking the algorithm.
 * Created by hariharank12 on 17/05/20.
 */
public class FraudHandler implements InvocationHandler {

    private FraudDetectionService fraudDetectionService;

    /**
     * Parameterized constructor containing reference to service call.
     *
     * @param fraudDetectionService reference object of fraud service API.
     */
    public FraudHandler(final FraudDetectionService fraudDetectionService) {
        this.fraudDetectionService = fraudDetectionService;
    }

    /**
     * Dynamic Proxies to filter the service calls.
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        validate(args);
        List<String> result = (List<String>) method.invoke(fraudDetectionService, args);
        return result;
    }

    /**
     * Perform validation of fromd date, to date and hour range.
     *
     * @param args
     */
    private void validate(Object[] args) {
        Date fromDate = (Date) args[1];
        Date toDate = (Date) args[2];
        long hoursDifference = TimeUnit.HOURS.convert(Math.abs(toDate.getTime() - fromDate.getTime()), TimeUnit.MILLISECONDS);
        if (fromDate.compareTo(toDate) > 0) {
            throw new FraudDetectionException("From Date cannot be greater than To Date");
        } else if (hoursDifference > 24) {
            throw new FraudDetectionException("Number of hours is greater than 24 hours sliding window");
        }
    }

}