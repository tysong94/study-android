package com.songtaeyang.mobilestock;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    String stock_str;
    String stock_str1;
    ListView listView;
    StockAdapter stockAdapter;

    @Nullable
    @Override
    /** 인플레이터, container : 최상위 뷰, **/
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView);
        String url = "http://finance.daum.net/xml/xmlallpanel.daum?stype=P&type=S";

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        return rootView;
    }

    /** NETWORK TASK **/
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            stock_str = s;
            int idx = stock_str.indexOf("[");
            int idx2 = stock_str.indexOf("]");
            stock_str1 = stock_str.substring(idx, idx2+1);
            stock_str1 = stock_str1.replace("code", "\"code\"").replace("name", "\"name\"").replace("cost", "\"cost\"").replace("rate", "\"rate\"").replace("updn", "\"updn\"");

            doJSONParser(stock_str1);
        }
    }

    /** doJSONParser **/
    //json형식이었던 것을 파싱하고 listView에 출력하기 위해 adapter에 저장
    public void doJSONParser(String s) {
        List<StockItem> stockItemList = new ArrayList<StockItem>();

        try {
            JSONArray jsonArray = new JSONArray(s);
            JSONObject jObject;

            for (int i=0; i < jsonArray.length(); i++) {
                jObject = jsonArray.getJSONObject(i);
                String code = jObject.getString("code");
                String name = jObject.getString("name");
                String cost = jObject.getString("cost");
                String updn = jObject.getString("updn");
                String rate = jObject.getString("rate");

                stockItemList.add(new StockItem(code, name, cost, updn, rate));
            }
            stockAdapter = new StockAdapter(getContext(), stockItemList);
            listView.setAdapter(stockAdapter);
        } catch (JSONException e) {
            Log.e("STOCK", e.getMessage());
        }
    }

}
