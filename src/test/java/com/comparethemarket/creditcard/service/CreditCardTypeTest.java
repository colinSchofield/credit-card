package com.comparethemarket.creditcard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.comparethemarket.creditcard.service.CreditCardValidator.CARD_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreditCardTypeTest {

    private CreditCardValidator creditCardValidator;

    @BeforeEach
    public void setup() {
        creditCardValidator = new CreditCardValidator();
    }

    @Test
    public void nullValuePassedForCreditCard() {
        // Given
        String creditCardNumber = null;
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.Unknown, cardType, "Card is unrecognised");
    }

    @Test
    public void longValuePassedForCreditCard() {
        // Given
        String creditCardNumber = "123456789012345678";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.Unknown, cardType, "Card is unrecognised");
    }

    @Test
    public void shortValuePassedForCreditCard() {
        // Given
        String creditCardNumber = "1234";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.Unknown, cardType, "Card is unrecognised");
    }

    @Test
    public void amexPatternWith34() {
        // Given
        String creditCardNumber = "341234567890123";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.AMEX, cardType, "Card is of type AMEX");
    }

    @Test
    public void amexPatternWith37() {
        // Given
        String creditCardNumber = "371234567890123";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.AMEX, cardType, "Card is of type AMEX");
    }

    @Test
    public void discoverCardPattern() {
        // Given
        String creditCardNumber = "6011123456789012";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.Discover, cardType, "Card is of type Discover");
    }

    @Test
    public void discoverPatternButNumberTooShort() {
        // Given
        String creditCardNumber = "60111234567890";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.Unknown, cardType, "Card is unrecognised");
    }

    @Test
    public void masterCardPattern51() {
        // Given
        String creditCardNumber = "5112345678901234";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.MasterCard, cardType, "Card is of type MasterCard");
    }

    @Test
    public void masterCardPattern52() {
        // Given
        String creditCardNumber = "5112345678901234";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.MasterCard, cardType, "Card is of type MasterCard");
    }

    @Test
    public void masterCardPattern53() {
        // Given
        String creditCardNumber = "5312345678901234";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.MasterCard, cardType, "Card is of type MasterCard");
    }

    @Test
    public void masterCardPattern54() {
        // Given
        String creditCardNumber = "5412345678901234";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.MasterCard, cardType, "Card is of type MasterCard");
    }

    @Test
    public void masterCardPattern55() {
        // Given
        String creditCardNumber = "5512345678901234";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.MasterCard, cardType, "Card is of type MasterCard");
    }

    @Test
    public void visaCardPatternBeginsWith4Length13() {
        // Given
        String creditCardNumber = "4123456789012";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.VISA, cardType, "Card is of type Visa");
    }

    @Test
    public void visaCardPatternBeginsWith4Length16() {
        // Given
        String creditCardNumber = "4123456789012345";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.VISA, cardType, "Card is of type Visa");
    }

    @Test
    public void visaPatternButNumberTooShort() {
        // Given
        String creditCardNumber = "412345678901234";
        // When
        CARD_TYPE cardType = creditCardValidator.creditCardTypeCheck(creditCardNumber);
        // Then
        assertEquals(CARD_TYPE.Unknown, cardType, "Card is unrecognised (too short)");
    }
}