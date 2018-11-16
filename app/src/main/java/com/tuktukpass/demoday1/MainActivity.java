package com.tuktukpass.demoday1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView1;
    EditText editText1;
    Button button1;

    class Shop {
        String shopId;
        String shopName;
        String shopDescription;
        Double lat;
        Double lng;
        String create;
        String thumbnail;
    }

    ArrayList<Shop> shops = new ArrayList<Shop>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView);
        editText1 = findViewById(R.id.editText);
        button1 = findViewById(R.id.button);
        button1.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        // Initialize a new JsonObjectRequest instance

        String url = "http://178.128.170.56/shop.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            // Get the JSON array
                            JSONArray jsonArray = response.getJSONArray("shops");

                            // Loop through the array elements
                            for(int i=0;i<jsonArray.length();i++){
                                // Get current json object
                                JSONObject item = jsonArray.getJSONObject(i);

                                // Get the current shop (json object) data
                                Shop shop = new Shop();
                                shop.shopId = item.getString("shop_id");
                                shop.shopName = item.getString("shop_name");
                                shop.shopDescription = item.getString("shop_description");
                                shop.lat = item.getDouble("lat");
                                shop.lng = item.getDouble("lng");
                                shop.create = item.getString("create");
                                shop.thumbnail = item.getString("thumbnail");
                                shops.add(shop);
                            }
                            //demo do something after finished
                            textView1.setText(shops.get(0).shopName);
                            Log.d("DEBUG", "Total item(s) in object = " + String.valueOf(shops.size()));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("ERROR", error.getMessage());
                    }
                });

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);

    }

}
