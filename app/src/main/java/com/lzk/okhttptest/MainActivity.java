package com.lzk.okhttptest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lzk.okhttptest.OkHttp.CommonOkhttpClient;
import com.lzk.okhttptest.OkHttp.Listener.DisposeDataHandle;
import com.lzk.okhttptest.OkHttp.Listener.DisposeDataListener;
import com.lzk.okhttptest.OkHttp.Request.CommonRequest;
import com.lzk.okhttptest.OkHttp.Request.RequestParams;
import com.lzk.okhttptest.OkHttp.Response.CommonJsonCallback;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.main_tv);
        requestWeather();
    }

    private void requestWeather(){
        RequestCenter.checkUserIsExist("15730266450", new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                UserIsExist userIsExist = (UserIsExist) responseObj;
                mTextView.setText("code:"+userIsExist.getCode()+"\n"+"msg:"+userIsExist.getMsg());
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }
}
