package com.hisign.thirdparty.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hisign.thirdparty.R;

public class DefaultCaptureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_capture);
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.flt_zxing_container, captureFragment).commit();
    }


    /**
     * 二维码解析回调函数
     */
    QrCodeUtil.AnalyzeCallback analyzeCallback = new QrCodeUtil.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(QrCodeUtil.RESULT_TYPE, QrCodeUtil.RESULT_SUCCESS);
            bundle.putString(QrCodeUtil.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            DefaultCaptureActivity.this.setResult(RESULT_OK, resultIntent);
            DefaultCaptureActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(QrCodeUtil.RESULT_TYPE, QrCodeUtil.RESULT_FAILED);
            bundle.putString(QrCodeUtil.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            DefaultCaptureActivity.this.setResult(RESULT_OK, resultIntent);
            DefaultCaptureActivity.this.finish();
        }
    };
}