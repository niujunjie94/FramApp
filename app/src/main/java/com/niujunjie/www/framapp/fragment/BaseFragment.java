package com.niujunjie.www.framapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niujunjie.www.framapp.ui.LoadingPage;
import com.niujunjie.www.framapp.utils.UIUtils;

import java.util.List;

/**
 * Created by niujunjie on 2015/7/23.
 */
public abstract class BaseFragment extends Fragment{
    public Context ctx;
    private LoadingPage loadingPage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         ctx = getActivity();

    }



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        //将加载的页面分成几种情况封装成对象
        loadingPage = new LoadingPage(UIUtils.getAppContext()){

            @Override
            protected LoadingPageState onLoad() {
                return  BaseFragment.this.onLoad();
            }

            @Override
            protected View onCreateSuccessedView() {
                return initRootView(inflater,container,savedInstanceState);
            }
        };

        requestNetData();
        return loadingPage;
    }




    /**
     *
     */
    protected abstract LoadingPage.LoadingPageState onLoad();

    /**
     * 网络数据请求
     */
    protected abstract void requestNetData();
    /**   * 初始化显示界面
     */
    public abstract View initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    //调用此方法先触发onLoad方法，条件成立触发initRootView方法
    public void showView(){
        if(loadingPage != null){
            loadingPage.show();
        }
    }


    //判断联网的状态
    public int checkNetDataReturnState(Object data){
        if(data != null) {
            if (data instanceof List) {
                if (((List) data).size() == 0) {
                    return LoadingPage.LoadingPageState.STATE_EMPTY;
                } else if (((List) data).size() > 0) {
                    return LoadingPage.LoadingPageState.STATE_SUCCESSED;
                }
            }
        }else{
            return LoadingPage.LoadingPageState.STATE_ERROR;
        }
        return LoadingPage.LoadingPageState.STATE_ERROR;
    }
}
