package com.jaydenxiao.mvpframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jaydenxiao.common.commonutils.ToastUitl;

public class MainActivity extends AppCompatActivity {
    private EditText etWay;
    private TextView tvContent;
    private Button btCalcalate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWay = (EditText) findViewById(R.id.et_way);
        tvContent = (TextView) findViewById(R.id.tv_content);
        btCalcalate = (Button) findViewById(R.id.bt_caculate);
        btCalcalate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(etWay.getText().toString())) {
                } else {
                    ToastUitl.showShort("公式不能为空");
                }
            }
        });
    }
}
