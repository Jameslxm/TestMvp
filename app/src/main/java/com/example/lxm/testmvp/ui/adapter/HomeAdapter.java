package com.example.lxm.testmvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.common.model.XianDuBean;
import com.example.lxm.R;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<XianDuBean.DataBean.DatasBean, BaseViewHolder> {
    public HomeAdapter(@Nullable List<XianDuBean.DataBean.DatasBean> data) {
        super(R.layout.item_text, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, XianDuBean.DataBean.DatasBean item) {
        helper.setText(R.id.tv_item_title, item.getTitle());
    }
}
