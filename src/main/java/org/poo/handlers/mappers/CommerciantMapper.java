package org.poo.handlers.mappers;

import org.poo.bankInput.Commerciant;
import org.poo.fileio.CommerciantInput;

import java.util.ArrayList;
import java.util.List;

public class CommerciantMapper {
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
