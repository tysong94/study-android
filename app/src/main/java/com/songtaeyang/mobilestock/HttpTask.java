//package com.songtaeyang.mobilestock;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//
////import org.json.simple.parser.ParseException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//public class HttpTask extends AsyncTask<String, Void, String> {
//
//    @Override
//    protected String doInBackground(String... strings) {
//        Log.d("EBank", "doInBackground");
//
//        InputStream is = getInputStreamFromUrl(strings[0]);
//        String result = convertStreamToString(is);
//
//        Log.d("EBank", result);
//
//        return result;
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        //result를 처리한다.
//        //보통 UI변경 ( ex) textview.setText("~~") )할때 많이 사용된다.
//
//        Log.d("EBank", "onPostExecute");
//
//        //parseExchangeRate (s);
//    }
//
//
//    public InputStream getInputStreamFromUrl(String url) {
//        InputStream content = null;
//        try{
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpResponse response = httpclient.execute(new HttpGet(url));
//            content = response.getEntity().getContent();
//        } catch (Exception e) {
//            Log.e("[GET REQUEST]", "Network exception");
//        }
//        return content;
//    }
//
//    private static String convertStreamToString(InputStream is) {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        StringBuilder sb = new StringBuilder();
//        String line = null;
//
//        try {
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return sb.toString();
//    }
//}
