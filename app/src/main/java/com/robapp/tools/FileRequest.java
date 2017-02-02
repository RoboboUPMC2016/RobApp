package com.robapp.tools;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

/**
 * A request for downloading a file using HTTP protocol.
 * Created by Arthur on 18/01/2017.
 */

public class FileRequest  extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;
    private Map<String, String> mParams;

    //create a static map for directly accessing headers
    public Map<String, String> responseHeaders ;

    /**
     * Constructor
     * @param method  GET
     * @param mUrl File Urle
     * @param listener Succes Listener
     * @param errorListener Error Listener
     * @param params some params
     */
    public FileRequest (int method, String mUrl ,Response.Listener<byte[]> listener,
                                    Response.ErrorListener errorListener, HashMap<String, String> params) {

        super(method, mUrl, errorListener);
        // this request would never use cache.
        setShouldCache(false);
        mListener = listener;
        mParams=params;
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return mParams;
    };


    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;

        //Pass the response data here
        return Response.success( response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}