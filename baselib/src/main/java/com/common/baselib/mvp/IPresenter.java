package com.common.baselib.mvp;

import android.content.Context;

/**
 * Created by zhenkuntang on 2018/5/29.
 */

public interface IPresenter<V extends IView>{
    void attachView(Context context, V view);
    void attachView(Context context);
    void destroy();
}
