package com.gars.querybuilder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Владимир on 25.08.2015.
 */
public class QueryTool {
    public boolean getConnection(Context c) {
        ConnectivityManager cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isEnable = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        cm = null;
        activeNetwork = null;
        return isEnable;
    }

    public static void showMsg(Context context, String msg) {
        if(context != null) {
            Toast m = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            m.setGravity(48, 0, 60);
            m.show();
        }
    }
}
