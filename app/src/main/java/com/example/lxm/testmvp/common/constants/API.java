package com.example.lxm.testmvp.common.constants;


import com.example.lxm.BuildConfig;

public interface API {
    // 请求地址
    String URL = BuildConfig.URL;
    interface NEWS{
        // 获取快讯列表
        String FAST_NEWS = "/soccernews/api/v8/fNew_find";
    }
}
