package com.hyum9.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class SignupPage2 extends AppCompatActivity {

    Spinner gradeSpinner, majorSpinner, subject1Spinner, subject2Spinner;
    EditText schoolInput;
    Button goBtn;
    //php로 보내야하는 params
    int grade;
    String id, password;
    String school, major;
    int subject1, subject2;
    int majorInt;
    RequestQueue requestQueue1, requestQueue2;
    String insertUrl = "http://13.209.178.185/insertStudent.php";
    String insertUrl2 = "http://13.209.178.185/insertStudent2.php";

    ArrayAdapter<CharSequence> subjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page2);

        //요소 가져오기
        schoolInput = findViewById(R.id.schoolInput);
        gradeSpinner = findViewById(R.id.gradeSpinner);
        majorSpinner = findViewById(R.id.majorSpinner);
        subject1Spinner = findViewById(R.id.subject1Spinner);
        subject2Spinner = findViewById(R.id.subject2Spinner);
        goBtn = findViewById(R.id.goBtn);
        requestQueue1 = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);


        //초기값 설정
        gradeSpinner.setSelection(0);
        majorSpinner.setSelection(0);
        majorInt = 0;

        //gradeSpinner-gradeAdapter
        ArrayAdapter<CharSequence> gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade_array, android.R.layout.simple_spinner_item);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        gradeSpinner.setAdapter(gradeAdapter);

        //majorSpinner-gradeAdapter
        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(this, R.array.major_array, android.R.layout.simple_spinner_item);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        majorSpinner.setAdapter(majorAdapter);

        //


        //item selected listener
        //gradeSpinner에서 선택되었을 때 grade에 저장한다
        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //majorSpinner에서 선택되었을 때 major에 저장한다
        //그리고 새로운 뷰가 등장한다
        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        majorInt = 0;
                        subjectAdapter = ArrayAdapter.createFromResource(SignupPage2.this, R.array.Natural_Sciences_subjects, android.R.layout.simple_dropdown_item_1line);
                        break;
                    case 1:
                        majorInt = 1;
                        subjectAdapter = ArrayAdapter.createFromResource(SignupPage2.this, R.array.Liberal_Arts_subjects, android.R.layout.simple_dropdown_item_1line);
                        break;
                    case 2:
                        majorInt = 2;
                        subjectAdapter = ArrayAdapter.createFromResource(SignupPage2.this, R.array.Technology_subjects, android.R.layout.simple_dropdown_item_1line);
                        break;
                }
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                subject1Spinner.setAdapter(subjectAdapter);
                subject2Spinner.setAdapter(subjectAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //subject1 선택
        subject1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject1 = majorInt * 10 + position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //subject2 선택
        subject2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject2 = majorInt * 10 + position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        goBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //System.out.println("INSERT is clicked"+" \n");

                school = schoolInput.getText().toString();

                switch(majorInt)
                {
                    case 0:
                        major = "Natural_Sciences";
                        break;
                    case 1:
                        major = "Liberal_Arts";
                        break;
                    case 2:
                        major = "Technology";
                        break;
                }

                Bundle extras = getIntent().getExtras();
                id = extras.getString("id");
                password = extras.getString("password");


                //school(String), grade(int), major(String), subject1(Int), subject2(Int), password(String), id(String) 모두 선택되어 있음
                //저장한다

                //위 아이디, 패스워드로 로그인, 접속한다!
                //자신의 아이디, 패스워드로 접속한 다음 페이지 화면 보여줌!

                //Test Code
                //Log.i("cookie"," id:" + id + " password:" + password);
                //Log.i("cookie","school: "+ school + "grade: " + grade + "major: "+ major + "subject1: "+subject1 + "subject2: " +subject2);


                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) { }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String,String> parameters  = new HashMap<String, String>();

                        parameters.put("id",id);
                        parameters.put("password",password);


                        return parameters;
                    }
                };
                requestQueue1.add(request);

                StringRequest request2 = new StringRequest(Request.Method.POST, insertUrl2, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) { }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String,String> parameters  = new HashMap<String, String>();

                        parameters.put("school",school);
                        parameters.put("grade",String.valueOf(grade));
                        parameters.put("major",major);
                        parameters.put("subject1",String.valueOf(subject1));
                        parameters.put("subject2",String.valueOf(subject2));


                        return parameters;
                    }
                };
                requestQueue2.add(request2);
            }
        });

    }
}
