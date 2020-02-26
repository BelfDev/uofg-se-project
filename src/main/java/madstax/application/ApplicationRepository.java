package madstax.application;

import madstax.model.CoursePlanListItem;
import madstax.model.Teacher;
import madstax.model.util.ResourceIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class ApplicationRepository {

    private static ApplicationRepository instance = null;

    private List<Teacher> teachers;
    private Supplier<Map<Integer, Teacher>> teacherMap = () -> {
        HashMap<Integer, Teacher> map = initTeacherMap();
        teacherMap = () -> map;
        return map;
    };
    private List<CoursePlanListItem> coursePlanListItems;

    private ApplicationRepository() {
        this.teachers = new ArrayList<>();
        this.coursePlanListItems = new ArrayList<>();
    }

    public static ApplicationRepository getInstance() {
        if (instance == null) {
            instance = new ApplicationRepository();
        }
        return instance;
    }

    private HashMap<Integer, Teacher> initTeacherMap() {
        HashMap<Integer, Teacher> result = new HashMap<>();
        if (!teachers.isEmpty()) {
            result = teachers.stream().collect(
                    toMap(Teacher::getId, t -> t, (oldValue, newValue) -> oldValue, HashMap::new));
        }
        return result;
    }

    public List<Teacher> getTeachers() {
        if (teachers.isEmpty()) {
            teachers = ResourceIO.parseDataFromCSV("teachers", Teacher.class);
        }
        return teachers;
    }

    public Map<Integer, Teacher> getTeacherMap() {
        return teacherMap.get();
    }

    public List<CoursePlanListItem> getCoursePlanListItems() {
        if (coursePlanListItems.isEmpty()) {
            coursePlanListItems = ResourceIO.parseDataFromCSV("course-plan", CoursePlanListItem.class);
        }
        return coursePlanListItems;
    }

    public void updateCoursePlanList(List<CoursePlanListItem> newList) {
        List<CoursePlanListItem> originalList = getCoursePlanListItems();
        newList.addAll(originalList);
        coursePlanListItems = newList.stream()
                .sorted()
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
        ResourceIO.writeDataToCSV("course-plan", coursePlanListItems, CoursePlanListItem.class);
    }

}