package com.example.manageattendence.ui.main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.manageattendence.Helper;
import com.example.manageattendence.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link thrusday#newInstance} factory method to
 * create an instance of this fragment.
 */
public class thrusday extends Fragment {
    private Helper helper;
    private SQLiteDatabase db;
    private ArrayList<String> subject_name,time_table,location;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public thrusday() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment thrusday.
     */
    // TODO: Rename and change types and number of parameters
    public static thrusday newInstance(String param1, String param2) {
        thrusday fragment = new thrusday();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_thrusday,container,false);
        // Inflate the layout for this fragment
        //-->
        //initialix=ze variable
        helper=new Helper(getContext());
        db=helper.getWritableDatabase();
        subject_name=new ArrayList<String>();
        time_table=new ArrayList<String>();
        location=new ArrayList<String>();
        //-->
        //-->
        //Retrive data from dataabase


        Cursor cursor=db.rawQuery("SELECT * FROM TIME_TABLE WHERE DAY_NAME='THURSDAY'",null);
        if (cursor.moveToFirst()){
            do {
                subject_name.add(cursor.getString(0));
                time_table.add(cursor.getString(2));
                location.add(cursor.getString(3));
            }while (cursor.moveToNext());
        }
        cursor.close();

        //->>
        //->>
        //add click listen on add subject in time table

        Button add_subject=(Button)root.findViewById(R.id.add_subject_thursday_time_table);
        add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),add_subject_time_table.class);
                intent.putExtra("DAY","THURSDAY");
                startActivity(intent);
            }
        });

        //-->>
        GridView gridView=(GridView)root.findViewById(R.id.grid_view_thursday);
        GridAdapter gridAdapter=new GridAdapter(getContext(),subject_name,time_table,location);
        gridView.setAdapter(gridAdapter);
        return root;
    }
}