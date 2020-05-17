package com.afterpay.aspect;

import com.afterpay.exception.FraudDetectionException;
import com.afterpay.service.FraudDetectionService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

/**
 * Aspect validation to validate inputs before invoking the algorithm.
 * Created by hariharank12 on 17/05/20.
 */
public class FraudHandler implements InvocationHandler {

    private final FraudDetectionService fraudDetectionService;

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
     * @param proxy Object proxy
     * @param method Reflection method containing the method
     * @param args commandline arguments
     * @return Object
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        validate(args);
        return method.invoke(fraudDetectionService, args);
    }

    /**
     * Perform validation of fromd date, to date and hour range.
     *
     * @param args commandline arguments passed from the main function.
     */
    private void validate(Object[] args) {
        Instant fromDate = (Instant) args[1];
        Instant toDate = (Instant) args[2];
        if (fromDate.compareTo(toDate) > 0) {
            throw new FraudDetectionException("From Date cannot be greater than To Date");
        } else if (Duration.between(fromDate, toDate).toHours() > 24) {
            throw new FraudDetectionException("Number of hours is greater than 24 hours sliding window");
        }
    }

}