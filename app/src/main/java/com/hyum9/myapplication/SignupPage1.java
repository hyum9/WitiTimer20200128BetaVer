package com.hyum9.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupPage1 extends AppCompatActivity {

    Button nextBtn;
    EditText idInput, passwordInput;
    String id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page1);

        //요소 가져오기
        nextBtn = findViewById(R.id.nextBtn);
        idInput = findViewById(R.id.idInput);
        passwordInput = findViewById(R.id.passwordInput);


        nextBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                //TO DO
                //if id == null 그리고 password == null인 경우 예외 처리

                id = idInput.getText().toString();
                password = passwordInput.getText().toString();


                Intent intent = new Intent(SignupPage1.this, SignupPage2.class);
                intent.putExtra("id", id);
                intent.putExtra("password",password);

                startActivity(intent);
            }
        });


    }
}
