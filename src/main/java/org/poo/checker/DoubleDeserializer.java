package org.poo.checker;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * The type Double deserializer.
 */
public final class DoubleDeserializer extends JsonDeserializer<Double> {
    private static final String SEPARATOR = ".";
    private static final int HAS_TWO_SIDES = 2;

    /**
     * Deserialize double.
     *
     * @param p       the p
     * @param context the context
     * @return double
     * @throws IOException the io exception
     * @inheritDoc
     */
    @Override
    public Double deserialize(
            final JsonParser p,
            final DeserializationContext context) throws IOException {
        final var valueAsString = p.getValueAsString();
        final var sides = valueAsString.split(SEPARATOR);

        if (sides.length < HAS_TWO_SIDES) {
            return Double.valueOf(valueAsString);
        }

        return Double.valueOf(sides[0] + sides[1].substring(0, 2));
    }
}
