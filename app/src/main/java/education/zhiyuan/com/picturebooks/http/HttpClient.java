package education.zhiyuan.com.picturebooks.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import education.zhiyuan.com.picturebooks.MyApp;
import me.xiaopan.android.net.NetworkUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/8/30.
 */
public class HttpClient {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static Handler handler = new Handler(Looper.getMainLooper());

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .addInterceptor(new LoggingInterceptor())
//            .addNetworkInterceptor(new StethoInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    /**
     * 上传多张或者单张图片
     *
     * @param tag
     * @param url
     * @param key
     * @param map
     * @param paths
     * @param callBack
     */
    public static void postImage(Object tag, String url, String key, HashMap<String, String> map, List<String> paths, final CallBack callBack) {

        if (!NetworkUtils.isConnectedByState(MyApp.getContext())) {
            callBack.onFailure("网络开小差了！！");
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        for (String path : paths) {
            builder.addFormDataPart(key, path, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
        }
        for (String s : map.keySet()) {
            builder.addFormDataPart(s, map.get(s));
        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .tag(tag)
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                json = json.replace("null", "\"\"");
                final String finalJson = json;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callBack.onSuccess(new Gson().fromJson(finalJson, callBack.type));
                        } catch (Exception e) {
                            callBack.onFailure(e.getLocalizedMessage());
                        }

                    }
                });

            }
        });

    }

    /**
     * post请求
     *
     * @param tag
     * @param url
     * @param map
     * @param callBack
     */
    public static void post(Object tag, String url, HashMap<String, Object> map, final CallBack callBack) {

        if (!NetworkUtils.isConnectedByState(MyApp.getContext())) {
            callBack.onFailure("网络开小差了！！");
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            for (String s : map.keySet()) {
                System.out.println("s:" + s + "-ss:" + map.get(s));
                builder.add(s, String.valueOf(map.get(s)));
            }
        }
        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .tag(tag)
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                json = json.replace("null", "\"\"");
                System.out.println("json = " + json);

                final String finalJson = json;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            com.orhanobut.logger.Logger.json(finalJson);
                            Logger.json(finalJson);
                            System.out.println("success");
                            callBack.onSuccess(new Gson().fromJson(finalJson, callBack.type));
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.onFailure(e.getLocalizedMessage());
                            System.out.println("error:" + e.getLocalizedMessage());
                        }

                    }
                });

            }
        });
    }

    /**
     * get请求，通过接口回调返回值
     * */
    public static void get(final Object tag, final String url, final CallBack callBack) {
        if (!NetworkUtils.isConnectedByState(MyApp.getContext())) {
            callBack.onFailure("网络开小差了！！");
            return;
        }

        Request request = new Request.Builder()
                .tag(tag)
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                String json = response.body().string();
                json = json.replace("\"null\"", "\"\"");
                final String finalJson = json;
                System.out.println("json = " + json);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //    Logger.json(finalJson);
                            System.out.println("hhh:" + finalJson);
                            System.out.println("hhh:" + callBack.type);
                            callBack.onSuccess(new Gson().fromJson(finalJson, callBack.type));
                        } catch (Exception e) {
                            callBack.onFailure(e.getLocalizedMessage());
                        }
                    }
                });

            }
        });

    }

    /**
     * 请求响应日志信息，方便debug
     */
    /*public static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Logger.i(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Logger.i(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }*/
    public static void getStr(final Object tag, final String url, final CallBack<String> callBack) {
        if (!NetworkUtils.isConnectedByState(MyApp.getContext())) {
            callBack.onFailure("网络开小差了！！");
            Logger.d("network error");
            return;
        }

        Request request = new Request.Builder()
                .tag(tag)
                .url(url)
                .build();
        Logger.d("get:url:" + url);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getLocalizedMessage() + "error url:"
                                + url);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                resultStr = resultStr.replace("null", "\"\"");
                final String finalStr = resultStr;
                Logger.d(resultStr);
                Logger.xml(resultStr);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Logger.resultStr(finalStr);
                            callBack.onSuccess(finalStr);

                        } catch (Exception e) {
                            callBack.onFailure(e.getLocalizedMessage() + "error url:"
                                    + url);
                        }
                    }
                });

            }
        });

    }

}
