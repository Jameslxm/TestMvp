package com.example.lxm.testmvp.presenter;

import com.common.baselib.databus.RegisterBus;
import com.common.baselib.databus.RxBus;
import com.common.baselib.utils.L;
import com.common.model.XianDuBean;
import com.example.lxm.testmvp.common.constants.HttpCode;
import com.example.lxm.testmvp.contract.MainContract;
import com.common.baselib.mvp.BasePresenter;
import com.example.lxm.testmvp.model.MainModel;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter extends BasePresenter<MainContract.View> {
    public List<XianDuBean.DataBean.DatasBean> mDatas = new ArrayList<>();
    private boolean mIsLoadMore;
    private int mPageIndex =1;
    private MainModel mModel;
    public MainPresenter(){
        mModel = new MainModel();
        RxBus.getInstance().register(this);
    }
    public void  reqeuestFetchToday(boolean isLoadMore){
        this.mIsLoadMore = isLoadMore;
        L.d(mPageIndex + "isLoadMore:"+isLoadMore);
        if(!isLoadMore){
            mPageIndex = 1;
        }
        mModel.reqeuestFetchFastNews(mPageIndex);
    }
    @RegisterBus
    public void rxReqeuestFetchFastNews(XianDuBean data){
        if(data.getErrorCode() == HttpCode.SERVICE_SUCCESS){      // 请求成功
            vLoading.showContent();
            mPageIndex = mPageIndex + 1;
            if(mIsLoadMore){
                mDatas.addAll(data.getData().getDatas());
                data.code = HttpCode.SERVICE_SUCCESS_LOAD_MORE;
            }else{
                mDatas.clear();
                mDatas.addAll(data.getData().getDatas());
            }
        }else if(data.code == HttpCode.SERVICE_EMPTY){  // 空数据
            if(mIsLoadMore){
                data.code = HttpCode.SERVICE_EMPTY_LOAD_MORE;
            }else {
                vLoading.showEmpty();
            }
        }else if(data.code == HttpCode.SERVICE_FAIL){   // 请求超时或服务器错误
            if(mIsLoadMore){
                data.code = HttpCode.SERVICE_FAIL_LOAD_MORE;
            }
        }

        mView.viewState(data);
    }
    @Override
    public void destroy() {
        RxBus.getInstance().unRegister(this);
        cancelNetRequestTag(mModel);
    }
}
