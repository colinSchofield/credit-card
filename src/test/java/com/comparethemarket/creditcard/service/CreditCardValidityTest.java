package com.comparethemarket.creditcard.service;

import com.comparethemarket.creditcard.exception.InvalidCardTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardValidityTest {

    CreditCardValidator creditCardValidator;

    @BeforeEach
    public void setup() {
        creditCardValidator = new CreditCardValidator();
    }

    @Test
    public void passedNullValue() {
        // Given
        String creditCardNumber = null;
        // Expect InvalidCardTypeException
        assertThrows(InvalidCardTypeException.class, () -> {
            creditCardValidator.checkCreditCardValidity(creditCardNumber);
        });
    }

    @Test
    public void completelyWrongCreditCard() {
        // Given
        String creditCardNumber = "123456789012345";
        // Expect InvalidCardTypeException
        assertThrows(InvalidCardTypeException.class, () -> {
            creditCardValidator.checkCreditCardValidity(creditCardNumber);
        });
    }

    @Test
    public void charactersDashesAndSpaces() {
        // Given
        String creditCardNumber = "xxxx123-456-78 90-1";
        // Expect InvalidCardTypeException
        assertThrows(InvalidCardTypeException.class, () -> {
            creditCardValidator.checkCreditCardValidity(creditCardNumber);
        });
    }

    @Test
    public void visaCardPass() {
        // Given
        String creditCardNumber = "4111111111111111";
        // When
        ResponseEntity responseEntity = creditCardValidator.checkCreditCardValidity(creditCardNumber);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Card is a valid visa card");
    }

    @Test
    public void visaCardFail() {
        // Given
        String creditCardNumber = "4111111211111111";
        // When
        ResponseEntity responseEntity = creditCardValidator.checkCreditCardValidity(creditCardNumber);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Card is an invalid visa card");
    }
}