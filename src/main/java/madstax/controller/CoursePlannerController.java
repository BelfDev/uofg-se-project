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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static madstax.model.user.Permission.*;

// TODO: Refactor to account for different interfaces
public class CoursePlannerController extends ScreenController<CoursePlannerScreen> implements ListSelectionListener, ModalEditorListener {

    private static final String[] TABLE_HEADER = {"INDEX", "COURSE", "REQUIREMENTS", "TEACHER NAME", "STATUS"};

    private User user;
    private ApplicationRepository repo;
    private HashMap<Permission, Boolean> userPermissions;

    private List<Teacher> teachers;
    private List<CoursePlanListItem> list;

    private EditorToolbar editorToolbar;
    private ModalEditor modalEditor;

    public CoursePlannerController(User user) {
        super(new CoursePlannerScreen());
        this.user = user;
        this.userPermissions = user.getRole().getPermissions();
        this.repo = ApplicationRepository.getInstance();

        processPermissions();
    }

    // TODO: Change permissions to a TreeSet
    private void processPermissions() {
        if (userPermissions.get(ASSIGN_STAFF)) {
            screen.setEditorToolbarType(Teacher.class);
        } else if (userPermissions.get(APPROVE_TEACHING_REQUEST)) {
            screen.setEditorToolbarType(RequestStatus.class);
        } else if (userPermissions.get(ADD_REQUIREMENT)) {
            this.modalEditor = screen.getModalEditor();
            screen.setEditorToolbarType(null); // Temporary
            modalEditor.setListener(this);
        }
    }

    @Override
    public void onAttached() {
        String subtitle = user.getRole().getName();
        navBar.createSubtitleItem(subtitle);
        navBar.createRightButtonItem("EDIT");

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

        screen.setConfirmButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = screen.getSelectedRow();
                CoursePlanListItem editedItem = list.get(row);

                if (userPermissions.get(ASSIGN_STAFF)) {
                    Teacher selectedTeacher = (Teacher) screen.getSelectedDropdownValue();
                    if (selectedTeacher != null) {
                        editedItem.setTeacherId(selectedTeacher.getId());
                        screen.updateAssignedTeacher(selectedTeacher.getName());
                    }
                } else if (userPermissions.get(APPROVE_TEACHING_REQUEST)) {
                    RequestStatus selectedStatus = (RequestStatus) screen.getSelectedDropdownValue();
                    if (selectedStatus != null) {
                        editedItem.setStatus(selectedStatus);
                        screen.updateStatus(selectedStatus);
                    }
                } else if (userPermissions.get(ADD_REQUIREMENT)) {
                    CoursePlanListItem item = list.get(row);
                    screen.showModalEditor(item.getRequirements());
                }

//                repo.updateCoursePlanList(list);
            }
        });

        super.onAttached();
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
        e.getValueIsAdjusting();
        if (!e.getValueIsAdjusting()) {
            int row = screen.getSelectedRow();
            CoursePlanListItem item = list.get(row);
            if (userPermissions.get(ASSIGN_STAFF)) {

                EditorToolbar<Teacher> toolbar = screen.getEditorToolbar();
                toolbar.setCourseLabelText(item.getCourse());
                Teacher[] suitableTeachers = filterSuitableTeachers(item.getRequirements());
                toolbar.setDecisionDropdownData(suitableTeachers);

            } else if (userPermissions.get(APPROVE_TEACHING_REQUEST)) {

                EditorToolbar<RequestStatus> toolbar = screen.getEditorToolbar();
                toolbar.setCourseLabelText(item.getCourse());
                RequestStatus[] decisions = new RequestStatus[]{RequestStatus.UNASSIGNED, RequestStatus.APPROVED, RequestStatus.DENIED};
                toolbar.setDecisionDropdownData(decisions);

            }
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
        navBar.setRightButtonText("SAVE");
        System.out.println("Clicked");
    }
}
