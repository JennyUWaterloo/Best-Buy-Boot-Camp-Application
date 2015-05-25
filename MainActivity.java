package com.example.dx205.bestbuy;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import java.util.ArrayList;
import android.content.Context;
import android.widget.Toast;
import org.json.JSONArray;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    private static String url = "https://api.remix.bestbuy.com/v1/products?apiKey=8pussre9deej6m4fezayxha9&callback=JSON_CALLBACK&format=json";

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] categories = {"All Items", "All Cellphones with Plans", "Desktop & All-in-One Computers", "Digital Cameras", "Health, Fitness & Beauty",
                "Headphones", "Home Audio", "Home Automation & Security", "iPad, Tablets & E-Readers", "Laptops", "Nintendo 3DS", "PlayStation 4",
                "Portable & Wireless Speakers", "PS Vita", "Ranges, Cooktops & Ovens", "Refrigerators", "Small Kitchen Appliances", "TVs",
                "Washers & Dryers", "Wii U", "Xbox One"};
        ListAdapter myAdapter = new CustomAdapter(this, categories);
        ListView myList = (ListView) findViewById(R.id.myList);
        myList.setAdapter(myAdapter);
        myList.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getApplicationContext(), Category.class);
                        i.putExtra("category", position);
                        i.putExtra("categoryname", categories[position]);
                        i.putExtra("pagenumber", 1);
                        startActivity(i);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }


}
