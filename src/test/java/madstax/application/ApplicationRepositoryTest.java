package madstax.application;

import madstax.model.CoursePlanListItem;
import madstax.model.RequestStatus;
import madstax.model.Teacher;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class ApplicationRepositoryTest {
    private ApplicationRepository repo;
    private List<CoursePlanListItem> coursePlanList;

    private RequestStatus firstItemInitialStatus;

    @BeforeAll
    void setupTests() {
        repo = ApplicationRepository.getInstance();
        coursePlanList = repo.getCoursePlanListItems();

        firstItemInitialStatus = coursePlanList.get(0).getStatus();
    }

    @AfterAll
    void resetTests() {
        coursePlanList.get(0).setStatus(firstItemInitialStatus);
        repo.updateCoursePlanList(coursePlanList);
    }

    @Test
    @DisplayName("Should return course plan list")
    void shouldReturnCoursePlanList() {
        assertFalse(coursePlanList.isEmpty());
    }

    @Test
    @DisplayName("Should return teachers")
    void shouldReturnTeachers() {
        List<Teacher> teachers = repo.getTeachers();
        assertFalse(teachers.isEmpty());
    }

    @Test
    @DisplayName("Should create map of correct size")
    void shouldReturnTeacherMap() {
        List<Teacher> teachers = repo.getTeachers();
        Map<Integer, Teacher> map = repo.getTeacherMap();
        assertEquals(teachers.size(), map.size());
    }

    @Test
    @DisplayName("Should update course plan list")
    void shouldUpdateCoursePlanList() {
        coursePlanList.get(0).setStatus(RequestStatus.AWAITING_APPROVAL);
        repo.updateCoursePlanList(coursePlanList);

        List<CoursePlanListItem> updatedCoursePlanList = repo.getCoursePlanListItems();

        assertEquals(updatedCoursePlanList.get(0).getStatus(), RequestStatus.AWAITING_APPROVAL);
    }
}