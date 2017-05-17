package com.jaydenxiao.mvpframework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText etWay;
    private TextView tvContent;
    private Button btCalcalate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWay = (EditText) findViewById(R.id.et_way);
        etWay.setText(BuildConfig.BASE_URL);
        tvContent= (TextView) findViewById(R.id.tv_content);
        tvContent.setText(BuildConfig.APPLICATION_ID);
    }
}
