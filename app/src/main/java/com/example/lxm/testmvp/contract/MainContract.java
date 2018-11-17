package com.example.lxm.testmvp.contract;

import com.common.baselib.mvp.IView;

public interface MainContract {
    interface View extends IView {
        void viewState(Object ... obj);
    }
}
