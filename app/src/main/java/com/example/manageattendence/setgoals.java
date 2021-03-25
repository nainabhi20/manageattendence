package com.example.manageattendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manageattendence.Helper;
import com.example.manageattendence.R;

public class setgoals extends AppCompatActivity {
    private Helper helper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgoals);
        helper=new Helper(this);
        db=helper.getWritableDatabase();
        SeekBar seekBar=(SeekBar)findViewById(R.id.seekBar2);//GETTTING THE SEEKBAR INSTANCE
        seekBar.setMax(100);//SET MAX AS 100
        seekBar.setProgress(60);//SET DEFAULT ASS 75
        final TextView criteria=(TextView)findViewById(R.id.percentage_criteria_text_view_set_goal);
        criteria.setText(""+seekBar.getProgress());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                criteria.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button btn=(Button)findViewById(R.id.save_set_goal);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer p_c=new Integer(Integer.parseInt(criteria.getText().toString()));
                db.execSQL("UPDATE GOAL SET ATTENDENCE_GOAL="+p_c);
                Toast.makeText(setgoals.this, "Updated", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(setgoals.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}