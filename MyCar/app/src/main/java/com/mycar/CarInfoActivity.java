package com.mycar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class CarInfoActivity extends AppCompatActivity {

    private ProgressBar pbEo;
    private ProgressBar pbEc;
    private ProgressBar pbTf;
    private ProgressBar pbPsf;
    private ProgressBar pbBf;

    private TextView tvEo;
    private TextView tvEc;
    private TextView tvTf;
    private TextView tvPsf;
    private TextView tvBf;

    private TextView tvBrand;
    private TextView tvColor;
    private TextView tvModel;
    private TextView tvPlate;

    private static String baseURL = "http://192.168.1.104/MyCar/";
    private String id;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        Intent intent = getIntent();
        String brand = intent.getStringExtra("carBrand");
        id = intent.getStringExtra("user_id");

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.background)));
        getSupportActionBar().setTitle(brand);

        pbEo = findViewById(R.id.eo);
        pbEc = findViewById(R.id.ec);
        pbTf = findViewById(R.id.tf);
        pbPsf = findViewById(R.id.psf);
        pbBf = findViewById(R.id.bf);

        tvEo = findViewById(R.id.eo_progress);
        tvEc = findViewById(R.id.ec_progress);
        tvTf = findViewById(R.id.tf_progress);
        tvPsf = findViewById(R.id.psf_progress);
        tvBf = findViewById(R.id.bf_progress);

        tvBrand = findViewById(R.id.tv_brand);
        tvModel = findViewById(R.id.tv_model);
        tvColor = findViewById(R.id.tv_color);
        tvPlate = findViewById(R.id.tv_plate);

        builder = new AlertDialog.Builder(this);

        getCarInfo();
        getCarFluids();

    }

    public void getCarInfo(){
        String URL = String.format(baseURL + "getCarInfo.php?id=%1$s", id);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                response -> {

                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i = 0; i < array.length(); i++){
                            JSONObject object = array.getJSONObject(i);

                            tvBrand.setText(object.getString("brand").trim());
                            tvModel.setText(object.getString("model").trim());
                            tvColor.setText(object.getString("color").trim());
                            tvPlate.setText(object.getString("plate").trim());

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(CarInfoActivity.this, error.toString(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getCarFluids(){
        String URL = String.format(baseURL + "getCarFluids.php?id=%1$s", id);
        @SuppressLint("SetTextI18n") StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                response -> {

                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i = 0; i < array.length(); i++){
                            JSONObject object = array.getJSONObject(i);

                            pbEo.setProgress(Integer.parseInt(object.getString("engine_oil").trim()));
                            pbEc.setProgress(Integer.parseInt(object.getString("engine_coolant").trim()));
                            pbTf.setProgress(Integer.parseInt(object.getString("transmission_fluid").trim()));
                            pbPsf.setProgress(Integer.parseInt(object.getString("power_steering_fluid").trim()));
                            pbBf.setProgress(Integer.parseInt(object.getString("breaks_fluid").trim()));

                            tvEo.setText(object.getString("engine_oil").trim() + "%");
                            tvEc.setText(object.getString("engine_coolant").trim() + "%");
                            tvTf.setText(object.getString("transmission_fluid").trim() + "%");
                            tvPsf.setText(object.getString("power_steering_fluid").trim() + "%");
                            tvBf.setText(object.getString("breaks_fluid").trim() + "%");

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(CarInfoActivity.this, error.toString(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @SuppressLint("SetTextI18n")
    public void refillEo(View view) {
        if (pbEo.getProgress() < 100){
            builder.setTitle("Refill Engine Oil")
                    .setMessage("Are you sure you want to refill the engine oil?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", (dialogInterface, i1) -> finish())
                    .setNegativeButton("No", (dialogInterface, i12) -> dialogInterface.cancel()).show();

            pbEo.setProgress(100);
            tvEo.setText(pbEo.getProgress() + "%");
        }
    }

    @SuppressLint("SetTextI18n")
    public void refillEc(View view) {
        if (pbEc.getProgress() < 100){
            builder.setTitle("Refill Engine Coolant")
                    .setMessage("Are you sure you want to refill the engine coolant?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", (dialogInterface, i1) -> finish())
                    .setNegativeButton("No", (dialogInterface, i12) -> dialogInterface.cancel()).show();
            pbEc.setProgress(100);
            tvEc.setText(pbEc.getProgress() + "%");
        }
    }

    @SuppressLint("SetTextI18n")
    public void refillTf(View view) {
        if (pbTf.getProgress() < 100){
            builder.setTitle("Refill Transmission Fluid")
                    .setMessage("Are you sure you want to refill the transmission fluid?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", (dialogInterface, i1) -> finish())
                    .setNegativeButton("No", (dialogInterface, i12) -> dialogInterface.cancel()).show();
            pbTf.setProgress(100);
            tvTf.setText(pbTf.getProgress() + "%");
        }
    }

    @SuppressLint("SetTextI18n")
    public void refillPsf(View view) {
        if (pbPsf.getProgress() < 100){
            builder.setTitle("Refill Power Steering Fluid")
                    .setMessage("Are you sure you want to refill the power steering fluid?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", (dialogInterface, i1) -> finish())
                    .setNegativeButton("No", (dialogInterface, i12) -> dialogInterface.cancel()).show();
            pbPsf.setProgress(100);
            tvPsf.setText(pbPsf.getProgress() + "%");
        }
    }

    @SuppressLint("SetTextI18n")
    public void refillBf(View view) {
        if (pbBf.getProgress() < 100){
            builder.setTitle("Refill Breaks Fluid")
                    .setMessage("Are you sure you want to refill the breaks fluid?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", (dialogInterface, i1) -> finish())
                    .setNegativeButton("No", (dialogInterface, i12) -> dialogInterface.cancel()).show();
        }
        pbBf.setProgress(100);
        tvBf.setText(pbBf.getProgress() + "%");
    }

    public void goToAddDistance(View v){
        Intent intent = new Intent(CarInfoActivity.this, AddDistanceActivity.class);
        startActivity(intent);
    }

    public void goToMoreInfo(View v){
        Intent intent = new Intent(CarInfoActivity.this, MoreInfoActivity.class);
        startActivity(intent);
    }
}