package madstax.controller;

import java.util.EventListener;
import java.util.List;

public interface ModalEditorListener extends EventListener {

    /**
     * Invoked when the user clicked the save button
     */
    public void onSavedRequirements(List<String> requirements);

}
