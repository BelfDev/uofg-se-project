package madstax.model;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

    @CsvBindByName
    private int id;

    @CsvBindByName
    private String name;

    @CsvBindAndSplitByName(elementType = String.class, collectionType = ArrayList.class, splitOn = ",")
    private List<String> qualifications;

    public Teacher() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getQualifications() {
        return qualifications;
    }

    @Override
    public String toString() {
        return name;
    }
}
