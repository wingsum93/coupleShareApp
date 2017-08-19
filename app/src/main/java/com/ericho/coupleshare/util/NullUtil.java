package com.ericho.coupleshare.util;

import java.util.Collection;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.util
 */

public class NullUtil {

    public static boolean isNull(Object object){
        return object!=null ;
    }
    public static boolean isNullOrEmpty(Collection<?> collection){
        return collection==null || collection.isEmpty();
    }
}
