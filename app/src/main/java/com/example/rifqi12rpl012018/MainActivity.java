package com.example.rifqi12rpl012018;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private AppCompatImageButton fab_login;
    private Button btn_register;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab_login = findViewById(R.id.fab_login);
        final EditText et_username = findViewById(R.id.et_username);
        final EditText et_password = findViewById(R.id.et_password);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        fab_login = findViewById(R.id.fab_login);
        fab_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                HashMap<String, String> body = new HashMap<>();
                body.put("email", username);
                body.put("password", password);
                AndroidNetworking.post("http://192.168.43.145/API_Rifqi/login.php")
                        .addBodyParameter(body)
//                        .setOkHttpClient(((Initial) getApplication()).getOkHttpClient())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("RFH", "respon : " + response);
                                String status = response.optString("STATUS");
                                String message = response.optString("MESSAGE");
                                if (status.equalsIgnoreCase("SUCCESS")) {
                                    JSONObject payload = response.optJSONObject("PAYLOAD");
                                    String LOGIN_ID = payload.optString("LOGIN_ID");
                                    String LOGIN_NAME = payload.optString("LOGIN_NAME");
                                    String NOMOR_HP = payload.optString("NOMOR_HP");
                                    String NOMOR_KTP = payload.optString("NOMOR_KTP");
                                    String ALAMAT = payload.optString("ALAMAT");
                                    String ROLE = payload.optString("ROLE");

                                    preferences = getSharedPreferences("DBSEPEDA", Context.MODE_PRIVATE);
                                    preferences.edit()
                                            .putString(LOGIN_ID, LOGIN_ID)
                                            .putString(LOGIN_NAME, LOGIN_NAME)
                                            .putString(NOMOR_HP, NOMOR_HP)
                                            .putString(NOMOR_KTP, NOMOR_KTP)
                                            .putString(ALAMAT, ALAMAT)
                                            .putString(ROLE, ROLE)
                                            .apply();
                                    if (ROLE.equalsIgnoreCase("Customer")) {
                                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                        startActivity(intent);
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                        finish();
                                        finishAffinity();
                                    }else if (ROLE.equalsIgnoreCase("admin")){
                                        Intent intent = new Intent(MainActivity.this, Admin.class);
                                        startActivity(intent);
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                        finish();
                                        finishAffinity();
                                    }
                                }
                                else {
                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(MainActivity.this, "Kesalahan Internal", Toast.LENGTH_SHORT).show();
                                Log.d("RFH", "onError: " + anError.getErrorBody());
                                Log.d("RFH", "onError: " + anError.getLocalizedMessage());
                                Log.d("RFH", "onError: " + anError.getErrorDetail());
                                Log.d("RFH", "onError: " + anError.getResponse());
                                Log.d("RFH", "onError: " + anError.getErrorCode());
                            }
                        });


//                startActivity(new Intent(getApplicationContext(), dashboard.class));
            }
        });


    }

}
