package com.robapp.app.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.robapp.R;
import com.robapp.utils.FileRequest;
import com.robapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.net.ResponseCache;
import java.util.ArrayList;
import java.util.LinkedList;

public class DownloadBehaviorActivity extends BaseActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    private static final String url = "http://robhub.esy.es/api/behaviors.php";
    private ListView myList;
    private ArrayAdapter<String> adapter;
    private RequestQueue queue;
    private JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_behavior);
        setupDrawer();


        myList = (ListView)findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new LinkedList<String>());
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String label  = adapter.getItem(position);
                JSONObject obj = null;
                for(int i = 0;i< result.length();i++)
                {
                    try {
                        if(obj.getString("label").equals(label))
                            downloadFile(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        queue.add(request);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            System.out.println(response.toString(3));
            result = response.getJSONArray("behaviors");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        LinkedList<String> list = new LinkedList<String>();

        for(int i = 0;i< result.length();i++)
        {
            try {
                System.out.println("i : "+i);
                JSONObject b = result.getJSONObject(i);
                String label = b.getString("label");
                System.out.println("label : "+label);
                list.addLast(label);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("Error : "+error.getMessage() );
        error.printStackTrace();
    }


    public void downloadFile(final JSONObject obj) throws Exception {

        String mUrl= obj.getString("dex_url");

        Response.Listener<byte[]> success =  new Response.Listener<byte[]>() {

            @Override
            public void onResponse(byte[] response) {

                try {
                    if (response!=null) {

                        Utils.saveDownloadFile(obj,response);
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

        FileRequest request = new FileRequest(Request.Method.GET, mUrl,success,error,null);
        queue.add(request);
    }
}
