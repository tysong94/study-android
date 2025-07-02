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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Fragment1 extends Fragment {
    // kospi
    TextView kospi_costText;
    TextView kospi_updnText;
    TextView kospi_rateText;

    // kosdaq
    TextView kosdaq_costText;
    TextView kosdaq_updnText;
    TextView kosdaq_rateText;

    // kospi_listView
    ListView kospi_growth_listView;
    ListView kospi_drop_listView;

    // kosdaq_listView
    ListView kosdaq_growth_listView;
    ListView kosdaq_drop_listView;

    @Nullable
    @Override
    /** 최상위 뷰, **/
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);
        kospi_costText = rootView.findViewById(R.id.kospi_costText);
        kospi_updnText = rootView.findViewById(R.id.kospi_updnText);
        kospi_rateText = rootView.findViewById(R.id.kospi_rateText);
        kosdaq_costText = rootView.findViewById(R.id.kosdaq_costText);
        kosdaq_updnText = rootView.findViewById(R.id.kosdaq_updnText);
        kosdaq_rateText = rootView.findViewById(R.id.kosdaq_rateText);
        kospi_growth_listView = rootView.findViewById(R.id.kospi_growth_listView);
        kospi_drop_listView = rootView.findViewById(R.id.kospi_drop_listView);
        kosdaq_growth_listView = rootView.findViewById(R.id.kosdaq_growth_listView);
        kosdaq_drop_listView = rootView.findViewById(R.id.kosdaq_drop_listView);

        String url1 = "http://finance.daum.net/xml/xmlallpanel.daum?stype=P&type=S";
        NetworkTask1 networkTask1 = new NetworkTask1(url1, null);
        networkTask1.execute();

        String url2 = "http://finance.daum.net/xml/xmlallpanel.daum?stype=Q&type=S";
        NetworkTask2 networkTask2 = new NetworkTask2(url2, null);
        networkTask2.execute();

        return rootView;
    }

    /** NETWORK TASK1 **/
    public class NetworkTask1 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask1(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            int idx = result.indexOf("=");
            result = result.substring(idx+1);
            Log.d("result", result);

            return result;
        }

        //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어옴.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            parseKospi(result);
        }
    }

    /** parseKospi **/
    //json형식이었던 것을 파싱하고 listView에 출력하기 위해 adapter에 저장
    public void parseKospi(String result) {
        try {
            // 전체 객체 목록 가져오기
            JSONObject jsonObject = new JSONObject(result);

            // kospi 데이터
            JSONObject kospi_total = jsonObject.getJSONObject("kospi");

            String kospi_cost = kospi_total.getString("cost");
            String kospi_updn = kospi_total.getString("updn");
            String kospi_rate = kospi_total.getString("rate");
            kospi_costText.setText(kospi_cost);
            kospi_updnText.setText(kospi_updn);
            kospi_rateText.setText(kospi_rate);

            // kosdaq 데이터
            JSONObject kosdaq_total = jsonObject.getJSONObject("kosdaq");

            String kosdaq_cost = kosdaq_total.getString("cost");
            String kosdaq_updn = kosdaq_total.getString("updn");
            String kosdaq_rate = kosdaq_total.getString("rate");
            kosdaq_costText.setText(kosdaq_cost);
            kosdaq_updnText.setText(kosdaq_updn);
            kosdaq_rateText.setText(kosdaq_rate);

            // kospi item 데이터
            JSONArray kospi_item = jsonObject.getJSONArray("item");

            ArrayList<RateItem> kospi_growth_items = new ArrayList<RateItem>();
            ArrayList<RateItem> kospi_drop_items = new ArrayList<RateItem>();

            for (int i=0; i < kospi_item.length(); i++) {
                JSONObject item = kospi_item.getJSONObject(i);
                String name = item.getString("name");
                String rate = item.getString("rate");

                if(rate.startsWith("-")) {
                    kospi_drop_items.add(new RateItem(name, rate));
                } else if(rate.startsWith("+")){
                    kospi_growth_items.add(new RateItem(name, rate));
                } else {
                    kospi_growth_items.add(new RateItem(name, "+" + rate));
                }
            }

            // 내림차순 정렬
            class DescendingObj implements Comparator<RateItem> {
                @Override public int compare(RateItem s1, RateItem s2) {
                    Double rate1 = Double.parseDouble(s1.getRate().replace("%", ""));
                    Double rate2 = Double.parseDouble(s2.getRate().replace("%", ""));

                    return rate2.compareTo(rate1);
                }
            }
            // 오름차순 정렬
            class AscendingObj implements Comparator<RateItem> {
                @Override public int compare(RateItem s1, RateItem s2) {
                    Double rate1 = Double.parseDouble(s1.getRate().replace("%", ""));
                    Double rate2 = Double.parseDouble(s2.getRate().replace("%", ""));

                    return rate1.compareTo(rate2);
                }
            }

            // kospi 상승률
            Collections.sort(kospi_growth_items, new DescendingObj());
            RateViewAdapter kospiGrowthAdapter = new RateViewAdapter(getContext(), kospi_growth_items);
            kospi_growth_listView.setAdapter(kospiGrowthAdapter);

            // kospi 하락률
            Collections.sort(kospi_drop_items, new AscendingObj());
            RateViewAdapter kospiDropAdapter = new RateViewAdapter(getContext(), kospi_drop_items);
            kospi_drop_listView.setAdapter(kospiDropAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** NETWORK TASK2 **/
    public class NetworkTask2 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask2(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            int idx = result.indexOf("=");
            result = result.substring(idx+1);
            Log.d("result", result);

            return result;
        }

        //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어옴.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            parseKosdaq(result);
        }
    }

    /** parseKosdaq**/
    //json형식이었던 것을 파싱하고 listView에 출력하기 위해 adapter에 저장
    public void parseKosdaq(String result) {
        try {
            // 전체 객체 목록 가져오기
            JSONObject jsonObject = new JSONObject(result);

            // kosdaq item 데이터
            JSONArray kosdaq_items = jsonObject.getJSONArray("item");

            ArrayList<RateItem> kosdaq_growth_items = new ArrayList<RateItem>();
            ArrayList<RateItem> kosdaq_drop_items = new ArrayList<RateItem>();

            for (int i=0; i < kosdaq_items.length(); i++) {
                JSONObject item = kosdaq_items.getJSONObject(i);
                String name = item.getString("name");
                String rate = item.getString("rate");

                if(rate.startsWith("-")) {
                    kosdaq_drop_items.add(new RateItem(name, rate));

                } else if(rate.startsWith("+")){
                    kosdaq_growth_items.add(new RateItem(name, rate));
                } else {
                    kosdaq_growth_items.add(new RateItem(name, "+" + rate));
                }
            }

            // 내림차순 정렬
            class DescendingObj implements Comparator<RateItem> {
                @Override public int compare(RateItem s1, RateItem s2) {
                    Double rate1 = Double.parseDouble(s1.getRate().replace("%", ""));
                    Double rate2 = Double.parseDouble(s2.getRate().replace("%", ""));

                    return rate2.compareTo(rate1);
                }
            }
            // 오름차순 정렬
            class AscendingObj implements Comparator<RateItem> {
                @Override public int compare(RateItem s1, RateItem s2) {
                    Double rate1 = Double.parseDouble(s1.getRate().replace("%", ""));
                    Double rate2 = Double.parseDouble(s2.getRate().replace("%", ""));

                    return rate1.compareTo(rate2);
                }
            }

            // kosdaq 상승률
            Collections.sort(kosdaq_growth_items, new DescendingObj());
            RateViewAdapter kosdaqGrowthAdapter = new RateViewAdapter(getContext(), kosdaq_growth_items);
            kosdaq_growth_listView.setAdapter(kosdaqGrowthAdapter);

            // kosdaq 하락률
            Collections.sort(kosdaq_drop_items, new AscendingObj());
            RateViewAdapter kosdaqDropAdapter = new RateViewAdapter(getContext(), kosdaq_drop_items);
            kosdaq_drop_listView.setAdapter(kosdaqDropAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
