package com.example.rifqi12rpl012018;

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

public class Register extends AppCompatActivity {

    Button btn_login;
    private AppCompatImageButton fab_register;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fab_register = findViewById(R.id.fab_register);
        final EditText et_email = findViewById(R.id.et_email);
        final EditText et_username = findViewById(R.id.et_username);
        final EditText et_password = findViewById(R.id.et_password);
        final EditText et_nohp = findViewById(R.id.et_nohp);
        final EditText et_noktp = findViewById(R.id.et_noktp);
        final EditText et_alamat = findViewById(R.id.et_alamat);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        fab_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = et_email.getText().toString();
                String username1 = et_username.getText().toString();
                String nohp = et_nohp.getText().toString();
                String noktp = et_noktp.getText().toString();
                String alamat = et_alamat.getText().toString();
                String pass = et_password.getText().toString();

                HashMap<String, String> body = new HashMap<>();
                body.put("noktp", noktp);
                body.put("email", email);
                body.put("password", pass);
                body.put("nama", username1);
                body.put("nohp", nohp);
                body.put("alamat", alamat);
                body.put("role", "1");
                AndroidNetworking.post("http://192.168.43.145/API_RIfqi/register.php")
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
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                                    finish();
                                    finishAffinity();
                                }
                                else {
                                    Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(Register.this, "Kesalahan Internal", Toast.LENGTH_SHORT).show();
                                Log.d("RFH", "onError: " + anError.getErrorBody());
                                Log.d("RFH", "onError: " + anError.getLocalizedMessage());
                                Log.d("RFH", "onError: " + anError.getErrorDetail());
                                Log.d("RFH", "onError: " + anError.getResponse());
                                Log.d("RFH", "onError: " + anError.getErrorCode());
                            }
                        });
            }
        });
    }
}

