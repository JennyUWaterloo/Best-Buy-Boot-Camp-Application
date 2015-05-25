package com.example.dx205.bestbuy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;


public class Category extends ActionBarActivity {

    private static final String TAG_CONTACTS = "contacts";
    String url;
    int pagecount;
    int category;
    String categoryname;
    String categoryID;
    String pagination;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);


        Bundle categoryBundle = getIntent().getExtras();
        if (categoryBundle == null) return;
        category = categoryBundle.getInt("category");
        categoryname = categoryBundle.getString("categoryname");
        setTitle(categoryname);
        pagecount = categoryBundle.getInt("pagenumber");

        switch(category){
            case 0:
                categoryID = "";
                break;
            case 1:
                categoryID = "((categoryPath.id=pcmcat209400050001))";
                break;
            case 2:
                categoryID = "((categoryPath.id=abcat0501000))";
                break;
            case 3:
                categoryID = "((categoryPath.id=abcat0401000))";
                break;
            case 4:
                categoryID = "((categoryPath.id=pcmcat242800050021))";
                break;
            case 5:
                categoryID = "((categoryPath.id=abcat0204000))";
                break;
            case 6:
                categoryID = "((categoryPath.id=pcmcat241600050001))";
                break;
            case 7:
                categoryID = "((categoryPath.id=pcmcat254000050002))";
                break;
            case 8:
                categoryID = "((categoryPath.id=pcmcat209000050006))";
                break;
            case 9:
                categoryID = "((categoryPath.id=abcat0502000))";
                break;
            case 10:
                categoryID = "((categoryPath.id=pcmcat232900050000))";
                break;
            case 11:
                categoryID = "((categoryPath.id=pcmcat295700050012))";
                break;
            case 12:
                categoryID = "((categoryPath.id=pcmcat310200050004))";
                break;
            case 13:
                categoryID = "((categoryPath.id=pcmcat243400050029))";
                break;
            case 14:
                categoryID = "((categoryPath.id=abcat0904000))";
                break;
            case 15:
                categoryID = "((categoryPath.id=abcat0901000))";
                break;
            case 16:
                categoryID = "((categoryPath.id=abcat0912000))";
                break;
            case 17:
                categoryID = "((categoryPath.id=abcat0101000))";
                break;
            case 18:
                categoryID = "((categoryPath.id=abcat0910000))";
                break;
            case 19:
                categoryID = "((categoryPath.id=pcmcat273800050036))";
                break;
            case 20:
                categoryID = "((categoryPath.id=pcmcat300300050002))";
                break;
            default:
                categoryID = "";
                break;
        }


        switch (pagecount)
        {
            case 1:
                pagination = "";
                break;
            default:
                pagination = "&page=" + pagecount;
        }
        url = "https://api.remix.bestbuy.com/v1/products" + categoryID + "?apiKey=8pussre9deej6m4fezayxha9" + pagination + "&callback=JSON_CALLBACK&format=json";

        new updateList().execute();
    }

    private class updateList extends AsyncTask<String, Void, Boolean> {
        String[] nameArray = new String[10];
        String[] urlArray = new String[10];
        int[] finalPrice = new int[10];

        protected Boolean doInBackground(final String... args) {
            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(url); //get JSON data from URL

            for (int i = 0; i < json.length(); i++)
            {
                try {
                    JSONObject c = json.getJSONObject(i);
                    String site = c.getString("mobileUrl");
                    urlArray[i] = site;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(c.getString("name"));
                    sb.append("\n");
                    double regPrice = c.getDouble("regularPrice");
                    double salePrice = c.getDouble("salePrice");
                    if (regPrice > salePrice){
                        sb.append("$");
                        sb.append(salePrice);
                        sb.append(" sale");
                    }
                    else{
                        sb.append("$");
                        sb.append(regPrice);
                    }
                    String finalString = sb.toString();
                    nameArray[i] = finalString;

                } catch (JSONException e) {}
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    spinner.setVisibility(View.GONE);
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            ListAdapter myAdapter = new CustomAdapter(Category.this, nameArray);
            ListView aList = (ListView) findViewById(R.id.categoryList);
            aList.setAdapter(myAdapter);
            aList.setOnItemClickListener(
                    new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(getApplicationContext(), webView.class);
                            i.putExtra("website", urlArray[position]);
                            startActivity(i);
                        }
                    }
            );
            if (pagecount > 1)
            {
            Button prevButton = (Button) findViewById(R.id.prevButton);
            prevButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pagecount -= 1;
                    new loadMoreListView().execute();
                }
            });
            }
            Button nextButton = (Button) findViewById(R.id.nextButton);
            nextButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pagecount += 1;
                    new loadMoreListView().execute();
                }
            });
        }
    }

    private class loadMoreListView extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... unused) {
            runOnUiThread(new Runnable() {
                public void run() {
                    // Next page request
                    switch (pagecount)
                    {
                        case 1:
                            pagination = "";
                            break;
                        default:
                            pagination = "&page=" + pagecount;
                    }
                    url = "https://api.remix.bestbuy.com/v1/products" + categoryID + "?apiKey=8pussre9deej6m4fezayxha9" + pagination + "&callback=JSON_CALLBACK&format=json";
                    spinner.setVisibility(View.VISIBLE);
                    new updateList().execute();
                }
            });
            return (null);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
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


}
