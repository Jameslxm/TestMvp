package com.common.baselib.mvp;

import android.app.Activity;
import android.content.Context;

import com.common.baselib.common.base.CommonActivity;
import com.lzy.okgo.OkGo;

import ezy.ui.layout.LoadingLayout;

public abstract class BasePresenter<V extends IView> implements IPresenter<V> {

    protected Context mContext;
    protected V mView;
    protected LoadingLayout vLoading;
    @Override
    public void attachView(Context context, V view) {
        this.mContext = context;
        this.mView = view;

    }
    @Override
    public void attachView(Context context) {
        this.mContext = context;
        vLoading = LoadingLayout.wrap((Activity) context);
    }
    protected void cancelNetRequestTag(BaseModel tag){
        OkGo.getInstance().cancelTag(tag);

    }

}
