package com.raise.practice.iotdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.raise.practice.R;
import com.raise.trace.Trace;

public class DialogActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private final String TAG = "DialogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    public void click_show_dialog(View view) {
        final String[] items = new String[]{"汽车", "飞机", "大炮"};

        new IotDialog.Builder(this)
//                .setTitle("我是标题")
//                .setMessage("我是消息内容")
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Trace.w(TAG, "onItemClick() %s", items[position]);
                        if (view instanceof CheckedTextView) {
                            CheckedTextView ctv = (CheckedTextView) view;
                            ctv.setChecked(!ctv.isChecked());
                            //调整周期代码
                        }
                        for (int i = 0; i < parent.getChildCount(); i++) {
                            CheckedTextView ctv = (CheckedTextView) parent.getChildAt(i);
                            if (ctv != view)
                                ctv.setChecked(false);
                        }
                    }
                })
                .setPositiveButton("确定", this)
                .setNegativeButton("取消", this)
                .create()
                .show();
    }


    public void click_custom_dialog(View view) {
        //inflate custom view
        View customView = LayoutInflater.from(this).inflate(R.layout.dialog_custom_view_progress, null);
        TextView tips_tv = (TextView) customView.findViewById(R.id.textview_tips);
        tips_tv.setText(R.string.app_name);
        tips_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DialogActivity.this, "请稍等", Toast.LENGTH_SHORT).show();
            }
        });

        new IotDialog.Builder(this)
//                .setTitle("这是一个进度框")
                .setView(customView)
                .setNegativeButton("关闭", this)
                .create()
                .show();
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, DialogActivity.class));
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Trace.d(TAG, "onClick() ---iot dialog-" + i);
    }
}
