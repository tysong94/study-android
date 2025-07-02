package com.songtaeyang.a24list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WeatherItemView extends LinearLayout {
    ImageView imageView;
    TextView dayText;
    TextView hourText;
    TextView ptyText;
    TextView wfKorText;
    TextView tempText;
    TextView tmxText;
    TextView tmnText;

    // 생성자, 생성하면서 inflation 됨. ///////////////////////////////////////
    public WeatherItemView(Context context) {
        super(context);
        init(context);
    }

    public WeatherItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // xml 레이아웃을 인플레이션 하여 객체화.
    private  void init (Context context)  {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_weatheritem, this, true); // 현재 java source에 객체화시킴.

        imageView = (ImageView) findViewById(R.id.imageView);
        dayText = (TextView) findViewById(R.id.dayText);
        hourText = (TextView) findViewById(R.id.hourText);
        ptyText = (TextView) findViewById(R.id.ptyText);
        wfKorText = (TextView) findViewById(R.id.wfKorText);
        tempText = (TextView) findViewById(R.id.tempText);
        tmxText = (TextView) findViewById(R.id.tmxText);
        tmnText = (TextView) findViewById(R.id.tmnText);
    }

    // view를 세팅함.
    public void setImageView(int sky) {
        imageView.setImageResource(sky);
    }
    public void setDayText(String day) {
        dayText.setText(day);
    }
    public void setHourText(String hour) {
        hourText.setText(hour);
    }
    public void setPtyText(String pty) {
        ptyText.setText(pty);
    }
    public void setWfKorText(String wfKor) {
        wfKorText.setText(wfKor);
    }
    public void setTempText(String temp) {
        tempText.setText(temp);
    }
    public void setTmxText(String tmx) {
        tmxText.setText(tmx);
    }
    public void setTmnText(String tmn) {
        tmnText.setText(tmn);
    }
}
