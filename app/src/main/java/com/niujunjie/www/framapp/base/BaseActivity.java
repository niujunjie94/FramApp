package com.niujunjie.www.framapp.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.niujunjie.www.framapp.R;
import com.niujunjie.www.framapp.fragment.BaseFragment;

/**
 * Created by niujunjie on 2015/7/23.
 */

public class BaseActivity extends FragmentActivity{
    private FragmentTransaction transaction;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if(savedInstanceState == null){
            //创建fragment,避免fragment多次创建
        }
    }

    /**
     * fragment 之间传递数据
     * 结果：未测试
     * @param from
     * @param to
     */
    public void setFragmentDataDeliver(BaseFragment from,BaseFragment to){
             to.setArguments(from.getArguments());
    }

    public void switchFragment(BaseFragment  from,BaseFragment to){

            transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.fl_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
    }
    /**
     * fragment管理方法
     */
    public void showFragment(){

    }
}
