package cashkaro.com.volleycustomlibrary;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.error.ParseError;
import com.android.volley.request.JsonRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by yasar on 3/8/17.
 */

public class JsonObjectRequestCustom<T> extends JsonRequest<T> {
    private final Gson gson = new Gson();

    private final Class<T> clazz;
    private final Response.Listener<T> listener;

    /**
     * Creates a new request.
     *
     * @param method        the HTTP method to use
     * @param url           URL to fetch the JSON from
     * @param jsonRequest   A {@link JSONObject} to post with the request. Null is allowed and
     *                      indicates no parameters will be posted along with request.
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public JsonObjectRequestCustom(Class<T> clazz, int method, String url, JSONObject jsonRequest,
                                   Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
        this.clazz = clazz;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(T response) {

        listener.onResponse(response);

    }

    /**
     * Constructor which defaults to <code>GET</code> if <code>jsonRequest</code> is
     * <code>null</code>, <code>POST</code> otherwise.
     *
     * @see #JsonObjectRequestCustom(Class, int, String, JSONObject, Response.Listener, Response.ErrorListener)
     */
    public JsonObjectRequestCustom(Class<T> clazz, String url, JSONObject jsonRequest, Response.Listener<T> listener,
                                   Response.ErrorListener errorListener) {
        this(clazz, jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest,
                listener, errorListener);

//        this.clazz = clazz;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        try {

            String json = new String(

                    response.data,

                    HttpHeaderParser.parseCharset(response.headers));

            return Response.success(

                    gson.fromJson(json, clazz),

                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {

            return Response.error(new ParseError(e));

        } catch (JsonSyntaxException e) {

            return Response.error(new ParseError(e));

        }

//        try {
//            String jsonString =
//                    new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
//            return Response.success(new JSONObject(jsonString),
//                    HttpHeaderParser.parseCacheHeaders(response));
//        } catch (UnsupportedEncodingException e) {
//            return Response.error(new ParseError(e));
//        } catch (JSONException je) {
//            return Response.error(new ParseError(je));
//        }
    }
}