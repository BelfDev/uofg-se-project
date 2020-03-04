package madstax.model.util;

import com.opencsv.bean.AbstractBeanField;
import madstax.model.RequestStatus;

import java.util.ArrayList;

public class StatusCSVConverter extends AbstractBeanField<RequestStatus, ArrayList<RequestStatus>> {

    @Override
    protected Object convert(String s) {
        return RequestStatus.valueOf(s);
    }

}
