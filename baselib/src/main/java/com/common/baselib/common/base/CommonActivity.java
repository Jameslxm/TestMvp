package com.common.baselib.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.common.baselib.common.manager.AppManager;
import com.common.baselib.common.manager.PresenterManager;
import com.common.baselib.mvp.IView;
import com.gyf.barlibrary.ImmersionBar;

import ezy.ui.layout.LoadingLayout;

public abstract class CommonActivity extends AppCompatActivity implements View.OnClickListener, IView {
    protected ImmersionBar mImmersionBar;
    protected Activity mActivity;

    private PresenterManager mPresenterManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().init(this);

        mActivity = this;
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
//                .keyboardEnable(onKeyboard())
//                .navigationBarColor(Tcore.getColor(R.color.color_f1f1f1))
                .statusBarColor(onStatusBarColor())
                .statusBarDarkFont(onStatusBarDarkFont(), 0.2f)
                .init();   // 所有子类都将继承这些相同的属性

        mPresenterManager = PresenterManager.inject(this);
        mPresenterManager.attachView(this, this);
        Object obj = setLayoutId();
        if(obj instanceof View){
            setContentView((View) obj);
        }else if(obj instanceof Integer){
            setContentView((Integer) obj);
        }
        mPresenterManager.attachView(this);
        // 初始化view
        initView(savedInstanceState);
        // 初始化数据
        initData();
    }

    @Override
    public void onClick(View v) {

    }

    // 释放资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().destroy(this);

        if(mImmersionBar != null){
            mImmersionBar.destroy();
        }

        mPresenterManager.destroy();
    }

    // 设置资源文件
    protected abstract Object setLayoutId();

    // 初始化View
    protected abstract void initView(Bundle savedInstanceState);

    // 初始化数据
    protected void initData() {}

    // 当前状态栏样色
    protected int onStatusBarColor(){return android.R.color.transparent;}

    // 状态栏颜色是否为深色
    protected boolean onStatusBarDarkFont(){return false;}
}
