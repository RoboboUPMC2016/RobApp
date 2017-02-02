package com.robapp.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robapp.R;
import com.robapp.app.adapter.BehaviorDownloadAdapter;
import com.robapp.behaviors.item.ItemDownload;
import com.robapp.tools.FileRequest;
import com.robapp.tools.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The activity for downloading behaviors from RobHub
 */
public class DownloadBehaviorActivity extends BaseActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    private static final String url = "http://robhub.esy.es/api/getbehaviors.php";
    private ListView myList;
    private BehaviorDownloadAdapter adapter;
    private RequestQueue queue;
    private SearchView search;
    private ArrayList<ItemDownload>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_behavior);
        setupDrawer();


        list = new ArrayList<ItemDownload>();
        myList  = (ListView)findViewById(R.id.list);
        adapter = new BehaviorDownloadAdapter(new ArrayList<ItemDownload>(),this);
        myList.setAdapter(adapter);
        search = (SearchView)findViewById(R.id.searchView);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filtre(list,query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filtre(list,newText);
                return false;
            }
        });
        queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        queue.add(request);
    }

    /**
     * Methods called when the http request has ended
     * @param response
     */
    @Override
    public void onResponse(JSONObject response) {
        try {
            System.out.println(response.toString(2));
            JSONArray result = response.getJSONArray("behaviors");

            for(int i = 0;i< result.length();i++)
            {
                try {
                    JSONObject b = result.getJSONObject(i);
                    String id = b.getString("id");
                    String label = b.getString("label");
                    String desc = b.getString("desc");
                    String dexURL = b.getString("dex_url");
                    String note = b.get("mark")+"";
                    String detailsURL = b.getString("behaviordetails_url");
                    String filename =b.getString("filename");

                    ItemDownload item = new ItemDownload(id,label,dexURL,detailsURL,note,desc,filename);
                    list.add(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            adapter.clear();
            adapter.addAll(list);
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method called if during the http request an error occures
     * @param error
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("Error : "+error.getMessage() );
        error.printStackTrace();
    }


    /**
     * Download a behavior
     * @param item The item representing the behavior
     * @throws Exception
     */
    public void downloadFile(final ItemDownload item) throws Exception {


        Response.Listener<byte[]> success =  new Response.Listener<byte[]>() {

            @Override
            public void onResponse(byte[] response) {

                try {
                    if (response!=null) {

                        Utils.saveDownloadFile(item,response);
                        Toast.makeText(DownloadBehaviorActivity.this, "Download complete.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DownloadBehaviorActivity.this, "Download failure.", Toast.LENGTH_LONG).show();
            }
        };

        FileRequest request = new FileRequest(Request.Method.GET, item.getDexURL(),success,error,null);
        queue.add(request);
    }
}
