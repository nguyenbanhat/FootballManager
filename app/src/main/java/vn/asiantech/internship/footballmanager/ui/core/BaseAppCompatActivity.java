package vn.asiantech.internship.footballmanager.ui.core;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 *  Created by sunday on 20/10/2015.
 */
@EActivity
public abstract class BaseAppCompatActivity extends AppCompatActivity{
    @AfterViews
    protected void initView(){
        afterView();
    }

    public abstract void afterView();
}
