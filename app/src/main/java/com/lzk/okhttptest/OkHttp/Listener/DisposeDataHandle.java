package com.lzk.okhttptest.OkHttp.Listener;

/**
 * Created by administrator on 2019/8/2.
 */

public class DisposeDataHandle {
    public DisposeDataListener mListener;
    public Class<?> mClass = null;

    public DisposeDataHandle (DisposeDataListener listener){
        mListener = listener;
    }

    public DisposeDataHandle (DisposeDataListener listener , Class<?> clazz){
        mListener = listener;
        mClass = clazz;
    }
}
