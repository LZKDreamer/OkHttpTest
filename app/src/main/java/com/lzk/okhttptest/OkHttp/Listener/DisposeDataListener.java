package com.lzk.okhttptest.OkHttp.Listener;

/**
 * Created by administrator on 2019/8/2.
 */

public interface DisposeDataListener {

    /**
     * 请求成功回调
     * @param responseObj
     */
    public void onSuccess(Object responseObj);

    /**
     * 请求失败回调
     * @param reasonObj
     */
    public void onFailure(Object reasonObj);
}
