package com.gars.httpquery;

import android.content.Context;

import com.gars.querybuilder.BaseQuery;

/**
 * Created by Владимир on 02.12.2015.
 */
public class Query extends BaseQuery<Query, MyResult> {
    public Query(Context context) {
        super(context);
    }

    public Query(Context context, boolean async) {
        super(context, async);
    }

    @Override
    protected Query preinit(String url) {
        return null;
    }

    @Override
    public void resultDebug(OnQuerySuccessListener successListener) {

    }

    @Override
    public boolean fail(MyResult stat, String data) {
        return false;
    }

    @Override
    public int getConnectErrorMessage() {
        return 0;
    }

    @Override
    public int getServerErrorMessage() {
        return 0;
    }
}
