package com.raise.practice.iotdialog;

/**
 * Created by raise.yang on 17/10/12.
 */

public interface IVCDialog {

    boolean isShowing();

    void dismiss();

    IVCDialog show();
}
