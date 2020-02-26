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

    @CsvBindAndSplitByName(elementType = String.class, collectionType = ArrayList.class)
    private List<String> qualifications;

    public Teacher() {
    }

    public Teacher(int id, String name, List<String> qualifications) {
        this.id = id;
        this.name = name;
        this.qualifications = qualifications;
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

}