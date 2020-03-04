package madstax.model.util;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClassFieldOrderComparator implements Comparator<String> {

    List<String> fieldNamesInOrderWithinClass;

    public ClassFieldOrderComparator(Class<?> clazz) {
        fieldNamesInOrderWithinClass = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> (field.getAnnotation(CsvBindByName.class) != null) ||
                        (field.getAnnotation(CsvBindAndSplitByName.class) != null) ||
                        (field.getAnnotation(CsvCustomBindByName.class) != null)
                )
                .map(field -> field.getName().toUpperCase())
                .collect(Collectors.toList());
    }

    @Override
    public int compare(String o1, String o2) {
        int fieldIndexO1 = fieldNamesInOrderWithinClass.indexOf(o1);
        int fieldIndexO2 = fieldNamesInOrderWithinClass.indexOf(o2);
        return Integer.compare(fieldIndexO1, fieldIndexO2);
    }
}