package madstax.controller;

import madstax.application.ApplicationRepository;
import madstax.model.CoursePlanListItem;
import madstax.model.CoursePlanListModel;
import madstax.model.RequestStatus;
import madstax.model.Teacher;
import madstax.model.user.Permission;
import madstax.model.user.User;
import madstax.view.CoursePlannerScreen;
import madstax.view.EditorToolbar;
import madstax.view.ModalEditor;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static madstax.model.user.Permission.*;

// TODO: Refactor to account for different interfaces
public class CoursePlannerController extends ScreenController<CoursePlannerScreen> implements ListSelectionListener, ModalEditorListener {

    private static final String[] TABLE_HEADER = {"INDEX", "COURSE", "REQUIREMENTS", "TEACHER NAME", "STATUS"};

    private static final String EDIT_BUTTON_TITLE = "EDIT";
    private static final String SAVE_BUTTON_TITLE = "SAVE";

    private User user;
    private ApplicationRepository repo;
    private TreeSet<Permission> userPermissions;

    private List<Teacher> teachers;
    private List<CoursePlanListItem> list;

    private EditorToolbar editorToolbar;
    private ModalEditor modalEditor;

    private boolean isEditModeEnabled;

    public CoursePlannerController(User user) {
        super(new CoursePlannerScreen());
        this.user = user;
        this.userPermissions = user.getRole().getPermissions();
        this.repo = ApplicationRepository.getInstance();
        this.isEditModeEnabled = false;
        processPermissions();
    }

    // TODO: Change permissions to a TreeSet
    private void processPermissions() {
        if (userPermissions.contains(ASSIGN_STAFF) || userPermissions.contains(APPROVE_TEACHING_REQUEST)) {
            editorToolbar = new EditorToolbar.Builder()
                    .withDropdown().build();
        } else if (userPermissions.contains(ADD_REQUIREMENT)) {
            editorToolbar = new EditorToolbar.Builder().build();
            modalEditor = new ModalEditor();
            modalEditor.setListener(this);
        }
        screen.addEditorToolbar(editorToolbar);
    }

    @Override
    public void onAttached() {
        String subtitle = user.getRole().getName();
        navBar.createSubtitleItem(subtitle);
        navBar.createRightButtonItem(EDIT_BUTTON_TITLE);

        loadData();

        Map<Integer, Teacher> map = repo.getTeacherMap();

        Object[][] data = list.stream()
                .map(CoursePlanListItem::toArray)
                .peek(row -> {
                    int teacherId = (int) row[3];
                    row[3] = map.containsKey(teacherId) ? map.get(teacherId).getName() : "";
                })
                .toArray(Object[][]::new);

//        CoursePlanListModel model = new CoursePlanListModel(list);
        CoursePlanListModel model = new CoursePlanListModel(data);

        screen.setTableModel(model, this);
        editorToolbar.setConfirmButtonListener(onConfirmButtonClick());

        super.onAttached();
    }

    private ActionListener onConfirmButtonClick() {
        return e -> {
            int row = screen.getSelectedRow();
            CoursePlanListItem editedItem = list.get(row);
            if (userPermissions.contains(ADD_REQUIREMENT)) {
                CoursePlanListItem item = list.get(row);
                modalEditor.setRequirements(item.getRequirements());
                modalEditor.setVisible(true);
            } else if (userPermissions.contains(ASSIGN_STAFF)) {
                Teacher selectedTeacher = (Teacher) editorToolbar.getSelectedDropdownItem();
                if (selectedTeacher != null) {
                    editedItem.setTeacherId(selectedTeacher.getId());
                    screen.updateAssignedTeacher(selectedTeacher.getName());
                }
            } else if (userPermissions.contains(APPROVE_TEACHING_REQUEST)) {
                RequestStatus selectedStatus = (RequestStatus) editorToolbar.getSelectedDropdownItem();
                if (selectedStatus != null) {
                    editedItem.setStatus(selectedStatus);
                    screen.updateRequestStatus(selectedStatus);
                }
            }
        };
    }

    private void loadData() {
        this.teachers = repo.getTeachers();
        this.list = repo.getCoursePlanListItems();
    }

    private Teacher[] filterSuitableTeachers(List<String> requirements) {
        return teachers.stream()
                .filter(t -> t.getQualifications().stream()
                        .anyMatch(requirements::contains))
                .toArray(Teacher[]::new);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            // If the user has selected the table manually
            if (!isEditModeEnabled) {
                // Update edit mode flag and navigation bar right button text
                isEditModeEnabled = true;
                editorToolbar.activate();
                navBar.setRightButtonText(SAVE_BUTTON_TITLE);
            }

            int row = screen.getSelectedRow();
            CoursePlanListItem item = list.get(row);
            editorToolbar.setCourseLabelText(item.getCourse());
            Object[] data = null;

            if (userPermissions.contains(ASSIGN_STAFF)) {
                data = filterSuitableTeachers(item.getRequirements());
            } else if (userPermissions.contains(APPROVE_TEACHING_REQUEST)) {
                data = new RequestStatus[]{RequestStatus.UNASSIGNED, RequestStatus.APPROVED, RequestStatus.DENIED};
            }
            editorToolbar.setDropdownData(data);
        }
    }

    @Override
    public void onSavedRequirements(List<String> requirements) {
        int row = screen.getSelectedRow();
        CoursePlanListItem item = list.get(row);
        item.setRequirements(requirements);
        screen.updateRequirements(requirements);
        modalEditor.setVisible(false);
    }

    @Override
    public void onRightNavigationButtonClicked() {
        navBar.setRightButtonText(isEditModeEnabled ? EDIT_BUTTON_TITLE : SAVE_BUTTON_TITLE);
        if (isEditModeEnabled) {
            // User is currently in the EDIT mode
            // The next action is to SAVE the changes

            // Update the repo asynchronously
            Runnable r = () -> repo.updateCoursePlanList(list);
            new Thread(r).start();

            editorToolbar.deactivate();
            isEditModeEnabled = false;
        } else {
            // User is activating the EDIT mode
            screen.selectFirstRow();
            editorToolbar.activate();
            isEditModeEnabled = true;
        }

    }
}
