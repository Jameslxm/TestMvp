package com.common.baselib.utils;

import com.common.baselib.common.constants.ConstantsValue;
import com.orhanobut.logger.Logger;

/**
 * 日志
 */
public class L {
    public static void d(Object object){
        if(ConstantsValue.IS_LOG) {
            Logger.d(object);
        }
    }
}
