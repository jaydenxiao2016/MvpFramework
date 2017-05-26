介绍：zxing二维码、条码扫描和生成

使用方法：

扫描二维码/条码
CaptureActivity.startAction(Activity activity, int RequestCode);
或者
CaptureActivity.startAction(Fragment fragment, int RequestCode)

获取扫描结果：
 @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    String resultStr= intent.getString("result");
    }


生成条形码（返回bitmap）：
BarCodeUtil
生成二维码(返回bitmap)：
QRCodeUtil