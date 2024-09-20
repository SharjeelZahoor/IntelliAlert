package com.sharjeel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sharjeel.common.App;
import com.sharjeel.entity.User;


public class ProfileActivity extends AppCompatActivity {
    App app;
    User user;
    TextView tvUserName,tvType,tvGender,tvStatus,tvLoginId,tvContact,tvDesig,tvDept,tvAddress;
    Button btnChPw,btnEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvUserName=(TextView)findViewById(R.id.textViewUserName);
        tvType=(TextView)findViewById(R.id.textViewProfileType);
        tvGender=(TextView)findViewById(R.id.textViewProfileGender);
        tvStatus=(TextView)findViewById(R.id.textViewProfileStatus);
        tvLoginId=(TextView)findViewById(R.id.textViewProfileLoginId);
        tvContact=(TextView)findViewById(R.id.textViewProfileContact);
        tvDesig=(TextView)findViewById(R.id.textViewProfileDesig);
        tvDept=(TextView)findViewById(R.id.textViewProfileDept);
        tvAddress=(TextView)findViewById(R.id.textViewProfileAddress);
        btnChPw=(Button) findViewById(R.id.btnChPw);
        btnEdit=(Button) findViewById(R.id.btnEdit);
        this.setTitle("Profile");
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
            tvUserName.setText(user.getName());tvType.setText(user.getType());tvGender.setText(user.getGender());
            tvStatus.setText(user.getStatusStr());tvContact.setText(user.getContact());tvLoginId.setText(user.getCnic());
            tvDept.setText((user.getDepartment().equals("")?"No Department":user.getDepartment()));tvAddress.setText(user.getAddress());tvDesig.setText(user.getDesignation());
            if(u.getId()!=user.getId())
            {
                btnChPw.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);
            }
        }
    }
    public void changePw(View view)
    {
        if(user==null)
            return;
        Intent i=new Intent(this,ChangePasswordActivity.class);
        i.putExtra("user",user);
        startActivity(i);
        ProfileActivity.this.finish();
    }
    public void editProfile(View view)
    {
        if(user==null)
            return;
        Intent i=new Intent(this,EditProfileActivity.class);
        i.putExtra("user",user);
        startActivity(i);
        ProfileActivity.this.finish();
    }

}