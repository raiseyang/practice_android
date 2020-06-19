package com.raise.practice;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by raise.yang on 20/06/19.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("你好，世界");
        setContentView(tv);
    }
}
