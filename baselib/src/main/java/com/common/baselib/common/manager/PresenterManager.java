package com.common.baselib.common.manager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.common.baselib.mvp.CreatePresenter;
import com.common.baselib.mvp.IPresenter;
import com.common.baselib.mvp.IView;
import com.common.baselib.mvp.InjectPresenter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PresenterManager <P extends IPresenter, V extends IView> {
    private Map<String, P> mPresenterMaps = new HashMap<>();

    public static PresenterManager inject(Activity activity) {
        return new PresenterManager(activity);
    }

    public static PresenterManager inject(Fragment fragment) {
        return new PresenterManager(fragment);
    }

    private PresenterManager(Activity activity){
        init(activity.getClass(), activity);
    }

    private PresenterManager(Fragment fragment){
        init(fragment.getClass(), fragment);
    }

    private void init(Class c, Object object){
        initClassCreate(c);

        initClassInject(c, object);
    }

    // 获取注解类并创建对象
    private void initClassCreate(Class<?> c) {
        CreatePresenter cp = c.getAnnotation(CreatePresenter.class);
        if(cp != null){
            Class<P>[] clazzs = (Class<P>[]) cp.value();
            for(Class<P> clazz : clazzs){
                try {
                    mPresenterMaps.put(clazz.getCanonicalName(), clazz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 给注解中类赋值
    private void initClassInject(Class c, Object object) {
        Field[] fields = c.getDeclaredFields();
        if(fields != null){
            for (Field field : fields){
                Annotation[] anns = field.getDeclaredAnnotations();
                if (anns.length < 1) {
                    continue;
                }
                if(anns[0] instanceof InjectPresenter){
                    String canonicalName = field.getType().getName();
                    P newPresenter = mPresenterMaps.get(canonicalName);
                    if(newPresenter != null){
                        try {
                            field.setAccessible(true);
                            field.set(object, newPresenter);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    public void attachView(Context context){
        for (Map.Entry<String, P> entry : mPresenterMaps.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.attachView(context);
            }
        }
    }
    public void attachView(Context context, V view){
        for (Map.Entry<String, P> entry : mPresenterMaps.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.attachView(context, view);
            }
        }
    }

    public void destroy(){
        for (Map.Entry<String, P> entry : mPresenterMaps.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.destroy();
            }
        }
    }

    public void init(Activity mActivity) {

    }
}
