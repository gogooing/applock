package com.sanqiu.loro.applocktest.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by loro on 2018/4/8.
 */

public class ConnectionUtil {

    public static String[] USER_AGENT = {
            "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.3 Mobile/14E277 Safari/603.1.30"
//            "Mozilla/5.0 (Linux; U; Android 2.3.6; zh-cn; GT-S5660 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 MicroMessenger/4.5.255",
//            "Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.2",
//            "Mozilla/5.0 (iPad; U; CPU OS 3_2_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B500 Safari/531.21.11",
//            "Mozilla/5.0 (SymbianOS/9.4; Series60/5.0 NokiaN97-1/20.0.019; Profile/MIDP-2.1 Configuration/CLDC-1.1) AppleWebKit/525 (KHTML, like Gecko) BrowserNG/7.1.18121",
//            //https://blog.csdn.net/yjflinchong
//            "Nokia5700AP23.01/SymbianOS/9.1 Series60/3.0",
//            "UCWEB7.0.2.37/28/998",
//            "NOKIA5700/UCWEB7.0.2.37/28/977",
//            "Openwave/UCWEB7.0.2.37/28/978",
//            "Mozilla/4.0 (compatible; MSIE 6.0; ) Opera/UCWEB7.0.2.37/28/989"
    };

    /**
     * @param urlStr 网址
     * @return 网页源码
     */
    public static String getContextByHttp(String urlStr) {
        StringBuilder sb = new StringBuilder();
        try {


            URL serverUrl = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) serverUrl
                    .openConnection();
            connection.setRequestMethod("GET");
            // 必须设置false，否则会自动redirect到Location的地址
            connection.setInstanceFollowRedirects(false);

            connection.addRequestProperty("Accept-Charset", "UTF-8;");
            connection.addRequestProperty("User-Agent", USER_AGENT[0]);
            connection.connect();
            String location = connection.getHeaderField("Location");

            serverUrl = new URL(location);
            connection = (HttpURLConnection) serverUrl.openConnection();
            connection.setRequestMethod("GET");

            connection.addRequestProperty("Accept-Charset", "UTF-8;");
            connection.addRequestProperty("User-Agent", USER_AGENT[0]);
            connection.connect();

            int code = connection.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                System.out.println("***********wap 连接失败************** ");
                System.out.println("*********** code： " + code);
                System.out.println("*********** url： " + urlStr);
                return "connection error:" + connection.getResponseCode();
            } else {

                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String temp;
                while ((temp = reader.readLine()) != null) {
                    sb.append(temp);
                }
                reader.close();

            }
            connection.disconnect();
        } catch (Exception e) {
            return e.toString();
        }
        return sb.toString();
    }

    //加载图片
    public static Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection connection = (HttpURLConnection) myurl.openConnection();
            connection.setConnectTimeout(6000);//设置超时
            connection.setDoInput(true);
            connection.setUseCaches(false);//不缓存
            connection.connect();

            InputStream is = connection.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    /**
     * @param urlStr 网址
     * @return 网页源码
     */
    public static String getContextByHttpJson(String urlStr) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String temp;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
            reader.close();

            connection.disconnect();
        } catch (Exception e) {
            return e.toString();
        }
        return sb.toString();
    }
}
