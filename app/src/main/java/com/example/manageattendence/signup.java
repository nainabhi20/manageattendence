package com.example.manageattendence;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signup extends Fragment {
    FirebaseAuth mAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signup.
     */
    // TODO: Rename and change types and number of parameters
    public static signup newInstance(String param1, String param2) {
        signup fragment = new signup();
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
        final View root=inflater.inflate(R.layout.fragment_signup,container,false);
        Button sign_up=(Button)root.findViewById(R.id.sign_up_sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final EditText name, email, pass;
                name = (EditText) root.findViewById(R.id.name_sign_up);
                email = (EditText) root.findViewById(R.id.email_sign_up);
                pass = (EditText) root.findViewById(R.id.password_sign_up);
                if (TextUtils.isEmpty(name.getText().toString())) {
                    final Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    final VibrationEffect vibrationEffect1;
                    vibrationEffect1 = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE);
                    // it is safe to cancel other vibrations currently taking place
                    vibrator.cancel();
                    vibrator.vibrate(vibrationEffect1);
                    Toast.makeText(getActivity().getApplicationContext(), "Enter your name first", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(email.getText().toString())) {
                        final Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        final VibrationEffect vibrationEffect1;
                        vibrationEffect1 = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE);
                        // it is safe to cancel other vibrations currently taking place
                        vibrator.cancel();
                        vibrator.vibrate(vibrationEffect1);
                        Toast.makeText(getActivity().getApplicationContext(), "Enter email", Toast.LENGTH_SHORT).show();
                    } else {
                        if (TextUtils.isEmpty(pass.getText().toString())) {
                            final Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                            final VibrationEffect vibrationEffect1;
                            vibrationEffect1 = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE);
                            // it is safe to cancel other vibrations currently taking place
                            vibrator.cancel();
                            vibrator.vibrate(vibrationEffect1);
                            Toast.makeText(getActivity().getApplicationContext(), "create a password", Toast.LENGTH_SHORT).show();
                        } else {
                            mAuth = FirebaseAuth.getInstance();
                            mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity().getApplicationContext(), "Account created succesfully", Toast.LENGTH_SHORT).show();
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("MAINDATA").child(mAuth.getCurrentUser().getUid());
                                        Map<String, String> map = new HashMap<String, String>();
                                        map.put("name", name.getText().toString());
                                        reference.setValue(map);
                                        Fragment fragment = new signin();
                                        FragmentManager fm = getFragmentManager();
                                        FragmentTransaction transaction = fm.beginTransaction();
                                        transaction.replace(R.id.fragment, fragment);
                                        transaction.commit();
                                    } else {
                                        Toast.makeText(getActivity().getApplicationContext(), "Enter email in formated way", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }
        });
        TextView already=(TextView)root.findViewById(R.id.already_have_an_account);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new signin();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment, fragment);
                transaction.commit();
            }
        });
        return root;
    }
}