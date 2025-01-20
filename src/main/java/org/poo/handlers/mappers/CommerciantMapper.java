package org.poo.handlers.mappers;

import org.poo.bankInput.Commerciant;
import org.poo.fileio.CommerciantInput;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Commerciant mapper.
 */
public final class CommerciantMapper {

    private CommerciantMapper() {
    }

    /**
     * Map to commerciant list.
     *
     * @param commerciantInputs the commerciant inputs
     * @return the list
     */
    public static List<Commerciant> mapToCommerciant(final CommerciantInput[] commerciantInputs) {
        final List<Commerciant> commerciants = new
                ArrayList<>();
        if (commerciantInputs != null) {
            for (final CommerciantInput commerciantInput : commerciantInputs) {
                commerciants.add(new Commerciant.Builder(commerciantInput.getCommerciant())
                        .commerciantID(commerciantInput.getId())
                        .commerciantIBAN(commerciantInput.getAccount())
                        .commerciantType(commerciantInput.getType())
                        .cashbackStrategy(commerciantInput.getCashbackStrategy())
                        .build());
            }
        }
        return commerciants;
    }
}
