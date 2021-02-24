package de.fallapalooza.streamapi.annotation;

import de.fallapalooza.streamapi.annotation.model.Point;
import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import de.fallapalooza.streamapi.annotation.processor.ConstantCellDefinition;
import de.fallapalooza.streamapi.annotation.processor.NestedCellDefinition;

import java.util.LinkedHashMap;
import java.util.Map;

public class Bed {
    private static final Map<String, CellDefinition<?>> SUBDEFS = new LinkedHashMap<>();

    static {
        SUBDEFS.put("one", new ConstantCellDefinition<>(new Point(1, 1), 1));
        SUBDEFS.put("two", new ConstantCellDefinition<>(new Point(1, 2), 2));
        SUBDEFS.put("three", new ConstantCellDefinition<>(new Point(1, 3), 3));
        SUBDEFS.put("mattress", Mattress.DEFINITION);
    }

    public static final NestedCellDefinition<Bed> DEFINITION = new NestedCellDefinition<>(Bed.class, SUBDEFS);

    int one;
    int two;
    int three;
    Mattress mattress;

    public int getOne() {
        return one;
    }

    public int getTwo() {
        return two;
    }

    public int getThree() {
        return three;
    }

    public Mattress getMattress() {
        return mattress;
    }

    public static class Mattress {
        private static final Map<String, CellDefinition<?>> SUBDEFS = new LinkedHashMap<>();

        static {
            SUBDEFS.put("one", new ConstantCellDefinition<>(new Point(1, 1), 1));
            SUBDEFS.put("two", new ConstantCellDefinition<>(new Point(1, 2), 2));
            SUBDEFS.put("three", new ConstantCellDefinition<>(new Point(1, 3), 3));
        }

        public static final NestedCellDefinition<Mattress> DEFINITION = new NestedCellDefinition<>(Mattress.class, SUBDEFS);

        int one;
        int two;
        int three;

        public int getOne() {
            return one;
        }

        public int getTwo() {
            return two;
        }

        public int getThree() {
            return three;
        }
    }
}
