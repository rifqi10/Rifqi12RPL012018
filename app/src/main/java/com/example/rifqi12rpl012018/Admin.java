package com.example.rifqi12rpl012018;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private rv_adapter rv_adapter;
    private List<rv_model> modelAdminArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        AndroidNetworking.get("http://192.168.43.145/API_Rifqi/show_user.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("STATUS");
                            if (status.equalsIgnoreCase("SUCCESS")) {
                                JSONObject payload = response.getJSONObject("PAYLOAD");
                                JSONArray data = payload.getJSONArray("DATA");
                                Log.d("Data", String.valueOf(data));
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject item = data.getJSONObject(i);

                                    rv_model madmin = new rv_model();
                                    madmin.setNama(item.getString("NAMA"));
                                    madmin.setEmail(item.getString("EMAIL"));
                                    madmin.setId(item.getString("ID"));
                                    modelAdminArrayList.add(madmin);

                                }
                                Log.d("Array", String.valueOf(modelAdminArrayList.size()));
                                rv_adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("anError", anError.getLocalizedMessage());
                    }
                });

        rv_adapter = new rv_adapter(getApplicationContext(),modelAdminArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rv_adapter);
    }


}