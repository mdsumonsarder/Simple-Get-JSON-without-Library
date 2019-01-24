package com.sumoncse.codexive.json_one;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    BufferedReader bufferedReader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.t1);
        //Create Object JSONTask Class;
        JsonTask jsonTask = new JsonTask();
        jsonTask.execute();
    }

    public class JsonTask extends AsyncTask<String,String,String> {

        //JSONData show Variable;
        String name;
        String id;
        String department;
        String section;
        String semester;

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection httpURLConnection = null;

            //1.Url Connection;
            try {
                URL url = new URL("https://api.myjson.com/bins/1770wk");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                //InputStream mathod is Byte code data read;
                InputStream inputStream = httpURLConnection.getInputStream();
                 //Byte code into CharecterCode Convert;
                 bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                 //CharecterStream into Line to Line Conver then use StringBuffer;
                 StringBuffer stringBuffer = new StringBuffer();
                 StringBuffer lastStringBuffer = new StringBuffer();
                 //Line Count then use String Line;
                 String line = "";

                 while ((line = bufferedReader.readLine()) != null){

                       stringBuffer.append(line);
                 }

                 //Serioul wise JsonData Show;
                 String file = stringBuffer.toString();

                 JSONObject fileObject  = new JSONObject(file);
                 JSONArray jssonArray = fileObject.getJSONArray("studentinfo");
                 //JSONObject index number;
                for(int i=0;i<jssonArray.length();i++) {


                    JSONObject arrayObject = jssonArray.getJSONObject(i);

                    //Json to getData;
                    name = arrayObject.getString("name");
                    id = arrayObject.getString("id");
                    department = arrayObject.getString("department");
                    section = arrayObject.getString("section");
                    semester = arrayObject.getString("semester");
                    lastStringBuffer.append(name+"\n"+id+"\n"+department+"\n"+section+"\n"+semester+"\n\n");
                }

                 return lastStringBuffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }
    }
}
