package com.sharjeel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sharjeel.common.App;
import com.sharjeel.entity.User;


public class DashboardActivity extends AppCompatActivity {
    CardView cvUsers,cvDepartments,cvMeetings,cvNotifications,cvFeedback;
    LinearLayout layoutAdmin,layoutOther;
    App app;
    User user;
    TextView tvWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        app=(App) getApplication();
        tvWelcome=(TextView) findViewById(R.id.textViewWelcome);
        cvUsers=(CardView)findViewById(R.id.layoutUsers);
        cvDepartments=(CardView)findViewById(R.id.layoutDep);
        cvMeetings=(CardView)findViewById(R.id.layoutMeetings);
        cvNotifications=(CardView)findViewById(R.id.layoutNotifications);
        cvFeedback=(CardView)findViewById(R.id.layoutFeedback);
        layoutAdmin=(LinearLayout) findViewById(R.id.linearLayout01);
        layoutOther=(LinearLayout) findViewById(R.id.linearLayout02);
        user=app.getUser();
    }
    @Override
    protected void onResume() {
        super.onResume();
        app=(App) getApplication();
        user=app.getUser();
        if(user==null)
        {
            Toast.makeText(app, "You are not logged in.", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        else
        {
            tvWelcome.setText("Welcome "+user.getName()+" ("+user.getType()+")");
            if(user.getType().equals("Admin"))
            {
                cvMeetings.setVisibility(View.GONE);
                cvNotifications.setVisibility(View.GONE);
                cvFeedback.setVisibility(View.GONE);
                layoutOther.setVisibility(View.GONE);
            }
            else
            {
                cvUsers.setVisibility(View.GONE);
                cvDepartments.setVisibility(View.GONE);
                layoutAdmin.setVisibility(View.GONE);
            }
        }
    }
    public void openUsers(View view){
        Intent i=new Intent(this,UsersActivity.class);
        startActivity(i);
    }
    public void openDepartments(View view){
        Intent i=new Intent(this,DepartmentsActivity.class);
        startActivity(i);
    }
    public void openMeetings(View view){
        Intent i=new Intent(this,MeetingsActivity.class);
        startActivity(i);
    }
    public void openNotifications(View view){
        Intent i=new Intent(this,NotificationsActivity.class);
        startActivity(i);
    }
    public void openFeedbacks(View view){
        Intent i=new Intent(this,FeedbacksActivity.class);
        startActivity(i);}
    public void openProfile(View view){
        Intent i=new Intent(this,ProfileActivity.class);
        i.putExtra("user",app.getUser());
        startActivity(i);
    }
    public void logout(View view){
        String body="<html><body><p>Are you sure you want to logout from app?</p></body></html>";
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Exit?");
        alert.setMessage(Html.fromHtml(body));
        alert.setIcon(android.R.drawable.ic_menu_info_details);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                app.setUser(null);
                DashboardActivity.this.finish();
                dialog.dismiss();
            }
        });
        AlertDialog alertDlg=alert.create();
        alertDlg.show();
    }

}