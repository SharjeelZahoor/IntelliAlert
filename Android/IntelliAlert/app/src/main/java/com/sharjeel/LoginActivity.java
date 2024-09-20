package com.sharjeel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sharjeel.R;
import com.sharjeel.common.App;
import com.sharjeel.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText editTextCNIC,editTextPassword;
    TextView tvInfo;
    ProgressBar progressBar;
    Button btnLogin;
    CheckBox cbRemember;
    //
    App app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // gui
        btnLogin=(Button)findViewById(R.id.btnLogin);
        editTextCNIC =(EditText)findViewById(R.id.editTextLoginId);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        cbRemember=(CheckBox) findViewById(R.id.cbRemember);
        tvInfo=(TextView) findViewById(R.id.textViewInfo);
        // app
        app=(App) getApplication();
        if(app.getRem())
        {
            cbRemember.setChecked(true);
            editTextCNIC.setText(app.getLoginId());
            editTextPassword.setText(app.getPw());
        }
    }
    public void login(View view)
    {
        tvInfo.setText("");
        final String cnic= editTextCNIC.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();
        if(cnic.isEmpty())
        {
            editTextCNIC.setError("required..!");
            editTextCNIC.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editTextPassword.setError("required..!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<4)
        {
            editTextPassword.setError("Min. length is 4");
            editTextPassword.requestFocus();
            return;
        }
        btnLogin.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        app.setCredentials(cnic,password,cbRemember.isChecked());
        String url=app.getBaseUrl()+"login.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cnic",cnic);
            jsonObject.put("pw",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                String response_type="-1";
                User user=null;
                try {
                    response_type=response.get("type").toString();
                    JSONArray userArray = response.getJSONArray("user");
                    for (int i = 0; i < userArray.length(); i++)
                    {
                        user=new User();
                        user.setId(userArray.getJSONObject(i).getInt("user_id"));
                        user.setCnic(userArray.getJSONObject(i).getString("cnic"));
                        user.setPw(userArray.getJSONObject(i).getString("password"));
                        user.setName(userArray.getJSONObject(i).getString("user_name"));
                        user.setGender(userArray.getJSONObject(i).getString("gender"));
                        user.setType(userArray.getJSONObject(i).getString("user_type"));
                        user.setAge(userArray.getJSONObject(i).getInt("age"));
                        user.setContact(userArray.getJSONObject(i).getString("contact"));
                        user.setDepartment(userArray.getJSONObject(i).getString("department"));
                        user.setDesignation(userArray.getJSONObject(i).getString("designation"));
                        user.setAddress(userArray.getJSONObject(i).getString("address"));
                        user.setStatus(userArray.getJSONObject(i).getInt("user_status"));
                        user.setDeptId(userArray.getJSONObject(i).getInt("dept_id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
                if(response_type.equals("-1"))
                {
                    Toast.makeText(LoginActivity.this,"Some Unknown Error.",Toast.LENGTH_LONG).show();
                    tvInfo.setText("Some Unknown Error.");
                    tvInfo.setTextColor(Color.RED);
                }
                else if(response_type.equals("0"))
                {
                    Toast.makeText(LoginActivity.this,"Invalid ID / Password",Toast.LENGTH_LONG).show();
                    tvInfo.setText("No such user exists.");
                    tvInfo.setTextColor(Color.RED);
                }
                else
                {
                    if(user.getStatus()==0)
                    {
                        tvInfo.setText("Your login status is In-Active. Please contact admin.");
                        tvInfo.setTextColor(Color.RED);
                    }
                    else
                    {
                        if(!user.getType().equals("Admin") && user.getDeptId()==0)
                        {
                            tvInfo.setText("You have not been assigned any department. Please contact admin.");
                            tvInfo.setTextColor(Color.RED);
                        }
                        else
                        {
                            app.setUser(user);
                            Toast.makeText(app, "Login Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                            LoginActivity.this.finish();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(app, "VolleyError Error."+error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void openBaseAddressSettings(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Base Address");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        input.setText(app.getWebAddress());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!input.getText().toString().equals("")) {
                    app.setWebAddress(input.getText().toString());
                    Toast.makeText(app, "Application Base Address Set Successfully.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(app, "The address can not be blank.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void register(View view)
    {
        startActivity(new Intent(this,RegisterActivity.class));
    }
}