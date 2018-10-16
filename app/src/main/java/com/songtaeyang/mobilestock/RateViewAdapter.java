package com.songtaeyang.mobilestock;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class RateViewAdapter extends BaseAdapter{

    // 프로퍼티
    private Context context;
    private List<RateItem> rateItemList;

    // 생성자
    public RateViewAdapter(Context context, List<RateItem> rateItemList) {
        this.context = context;
        this.rateItemList = rateItemList;
    }

    // 항목 개수 반환
    @Override
    public int getCount() {
        return rateItemList.size();
    }

    // 특정 항목 반환
    @Override
    public Object getItem(int i) {
        return rateItemList.get(i);
    }

    // 그대로 반환...
    @Override
    public long getItemId(int i) {
        return i;
    }

    // 한 항목에 대한 뷰를 세팅
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RateView rateView = new RateView(context);
        rateView.nameText.setText(rateItemList.get(i).getName());
        rateView.rateText.setText(rateItemList.get(i).getRate());

        return rateView;
    }
}
