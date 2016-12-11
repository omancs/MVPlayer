package com.itheima.mvplayer.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.mvplayer.utils.URLProviderUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkManager {
    public static final String TAG = "NetworkManager";

    private Gson mGson;

    private static NetworkManager mNetworkManager;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private NetworkManager() {
        mOkHttpClient = new OkHttpClient();
        mGson = new Gson();
    }


    public static NetworkManager getInstance() {
        if (mNetworkManager == null) {
            synchronized (NetworkManager.class) {
                if (mNetworkManager == null) {
                    mNetworkManager = new NetworkManager();
                }
            }
        }
        return mNetworkManager;
    }

    public void loadHomeData(final NetworkCallback callback) {
        Request request = new Request.Builder().get().url(URLProviderUtil.getHomeUrl(0, 10)).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultString = response.body().string();//Notice it not toString(), is string()
                Log.d(TAG, "onResponse: " + resultString );
                final List<HomeItemBean> result = mGson.fromJson(resultString, new TypeToken<List<HomeItemBean>>(){}.getType());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onSuccess(result);
                        }
                    }
                });
            }
        });
    }


}