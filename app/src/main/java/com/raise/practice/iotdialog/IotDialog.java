package com.raise.practice.iotdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.raise.practice.R;

/**
 * http://www.jianshu.com/p/526fcf3e8db3
 * <p>
 * https://github.com/tianzhijiexian/EasyDialog/blob/HEAD/lib/src/main/java/kale/ui/view/dialog/BaseEasyDialog.java
 * Created by raise.yang on 17/10/10.
 */

public class IotDialog extends AppCompatDialogFragment implements IVCDialog {

    private final String TAG = "IotDialog";

    private Dialog mDialog;
    private Builder mBuilder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return mDialog;
    }

    @Override
    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }

    @Override
    public IVCDialog show() {
        FragmentActivity fa = (FragmentActivity) mBuilder.context;
        show(fa.getSupportFragmentManager(), "IotDialog");
        return this;
    }

    /**
     * IotDialogBuilder
     * 构建一个IotDialog
     */
    public static class Builder implements IBuilder {

        private final String TAG = "Builder";

        Context context;
        Dialog dialog;

        String title;
        String message;
        String[] list_view_items = new String[]{};

        String positiveButtonText;
        DialogInterface.OnClickListener positiveListener;

        String negativeButtonText;
        DialogInterface.OnClickListener negativeListener;

        boolean cancelable;//dialog是否可以取消
        DialogInterface.OnCancelListener onCancelListener;

        View customView;//message区域部分
        AdapterView.OnItemClickListener onItemClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        @Override
        public IBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        @Override
        public IBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        @Override
        public IBuilder setItems(String[] items, AdapterView.OnItemClickListener listener) {
            this.list_view_items = items;
            this.onItemClickListener = listener;
            return this;
        }

        @Override
        public IBuilder setPositiveButton(String text, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = text;
            this.positiveListener = listener;
            return this;
        }

        @Override
        public IBuilder setNegativeButton(String text, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = text;
            this.negativeListener = listener;
            return this;
        }

        @Override
        public IBuilder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        @Override
        public IBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        @Override
        public IBuilder setView(View view) {
            this.customView = view;
            return this;
        }

        @Override
        public IVCDialog create() {
            dialog = new Dialog(context);
            //去掉头部
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_iot);
            setupView(dialog.getWindow());

            dialog.setCancelable(cancelable);
            dialog.setOnCancelListener(onCancelListener);

            IotDialog iotDialog = new IotDialog();
            iotDialog.mDialog = dialog;
            iotDialog.mBuilder = this;
            return iotDialog;
        }

        private void setupView(Window window) {
            //背景透明
            window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
//            WindowManager.LayoutParams attributes = window.getAttributes();
//            attributes.gravity = Gravity.BOTTOM;
            //设置宽高
            ViewGroup root = (ViewGroup) window.findViewById(R.id.dialog_layout_background);
            ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
            layoutParams.width = context.getResources().getDisplayMetrics().widthPixels * 3 / 5;
            layoutParams.height = context.getResources().getDisplayMetrics().heightPixels * 3 / 5;



//            root.setBackgroundResource(R.drawable.dialog_bg_gray);
            //title
            TextView title_tv = (TextView) window.findViewById(R.id.dialog_textview_title);
            if (title_tv != null) {
                if (TextUtils.isEmpty(title)) {
                    title_tv.setVisibility(View.GONE);
                } else {
                    title_tv.setText(title);
                    title_tv.setVisibility(View.VISIBLE);
                }
            }
            //message
            ViewGroup msg_vg = (ViewGroup) window.findViewById(R.id.dialog_layout_message);
            TextView message_tv = (TextView) msg_vg.findViewById(R.id.dialog_textview_message);
            if (customView != null) {
                //自定义view
                msg_vg.removeAllViews();
                msg_vg.addView(customView);
            } else {
                if (TextUtils.isEmpty(message)) {
                    message_tv.setVisibility(View.GONE);
                } else {
                    message_tv.setVisibility(View.VISIBLE);
                    message_tv.setText(message);
                }
            }

            if (list_view_items.length > 0) {
                ListView listView = (ListView) msg_vg.findViewById(R.id.dialog_listview_message);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_single_choice, list_view_items));
                listView.setOnItemClickListener(onItemClickListener);
            }

            //buttons
            ViewGroup buttons_vg = (ViewGroup) window.findViewById(R.id.dialog_layout_buttons);
            if (TextUtils.isEmpty(positiveButtonText) && TextUtils.isEmpty(negativeButtonText)) {
                //没有按钮
                buttons_vg.setVisibility(View.GONE);
            } else {
                buttons_vg.setVisibility(View.VISIBLE);
                Button positiveButton = (Button) window.findViewById(R.id.dialog_button_positive);
                if (TextUtils.isEmpty(positiveButtonText)) {
                    positiveButton.setVisibility(View.GONE);
                } else {
                    positiveButton.setText(positiveButtonText);
                    positiveButton.setVisibility(View.VISIBLE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (positiveListener != null)
                                positiveListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            dialog.cancel();
                        }
                    });
                }
                final Button negativeButton = (Button) window.findViewById(R.id.dialog_button_negative);
                if (TextUtils.isEmpty(negativeButtonText)) {
                    negativeButton.setVisibility(View.GONE);
                } else {
                    negativeButton.setText(negativeButtonText);
                    negativeButton.setVisibility(View.VISIBLE);
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (negativeListener != null)
                                negativeListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                            dialog.cancel();
                        }
                    });
                }
            }
        }
    }

}
