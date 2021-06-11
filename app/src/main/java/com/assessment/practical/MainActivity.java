package com.assessment.practical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.assessment.practical.helper.DataListViewAdapter;
import com.assessment.practical.helper.MyExpandableListAdapter;
import com.assessment.practical.model.Articles;
import com.assessment.practical.model.Media;
import com.assessment.practical.model.MediaMetadata;
import com.assessment.practical.model.ResultsList;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 public class MainActivity extends AppCompatActivity implements DataListViewAdapter.ItemClickListener {
    //public class MainActivity extends AppCompatActivity  {
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    RecyclerView recyclerView;
    List<String> groupList;
    List<String> childList;
    Map<String,List<String>> mobilecollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    Articles articles;
    ResultsList resultsList;
     Media media;
     MediaMetadata mediaMetadata;
     ArrayList<ResultsList> resultArrayList;
     String jsonUrl;

     DataListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        setupDrawerContent(nvDrawer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.mPatient);
        int numberOfColumns  =1;
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,numberOfColumns));

//        RequestQueue requestQueue;
//        requestQueue = Volley.newRequestQueue(this);
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
//                "https://api.nytimes.com/svc/mostpopular/v2/viewed/30.json?api-key=tPtZrPPaHEwsGeq4ddRbbOgZ0DUhJNEV ", null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                try {
//                    articlesArrayList = new ArrayList<Articles>();
//                    for (int i=0; i<response.length();i++){
//                        JSONObject obj = response.getJSONObject(i);
//                        JSONArray array = obj.getJSONArray("results");
////                        articles = new Articles();
////                        articles.setStatus(obj.getString("status"));
////                        articles.setCopyright(obj.getString("copyright"));
////                        articles.setNum_results(obj.getString("num_results"));
//                      //  articles.setResultsList(obj.getJSONArray("resultsList"));
////                        Log.d("joe", "onResponse: JSON RESULTS: " +
////                                obj.getString("status"));
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("error", error.getMessage());
//                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        requestQueue.add(jsonArrayRequest);

        new BackgroundTask().execute();
    }

     class BackgroundTask extends AsyncTask<Void,Void,String>
     {
         private ProgressDialog dialog;
         protected void onPreExecute() {

             jsonUrl  = "https://api.nytimes.com/svc/mostpopular/v2/viewed/30.json?api-key=tPtZrPPaHEwsGeq4ddRbbOgZ0DUhJNEV";
             dialog = new ProgressDialog(MainActivity.this);
             dialog.setMessage("Processing..Please Wait");
             dialog.show();

         }
         @Override
         protected String doInBackground(Void... voids) {
             StringBuilder builder = null;
             try{
                 URL url  = new URL(jsonUrl);

                 HttpURLConnection con = (HttpURLConnection)url.openConnection();
                 InputStreamReader reader  = new InputStreamReader(con.getInputStream());
                 BufferedReader bufferedReader = new BufferedReader(reader);
                 String line ="";
                 builder = new StringBuilder();
                 while((line = bufferedReader.readLine())!= null)
                 {
                     builder.append(line);
                 }

             }catch (MalformedURLException e)
             {
                 e.printStackTrace();
             }catch (IOException e){
                 e.printStackTrace();
             }
            // Log.e("Error",builder.toString());
             return builder.toString();
         }

         @Override
         protected void onPostExecute(String s) {
             super.onPostExecute(s);
              try{
                  articles = new Articles();
                  JSONObject  jsonObject = new JSONObject(s);
                  articles.setStatus(jsonObject.getString("status"));
                  articles.setNum_results(jsonObject.getString("num_results"));
                  articles.setCopyright(jsonObject.getString("copyright"));
                  articles.setResultsList(setResulArrayList(jsonObject.getJSONArray("results")));
                  resultArrayList = setResulArrayList(jsonObject.getJSONArray("results"));
                  //articles.setResultsList((List<ResultsList>) jsonObject.getJSONArray("results"));
                  //JSONArray jsonArray = jsonObject.getJSONArray("results");
                  //for(int)
//                  for(int i = 0; i<articles.getResultsList().size();i++)
//                  {
//                      Log.e("Error", String.valueOf(articles.getResultsList().get(i).getUrl()));
//                  }
                  Log.e("Error", String.valueOf(articles.getResultsList().size()));
                  //Log.e("Error", String.valueOf(articles.getResultsList())));
                 // String num_results = jsonObject.getString("")
                  adapter = new DataListViewAdapter(MainActivity.this,  articles.getResultsList());
                  adapter.setClickListener(MainActivity.this);
                  recyclerView.setAdapter(adapter);
              }catch(JSONException e)
              {
                  e.printStackTrace();
              }

             dialog.dismiss();
         }
     }

     public ArrayList<ResultsList> setResulArrayList(JSONArray arr)
     {
         ArrayList<ResultsList> all = new ArrayList<ResultsList>();
           for(int i  =0; i<arr.length();i++)
           {
               String[] des_facet_arr = null;
               String[] org_facet_arr = null;
               String[] per_facet_arr = null;
               String[] geo_facet_arr = null;
               ArrayList<Media> media_arr = null;
               ArrayList<MediaMetadata> meta_data_arr = null;
               try {
                   JSONObject JO = arr.getJSONObject(i);

                   resultsList = new ResultsList();
                   resultsList.setUri((String) JO.get("uri"));
                   resultsList.setUrl((String) JO.get("url"));
                   resultsList.setId((Long) JO.get("id"));
                   resultsList.setAssert_id((Long) JO.get("asset_id"));
                   resultsList.setSource((String) JO.get("source"));
                   resultsList.setPublished_date((String) JO.get("published_date"));
                   resultsList.setUpdated((String) JO.get("updated"));
                   resultsList.setSection((String) JO.get("section"));
                   resultsList.setSubsection((String) JO.get("subsection"));
                   resultsList.setNytdsection((String) JO.get("nytdsection"));
                   resultsList.setAdx_keywords((String) JO.get("adx_keywords"));
                   //resultsList.setColumn((String) JO.get("column"));
                   resultsList.setByline((String) JO.get("byline"));
                   resultsList.setType((String) JO.get("type"));
                   resultsList.setTitle((String) JO.get("title"));
                   resultsList.setAbstractt((String) JO.get("abstract"));

                   for(int x  = 0;x<JO.getJSONArray("des_facet").length(); x++){
                       des_facet_arr = new String[JO.getJSONArray("des_facet").length()];
                       des_facet_arr[x] = JO.getJSONArray("des_facet").getString(x);
                   }
                   resultsList.setDes_facet(des_facet_arr);

                   for(int x  = 0;x<JO.getJSONArray("org_facet").length(); x++){
                       org_facet_arr = new String[JO.getJSONArray("org_facet").length()];
                       org_facet_arr[x] = JO.getJSONArray("org_facet").getString(x);
                   }

                   resultsList.setOrg_facet(org_facet_arr);

                   for(int x  = 0;x<JO.getJSONArray("per_facet").length(); x++){
                       per_facet_arr = new String[JO.getJSONArray("per_facet").length()];
                       per_facet_arr[x] = JO.getJSONArray("per_facet").getString(x);
                   }

                   resultsList.setPer_facet(per_facet_arr);

                   for(int x  = 0;x<JO.getJSONArray("geo_facet").length(); x++){
                       geo_facet_arr = new String[JO.getJSONArray("geo_facet").length()];
                       geo_facet_arr[x] = JO.getJSONArray("geo_facet").getString(x);
                   }

                   resultsList.setGeo_facet(geo_facet_arr);

                   media_arr = new ArrayList<Media>();
                   meta_data_arr = new ArrayList<MediaMetadata>();
                   for(int x = 0; x<JO.getJSONArray("media").length(); x++)
                   {
                       JSONObject mediaObj = JO.getJSONArray("media").getJSONObject(x);
                       media  = new Media();
                       media.setType(mediaObj.get("type").toString());
                       media.setApproved_for_syndication(mediaObj.get("approved_for_syndication").toString());
                       media.setCaption(mediaObj.get("caption").toString());
                       media.setCopyright(mediaObj.get("copyright").toString());
                       //Log.e("Error", String.valueOf(articles.getResultsList().size()));
                         for(int l = 0; l< mediaObj.getJSONArray("media-metadata").length(); l++)
                         {
                             JSONObject metaObj = mediaObj.getJSONArray("media-metadata").getJSONObject(l);
                             mediaMetadata = new MediaMetadata();
                             mediaMetadata.setUrl(metaObj.get("url").toString());
                             mediaMetadata.setFormat(metaObj.get("format").toString());
                             mediaMetadata.setHeight(metaObj.get("height").toString());
                             mediaMetadata.setWidth(metaObj.get("width").toString());

                             meta_data_arr.add(mediaMetadata);
                         }
                    //media_arr.add()

                   media.setMediametadata(meta_data_arr);
                   media_arr.add(media);
                   }

                resultsList.setMedia(media_arr);
                   resultsList.setEta_id((Integer) JO.get("eta_id"));

                   all.add(resultsList);

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
         return all;
     }
     class BackgroundTaskEach extends AsyncTask<String,Void,String>
     {

         private ProgressDialog dialog;
         @Override
         protected void onPreExecute() {

             dialog = new ProgressDialog(MainActivity.this);
             dialog.setMessage("Processing..Please Wait");
             dialog.show();
         }

         @Override
         protected String doInBackground(String... args) {

             String line = "";
             String response;
             StringBuilder dataBuilder = new StringBuilder();

             String id = args[0];
             // long count;
             try {

                for(int i = 0;i<resultArrayList.size();i++)
                {
                    if(resultArrayList.get(i).getId() == Long.parseLong(id)){
                            resultsList = resultArrayList.get(i);
                    }
                }
             } catch (Exception ex) {
                 ex.printStackTrace();
             } finally {
                 //   obj_database.close();
             }

             return null;
         }

         @Override
         protected void onPostExecute(String s) {
             dialog.dismiss();
             Intent intr = new Intent(MainActivity.this, Details.class);
             intr.putExtra("resultsList",resultsList);
             startActivity(intr);
         }


     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_drawer, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {

            onBackPressed();
        } else if (id == android.R.id.home) {

            onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        switch (menuItem.getItemId()) {


            case R.id.home:
                Intent i6 = new Intent(this, MainActivity.class);
                startActivity(i6);

                break;


            case R.id.about_us:


                break;

            default:
                Intent t = new Intent(this, MainActivity.class);
                startActivity(t);
        }

        menuItem.setChecked(true);
        // Set action bar title
        // setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

     @Override
     public void onItemClick(View view, int position) {
         Log.i("TAG", "You clicked number " + adapter.getItem(position).getId() + ", which is at cell position " + position);

         BackgroundTaskEach task =new BackgroundTaskEach();
         task.execute(String.valueOf(adapter.getItem(position).getId()));
     }


//    @Override
//    public void onItemClick(View view, int position) {
//
//    }
}