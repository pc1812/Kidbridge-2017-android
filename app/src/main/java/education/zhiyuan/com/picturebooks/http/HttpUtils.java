package education.zhiyuan.com.picturebooks.http;

import android.net.Uri;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.utils.Util;

/**
 * Created by Spring on 2017/8/2.
 */

public class HttpUtils {
    public String doPost(String url, Map map) {
        Map newMap = new HashMap();
        if (map.containsKey("token")) {
            newMap.put("token", map.get("token").toString());
            map.remove("token");
        }
        newMap.put("version", MyApp.versionName);
        newMap.put("timestamp", map.get("timestamp").toString());
        newMap.put("uri", Uri.parse(url).getPath());
        newMap.put("device", "android");
        newMap.put("sign", Util.sign(newMap));
        map.remove("version");
        map.remove("timestamp");
        map.remove("sign");
        map.remove("uri");
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL u = new URL(url);
            urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            if (newMap.containsKey("token")) {
                urlConnection.setRequestProperty("token", newMap.get("token").toString());
            }
            urlConnection.setRequestProperty("version", newMap.get("version").toString());
            urlConnection.setRequestProperty("timestamp", newMap.get("timestamp").toString());
            urlConnection.setRequestProperty("device", "android");
            urlConnection.setRequestProperty("sign", newMap.get("sign").toString());
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            Gson gson = new Gson();
            String jsonString = gson.toJson(map);
            wr.write(jsonString.getBytes());
            wr.flush();
            wr.close();
            int statusCode = urlConnection.getResponseCode();
            // 超时处理
            urlConnection.setReadTimeout(5 * 1000);
            urlConnection.setConnectTimeout(5 * 1000);
            // 建立实际连接
            urlConnection.connect();
            if (statusCode == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String response = getRequestStreamToString(inputStream);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }


    protected String getRequestStreamToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine = null;
        BufferedReader in = null;
        in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((inputLine = in.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        return stringBuilder.toString();
    }


    public String loadFile(String path, String filePath) {
        try {
            URL url = new URL(path);
            // 连接对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置连接方式
            conn.setRequestMethod("POST");
            // 超时处理
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            // 建立实际连接
            conn.connect();
            // 接收数据
            if (conn.getResponseCode() == 200) {
                // 输入流，读信息
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(filePath);
                byte[] buf = new byte[1024 * 10];
                int len = 0;
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
                return filePath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public byte[] loadImg(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            conn.connect();
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buf = new byte[1024 * 10];
                int len = 0;
                while ((len = is.read(buf)) != -1) {
                    output.write(buf, 0, len);
                    output.flush();
                }
                byte[] bytes = output.toByteArray();
                output.close();
                is.close();
                return bytes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

