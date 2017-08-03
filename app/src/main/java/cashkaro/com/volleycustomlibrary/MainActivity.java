package cashkaro.com.volleycustomlibrary;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        Log.e(TAG, "onCreate: ID  " + deviceId);

        //ffffffff-9991-8852-ffff-ffff99d603a9
        //ffffffff-9991-8852-ffff-ffff99d603a9
        //ffffffff-9991-8852-ffff-ffff99d603a9


        String url = "https://demoschool.pappaya.co.uk/mobile/rest/timetable_list";


        try {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("password", "pappaya");
            jsonObject1.put("login", "pappaya1994+068@gmail.com");
            jsonObject1.put("user_types", "Faculty");

            JSONObject jsonObject11 = new JSONObject();
            jsonObject11.put("params", jsonObject1);

            Log.e(TAG, "onCreate: post data " + jsonObject1.toString());

            Utils.showProgress(this, "Loading...");
            JsonObjectRequestCustom jsonObjectRequestCustom = new JsonObjectRequestCustom(Test.class, url, jsonObject11, new Response.Listener() {
                @Override
                public void onResponse(Object response) {

                    Utils.hideProgress();
                    Test test = (Test) response;

                    Log.e(TAG, "onResponse: " + test.getJsonrpc() + "  " + test.getId() + "   " + test.getResult().getUserTypes());

                    List<TimetableDatum> timetableDatumList = test.getResult().getTimetableData();

                    for (int i = 0; i < timetableDatumList.size(); i++) {
                        TimetableDatum timetableDatum = timetableDatumList.get(i);
                        Log.e(TAG, "onResponse: " + timetableDatum.getStartDatetime());
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Utils.hideProgress();
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                }
            });

//            jsonObjectRequestCustom.setPriority(Request.Priority.HIGH);

            MainApplication.getInstance(this).addToRequestQueue(jsonObjectRequestCustom);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void customResponseObject() {

        String url = "www.example.com"; // replace with your url.

        GsonRequest gsonRequest = new GsonRequest(url, Test.class, null, new Response.Listener() {

            @Override

            public void onResponse(Object response) {

                // Handle response

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {

                // Handle error

            }

        });

        // Add gson request to volley request queue.

        MainApplication.getInstance(this).addToRequestQueue(gsonRequest);

    }
}
