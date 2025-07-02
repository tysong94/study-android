package com.songtaeyang.mobilestock;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StockItemView extends LinearLayout {
    TextView codeText;
    TextView nameText;
    TextView costText;
    TextView updnText;
    TextView rateText;

    // xml 레이아웃을 인플레이션 하여 객체화.
    private  void init (Context context)  {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_stockitem, this, true); // 현재 java source에 객체화시킴.

        codeText = (TextView) findViewById(R.id.codeText);
        nameText = (TextView) findViewById(R.id.nameText);
        costText = (TextView) findViewById(R.id.costText);
        updnText = (TextView) findViewById(R.id.updnText);
        rateText = (TextView) findViewById(R.id.rateText);
    }

    // 생성자, 생성하면서 inflation 됨. ///////////////////////////////////////
    public StockItemView(Context context) {
        super(context);
        init(context);
    }

    public StockItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

//    // view를 세팅함.
//    public void setCodeText(String code) {
//        codeText.setText(code);
//    }
//    public void setCodeText(String code) {
//        codeText.setText(code);
//    }

}
