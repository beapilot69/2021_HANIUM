package com.example.oilmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MemberinfActivity extends AppCompatActivity {

    private TextView tv_memberID, tv_name, tv_point;
    private Button btn_gomain, btn_gofillpoint, btn_logout2;

    /*/-------------------------3/4(금) 하다가 중단----------------
    private userlistAdapter adapter;
    private List<user> userlist;
    //------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberinf);

        tv_memberID = findViewById(R.id.tv_memberID);
        tv_name = findViewById(R.id.tv_name);
        tv_point = findViewById(R.id.tv_point);

        btn_logout2 = (Button) findViewById(R.id.btn_logout2);
        btn_gomain=findViewById(R.id.btn_gomain);
        btn_gofillpoint = findViewById(R.id.btn_gofillpoint);


        Intent intent = getIntent();

        String userID = intent.getStringExtra("userID");  //이전 페이지에서 값들을 불러와서 바로 텍스트 박스에 넣어줌
        tv_memberID.setText(userID+"님");

        String userPoint = intent.getStringExtra("userPoint");
        tv_point.setText("잔여 포인트 : "+userPoint+"원");

        //String userPassword = intent.getStringExtra("userPassword");]
        String userName = intent.getStringExtra("userName");
        tv_name.setText("이름 : "+userName);

        String userRole = intent.getStringExtra("userRole");
        String userAmount = intent.getStringExtra("userAmount");

        //tv_point.setText("잔여 포인트 : "+userPoint);
        //tv_id.setText("아이디 : "+userID);

        btn_gomain.setOnClickListener(new View.OnClickListener() {//주유하러 가기 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MemberinfActivity.this,MainActivity.class);
                intent.putExtra("userID", userID); //메인 페이지에 회원 정보 값 전달.
                intent.putExtra("userPoint", userPoint);
                intent.putExtra("userName", userName);
                intent.putExtra("userRole", userRole);
                intent.putExtra("userAmount", userAmount);
                startActivity(intent);
            }
        });
        btn_logout2.setOnClickListener(new View.OnClickListener() {//로그아웃 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MemberinfActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_gofillpoint.setOnClickListener(new View.OnClickListener() {//포인트 충전 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MemberinfActivity.this,MainActivity2.class);
                intent.putExtra("userID", userID); //포인트 충전 페이지에 회원 정보 값 전달.
                intent.putExtra("userPoint", userPoint);
                intent.putExtra("userName", userName);
                intent.putExtra("userRole", userRole);
                intent.putExtra("userAmount", userAmount);
                startActivity(intent);
            }
        });
    }



}