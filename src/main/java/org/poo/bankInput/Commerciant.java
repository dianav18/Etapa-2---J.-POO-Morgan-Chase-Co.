package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Commerciant.
 */
@Getter
@Setter
public class Commerciant {
    private String name;
    private double totalSpent;

    /**
     * Instantiates a new Commerciant.
     *
     * @param name the name of the commerciant
     */
    public Commerciant(final String name) {
        this.name = name;
        totalSpent = 0;
    }

    /**
     * Add spent amount.
     *
     * @param amount the amount
     */
    public void addSpentAmount(final double amount) {
        totalSpent += amount;
    }
}
