package com.example.lxm.testmvp.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lxm.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * 自定义下拉顶部样式：
 * 实现接口中有对应注释
 */
public class CustomRefreshHeader extends LinearLayout implements RefreshHeader {

    private ImageView mImage, mImageBox;
    private TextView mTvRefresh;
    private AnimationDrawable refreshingAnim;

    public CustomRefreshHeader(Context context) {
        this(context, null, 0);
    }

    public CustomRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.layout_refresh_header, this);
        mImage = view.findViewById(R.id.iv_refresh);
        mImageBox = view.findViewById(R.id.iv_refresh_box);
        mTvRefresh = view.findViewById(R.id.tv_refresh);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }


    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case PullDownToRefresh: // 下拉刷新开始。正在下拉还没松手时调用
                mImageBox.setVisibility(View.VISIBLE);
                mImage.setImageResource(R.mipmap.ic_refresh_0);
                mImageBox.setImageResource(R.mipmap.ic_refresh_box);
                mTvRefresh.setText("下拉刷新...");
                break;
            case Refreshing: // 正在刷新。只调用一次
                mImageBox.setVisibility(View.GONE);
                mImage.setImageResource(R.drawable.list_refresh);
                refreshingAnim = (AnimationDrawable) mImage.getDrawable();
                refreshingAnim.start();
                mTvRefresh.setText("更新中...");
                break;
            case ReleaseToRefresh: // 下拉达到下拉布局高度回调
                mTvRefresh.setText("松开刷新...");
                break;
            case RefreshFinish: // 刷新结束
                // 结束动画
                if (refreshingAnim != null) {
                    refreshingAnim.stop();
                    refreshingAnim = null;
                }

                mImage.setImageResource(R.mipmap.ic_refresh_0);
                mImageBox.setImageResource(R.mipmap.ic_refresh_box);
                break;
        }
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        return 0;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImage.getLayoutParams();
        params.rightMargin = height;
        mImage.requestLayout();
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        // 下拉的百分比小于100%时，不断调用 setScale 方法改变图片大小
        if (percent <= 1) {
            int newOffset = height - offset;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImage.getLayoutParams();
            params.rightMargin = newOffset;
            mImage.requestLayout();

            params = (RelativeLayout.LayoutParams) mImageBox.getLayoutParams();
            params.topMargin = (offset >> 1) - 20 - (16 << 1);
            mImage.requestLayout();

            mImage.setScaleX(percent);
            mImage.setScaleY(percent);
            mImage.setAlpha(percent);

            mImageBox.setScaleX(percent);
            mImageBox.setScaleY(percent);
            mImageBox.setAlpha(percent);
        }else{
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImage.getLayoutParams();
            params.rightMargin = 0;
            mImage.requestLayout();

            params = (RelativeLayout.LayoutParams) mImageBox.getLayoutParams();
            params.topMargin = height / 2 - 20 - (16 << 1);
            mImage.requestLayout();

            mImage.setAlpha(1.0f);
            mImage.setScaleX(1.0f);
            mImage.setScaleY(1.0f);

            mImageBox.setAlpha(1.0f);
            mImageBox.setScaleX(1.0f);
            mImageBox.setScaleY(1.0f);
        }
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
}
