package com.example.lxm.testmvp.model;

import com.common.baselib.callback.JsonCallback;
import com.common.baselib.databus.RxBus;
import com.common.baselib.mvp.BaseModel;
import com.common.model.XianDuBean;
import com.example.lxm.testmvp.B2bInterface;
import com.example.lxm.testmvp.common.constants.HttpCode;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class MainModel extends BaseModel {
    public void reqeuestFetchFastNews(int pageIndex){
        String url = B2bInterface.CommonInterface.XIAN_DU.replace("replaceId", "408").replace("pageIndex", pageIndex + "");
        OkGo.<XianDuBean>get(url)
                .tag(this)
                .execute(new JsonCallback<XianDuBean>() {
                    @Override
                    public void onSuccess(XianDuBean data) {
                        if(data.getErrorCode() == HttpCode.SERVICE_SUCCESS){
                            if(data.getData() == null || data.getData().getDatas().size() <= 0){
                                data.code = HttpCode.SERVICE_EMPTY;
                            }else{
                                data.code = HttpCode.SERVICE_SUCCESS;
                            }
                        }else{
                            data.code = HttpCode.SERVICE_EMPTY;
                        }
                        RxBus.getInstance().send(data);
                    }

                    @Override
                    public void onError(XianDuBean data) {
                        RxBus.getInstance().send(data);
                    }
                });

    }
}
