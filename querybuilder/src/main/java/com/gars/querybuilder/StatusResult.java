package com.gars.querybuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by gars on 3/20/14.
 */
public class StatusResult {

    protected String data;
    protected String msg;
    protected Header[] headers;
    protected TYPE type;
    protected STATUS status = STATUS.SUCCESS;

    public enum TYPE{
        OBJECT, ARRAY, STRING
    }
    public enum STATUS{
        SUCCESS, ERROR, SERVERERROR
    }

    public StatusResult() {}

    public StatusResult(String data) {
        this(data, TYPE.STRING);
    }

    public StatusResult(String data, TYPE type){
        this.type = type;
        if (data == null)
            return;
        this.data = data;
    }


    public JSONArray getResultArray() {
        try {
            if(data!=null)
                return new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getResultObject() {
        try {
            if(data!=null)
                return new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getResponse() {
        return data;
    }

    public boolean isSuccess() {
        return status == STATUS.SUCCESS;
    }

    public boolean isError() {
        return status == STATUS.ERROR;
    }

    public boolean isServerError() {
        return status == STATUS.SERVERERROR;
    }

    public void setSuccess(){
        status = STATUS.SUCCESS;
    }
    public void setError(){
        status = STATUS.ERROR;
    }

    public void setServerError() {
        status = STATUS.SERVERERROR;
    }

    public String getMsg() {
        return msg != null ? msg : "";
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getHeader(String key){
        getHeader(key, null);
        return null;
    }

    public String getHeader(String key, String searchParam){
        if(headers == null)
            return null;
        for(Header head : headers){
            if(head.getName().equalsIgnoreCase(key)){
                if(searchParam == null)
                    return head.getValue();
                else if (head.getValue().contains(searchParam))
                    return head.getValue();
            }
        }

        return null;
    }

}
