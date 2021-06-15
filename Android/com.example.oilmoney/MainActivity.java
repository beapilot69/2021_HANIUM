package com.example.oilmoney;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "0.0.0.0";

    private static String TAG = "phptest";
    private TextView tv_id, tv_pass;
    private Button btn_logout, btn_mypage, bt_pay, bt_10000, bt_20000, bt_30000, bt_40000, bt_50000, bt_full;
    private EditText ptb_paywon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_mypage = (Button) findViewById(R.id.btn_mypage);
        btn_logout = (Button) findViewById(R.id.bt_logout);
        bt_pay = (Button) findViewById(R.id.bt_pay);
        bt_10000 = (Button) findViewById(R.id.bt_10000);
        bt_20000 = (Button) findViewById(R.id.bt_20000);
        bt_30000 = (Button) findViewById(R.id.bt_30000);
        bt_40000 = (Button) findViewById(R.id.bt_40000);
        bt_50000 = (Button) findViewById(R.id.bt_50000);
        bt_full = (Button) findViewById(R.id.bt_full);

        ptb_paywon = (EditText) findViewById(R.id.ptb_paywon);

        final Intent[] intent = {getIntent()};

        String userID = intent[0].getStringExtra("userID");  //아이디를 다음 페이지로 넘겨주고 그 아이디에 해당하는 회원의 정보를 같이 넘겨주고자 함.
        String userPoint = intent[0].getStringExtra("userPoint");
        String userName = intent[0].getStringExtra("userName");
        String userAmount = intent[0].getStringExtra("userAmount");
        String userRole = intent[0].getStringExtra("userRole");

        btn_logout.setOnClickListener(new View.OnClickListener() {//로그아웃 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_mypage.setOnClickListener(new View.OnClickListener() { //취소 버튼을 누르면 내 정보로 이동
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MemberinfActivity.class);
                intent.putExtra("userID", userID); //메인 페이지에 userID 값 전달.
                intent.putExtra("userPoint", userPoint);
                intent.putExtra("userName", userName);
                intent.putExtra("userRole", userRole);
                intent.putExtra("userAmount", userAmount);

                startActivity(intent);
            }
        });

        bt_10000.setOnClickListener(new View.OnClickListener() {//결제 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                final String[] temp = {"10000"};
                int A;
                A = Integer.parseInt(userPoint) - Integer.parseInt(temp[0]);
                temp[0] = Integer.toString(A);
                String cost = Integer.toString(10000);

                if (A < 0){
                    Toast.makeText(getApplicationContext(), "잔액이 부족합니다. 충전해주세요!", Toast.LENGTH_SHORT).show(); //팝업 메시지]
                }
                else
                {
                    //잔액이 있고, 권한(role)이 1일 때만 결제 가능하도록 해야함.
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jasonObject=new JSONObject(response);
                                boolean success=jasonObject.getBoolean("success");
                                if (success) {//로그인 성공한 경우
                                    String userRole = jasonObject.getString("userRole");
                                    if (Integer.parseInt(userRole) != 0){
                                        Intent intent =new Intent(MainActivity.this,MemberinfActivity.class);

                                        MainActivity.UpdateData task = new MainActivity.UpdateData();
                                        task.execute("http://" + IP_ADDRESS + "/payment.php", userID, cost, temp[0]);
                                        //mEditTextuserPoint.setText("");

                                        Toast.makeText(getApplicationContext(), "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show(); //팝업 메시지

                                        intent.putExtra("userID", userID); //메인 페이지에 userID 값 전달.
                                        intent.putExtra("userAmount", cost);
                                        intent.putExtra("userPoint", temp[0]);
                                        intent.putExtra("userName", userName);


                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "결제는 주유소에서만 가능", Toast.LENGTH_SHORT).show(); //팝업 메시지]
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(e.getMessage());
                            }
                        }
                    };
                    PayRequest loginRequest=new PayRequest(userID,responseListener);
                    RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }

            }
        });
        bt_20000.setOnClickListener(new View.OnClickListener() {//2만원 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                final String[] temp = {"20000"};
                int A;
                A = Integer.parseInt(userPoint) - Integer.parseInt(temp[0]);
                temp[0] = Integer.toString(A);
                String cost = Integer.toString(20000);

                if (A < 0){
                    Toast.makeText(getApplicationContext(), "잔액이 부족합니다. 충전해주세요!", Toast.LENGTH_SHORT).show(); //팝업 메시지]
                }
                else
                {
                    //잔액이 있고, 권한(role)이 1일 때만 결제 가능하도록 해야함.
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jasonObject=new JSONObject(response);
                                boolean success=jasonObject.getBoolean("success");
                                if (success) {//로그인 성공한 경우
                                    String userRole = jasonObject.getString("userRole");
                                    if (Integer.parseInt(userRole) != 0){
                                        Intent intent =new Intent(MainActivity.this,MemberinfActivity.class);
                                        MainActivity.UpdateData task = new MainActivity.UpdateData();
                                        task.execute("http://" + IP_ADDRESS + "/payment.php", userID, cost, temp[0]);

                                        Toast.makeText(getApplicationContext(), "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show(); //팝업 메시지

                                        intent.putExtra("userID", userID); //메인 페이지에 userID 값 전달.
                                        intent.putExtra("userAmount", cost);
                                        intent.putExtra("userPoint", temp[0]);
                                        intent.putExtra("userName", userName);

                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "결제는 주유소에서만 가능", Toast.LENGTH_SHORT).show(); //팝업 메시지]
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(e.getMessage());
                            }
                        }
                    };
                    PayRequest loginRequest=new PayRequest(userID,responseListener);
                    RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }

            }
        });
        bt_30000.setOnClickListener(new View.OnClickListener() {//3만원 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                final String[] temp = {"30000"};
                int A;
                A = Integer.parseInt(userPoint) - Integer.parseInt(temp[0]);
                temp[0] = Integer.toString(A);
                String cost = Integer.toString(30000);

                if (A < 0){
                    Toast.makeText(getApplicationContext(), "잔액이 부족합니다. 충전해주세요!", Toast.LENGTH_SHORT).show(); //팝업 메시지]
                }
                else
                {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jasonObject=new JSONObject(response);
                                boolean success=jasonObject.getBoolean("success");
                                if (success) {//로그인 성공한 경우
                                    String userRole = jasonObject.getString("userRole");
                                    if (Integer.parseInt(userRole) != 0){
                                        Intent intent =new Intent(MainActivity.this,MemberinfActivity.class);
                                        MainActivity.UpdateData task = new MainActivity.UpdateData();
                                        task.execute("http://" + IP_ADDRESS + "/payment.php", userID, cost, temp[0]);

                                        Toast.makeText(getApplicationContext(), "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show(); //팝업 메시지

                                        intent.putExtra("userID", userID); //메인 페이지에 userID 값 전달.
                                        intent.putExtra("userAmount", userAmount);
                                        intent.putExtra("userPoint", temp[0]);
                                        intent.putExtra("userName", userName);

                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "결제는 주유소에서만 가능", Toast.LENGTH_SHORT).show(); //팝업 메시지]
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(e.getMessage());
                            }
                        }
                    };
                    PayRequest loginRequest=new PayRequest(userID,responseListener);
                    RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }

            }
        });
        bt_40000.setOnClickListener(new View.OnClickListener() {//4만원 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                final String[] temp = {"40000"};
                int A;
                A = Integer.parseInt(userPoint) - Integer.parseInt(temp[0]);
                temp[0] = Integer.toString(A);
                String cost = Integer.toString(40000);
                if (A < 0){
                    Toast.makeText(getApplicationContext(), "잔액이 부족합니다. 충전해주세요!", Toast.LENGTH_SHORT).show(); //팝업 메시지]
                }
                else
                {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jasonObject=new JSONObject(response);
                                boolean success=jasonObject.getBoolean("success");
                                if (success) {//로그인 성공한 경우
                                    String userRole = jasonObject.getString("userRole");
                                    if (Integer.parseInt(userRole) != 0){
                                        Intent intent =new Intent(MainActivity.this,MemberinfActivity.class);
                                        MainActivity.UpdateData task = new MainActivity.UpdateData();
                                        task.execute("http://" + IP_ADDRESS + "/payment.php", userID, cost, temp[0]);

                                        Toast.makeText(getApplicationContext(), "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show(); //팝업 메시지

                                        intent.putExtra("userID", userID); //메인 페이지에 userID 값 전달.
                                        intent.putExtra("userAmount", cost);
                                        intent.putExtra("userPoint", temp[0]);
                                        intent.putExtra("userName", userName);

                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "결제는 주유소에서만 가능", Toast.LENGTH_SHORT).show(); //팝업 메시지]
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(e.getMessage());
                            }
                        }
                    };
                    PayRequest loginRequest=new PayRequest(userID,responseListener);
                    RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }

            }
        });
        bt_50000.setOnClickListener(new View.OnClickListener() {//4만원 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                final String[] temp = {"50000"};
                int A;
                A = Integer.parseInt(userPoint) - Integer.parseInt(temp[0]);
                temp[0] = Integer.toString(A);
                String cost = Integer.toString(50000);

                if (A < 0){
                    Toast.makeText(getApplicationContext(), "잔액이 부족합니다. 충전해주세요!", Toast.LENGTH_SHORT).show(); //팝업 메시지]
                }
                else
                {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jasonObject=new JSONObject(response);
                                boolean success=jasonObject.getBoolean("success");
                                if (success) {//로그인 성공한 경우
                                    String userRole = jasonObject.getString("userRole");
                                    if (Integer.parseInt(userRole) != 0){
                                        Intent intent =new Intent(MainActivity.this,MemberinfActivity.class);
                                        MainActivity.UpdateData task = new MainActivity.UpdateData();
                                        task.execute("http://" + IP_ADDRESS + "/payment.php", userID, cost, temp[0]);

                                        Toast.makeText(getApplicationContext(), "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show(); //팝업 메시지

                                        intent.putExtra("userID", userID); //메인 페이지에 userID 값 전달.
                                        intent.putExtra("userAmount", cost);
                                        intent.putExtra("userPoint", temp[0]);
                                        intent.putExtra("userName", userName);

                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "결제는 주유소에서만 가능", Toast.LENGTH_SHORT).show(); //팝업 메시지]
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(e.getMessage());
                            }
                        }
                    };
                    PayRequest loginRequest=new PayRequest(userID,responseListener);
                    RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }

            }
        });
        // FULL버튼을 사용하려면 주유건에 센서를 달아야함. 나중에 구현하도록 하고 캡스톤 디자인에서는 비활성화.
        bt_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "사용할 수 없는 기능입니다.", Toast.LENGTH_SHORT).show(); //팝업 메시지
            }
        });

        bt_pay.setOnClickListener(new View.OnClickListener() {//결제 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                final String[] paywon = {ptb_paywon.getText().toString()};
                int A;
                A = Integer.parseInt(userPoint) - Integer.parseInt(paywon[0]);  // A에 현재 포인트에서 입력받은 숫자만큼 뺀 값을 저장
                String cost= Integer.toString(Integer.parseInt(paywon[0]));      // 입력받은 수를 문자열로 변환하여 저장
                paywon[0] = Integer.toString(A);                              // 정수인 A를 문자열로 변환하여 저장

                if (A < 0){
                    Toast.makeText(getApplicationContext(), "잔액이 부족합니다. 충전해주세요!", Toast.LENGTH_SHORT).show(); //팝업 메시지
                }
                else
                {
                    //잔액이 있고, 권한(role)이 1일 때만 결제 가능하도록 해야함.
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jasonObject=new JSONObject(response);
                                boolean success=jasonObject.getBoolean("success");
                                if (success) {//로그인 성공한 경우
                                    String userRole = jasonObject.getString("userRole");
                                    if (Integer.parseInt(userRole) != 0){
                                        Intent intent =new Intent(MainActivity.this,MemberinfActivity.class);
                                        //paywon[0] = Integer.toString(A);

                                        MainActivity.UpdateData task = new MainActivity.UpdateData();
                                        task.execute("http://" + IP_ADDRESS + "/payment.php", userID, cost ,paywon[0]);
                                        //mEditTextuserPoint.setText("");

                                        Toast.makeText(getApplicationContext(), "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show(); //팝업 메시지

                                        intent.putExtra("userID", userID); //메인 페이지에 userID 값 전달.
                                        intent.putExtra("userAmount", cost);
                                        intent.putExtra("userPoint", paywon[0]);
                                        intent.putExtra("userName", userName);


                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "결제는 주유소에서만 가능", Toast.LENGTH_SHORT).show(); //팝업 메시지]
                                        //지금 팝업 메시지 안 뜸
                                    }

                                    //Intent intent = new Intent(MainActivity.this, MemberinfActivity.class);
                                    //intent.putExtra("userRole", userRole);

                                    //startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(e.getMessage());
                            }
                        }
                    };
                    PayRequest loginRequest=new PayRequest(userID,responseListener);
                    RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }
            }
        });
    }


    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override //초기화 부분
        protected void onPreExecute() {
            target = "http://0.0.0.0/userlist.php";
        }

        @Override
        protected String doInBackground(Void... voids){
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                //매 열마다 입력을 받을 수 있도록 하는 코드
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();  //해당 문자열의 집합을 반환

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override //상속만 받고 사용은 안 함
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this, MemberinfActivity.class);
            intent.putExtra("userlist", result);
            MainActivity.this.startActivity(intent);
        }
    }

    class UpdateData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();
            ptb_paywon.setText(result);
            Log.d(TAG, "POST response - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String userID = (String)params[1];
            String userPoint = (String)params[3];
            String userAmount = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "userID=" + userID + "&userAmount=" + userAmount + "&userPoint=" + userPoint;

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