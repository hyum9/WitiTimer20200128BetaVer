package com.hyum9.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button insert, show;
    RequestQueue requestQueue;
    String insertUrl = "http://172.20.10.2/insertStudent.php"; //insertStudent.php
    String showUrl = "http://172.20.10.2/showStudent.php";
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = findViewById(R.id.email);
        password =  findViewById(R.id.password);
        insert =  findViewById(R.id.submitBtn);
        show =  findViewById(R.id.showBtn);
        result =  findViewById(R.id.showview);


        requestQueue = Volley.newRequestQueue(this);


        show.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //System.out.println(response.toString());
                        try {
                            JSONArray students = response.getJSONArray("students");
                            for (int i = 0; i < students.length(); i++) {
                                JSONObject student = students.getJSONObject(i);//JSONArray 안에 JSONObject가 있음, JSONObject 순서가 있음

                                String email = student.getString("email");
                                String password = student.getString("password");

                                result.append(email+ " " + password+ " " + " \n");
                            }
                            result.append("===\n");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());

                    }
                });
                requestQueue.add(jsonObjectRequest);


            }

        });




        insert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //System.out.println("INSERT is clicked"+" \n");
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>()
                {
                        @Override
                        public void onResponse(String response) { System.out.println(response); }
                },
                        new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {}
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String,String> parameters  = new HashMap<String, String>();
                        parameters.put("email",email.getText().toString());
                        parameters.put("password",password.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);
            }
        });
    }

}