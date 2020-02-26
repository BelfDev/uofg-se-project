package madstax.controller;

import madstax.model.user.User;
import madstax.view.ApplicationWindow;
import madstax.view.CoursePlannerScreen;

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
        window.setNavigationBarSubtitle(user.getRole().getName());
    }

}
