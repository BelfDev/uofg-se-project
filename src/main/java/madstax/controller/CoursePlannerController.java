package madstax.controller;

import madstax.application.ApplicationRepository;
import madstax.model.CoursePlanListItem;
import madstax.model.Teacher;
import madstax.model.user.Permission;
import madstax.model.user.User;
import madstax.view.ApplicationWindow;
import madstax.view.CoursePlannerScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static madstax.model.user.Permission.*;

public class CoursePlannerController extends Controller<CoursePlannerScreen> {

    private User user;
    private ApplicationRepository repo;
    private HashMap<Permission, Boolean> userPermissions;

    private List<Teacher> teachers;
    private List<CoursePlanListItem> list;

    public CoursePlannerController(User user) {
        super(new CoursePlannerScreen());
        this.user = user;
        this.userPermissions = user.getRole().getPermissions();
        this.repo = ApplicationRepository.getInstance();
    }

    @Override
    public void onAttached(ApplicationWindow window) {
        super.onAttached(window);
        String subtitle = user.getRole().getName();
        window.setNavigationBarSubtitle(subtitle);

        loadData();

        if (userPermissions.get(VIEW_REQUIREMENT_LIST)) {
            String[][] rows = list.stream()
                    .map(this::convertToArray)
                    .toArray(String[][]::new);

            screen.setTable(rows);
        }

    }

    public String[] convertToArray(CoursePlanListItem item) {
        List<String> content = new ArrayList<String>() {{
            add(String.valueOf(item.getIndex()));
            add(item.getCourse());
            add(String.join("", item.getRequirements()));
            if (userPermissions.get(VIEW_ASSIGNED_STAFF))
                add(String.valueOf(item.getTeacherId()));
            if (userPermissions.get(VIEW_REQUEST_STATUS))
                add(item.getStatus().getDisplayName());
        }};

        return content.toArray(new String[0]);
    }

    private void loadData() {
        this.teachers = repo.getTeachers();
        this.list = repo.getCoursePlanListItems();
    }

}
