package com.niujunjie.www.framapp.ui;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.niujunjie.www.framapp.R;
import com.niujunjie.www.framapp.utils.TLog;
import com.niujunjie.www.framapp.utils.ThreadManager;
import com.niujunjie.www.framapp.utils.UIUtils;

/**
 * Created by niujunjie on 2015/7/23.
 */
public abstract  class LoadingPage extends FrameLayout{
    //未加载
    private int STATE_UNLOAD = 0;
    //正在加载
    private  int STATE_LOADING = 1;
    //加载错误
    private int STATE_ERROR = 2;
    //加载为空
    private int STATE_EMPTY = 3;
    //加载成功
    private int STATE_SUCCESSED = 4;

    private int state;

    //加载对应的View对像
    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successedView;
    private FrameLayout.LayoutParams layoutParams;

    public LoadingPage(Context context){
        super(context);
        layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
            initView();
    }

    private void initView() {
    //先将多个状态对应的界面效果添加到帧布局中
        state = STATE_UNLOAD;
        if(loadingView == null){
            loadingView = UIUtils.inflate(R.layout.layout_loading);
            //添加到FragmentLayout
            addView(loadingView, layoutParams);
        }

        if(errorView == null){
            errorView = UIUtils.inflate(R.layout.layout_error);
            //添加到FragmentLayout
            addView(errorView, layoutParams);
        }

        if(emptyView == null){
            emptyView = UIUtils.inflate(R.layout.layout_empty);
            //添加到FragmentLayout
            addView(emptyView, layoutParams);
        }
        //安全显示UI的操作
        showSafePage();
    }

    private void showSafePage(){
         UIUtils.runInMainThread(new Runnable() {
             @Override
             public void run() {
                 showPage();
             }
         });
    }

    private void showPage() {
        //在默认状态下展示lodingView
        if(loadingView!=null){
            //状态判断当前view的显示隐藏
            loadingView.setVisibility((state == STATE_UNLOAD || state == STATE_LOADING)
                    ?View.VISIBLE:View.GONE);
        }
        //错误界面的展示
        if(errorView!=null){
            errorView.setVisibility((state == STATE_ERROR)?View.VISIBLE:View.GONE);
        }

        //为空界面的展示
        if(emptyView!=null){
            emptyView.setVisibility((state == STATE_EMPTY)?View.VISIBLE:View.GONE);
        }

        //成功加载的界面
        if(successedView == null && state == STATE_SUCCESSED){
            successedView = onCreateSuccessedView();
            addView(successedView,layoutParams);
        }

        //根据状态判断是否显示
        if(successedView!=null){
            successedView.setVisibility((state == STATE_SUCCESSED)?View.VISIBLE:View.GONE);
        }
    }

    class RunnableTask implements Runnable{
        @Override
        public void run() {
            //请求网络操作返回的状态对象
            final  LoadingPageState onReturnLoadState = onLoad();
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    if(onReturnLoadState != null){

                        state = onReturnLoadState.getCurrentState();
                        //根据请求网络的状态，做页面的选择展示
                        showPage();
                    }
                }
            });
        }
    }

    protected abstract LoadingPageState onLoad();

    public void show(){
        if(state == STATE_EMPTY || state == STATE_ERROR || state == STATE_SUCCESSED){
            state = STATE_UNLOAD;
        }

        ThreadManager.getThreadPoolProxy().execute(new RunnableTask());

    }
    //创建成功界面的方法，具体实现未知
    protected abstract View onCreateSuccessedView();

    //联网状态选择
    public  static class LoadingPageState{
        public final static  int STATE_UNLOAD = 0;
        //正在加载、
        public final static int STATE_LOADING = 1;
        //加载错误
        public final static int STATE_ERROR = 2;
        //加载为空
        public final static int STATE_EMPTY = 3;
        //加载成功
        public final static int STATE_SUCCESSED = 4;
        //当前的联网的状态
        private int currentState;

        public LoadingPageState(int currentState){
            this.currentState = currentState;
        }
        public int getCurrentState() {
            return currentState;
        }

        public void setCurrentState(int currentState) {
            this.currentState = currentState;
        }



    }
}
