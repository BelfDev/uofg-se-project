package madstax.controller;

import madstax.application.ApplicationRepository;
import madstax.model.CoursePlanListItem;
import madstax.model.RequestStatus;
import madstax.model.Teacher;
import madstax.model.user.User;
import madstax.view.ApplicationWindow;
import madstax.view.CoursePlannerScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CoursePlannerController extends Controller<CoursePlannerScreen> {

    private User user;

    public CoursePlannerController(User user) {
        super(new CoursePlannerScreen());
        this.user = user;
        System.out.println(user.getRole());
    }

    @Override
    public void onAttached(ApplicationWindow window) {
        super.onAttached(window);
        String subtitle = user.getRole().getName();
        window.setNavigationBarSubtitle(subtitle);

    }

}
