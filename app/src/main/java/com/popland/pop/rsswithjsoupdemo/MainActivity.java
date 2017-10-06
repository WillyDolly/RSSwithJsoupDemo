package com.popland.pop.rsswithjsoupdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
ListView LV;
    ArrayList<RSSitems> itemsList;
    RSSparser parser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LV = (ListView)findViewById(R.id.LV);
        itemsList = new ArrayList<RSSitems>();
        parser = new RSSparser();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new XMLParser().execute("http://dantri.com.vn/trangchu.rss");
            }
        });
    }

    class XMLParser extends AsyncTask<String,String,ArrayList>{

        @Override
        protected ArrayList doInBackground(String... params) {
            itemsList = parser.getRSSFromFeed(params[0]);
            return itemsList;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
            CustomBaseAdapter adapter = new CustomBaseAdapter(MainActivity.this,R.layout.custom_layoutlv,arrayList);
            LV.setAdapter(adapter);
        }
    }
}
