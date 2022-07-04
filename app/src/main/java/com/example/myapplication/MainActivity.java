package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText etName, etPin;
    TextView textview;
    LinearLayout llResult;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etName = findViewById(R.id.etName);
        etPin = findViewById(R.id.etPin);
        textview = findViewById(R.id.tvResult);
        llResult = findViewById(R.id.llResult);
        btnSend = findViewById(R.id.btnSend);




        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "http://resttest.wmids.uk/rest/getaccount/" + etName.getText().toString().toLowerCase() + "/"
                        + etPin.getText().toString().toLowerCase();

                StringRequest stringRequest =
                        new StringRequest(
                                Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.contains("-1")) {
                                    textview.setText("Account not found");
                                } else {
                                    String json = response.toString();
                                    llResult.setVisibility(View.VISIBLE);
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject(json);
                                        textview.setText(jsonObject.get("mem_account").toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                llResult.setVisibility(View.VISIBLE);
                                textview.setText("That didn't work! " + error.getMessage().toString());
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                textview.setText(error.getMessage());

                            }
                        }


                        );

                queue.add(stringRequest);

            }
        });


    }

    private void setUpViews() {

    }
}