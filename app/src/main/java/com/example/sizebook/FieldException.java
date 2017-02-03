package com.example.sizebook;

import android.util.Log;
import android.widget.TextView;

/**
 * Created by Malcolm on 2017-02-01.
 */

public class FieldException extends Exception {
    private TextView field;
    public FieldException() {

    }

    public FieldException(String detailMessage) {
        super(detailMessage);
    }

    public FieldException(String detailMessage, TextView field) {
        super(detailMessage);
        setField(field);
        Log.d("tag",this.getField().toString());
    }

    public void setField(TextView field) {
        this.field = field;
    }

    public TextView getField() {
        return field;
    }
}
