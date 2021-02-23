package de.fallapalooza.streamapi.util;

import com.google.api.services.sheets.v4.model.ValueRange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ValueRangeUtils {
    public static List<Object> flatten(ValueRange range) {
        if(range == null) return null;
        List<List<Object>> contents = range.getValues();
        List<Object> flattenedContents = new ArrayList<>();
        if(contents != null) {
            for(List<Object> subList : contents) {
                if(subList != null) {
                    flattenedContents.addAll(subList);
                }
            }
        }
        return flattenedContents;
    }

    public static String getSingleValue(ValueRange range) {
        List<Object> flattened = flatten(range);
        if(flattened.isEmpty()) {
            return null;
        }
        return Objects.toString(flattened.get(0), null);
    }

    public static Integer getSingleValueInteger(ValueRange range) {
        String strValue = getSingleValue(range);
        return strValue == null ? null : Integer.valueOf(strValue);
    }

    public static List<String> getMultiValue(ValueRange range) {
        return flatten(range).stream().map(obj -> Objects.toString(obj, null)).collect(Collectors.toList());
    }

    public static List<Integer> getMultiValueInteger(ValueRange range) {
        List<String> strValue = getMultiValue(range);
        return strValue == null ? null : strValue.stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    public static <X> List<X> pad(List<X> list, int size, X padWith) {
        if(list == null) {
            return Collections.nCopies(size, padWith);
        }
        if(list.size() == size) {
            return list;
        } else if(list.size() > size) {
            return list.subList(0, size);
        } else {
            while(list.size() < size) {
                list.add(padWith);
            }
            return list;
        }
    }

    public static ValueRange fromConstant(Object value) {
        ValueRange range = new ValueRange();
        range.setValues(Collections.singletonList(Collections.singletonList(value)));
        return range;
    }
}
