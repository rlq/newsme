package com.he.util;

import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;
import android.os.Handler;
import com.google.gson.internal.$Gson$Types;

public class OkHttpUtil {

    private OkHttpClient client;
    private Handler mHandle;
    private static OkHttpUtil instance;
    public synchronized static OkHttpUtil getInstance(){
        if(instance == null){
            instance = new OkHttpUtil();
        }
        return instance;
    }

    private OkHttpUtil(){
        client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setCookieHandler(new CookieManager(null,
                CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mHandle = new Handler(Looper.getMainLooper());
    }

    public static void doGetRequest(String url,OkHttpCallBack callBack){
        Request request = new Request.Builder().url(url).build();
        getInstance().deliveryResult(request,callBack);
    }

    public static void soPostRequest(String url, List<PostParams> params, OkHttpCallBack callBack){
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (PostParams para :params) {
            builder.add(para.key, para.value);
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        getInstance().deliveryResult(request, callBack);
    }

    private void deliveryResult(Request request, final OkHttpCallBack callBack){
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                sendFailCallback(callBack, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String str = response.body().string();
                if(callBack.mType == String.class){
                    sendSuccessCallBack(callBack,str);
                }else{
                    try {
                        Object obj = JSON.parseObject(str,callBack.mType);
                        Object obj2 = new Gson().fromJson(str,callBack.mType);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void sendFailCallback(final OkHttpCallBack callback, final Exception e) {
        mHandle.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        });
    }

    private void sendSuccessCallBack(final OkHttpCallBack callback, final Object obj) {
        mHandle.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(obj);
                }
            }
        });
    }

    /**请求
     * 回调
     **/
    public static abstract class OkHttpCallBack<T>{

        Type mType;
        public OkHttpCallBack(){
            mType = getSuperClassTypeParameter(getClass());
        }
        Type getSuperClassTypeParameter(Class<?> subClass){
            Type superClass = subClass.getGenericSuperclass();
            if(superClass instanceof Class){
                throw new RuntimeException("Missing type parameter");
            }
            ParameterizedType parameterized = (ParameterizedType) superClass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }
        public abstract void onSuccess(T response);

        public abstract void onFailure(Exception e);

    }

    class PostParams{
        String key;
        String value;

        public PostParams(String key, String value){
            this.key = key;
            this.value = value;
        }
    }
}

