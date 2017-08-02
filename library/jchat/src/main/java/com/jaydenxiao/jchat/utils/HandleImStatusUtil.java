package com.jaydenxiao.jchat.utils;


/**
 * 描述：解析JMessage 返回的status状态码工具类
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/26
 * 最后修改时间:2016/12/26
 */
public class HandleImStatusUtil {
    public static String onHandle(int status) {
        switch (status) {
            case 0:
            
            case 1000:
                return "jmui_record_voice_permission_denied";
            case 1001:
                return "jmui_local_picture_not_found_toast";
            case 1002:
                return "jmui_user_already_exist_toast";
            case 1003:
                return "jmui_illegal_state_toast";
            case 800002:
                return "jmui_server_800002";
            case 800003:
                return "jmui_server_800003";
            case 800004:
                return "jmui_server_800004";
            case 800005:
                return "jmui_server_800005";
            case 800006:
                return "jmui_server_800006";
            case 800012:
                return "jmui_server_800012";
            case 800013:
                return "jmui_server_800013";
            case 800014:
                return "jmui_server_800014";
            case 801001:
            case 802001:
                return "jmui_server_802001";
            case 802002:
            case 898002:
            case 801003:
            case 899002:
                return "jmui_server_801003";
            case 899004:
            case 801004:
                return "jmui_server_801004";
            case 803001:
                return "jmui_server_803001";
            case 803002:
                return "jmui_server_803002";
            case 803003:
                return "jmui_server_803003";
            case 803004:
                return "jmui_server_803004";
            case 803005:
                return "jmui_server_803005";
            case 803008:
                return "jmui_server_803008";
            case 803010:
                return "jmui_server_803010";
            case 805002:
                return "";
            case 808003:
                return "jmui_server_808003";
            case 808004:
                return "jmui_server_808004";
            case 810003:
                return "jmui_server_810003";
            case 810005:
                return "jmui_server_810005";
            case 810007:
                return "jmui_server_810007";
            case 810008:
                return "jmui_server_810008";
            case 810009:
                return "jmui_server_810009";
            case 811003:
                return "jmui_server_811003";
            case 812002:
                return "jmui_server_812002";
            case 818001:
                return "jmui_server_818001";
            case 818002:
                return "jmui_server_818002";
            case 818003:
                return "jmui_server_818003";
            case 818004:
                return "jmui_server_818004";
            case 899001:
            case 898001:
                return "jmui_sdk_http_899001";
            case 898005:
                return "jmui_sdk_http_898005";
            case 898006:
                return "jmui_sdk_http_898006";
            case 898008:
                return "jmui_sdk_http_898008";
            case 898009:
                return "jmui_sdk_http_898009";
            case 898010:
                return "jmui_sdk_http_898010";
            case 898030:
                return "jmui_sdk_http_898030";
            case 800009:
            case 871104:
                return "jmui_sdk_87x_871104";
            case 871300:
                return "jmui_sdk_87x_871300";
            case 871303:
                return "jmui_sdk_87x_871303";
            case 871304:
                return "jmui_sdk_87x_871304";
            case 871305:
                return "jmui_sdk_87x_871305";
            case 871309:
                return "jmui_sdk_87x_871309";
            case 871310:
                return "jmui_sdk_87x_871310";
            case 871311:
                return "jmui_sdk_87x_871311";
            case 871312:
                return "jmui_sdk_87x_871312";
            case 871403:
                return "jmui_sdk_87x_871403";
            case 871404:
                return "jmui_sdk_87x_871404";
            case 871501:
                return "jmui_sdk_87x_871501";
            case 871502:
                return "jmui_sdk_87x_871502";
            case 871503:
                return "jmui_sdk_87x_871503";
            case 871504:
                return "jmui_sdk_87x_871504";
            case 871505:
                return "jmui_sdk_87x_871505";
            case 871506:
                return "jmui_sdk_87x_871506";
            case 871102:
            case 871201:
                return "jmui_sdk_87x_871201";
            default:
                return "JMessage Response status code: " + status;
        }
    }
}
