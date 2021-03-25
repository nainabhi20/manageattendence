package com.example.manageattendence;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manageattendence.ui.home.HomeFragment;
import com.example.manageattendence.ui.home.edit_subject_detail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    Fragment fragment = new HomeFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.nav_host_fragment, fragment);
                }
                if (item.getItemId() == R.id.time_table) {
                    Intent intent = new Intent(MainActivity.this, time_table.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.edit_att) {
                   //later cant add edit att activity here
                }
                if (item.getItemId() == R.id.edit_criteria) {
                    Intent intent = new Intent(MainActivity.this, setgoals.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.setting) {
                    //replace with action later

                }
                if (item.getItemId() == R.id.how_to_use) {
                    //later
                }
                if (item.getItemId() == R.id.reports) {
                    Intent intent = new Intent(MainActivity.this, bug_info.class);
                    startActivity(intent);
                }
                if (item.getItemId()==R.id.login){
                    Intent intent=new Intent(MainActivity.this,signinsignout.class);
                    startActivity(intent);
                }
                if (item.getItemId()==R.id.signout){
                    FirebaseAuth mAuth=FirebaseAuth.getInstance();
                    try {
                        mAuth.signOut();
                        Toast.makeText(MainActivity.this, "Sign out", Toast.LENGTH_SHORT).show();
                        View headerView = navigationView.getHeaderView(0);
                        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
                        navUsername.setText("Sign in to get sync features");
                        Intent intent=new Intent(MainActivity.this,signinsignout.class);
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
        View headerView = navigationView.getHeaderView(0);
        final TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
        if (mAuth.getCurrentUser()==null){
            navUsername.setText("Sign in to get sync freature");
        }else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("MAINDATA").child(mAuth.getCurrentUser().getUid()).child("name");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    navUsername.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.error_outline){
                    Intent intent=new Intent(MainActivity.this,bug_info.class);
                    startActivity(intent);
                }
                if (item.getItemId()==R.id.signin){
                    Intent intent=new Intent(MainActivity.this,signinsignout.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}