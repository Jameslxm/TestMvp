package com.common.baselib.callback;

import com.alibaba.fastjson.JSON;
import com.common.model.base.BaseBean;
import com.lzy.okgo.callback.AbsCallback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

public abstract class JsonCallback<T> extends AbsCallback<T> {
    protected Type mType;
    public JsonCallback(){
        ParameterizedType parameterized = (ParameterizedType) getClass()
                .getGenericSuperclass();
        mType = parameterized.getActualTypeArguments()[0];
    }

    @Override
    public T convertResponse(Response response) throws IOException {
        T data = null;
        if(mType == String.class){
            data = (T) response.body().string();
        }else if(mType == BaseBean.class){
            BaseBean bean = JSON.parseObject(response.body().string(), mType);
            if(200 == bean.code){
                data = (T) bean;
            }else{

            }
        }else{
            data = JSON.parseObject(response.body().string(), mType);
        }
        return data;
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        onSuccess(response.body());
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        onError(response.body());
    }

    public abstract void onSuccess(T data);
    public abstract void onError(T data);
}
