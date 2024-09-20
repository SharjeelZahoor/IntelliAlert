package com.sharjeel;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class RegisterActivity extends AppCompatActivity {
    App app;
    // UI elements
    EditText editTextName, editTextCNIC,editTextPW,editTextAddress, editTextAge,editTextContact, editTextDesig;
    Spinner spinnerGender,spinnerType;
    ProgressBar progressBar;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // UI
        editTextName =(EditText)findViewById(R.id.editTextName);
        spinnerGender=(Spinner) findViewById(R.id.spinnerGender);
        spinnerType=(Spinner) findViewById(R.id.spinnerUtype);
        editTextCNIC =(EditText)findViewById(R.id.editTextLoginId);
        editTextPW=(EditText)findViewById(R.id.editTextPassword);
        editTextAddress =(EditText)findViewById(R.id.editTextAddress);
        editTextAge =(EditText)findViewById(R.id.editTextAge);
        editTextContact =(EditText)findViewById(R.id.editTextContact);
        editTextDesig =(EditText)findViewById(R.id.editTextDesig);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        btnRegister =(Button)findViewById(R.id.btnRegister);
        app=(App) getApplication();
    }

    public void register(View view)
    {
        String name= editTextName.getText().toString().trim();
        String gender=spinnerGender.getSelectedItem().toString();
        String age= editTextAge.getText().toString().trim();
        String contact=editTextContact.getText().toString().trim();
        String type=spinnerType.getSelectedItem().toString();
        String desig=editTextDesig.getText().toString().trim();
        String loginId= editTextCNIC.getText().toString().trim();
        String password=editTextPW.getText().toString().trim();
        String address=editTextAddress.getText().toString();

        if(name.isEmpty())
        {
            editTextName.setError("required..!");
            editTextName.requestFocus();
            return;
        }
        if(age.isEmpty())
        {
            editTextAge.setError("required..!");
            editTextAge.requestFocus();
            return;
        }
        if(contact.isEmpty())
        {
            editTextContact.setError("required..!");
            editTextContact.requestFocus();
            return;
        }
        if(desig.isEmpty())
        {
            editTextDesig.setError("required..!");
            editTextDesig.requestFocus();
            return;
        }
        if(loginId.isEmpty())
        {
            editTextCNIC.setError("required..!");
            editTextCNIC.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editTextPW.setError("required..!");
            editTextPW.requestFocus();
            return;
        }
        if(password.length()<4)
        {
            editTextPW.setError("Min. length is 4");
            editTextPW.requestFocus();
            return;
        }
        User u=new User(0,loginId,password,name,gender,type,Integer.parseInt(age),contact,desig,address);
        progressBar.setVisibility(View.VISIBLE);
        btnRegister.setEnabled(false);
        String url=app.getBaseUrl()+"register.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id",u.getId());
            jsonObject.put("cnic",u.getCnic());
            jsonObject.put("password",u.getPw());
            jsonObject.put("user_name",u.getName());
            jsonObject.put("gender",u.getGender());
            jsonObject.put("user_type",u.getType());
            jsonObject.put("age",u.getAge());
            jsonObject.put("contact",u.getContact());
            jsonObject.put("department",u.getDepartment());
            jsonObject.put("designation",u.getDesignation());
            jsonObject.put("address",u.getAddress());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
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
                btnRegister.setEnabled(true);
                if(response_type.equals("-1"))
                {
                    Toast.makeText(RegisterActivity.this,"Some Unknown Error.",Toast.LENGTH_LONG).show();
                }
                else if(response_type.equals("1"))
                {
                    Toast.makeText(RegisterActivity.this,"Login ID already exists. Please choose another.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(app, "Registration Successful. Please login.", Toast.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(app, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnRegister.setEnabled(true);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void login(View view)
    {
        this.finish();
    }
}