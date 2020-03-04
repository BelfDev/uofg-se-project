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

/**
 * Class {@code ApplicationRepository} is the single source of all
 * application data.
 */
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

    /**
     * Returns a single instance of ApplicationRepository.
     *
     * @return a unique instance of ApplicationRepository.
     */
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

    /**
     * Retrieves all teachers from the 'teachers.csv' file
     * as a List of Teacher objects.
     *
     * @return a list of teachers.
     */
    public List<Teacher> getTeachers() {
        if (teachers.isEmpty()) {
            teachers = ResourceIO.parseDataFromCSV("teachers", Teacher.class);
        }
        return teachers;
    }

    /**
     * Converts the teachers list to a map
     *
     * @return a map of teachers index by their ids
     */
    public Map<Integer, Teacher> getTeacherMap() {
        return teacherMap.get();
    }

    /**
     * Retrieves all course plan list items from the 'course-plan.csv' file
     * as a list of CoursePlanListItem objects.
     *
     * @return a list of course plan list items
     */
    public List<CoursePlanListItem> getCoursePlanListItems() {
        if (coursePlanListItems.isEmpty()) {
            coursePlanListItems = ResourceIO.parseDataFromCSV("course-plan", CoursePlanListItem.class);
        }
        return coursePlanListItems;
    }

    /**
     * Updates the content of the 'course-plan.csv' file with the new
     * course plan list.
     *
     * @param newList a list containing the modified course plan list items
     */
    public void updateCoursePlanList(List<CoursePlanListItem> newList) {
        List<CoursePlanListItem> originalList = getCoursePlanListItems();
        if (!newList.isEmpty()) {
            newList.addAll(originalList);
            coursePlanListItems = newList.stream()
                    .sorted()
                    .distinct()
                    .collect(Collectors.toCollection(ArrayList::new));
            ResourceIO.writeDataToCSV("course-plan", coursePlanListItems, CoursePlanListItem.class);
        }
    }

    /**
     * Fetches teachers and course plan data and stores as member variables
     */
    public void prefetchAllData() {
        this.teachers = getTeachers();
        this.coursePlanListItems = getCoursePlanListItems();
    }

}
