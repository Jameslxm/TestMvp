package com.common.baselib.common.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.common.baselib.callback.ICustomHeaderAndFoot;
import com.common.baselib.common.constants.ConstantsValue;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public abstract class BaseApplication extends Application {

//    /** 初始化友盟第三方登录配置 */
//    {
//        PlatformConfig.setWeixin(ConstantsValue.AppKey.WX_KEY, ConstantsValue.AppKey.WX_SECRET);
//        PlatformConfig.setQQZone(ConstantsValue.AppKey.QQ_ID, ConstantsValue.AppKey.QQ_KEY);
//        PlatformConfig.setSinaWeibo(ConstantsValue.AppKey.SINA_KEY, ConstantsValue.AppKey.SINA_SECRET, ConstantsValue.AppKey.SINA_CALLBACK);
//    }
    private static ICustomHeaderAndFoot mCustomHeaderAndFoot;

    public static void setmCustomHeaderAndFoot(ICustomHeaderAndFoot mCustomHeaderAndFoot) {
        BaseApplication.mCustomHeaderAndFoot = mCustomHeaderAndFoot;
    }

    /** 全局下拉刷新加载更多头部样式和底部样式 */
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setEnableOverScrollBounce(false);
                return mCustomHeaderAndFoot.CustomHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                layout.setEnableLoadMore(false);
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        // 初始化核心库
//        Tcore.init(this).withAppName("zd_shop");

        // 初始化okgo
        initOkgo();
//
//        // 初始化umeng
//        initUmeng();
//
//        // 初始化x5内核
//        initX5();
//
//        // 初始化LOG日志
//        LogUtils.init(ConstantsValue.IS_LOG ? LogUtils.LEVEL_ERROR : LogUtils.LEVEL_NONE);
    }

    private void initLog() {
        if(ConstantsValue.IS_LOG) {
            Logger.addLogAdapter(new AndroidLogAdapter());
        }
    }

    // 初始化x5内核
    private void initX5() {
//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                // TODO Auto-generated method stub
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//            }
//
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//            }
//        };
//        //x5内核初始化接口
//        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    // 初始化友盟
    private void initUmeng() {
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
//
//        UMShareConfig config = new UMShareConfig();
//        config.isNeedAuthOnGetUserInfo(true);
//        UMShareAPI.get(this).setShareConfig(config);
    }

    // 初始化okgo
    private void initOkgo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("okgo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(ConstantsValue.IS_LOG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        //全局的读取超时时间
        builder.readTimeout(10 * 1000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(10 * 1000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
        // 公共请求头
        HttpHeaders headers = new HttpHeaders();
        OkGo.getInstance()
                .setRetryCount(1)
                .addCommonHeaders(headers)                        //全局公共头
                .setOkHttpClient(builder.build())
                .init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 65535
        MultiDex.install(this);
    }

}
