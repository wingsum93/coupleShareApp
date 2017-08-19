package com.ericho.coupleshare.network;

import android.content.Context;

import java.io.IOException;

/**
 * Created by steve_000 on 17/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http
 */

public interface OkHttp {

    String registerUser(Context context, String username, String password) throws IOException;
}
