package com.ericho.coupleshare.json;

import com.ericho.coupleshare.http.GsonUtil;
import com.ericho.coupleshare.http.model.BaseSingleResponse;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Type;

/**
 * Created by steve_000 on 18/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.json
 */
@RunWith(JUnit4.class)
public class GsonTest {
    @Test
    public void testPaseJson(){
        String res = "{\"status\":false,\"extra\":null,\"errorMessage\":\"account already register\",\"otherMessage\":null}";
        Type type = new TypeToken<BaseSingleResponse>(){}.getType();
        BaseSingleResponse baseSingleResponse = GsonUtil.getGson().fromJson(res,type);

    }
}
