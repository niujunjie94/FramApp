package com.niujunjie.www.framapp.net;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.niujunjie.www.framapp.utils.Constants;
import com.niujunjie.www.framapp.utils.TLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niujunjie on 2015/7/24.
 */
public abstract class BaseNetUtil<T> {
    private Map params;
    //???????
    public T getData(String path,RequestQueue mQueue){
        String data = getCacheData(path);
        String  result = getCacheData(path);

        if(!TextUtils.isEmpty(data)){
            result = data;
        }else{
            /**
             * ???????
             */

            result = getNetData(path,mQueue);
        }

        return paseJson(result);
    }




    private String getCacheData(String  path) {

        return null;
    }

     public String  getNetData(String path,RequestQueue mQueue){

       params = new HashMap();

         final String netData;

         RequestParmars(params);

         /**
          * ???????
          */
         StringRequest stringRequest;
                 stringRequest = new StringRequest(1,Constants.WEATHER_URL, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 TLog.e("niujunjie", "------:" + response);
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 TLog.e("niujunjie", error.getMessage());
             }
         }){
                     @Override
                     protected Map<String, String> getParams() throws AuthFailureError {
                         return params;
                     }
                 };

         mQueue.add(stringRequest);
        return null;
    }

    public  abstract T paseJson(String result);

    private void writeCacheData(String result,long CacheTime){

        //??????????????????????????  ????(???)>??????????(??)
        long time = System.currentTimeMillis()+CacheTime;
    }

    /**
     * ?????????
     */
    public  abstract void RequestParmars(Map params);
}
