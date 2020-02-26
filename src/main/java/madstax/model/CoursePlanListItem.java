package madstax.model;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import madstax.model.util.StatusCSVConverter;

import java.util.ArrayList;
import java.util.List;

public class CoursePlanListItem {

    @CsvBindByName
    private int index;

    @CsvBindByName
    private String course;

    @CsvBindAndSplitByName(elementType = String.class, collectionType = ArrayList.class)
    private List<String> requirements;

    @CsvBindByName
    private String teacherId;

    @CsvCustomBindByName(converter = StatusCSVConverter.class)
    private RequestStatus status;

    public CoursePlanListItem() {
    }

    public CoursePlanListItem(int index, String course, List<String> requirements, String teacherId, RequestStatus status) {
        this.index = index;
        this.course = course;
        this.requirements = requirements;
        this.teacherId = teacherId;
        this.status = status;
    }

    public int getIndex() {
        return index;
    }

    public String getCourse() {
        return course;
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public RequestStatus getStatus() {
        return status;
    }

}
