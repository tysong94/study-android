package com.songtaeyang.mobilestock;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StockAdapter extends BaseAdapter{

    private Context context;
    private List<StockItem> stockItemList;

    public StockAdapter(Context context, List<StockItem> stockItemList) {
        this.context = context;
        this.stockItemList = stockItemList;
    }

    // 항목 개수 반환
    @Override
    public int getCount() {
        return stockItemList.size();
    }

    // 특정 항목 반환
    @Override
    public Object getItem(int i) {
        return stockItemList.get(i);
    }

    // 그대로 반환...
    @Override
    public long getItemId(int i) {
        return i;
    }

    // 한 항목에 대한 뷰를 세팅
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.view_stockitem, null);
        TextView codeText = (TextView) v.findViewById(R.id.codeText);
        TextView nameText = v.findViewById(R.id.nameText);
        TextView costText = v.findViewById(R.id.costText);
        TextView updnText = v.findViewById(R.id.updnText);
        TextView rateText = v.findViewById(R.id.rateText);

        codeText.setText(stockItemList.get(i).getCode());
        nameText.setText(stockItemList.get(i).getName());
        costText.setText(stockItemList.get(i).getCost());
        updnText.setText(stockItemList.get(i).getUpdn());
        rateText.setText(stockItemList.get(i).getRate());

        return v;
    }
}
