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
//포인트 충전용 Activity
public class MainActivity2 extends AppCompatActivity {

    private static String IP_ADDRESS = "0.0.0.0";
    private static String TAG = "phptest";

    private EditText mEditTextuserID2, mEditTextuserPoint;
    private TextView mTextViewResult;
    private Button btn_fillcancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mEditTextuserPoint = (EditText) findViewById(R.id.ptb_point2);
        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());
        btn_fillcancel=findViewById(R.id.bt_fillcancel);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");  //로그인 한 아이디를 이전 페이지에서 가지고 옴
        String userPoint = intent.getStringExtra("userPoint");  //로그인 한 아이디를 이전 페이지에서 가지고 옴
        String userName = intent.getStringExtra("userName");  //로그인 한 아이디를 이전 페이지에서 가지고 옴
        String userRole = intent.getStringExtra("userRole");  //로그인 한 아이디를 이전 페이지에서 가지고 옴
        String userAmount = intent.getStringExtra("userAmount");

        btn_fillcancel.setOnClickListener(new View.OnClickListener() {//충전 취소 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,MemberinfActivity.class);
                intent.putExtra("userID", userID); //메인 페이지에 userID 값 전달.
                intent.putExtra("userPoint", userPoint);
                intent.putExtra("userName", userName);

                //5/18 수정 중 의문. 왜 세 개만 넘겨주나? 그래서 두 개 마저 넘겨주기로 함.
                intent.putExtra("userRole", userRole);
                intent.putExtra("userAmoutn", userAmount);

                startActivity(intent);
            }
        });

        Button buttonUpdate = (Button) findViewById(R.id.bt_fill);
        buttonUpdate.setOnClickListener(new View.OnClickListener() { //충전 버튼 누르면 실행
            @Override
            public void onClick(View v) {

                ////////////문자열 -> 정수 -> 문자열 형변환
                Intent intent =new Intent(MainActivity2.this,MemberinfActivity.class);
                String userPoint2 = mEditTextuserPoint.getText().toString();
                int SumOfPoint;
                SumOfPoint = Integer.parseInt(userPoint) + Integer.parseInt(userPoint2);
                userPoint2 = Integer.toString(SumOfPoint);
                /////////////////////////////////////////////

                UpdateData task = new UpdateData();
                task.execute("http://" + IP_ADDRESS + "/fillpoint.php", userID, userPoint2);

                intent.putExtra("userID", userID); //메인 페이지에 userID 값 전달.
                intent.putExtra("userPoint", userPoint2);
                intent.putExtra("userName", userName);

                //5/18 수정 중 두 줄 추가
                intent.putExtra("userRole", userRole);
                intent.putExtra("userAmount", userAmount);

                startActivity(intent);
            }
        });
    }

    class UpdateData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity2.this,
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
            String userPoint = (String)params[2];

            //충전 할 때는 userAmount 쓸 필요 없기 때문에 사용 안 한다.

            String serverURL = (String)params[0];
            String postParameters = "userID=" + userID + "&userPoint=" + userPoint;

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