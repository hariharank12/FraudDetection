package com.afterpay.exception;

/**
 * Created by hariharank12 on 17/05/20.
 */
public class FraudDetectionException extends RuntimeException {

    /**
     * Error message sent from handlers.
     *
     * @param message Error message from validation.
     */
    public FraudDetectionException(String message) {
        super(message);
    }

}
