package com.lzk.okhttptest;

import com.lzk.okhttptest.OkHttp.CommonOkhttpClient;
import com.lzk.okhttptest.OkHttp.Listener.DisposeDataHandle;
import com.lzk.okhttptest.OkHttp.Listener.DisposeDataListener;
import com.lzk.okhttptest.OkHttp.Request.CommonRequest;
import com.lzk.okhttptest.OkHttp.Request.RequestParams;

/**
 * Created by administrator on 2019/8/2.
 * 存放应用中所有的请求
 */

public class RequestCenter {

    private static void postRequest(String url, RequestParams params, DisposeDataListener listener,
                                    Class<?> clazz){
        CommonOkhttpClient.sendRequest(CommonRequest.createPostRequest(url,params),new DisposeDataHandle(listener,clazz));
    }

    public static void checkUserIsExist(String username,DisposeDataListener listener){
        RequestParams params = new RequestParams();
        params.put("username",username);
        RequestCenter.postRequest("http://www.athenall.com:8080/json/singleProductUserExist",params,listener,UserIsExist.class);
    }
}
