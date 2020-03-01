package madstax.controller;

import java.util.EventListener;

public interface ConfirmationModalListener extends EventListener {

    void onCancel();

    void onConfirm();

}
