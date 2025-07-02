package com.songtaeyang.mobilestock;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RateView extends LinearLayout {
    TextView nameText;
    TextView rateText;

    // xml 레이아웃을 인플레이션 하여 객체화.
    private  void init (Context context)  {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_rate, this, true); // 현재 java source에 객체화시킴.

        nameText = (TextView) findViewById(R.id.nameText);
        rateText = (TextView) findViewById(R.id.rateText);
    }

    // 생성자, 생성하면서 inflation 됨. ///////////////////////////////////////
    public RateView(Context context) {
        super(context);
        init(context);
    }

    public RateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
}
