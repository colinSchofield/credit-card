package com.comparethemarket.creditcard.service;

import com.comparethemarket.creditcard.exception.InvalidCardTypeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public enum CARD_TYPE {
        AMEX,
        Discover,
        MasterCard,
        Visa
    }

    protected CARD_TYPE isCreditCardTypeValid(String cardNumber) {

        if (!StringUtils.isNumeric(cardNumber) || cardNumber.length() < 13 || cardNumber.length() > 16) {
            LOG.debug("Credit card '{}' is not valid", cardNumber);
            throw new InvalidCardTypeException();
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
            return CARD_TYPE.Visa;
        } else {
            LOG.warn("Credit card '{}' does not conform to the standard set of supported cards", cardNumber);
            throw new InvalidCardTypeException();
        }
    }
}
