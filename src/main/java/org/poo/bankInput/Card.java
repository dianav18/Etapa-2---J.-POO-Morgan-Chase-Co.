package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Card.
 */
@Getter
@Setter
public class Card {

    private Account account;
    private String cardNumber;
    private String status;
    private boolean isOneTime;

    /**
     * Instantiates a new Card.
     *
     * @param cardNumber the card number
     * @param isOneTime  the is one time
     */
    public Card(Account account, final String cardNumber, final boolean isOneTime) {
        this.account = account;
        this.cardNumber = cardNumber;
        this.status = "active";
        this.isOneTime = isOneTime;
    }

    /**
     * Use card boolean.
     *
     * @return the boolean
     */
    public boolean useCard() {
        if (isOneTime) {
            this.status = "expired";
            return true;
        }
        return false;
    }

    /**
     * Destroy.
     */
    public void destroy() {
        this.status = "destroyed";
    }

    /**
     * Freeze.
     */
    public void freeze() {
        this.status = "frozen";
    }
}
