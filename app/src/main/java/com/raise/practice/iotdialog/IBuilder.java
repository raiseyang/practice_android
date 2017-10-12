package com.raise.practice.iotdialog;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

/**
 * Builder设计模式，build接口类，用于抽象需要构建的零部件；
 * 相当于构建房子所需要的厨房，客厅，卫生间，厂库；
 * <p>
 * http://www.cnblogs.com/yejg1212/archive/2013/02/25/2932526.html
 * Created by raise.yang on 17/10/11.
 */

public interface IBuilder {

    IBuilder setTitle(String title);

    IBuilder setMessage(String message);

    IBuilder setItems(String[] items, AdapterView.OnItemClickListener listener);

    IBuilder setPositiveButton(String text, DialogInterface.OnClickListener listener);

    IBuilder setNegativeButton(String text, DialogInterface.OnClickListener listener);

    IBuilder setCancelable(boolean cancelable);

    IBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

    IBuilder setView(View view);

    IVCDialog create();
}
