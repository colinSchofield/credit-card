package com.comparethemarket.creditcard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LuhnAlgorithmTest {

    private CreditCardValidator creditCardValidator;

    @BeforeEach
    public void setup() {
        creditCardValidator = new CreditCardValidator();
    }

    @Test
    public void visaCardLuhnAlgorithm() {
        // Given
        String creditCardNumber = "4111111111111111";
        // When
        boolean isValid = creditCardValidator.isCardValid(creditCardNumber);
        // Then
        assertTrue(isValid, "Card passes the Luhn Algorithm check");
    }

    @Test
    public void visaCardLuhnAlgorithmShortNumber() {
        // Given
        String creditCardNumber = "4111111111111";
        // When
        boolean isValid = creditCardValidator.isCardValid(creditCardNumber);
        // Then
        assertFalse(isValid, "Card fails the Luhn Algorithm check");
    }

    @Test
    public void visaCardLuhnAlgorithm2() {
        // Given
        String creditCardNumber = "4012888888881881";
        // When
        boolean isValid = creditCardValidator.isCardValid(creditCardNumber);
        // Then
        assertTrue(isValid, "Card passes the Luhn Algorithm check");
    }

    @Test
    public void amexCardLuhnAlgorithm() {
        // Given
        String creditCardNumber = "378282246310005";
        // When
        boolean isValid = creditCardValidator.isCardValid(creditCardNumber);
        // Then
        assertTrue(isValid, "Card passes the Luhn Algorithm check");
    }

    @Test
    public void discoverCardLuhnAlgorithm() {
        // Given
        String creditCardNumber = "6011111111111117";
        // When
        boolean isValid = creditCardValidator.isCardValid(creditCardNumber);
        // Then
        assertTrue(isValid, "Card passes the Luhn Algorithm check");
    }

    @Test
    public void masterCardLuhnAlgorithm() {
        // Given
        String creditCardNumber = "5105105105105100";
        // When
        boolean isValid = creditCardValidator.isCardValid(creditCardNumber);
        // Then
        assertTrue(isValid, "Card passes the Luhn Algorithm check");
    }

    @Test
    public void masterCardLuhnAlgorithmFails() {
        // Given
        String creditCardNumber = "5105105105105106";
        // When
        boolean isValid = creditCardValidator.isCardValid(creditCardNumber);
        // Then
        assertFalse(isValid, "Card fails the Luhn Algorithm check");
    }
}