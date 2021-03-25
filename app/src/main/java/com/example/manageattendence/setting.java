package com.example.manageattendence;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class setting extends AppCompatActivity {
    private Helper helper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        helper=new Helper(this);
        db=helper.getWritableDatabase();

        final Cursor cursor=db.rawQuery("SELECT * FROM SWITCH",null);
        cursor.moveToFirst();
        final Switch btn=(Switch) findViewById(R.id.switch1);
        if (cursor.getString(0).equals("0")){
            btn.setText("off");
            btn.setChecked(false);
        }else{
            btn.setText("on");
            btn.setChecked(true);
        }
        btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(btn.isChecked()){
                    Toast.makeText(setting.this, "On", Toast.LENGTH_SHORT).show();
                    db.execSQL("UPDATE SWITCH SET ISON=1");
                    btn.setText("on");
                }else{
                    db.execSQL("UPDATE SWITCH SET ISON=0");
                    Toast.makeText(setting.this, "off", Toast.LENGTH_SHORT).show();
                    btn.setText("off");
                }
            }
        });
    }
}