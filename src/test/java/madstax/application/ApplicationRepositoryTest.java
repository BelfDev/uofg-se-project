package madstax.application;

import madstax.model.CoursePlanListItem;
import madstax.model.Teacher;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class ApplicationRepositoryTest {
    private ApplicationRepository repo;

    @BeforeAll
    void setupTests() {
        repo = ApplicationRepository.getInstance();
    }

    @Test
    @DisplayName("Repo populates course plan list")
    void getCoursePlanListItems_coursePlanListPopulated() {
        List<CoursePlanListItem> coursePlanList = repo.getCoursePlanListItems();
        assertFalse(coursePlanList.isEmpty());
    }

    @Test
    @DisplayName("Repo populates teachers list")
    void getTeachers_teachersPopulated() {
        List<Teacher> teachers = repo.getTeachers();
        assertFalse(teachers.isEmpty());
    }

    @Test
    @DisplayName("Teachers list correctly converted to map")
    void getTeacherMap_TeachersList_TeachersMap() {
        List<Teacher> teachers = repo.getTeachers();
        Map<Integer, Teacher> map = repo.getTeacherMap();
        assertEquals(map.size(), teachers.size());
    }
}