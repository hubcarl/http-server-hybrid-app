package com.smart.app.example;

import android.os.Bundle;
import com.smart.app.framework.ui.activity.SmartBaseActivity;


public class MainActivity extends SmartBaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init("主页面","file:///android_asset/main.html", true);
    }
}