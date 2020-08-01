package com.comparethemarket.creditcard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardValidityTest {

    private CreditCardValidator creditCardValidator;

    @BeforeEach
    public void setup() {
        creditCardValidator = new CreditCardValidator();
    }

    @Test
    public void passedNullValue() {
        // Given
        String creditCardNumber = null;
        // When
        ResponseEntity responseEntity = creditCardValidator.checkCreditCardValidity(creditCardNumber);
        // Then
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode(), "Card is invalid");
        assertEquals("Unknown: null (invalid)", responseEntity.getBody(), "Card provides status body");
    }

    @Test
    public void completelyWrongCreditCard() {
        // Given
        String creditCardNumber = "123456789012345";
        // When
        ResponseEntity responseEntity = creditCardValidator.checkCreditCardValidity(creditCardNumber);
        // Then
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode(), "Card is invalid");
        assertEquals("Unknown: 123456789012345 (invalid)", responseEntity.getBody(), "Card provides status body");
    }

    @Test
    public void charactersDashesAndSpaces() {
        // Given
        String creditCardNumber = "xxxx123-456-78 90-1";
        // When
        ResponseEntity responseEntity = creditCardValidator.checkCreditCardValidity(creditCardNumber);
        // Then
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode(), "Card is invalid");
        assertEquals("Unknown: xxxx123-456-78 90-1 (invalid)", responseEntity.getBody(), "Card provides status body");
    }

    @Test
    public void visaCardPass() {
        // Given
        String creditCardNumber = "4111111111111111";
        // When
        ResponseEntity responseEntity = creditCardValidator.checkCreditCardValidity(creditCardNumber);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Card is a valid visa card");
        assertEquals("VISA: 4111111111111111 (valid)", responseEntity.getBody(), "Card provides status body");
    }

    @Test
    public void visaCardFail() {
        // Given
        String creditCardNumber = "4111111211111111";
        // When
        ResponseEntity responseEntity = creditCardValidator.checkCreditCardValidity(creditCardNumber);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Card is an invalid visa card");
        assertEquals("VISA: 4111111211111111 (invalid)", responseEntity.getBody(), "Card provides status body");
    }
}