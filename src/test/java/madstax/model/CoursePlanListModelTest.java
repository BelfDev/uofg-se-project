package madstax.model;

import madstax.application.ApplicationRepository;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class CoursePlanListModelTest {
    private ApplicationRepository repo;
    private List<Teacher> teachers;
    private List<CoursePlanListItem> list;
    private Object[][] data;
    private CoursePlanListModel model;

    @BeforeAll
    void setupTests() {
        repo = ApplicationRepository.getInstance();

        teachers = repo.getTeachers();
        list = repo.getCoursePlanListItems();
        Map<Integer, Teacher> map = repo.getTeacherMap();

        data = list.stream()
                .map(CoursePlanListItem::toArray)
                .peek(row -> {
                    int teacherId = (int) row[3];
                    row[3] = map.containsKey(teacherId) ? map.get(teacherId).getName() : "";
                })
                .toArray(Object[][]::new);
        model = new CoursePlanListModel(data);
    }

    @Test
    @DisplayName("Gets cell value at specific column and row index")
    void getValueAt_RowIndexAndColumnIndex_CellValue() {
        assertEquals("History of Memes", model.getValueAt(0, 1));
    }

    @Test
    @DisplayName("Gets column value at specific column index")
    void getColumnName_ColumnIndex_ColumnValue() {
        assertEquals("COURSE", model.getColumnName(1));
    }

    @Test
    @DisplayName("Get model data row count")
    void getRowCount_ModelData_RowCount() {
        assertEquals(data.length, model.getRowCount());
    }

    @Test
    @DisplayName("Gets model data column count")
    void getColumnCount_ModelData_ColumnCount() {
        assertEquals(5, model.getColumnCount());
    }

    @Test
    @DisplayName("Sets new value at specific column and row index")
    void setValueAt_NewCellValue_CellValueUpdated() {
        String newName = "Henrique Fernandes";
        model.setValueAt(newName, 1, 3);
        assertEquals(newName, model.getValueAt(1, 3));
    }
}
