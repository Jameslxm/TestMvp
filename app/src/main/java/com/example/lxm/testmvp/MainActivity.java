package com.example.lxm.testmvp;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.baselib.common.base.CommonActivity;
import com.common.baselib.mvp.CreatePresenter;
import com.common.baselib.mvp.InjectPresenter;
import com.common.model.XianDuBean;
import com.example.lxm.R;
import com.example.lxm.testmvp.common.constants.HttpCode;
import com.example.lxm.testmvp.contract.MainContract;
import com.example.lxm.testmvp.presenter.MainPresenter;
import com.example.lxm.testmvp.ui.adapter.HomeAdapter;
import com.example.lxm.testmvp.widget.CustomLoadMoreView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
@CreatePresenter(value = {MainPresenter.class})
public class MainActivity extends CommonActivity implements MainContract.View, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView mRvHomeList;
    private HomeAdapter mAdapter;
    private SmartRefreshLayout mRefresh;
    @InjectPresenter
    private MainPresenter mPresenter;

    @Override
    protected Object setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRefresh = (SmartRefreshLayout)findViewById(R.id.refresh);
        mRvHomeList = (RecyclerView)findViewById(R.id.rv_home_list);
        mRvHomeList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HomeAdapter(mPresenter.mDatas);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnLoadMoreListener(this, mRvHomeList);
        mRvHomeList.setAdapter(mAdapter);

        mPresenter.reqeuestFetchToday(false);
        mRefresh.setOnRefreshListener(this);
    }

    @Override
    public void viewState(Object... obj) {
        if(obj[0] instanceof XianDuBean){
            mRefresh.finishRefresh();
            mAdapter.notifyDataSetChanged();
            XianDuBean data = (XianDuBean) obj[0];
            mRefresh.setEnableRefresh(true);
            mAdapter.setEnableLoadMore(true);
            if(data.code == HttpCode.SERVICE_SUCCESS){
                // 允许加载更多
                // 下拉刷新成功
                mRefresh.finishRefresh();
                mAdapter.notifyDataSetChanged();
//                mStateView.setViewState(MultiStateView.ViewState.CONTENT);
            }else if(data.code == HttpCode.SERVICE_EMPTY){
                // TODO 数据为空展示空页面
            }else if(data.code == HttpCode.SERVICE_FAIL){
                // TODO 请求一次点击重新加载
            }else if(data.code == HttpCode.SERVICE_SUCCESS_LOAD_MORE){
                mAdapter.loadMoreComplete();
            }else if(data.code == HttpCode.SERVICE_EMPTY_LOAD_MORE){
                mAdapter.loadMoreEnd(false);
            }else if(data.code == HttpCode.SERVICE_FAIL_LOAD_MORE){
                mAdapter.loadMoreFail();
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {
        // 禁止下拉刷新
        mRefresh.setEnableRefresh(false);
        mPresenter.reqeuestFetchToday(true);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        // 禁止加载更多
        mAdapter.setEnableLoadMore(false);
        mPresenter.reqeuestFetchToday(false);
    }
}
