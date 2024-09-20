package com.sharjeel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sharjeel.common.App;
import com.sharjeel.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {

    App app;
    User user;
    TextView tvUserName;
    EditText etName,etDesig,etContact,etAge,etAddress;
    Spinner spinnerGender;
    Button btnSave;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        tvUserName=(TextView)findViewById(R.id.textViewUserName);
        etName=(EditText) findViewById(R.id.editTextUName);
        etDesig=(EditText) findViewById(R.id.editTextDesig);
        etContact=(EditText) findViewById(R.id.editTextContact);
        etAge=(EditText) findViewById(R.id.editTextAge);
        etAddress=(EditText) findViewById(R.id.editTextAddress);
        spinnerGender=(Spinner) findViewById(R.id.spinnerGender);
        btnSave=(Button) findViewById(R.id.btnSave);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        app=(App) getApplication();
        user=(User) getIntent().getSerializableExtra("user");
    }
    @Override
    protected void onResume() {
        super.onResume();
        app=(App) getApplication();
        User u=app.getUser();
        if(u==null)
        {
            Toast.makeText(app, "You are not logged in.", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        if(user!=null)
        {
            tvUserName.setText(user.getName());
            etName.setText(user.getName());etAge.setText(user.getAge()+"");etDesig.setText(user.getDesignation());
            etContact.setText(user.getContact());etAddress.setText(user.getAddress());
            if(user.getGender().equals("Male")) spinnerGender.setSelection(0); else spinnerGender.setSelection(1);
        }
    }
    public void save(View view)
    {
        if(etName.getText().toString().equals(""))
        {
            etName.setError("*Required");
            return;
        }
        if(etAge.getText().toString().equals(""))
        {
            etAge.setError("*Required");
            return;
        }
        if(etDesig.getText().toString().equals(""))
        {
            etDesig.setError("*Required");
            return;
        }
        if(etContact.getText().toString().equals(""))
        {
            etContact.setError("*Required");
            return;
        }
        if(etAddress.getText().toString().equals(""))
        {
            etAddress.setError("*Required");
            return;
        }
        user.setName(etName.getText().toString());
        user.setContact(etContact.getText().toString());
        user.setGender(spinnerGender.getSelectedItem().toString());
        user.setAge(Integer.parseInt(etAge.getText().toString()));
        user.setAddress(etAddress.getText().toString());
        user.setDesignation(etDesig.getText().toString());
        progressBar.setVisibility(View.VISIBLE);
        btnSave.setEnabled(false);
        String url=app.getBaseUrl()+"save-profile.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id",user.getId());
            jsonObject.put("cnic",user.getCnic());
            jsonObject.put("password",user.getPw());
            jsonObject.put("user_name",user.getName());
            jsonObject.put("gender",user.getGender());
            jsonObject.put("user_type",user.getType());
            jsonObject.put("age",user.getAge());
            jsonObject.put("contact",user.getContact());
            jsonObject.put("department",user.getDepartment());
            jsonObject.put("designation",user.getDesignation());
            jsonObject.put("address",user.getAddress());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("response="+response);
                String response_type="-1";
                String err="";
                try {
                    response_type=response.get("type").toString();
                    err=response.get("error").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Error="+err);
                }
                progressBar.setVisibility(View.VISIBLE);
                btnSave.setEnabled(true);
                if(response_type.equals("-1"))
                {
                    Toast.makeText(EditProfileActivity.this,"Some Unknown Error.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(app, "Profile is updated Successfully.", Toast.LENGTH_SHORT).show();
                    app.setUser(user);
                    EditProfileActivity.this.finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(app, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnSave.setEnabled(true);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}