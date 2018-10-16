package com.songtaeyang.a24list;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ForecastActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    GridParser gridParser = new GridParser();
    Map<String, Object> grid;
    String urlStr;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        // 위치 가져오기 버튼
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });
        // 좌표 텍스트뷰
        textView = (TextView) findViewById(R.id.textView);
        // 동네예보 리스트뷰
        listView = (ListView) findViewById(R.id.listView);
    }

    // 위치 가져오기 메소드
    public void startLocationService() {
        long minTime = 0;
        float minDistance = 0;
        // 단말에서 동작하고 있는 위치 시스템 관리자를 가져옴
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // manager를 통해 위치정보를 가져옴.
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, listener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
    } // end of startLocationService

    // 위치 리스너
    LocationListener listener  = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 위경도, gridxy 받아오기
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            grid = gridParser.getGridxy(latitude, longitude);
            int gridx = (int)(double) grid.get("x");
            int gridy = (int)(double) grid.get("y");

            // 텍스트뷰에 위경도, gridxy 보여주기
            textView.setText("내 위치 : " + latitude + ", " + longitude);
            textView.append("\ngridxy : " + grid.get("x") + ", " + grid.get("y"));

            // 동네예보 받아오기.
            urlStr = "http://www.kma.go.kr/wid/queryDFS.jsp?gridx="+gridx+"&gridy="+gridy;
            textView.setText(urlStr);
            new GetXMLTask().execute();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) { }

        @Override
        public void onProviderEnabled(String s) { }

        @Override
        public void onProviderDisabled(String s) { }
    };

    // 동네예보 XML 데이터 받아오기
    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... urls) {
            Document doc = null;

            URL url;
            try {
                url = new URL(urlStr);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {
            String s = "";
            NodeList nodeList = doc.getElementsByTagName("data");

            WeatherAdapter adapter = new WeatherAdapter();
            for(int i = 0; i< nodeList.getLength(); i++) {

                Node node = nodeList.item(i);
                Element elmt = (Element) node;

                NodeList sky = elmt.getElementsByTagName("sky");
                NodeList day = elmt.getElementsByTagName("day");
                NodeList hour = elmt.getElementsByTagName("hour");
                NodeList pty = elmt.getElementsByTagName("pty");
                NodeList temp = elmt.getElementsByTagName("temp");
                NodeList tmx = elmt.getElementsByTagName("tmx");
                NodeList tmn = elmt.getElementsByTagName("tmn");
                NodeList wfKor = elmt.getElementsByTagName("wfKor");

                String str_sky = sky.item(0).getChildNodes().item(0).getNodeValue();
                String str_day = day.item(0).getChildNodes().item(0).getNodeValue();
                    if(str_day.equals("0")) str_day = "오늘";
                    else if(str_day.equals("1")) str_day = "내일";
                    else if(str_day.equals("2")) str_day = "모레";
                String str_hour = hour.item(0).getChildNodes().item(0).getNodeValue() + "시";
                String str_pty = pty.item(0).getChildNodes().item(0).getNodeValue();
                String str_temp = temp.item(0).getChildNodes().item(0).getNodeValue();
                String str_tmx = tmx.item(0).getChildNodes().item(0).getNodeValue();
                String str_tmn = tmn.item(0).getChildNodes().item(0).getNodeValue();
                String str_wfKor = wfKor.item(0).getChildNodes().item(0).getNodeValue();
                    int int_wfKor = 0;
                    if(str_wfKor.equals("맑음")) { int_wfKor = R.drawable.i1; }
                    else if(str_wfKor.equals("구름 조금")) { int_wfKor = R.drawable.i2; }
                    else if(str_wfKor.equals("구름 많음")) { int_wfKor = R.drawable.i3; }
                    else if(str_wfKor.equals("흐림")) { int_wfKor = R.drawable.i4; }
                    else if(str_wfKor.equals("비")) { int_wfKor = R.drawable.i5; }
                    else if(str_wfKor.equals("눈/비")) { int_wfKor = R.drawable.i6; }
                    else if(str_wfKor.equals("눈")) { int_wfKor = R.drawable.i7; }

                WeatherItem weatherItem = new WeatherItem(int_wfKor, str_day, str_hour, str_temp, str_tmx, str_tmn, str_pty, str_wfKor);
                adapter.addItem(weatherItem);
            }
            listView.setAdapter(adapter);
            super.onPostExecute(doc);
        }
    }

    public class WeatherAdapter extends BaseAdapter{
        ArrayList<WeatherItem> items = new ArrayList<WeatherItem>();

        public void addItem(WeatherItem item) {
            items.add(item);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // item을 WeatherItemView에 넣음.
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WeatherItem item = items.get(position);

            // WeatherItem 값을 WeatherItemView로 넣어줌.
            WeatherItemView view = new WeatherItemView(getApplicationContext());
            view.setImageView(item.getSky());
            view.setDayText(item.getDay());
            view.setHourText(item.getHour());
            view.setPtyText(item.getPty());
            view.setWfKorText(item.getWfKor());
            view.setTempText(item.getTemp());
            view.setTmnText(item.getTmx());
            view.setTmxText(item.getTmn());

            return view;
        }
    }
}
