package com.example.oilmoney;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login,btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id=findViewById(R.id.ptb_ID);
        et_pass=findViewById(R.id.ptb_Password);
        btn_login=findViewById(R.id.bt_login);
        btn_register=findViewById(R.id.bt_gojoin);

        btn_register.setOnClickListener(new View.OnClickListener() {//회원가입 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {  //로그인 버튼 클릭 시 수행
            @Override
            public void onClick(View v) {
                String userID=et_id.getText().toString();
                String userPassword=et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject=new JSONObject(response);
                            boolean success=jasonObject.getBoolean("success");
                            if (success) {//로그인 성공한 경우
                                String userID = jasonObject.getString("userID");
                                String userPassword = jasonObject.getString("userPassword");
                                String userPoint = jasonObject.getString("userPoint");
                                String userName = jasonObject.getString("userName");
                                String userRole = jasonObject.getString("userRole");
                                String userAmount = jasonObject.getString("userAmount");

                                /*/-----3/4회원정보 출력하기 위한 수정----
                                String userPlate = jasonObject.getString("userPlate");
                                String userOil = jasonObject.getString("userOil");
                                //-----3/4회원정보 출력하기 위한 수정----/*/

                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show(); //팝업 메시지

                                Intent intent = new Intent(LoginActivity.this, MemberinfActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
                                intent.putExtra("userPoint", userPoint);
                                intent.putExtra("userName", userName);
                                intent.putExtra("userRole", userRole);
                                intent.putExtra("userAmount", userAmount);

                                //intent.putExtra("log", "User");   원래의 코드에 있던 두 줄인데 오류나서 위에 두 줄로 바꾸고 컴파일 성공
                                //intent.putExtra("userID", userID);

                                /*/-----3/4회원정보 출력하기 위한 수정----
                                intent.putExtra("userPlate", userPlate);
                                intent.putExtra("userOil", userOil);
                                //-----3/4회원정보 출력하기 위한 수정----/*/

                                startActivity(intent);
                            }
                            else{//회원등록 실패한 경우
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                };
                LoginRequest loginRequest=new LoginRequest(userID,userPassword,responseListener);
                RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}