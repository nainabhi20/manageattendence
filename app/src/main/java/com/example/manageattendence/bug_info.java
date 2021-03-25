package com.example.manageattendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class bug_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_info);
        Button suggestion=(Button)findViewById(R.id.suggestion);//get suggesstion button reference from xml to java
        suggestion.setOnClickListener(new View.OnClickListener() {//set click listener on suggestion button
            @Override
            public void onClick(View v) {
                //onclick a gamil portal is open to send eamil to the owner of the app to suggest good features fot app
                Intent intent = new Intent(Intent.ACTION_SENDTO);//create a intent of action send to
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, "abhisheknain1200@gmail.com");//Put your email as extra so that no need to input the email
                intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion to improve quality of the application");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        Button report_bug=(Button)findViewById(R.id.report_bug); //get refrence of report bug button
        report_bug.setOnClickListener(new View.OnClickListener() {//set click listener to report bug button
            @Override
            public void onClick(View v) {
                //onclick a gamil portal is open to send eamil to the owner of the app to suggest good features fot app
                Intent intent = new Intent(Intent.ACTION_SENDTO);//create a intent of action send to
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, "abhisheknain1200@gmail.com");//Put your email as extra so that no need to input the email
                intent.putExtra(Intent.EXTRA_EMAIL,"nain123@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Report the bug");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }
}