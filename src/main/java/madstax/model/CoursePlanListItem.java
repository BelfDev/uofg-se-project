package madstax.model;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import madstax.model.util.StatusCSVConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoursePlanListItem implements Comparable<CoursePlanListItem> {

    @CsvBindByName
    private int index;

    @CsvBindByName
    private String course;

    @CsvBindAndSplitByName(elementType = String.class, collectionType = ArrayList.class, splitOn = ",", writeDelimiter = ",")
    private List<String> requirements;

    @CsvBindByName
    private int teacherId;

    @CsvCustomBindByName(converter = StatusCSVConverter.class)
    private RequestStatus status;

    public CoursePlanListItem() {
    }

    public CoursePlanListItem(int index, String course, List<String> requirements, int teacherId, RequestStatus status) {
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

    public int getTeacherId() {
        return teacherId;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Object[] toArray() {
        List<Object> content = new ArrayList<Object>() {{
            add(getIndex());
            add(getCourse());
            add(getRequirements());
            add(getTeacherId());
            add(getStatus());
        }};
        return content.toArray(new Object[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursePlanListItem that = (CoursePlanListItem) o;
        return index == that.index &&
                course.equals(that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, course);
    }


    @Override
    public int compareTo(CoursePlanListItem o) {
        return Integer.compare(this.getIndex(), o.getIndex());
    }

}
