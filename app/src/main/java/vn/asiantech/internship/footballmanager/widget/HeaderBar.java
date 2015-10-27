package vn.asiantech.internship.footballmanager.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.asiantech.internship.footballmanager.R;

/**
 *  Created by sunday on 25/09/2015.
 */
public class HeaderBar extends RelativeLayout{
    private TextView mTvTitle;

    public HeaderBar(Context context) {
        super(context);
        initView(context);
    }

    public HeaderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.custom_header_bar, this, false);
        mTvTitle = (TextView) view.findViewById(R.id.tvTitleHeader);
        addView(view);
    }

    public void setTitle(String title){
        mTvTitle.setText(title);
    }

    //TODO when setEvent
    public interface OnHeaderListener{
        void onDoSomething();
    }
}


