package com.comparethemarket.creditcard.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

/**
 * The CreditCardValidator service will first check that the credit card is one which is from the valid range of cards
 * (i.e. AMEX, Discover, MasterCard or Visa). It will then check that the card's number is valid conforming to the
 * Luhn Algorithm.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Luhn_algorithm">Luhn Algorithm</a>
 */
@Service
public class CreditCardValidator {

    public final static Logger LOG = LoggerFactory.getLogger(CreditCardValidator.class);

    /** Valid Credit card types */
    public enum CARD_TYPE {
        AMEX,
        Discover,
        MasterCard,
        VISA,
        Unknown
    }

    protected CARD_TYPE creditCardTypeCheck(String cardNumber) {

        if (!StringUtils.isNumeric(cardNumber) || cardNumber.length() < 13 || cardNumber.length() > 16) {
            LOG.warn("Credit card '{}' is not valid", cardNumber);
            return CARD_TYPE.Unknown;
        } else if (cardNumber.matches("^(34|37).*$") && cardNumber.length() == 15) {
            LOG.debug("'{}' is an AMEX card", cardNumber);
            return CARD_TYPE.AMEX;
        } else if (cardNumber.startsWith("6011") && cardNumber.length() == 16) {
            LOG.debug("'{}' is a Discover card", cardNumber);
            return CARD_TYPE.Discover;
        } else if (cardNumber.matches("^(51|52|53|54|55).*$") && cardNumber.length() == 16) {
            LOG.debug("'{}' is a MasterCard", cardNumber);
            return CARD_TYPE.MasterCard;
        } else if (cardNumber.startsWith("4") && (cardNumber.length() == 13 || cardNumber.length() == 16)) {
            LOG.debug("'{}' is a Visa card", cardNumber);
            return CARD_TYPE.VISA;
        } else {
            LOG.warn("Credit card '{}' does not conform to the standard set of supported cards", cardNumber);
            return CARD_TYPE.Unknown;
        }
    }

    protected boolean isCardValid(String cardNumber) {

        int[] digitArray = cardNumber.chars().map(c -> Character.digit(c, 10)).toArray();
        return IntStream.range(0, digitArray.length)
                .map(ix -> (((ix % 2) ^ (digitArray.length % 2)) == 0) ? ((2 * digitArray[ix]) / 10 + (2 * digitArray[ix]) % 10) : digitArray[ix])
                .sum() % 10 == 0;
    }

    public ResponseEntity<String> checkCreditCardValidity(String cardNumber) {

        CARD_TYPE cardType = creditCardTypeCheck(cardNumber);

        if (cardType.equals(CARD_TYPE.Unknown)) {
            return new ResponseEntity<>(String.format("%s: %s (invalid)", cardType, cardNumber), HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (isCardValid(cardNumber)) {
            LOG.debug("Card {} is a valid card of type {}", cardNumber, cardType);
            return new ResponseEntity<>(String.format("%s: %s (valid)", cardType, cardNumber), HttpStatus.OK);
        } else {
            LOG.warn("Card {} is NOT a valid card of type {}", cardNumber, cardType);
            return new ResponseEntity<>(String.format("%s: %s (invalid)", cardType, cardNumber), HttpStatus.BAD_REQUEST);
        }
    }
}
