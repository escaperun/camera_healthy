/*
package com.fullsee.camera_healthy.orc;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

*/
/**
 * @author fullsee
 *//*

public class BaiduOcr {
    //设置APPID/AK/SK
    public static final String APP_ID = "23052911";
    public static final String API_KEY = "UaxlvRmajyRpRLIMxEWINY2Y";
    public static final String SECRET_KEY = "XRMWTB82AhHxxaxN6ed2DSMKguVOyhn6";

    public static void main(String[] args) {

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("language_type", "CHN_ENG");
//        options.put("detect_direction", "true");
//        options.put("detect_language", "true");
//        options.put("probability", "true");

        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        String path = "E:\\new\\find14.jpg";
        JSONObject res = client.basicGeneral(path, options);
        System.out.println(res.toString(2));
        
    }
}*/
