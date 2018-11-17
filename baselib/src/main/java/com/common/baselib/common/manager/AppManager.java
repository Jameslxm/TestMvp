package com.common.baselib.common.manager;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * 管理Activity工具类
 *
 * @author Administrator
 */
public class AppManager {

    private Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager(){}

    /**
     * 单例模式
     */
    public static AppManager getAppManager(){
        if(instance == null){
            synchronized (AppManager.class) {
                if(instance == null){
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }
    /**
     * 添加Activity到堆栈
     */
    public void init(Activity activity){
        if(activityStack == null){
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 删除Activity
     * @param activity
     */
    public void destroy(Activity activity){
        if(activity!=null){
            activityStack.remove(activity);
            activity = null;
        }
    }
    /**
     * 获取当前Activity(堆栈中最后一个压入的)
     */
    public Activity currentActivity(){
        return activityStack.lastElement();
    }

    public Activity firstActivity(){
        return activityStack.firstElement();
    }

    /**
     * 结束当前Activity(堆栈中最后一个压入的)
     */
    public void finishActivity(){
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }
    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity){
        try {
            if(activity!=null){
//                activityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        }catch (Exception e){}
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls){
        for (Activity activity : activityStack) {
            if(activity.getClass().equals(cls)){
                finishActivity(activity);
            }
        }
    }
    /**
     * 结束所有Activity
     */
    public void finishAllActivity(){
        for (int i = 0; i < activityStack.size(); i++) {
            if(null != activityStack.get(i)){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /** 关闭activity，设置保留activity个数 */
    public void finishKeepActivity(int count){
        try{
            for (int i = activityStack.size() - 1; i > 0; i--) {
                if(i == count - 1){
                    return;
                }
                if(null != activityStack.get(i) && i != 0){
                    finishActivity(activityStack.get(i));
//                    activityStack.remove(activityStack.get(i));
                }
            }
        }catch (Exception e){}
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context){
        try {
            finishAllActivity();
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            manager.restartPackage(context.getPackageName());
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stack<Activity> getActivityStack() {
        return activityStack;
    }
}
