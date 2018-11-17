package com.example.lxm.testmvp;

import android.content.Context;

import com.common.baselib.callback.ICustomHeaderAndFoot;
import com.common.baselib.common.base.BaseApplication;
import com.example.lxm.testmvp.widget.CustomRefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshHeader;

public class TestApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        setmCustomHeaderAndFoot(new ICustomHeaderAndFoot() {
            @Override
            public RefreshHeader CustomHeader(Context context) {
                return new CustomRefreshHeader(context);
            }
        });
    }

}
