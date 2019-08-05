package com.lzk.okhttptest.OkHttp.Response;

import android.os.Handler;
import android.os.Looper;

import com.lzk.okhttptest.OkHttp.Listener.DisposeDataHandle;
import com.lzk.okhttptest.OkHttp.Listener.DisposeDataListener;
import com.lzk.okhttptest.OkHttp.Util.ResponseEntityToModule;
import com.lzk.okhttptest.OkHttp.exception.OkHttpException;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by administrator on 2019/8/2.
 */

public class CommonJsonCallback implements Callback{

    protected final String RESULT_CODE = "code"; // 错误码字段名
    protected final String RESULT_CODE_VALUE = "0000";//错误码值
    protected final String ERROR_MSG = "msg";//错误信息字段名
    protected final String EMPTY_MSG = "";

    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int JSON_ERROR = -2; // the JSON relative error
    protected final int OTHER_ERROR = -3; // the unknow error

    /**
     * 将其它线程的数据转发到UI线程
     */
    private Handler mDeliveryHandler;
    private DisposeDataListener mListener;
    private Class<?> mClass;

    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void onFailure(Call call, final IOException e) {
        /**
         * 此时还在非UI线程，因此要转发
         */
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(String responseStr){
        if (responseStr == null && responseStr.trim().equals("")){
            mListener.onFailure(new OkHttpException(NETWORK_ERROR,EMPTY_MSG));
            return;
        }

        try{
            JSONObject result = new JSONObject(responseStr);
            if (result.has(RESULT_CODE)){
                //服务器返回成功
                if (result.getString(RESULT_CODE).equals(RESULT_CODE_VALUE)){
                    if (mClass == null){//不需要将结果转化为实体类
                        mListener.onSuccess(responseStr);
                    }else {//需要将结果转化为实体类
                        Object obj = ResponseEntityToModule.parseJsonObjectToModule(result, mClass);
                        if (obj != null) {
                            if (mListener!=null){
                                mListener.onSuccess(obj);
                            }
                        } else {
                            mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                        }
                    }
                }else {//服务器返回失败
                    mListener.onFailure(new OkHttpException(OTHER_ERROR,result.getString(ERROR_MSG)));
                }
            }

        }catch (Exception e){
            mListener.onFailure(new OkHttpException(OTHER_ERROR, e.getMessage()));
            e.printStackTrace();
        }
    }
}
