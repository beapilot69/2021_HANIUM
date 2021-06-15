package com.example.oilmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import androidx.appcompat.app.AlertDialog;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {

    
    private static String IP_ADDRESS = "0.0.0.0";
    private static String TAG = "phptest";

    private EditText mEditTextuserName;
    private EditText mEditTextuserID;
    private EditText mEditTextuserPassword;
    private EditText mEditTextuserOil;
    private EditText mEditTextuserPlate;
    private TextView mTextViewResult;
    private Button btn_joincancel, btn_IDcheck;

    private AlertDialog dialog;
    private boolean validate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mEditTextuserName = (EditText) findViewById(R.id.ptb_Name);
        mEditTextuserID = (EditText) findViewById(R.id.ptb_ID);
        mEditTextuserPassword = (EditText) findViewById(R.id.ptb_Password);
        mEditTextuserOil = (EditText) findViewById(R.id.ptb_Oil);
        mEditTextuserPlate = (EditText) findViewById(R.id.ptb_Plate);

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());
        btn_joincancel=findViewById(R.id.bt_joincancel);
        btn_IDcheck = findViewById(R.id.bt_IDcheck);

        btn_joincancel.setOnClickListener(new View.OnClickListener() {//회원가입 취소 버튼을 클릭 시 수행
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JoinActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        /////////////////////   5/20 아이디 중복확인 기능 추가.. 실행 안 됨!!
//        btn_IDcheck.setOnClickListener(new View.OnClickListener() {//중복확인 클릭 시 수행
//            @Override
//            public void onClick(View v) {
//                String userID = mEditTextuserID.getText().toString();
//                if(validate)
//                {
//                    return; //검증완료
//                }
//                if(userID.equals("")){  //아이디 값을 입력하지 않았으면
//                    AlertDialog.Builder builder=new AlertDialog.Builder(JoinActivity.this);
//                    dialog=builder.setMessage("아이디는 빈 칸일 수 없습니다.")
//                            .setPositiveButton("확인",null)
//                            .create();
//                    dialog.show();
//                    return;
//                }
//
//                //검증 시작
//                Response.Listener<String> responseListener=new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Toast.makeText(JoinActivity.this, response, Toast.LENGTH_LONG).show();
//
//                            JSONObject jsonResponse=new JSONObject(response);
//                            boolean success=jsonResponse.getBoolean("seccess");
//                            if(success){ //사용할 수 있는 아이디라면!
//                                AlertDialog.Builder builder=new AlertDialog.Builder(JoinActivity.this);
//                                Toast.makeText(getApplicationContext(), "사용가능요.", Toast.LENGTH_SHORT).show(); //팝업 메시지
//                                dialog=builder.setMessage("사용할 수  있는 아이디입니다.")
//                                        .setPositiveButton("확인",null)
//                                        .create();
//                                dialog.show();
//                                mEditTextuserID.setEnabled(false);  //아이디 값을 바꿀 수 없도록 함
//                                validate=true;  // 검증 완료
//                                // btn_IDcheck.setText("확인");
//                                btn_IDcheck.setBackgroundColor(getResources().getColor(R.color.colorGray));
//
//                            }
//                            else{ //사용할 수 없는 아이디라면
//                                AlertDialog.Builder builder=new AlertDialog.Builder(JoinActivity.this);
//
//                                dialog=builder.setMessage("사용할 수 없는 아이디입니다.")
//                                        .setNegativeButton("확인",null)
//                                        .create();
//                                dialog.show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                ValidateRequest validateRequest=new ValidateRequest(userID, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
//                queue.add(validateRequest);
//            }
//        });
            //////////////////////////////////////////////////////////////////////////

        Button buttonInsert = (Button) findViewById(R.id.bt_join);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = mEditTextuserID.getText().toString();
                String userPassword = mEditTextuserPassword.getText().toString();
                String userName = mEditTextuserName.getText().toString();
                String userOil = mEditTextuserOil.getText().toString();
                String userPlate = mEditTextuserPlate.getText().toString();



                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(JoinActivity.this, response, Toast.LENGTH_LONG).show();

                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("seccess");
                            if(success){ //사용할 수 있는 아이디라면!
                                AlertDialog.Builder builder=new AlertDialog.Builder(JoinActivity.this);
                                Toast.makeText(getApplicationContext(), "사용가능요.", Toast.LENGTH_SHORT).show(); //팝업 메시지
                                dialog=builder.setMessage("사용할 수  있는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                mEditTextuserID.setEnabled(false);  //아이디 값을 바꿀 수 없도록 함
                                validate=true;  // 검증 완료
                                // btn_IDcheck.setText("확인");
                                btn_IDcheck.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                InsertData task = new InsertData();
                                task.execute("http://" + IP_ADDRESS + "/insert.php", userID, userPassword, userName, userOil, userPlate);

                                mEditTextuserID.setText("");
                                mEditTextuserPassword.setText("");
                                mEditTextuserName.setText("");
                                mEditTextuserOil.setText("");
                                mEditTextuserPlate.setText("");
                            }
                            else{ //사용할 수 없는 아이디라면
                                AlertDialog.Builder builder=new AlertDialog.Builder(JoinActivity.this);

                                dialog=builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest=new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(validateRequest);


            }
        });
    }

    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(JoinActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String userID = (String)params[1];
            String userPassword = (String)params[2];
            String userName = (String)params[3];
            String userOil = (String)params[4];
            String userPlate = (String)params[5];

            String serverURL = (String)params[0];
            String postParameters = "userID=" + userID + "&userPassword=" + userPassword + "&userName=" + userName+ "&userOil=" + userOil + "&userPlate=" + userPlate;

            try{

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();


            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }
            //return null;
        }

    }

}