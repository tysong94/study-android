package com.songtaeyang.mobilestock;


import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private MainFragment1 mainFragment1;
    private MainFragment2 mainFragment2;
    private MainFragment3 mainFragment3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.main_frame);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        mainFragment1 = new MainFragment1();
        mainFragment2 = new MainFragment2();
        mainFragment3 = new MainFragment3();

        setFragment(mainFragment1);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.inv_quotes:
                        Toast.makeText(MainActivity.this, "시세", Toast.LENGTH_SHORT).show();
                        setFragment(mainFragment1);
                        break;
                    case R.id.inv_info:
                        Toast.makeText(MainActivity.this, "투자 정보", Toast.LENGTH_SHORT).show();
                        setFragment(mainFragment2);
                        break;
                    case R.id.inv_more:
                        Toast.makeText(MainActivity.this, "더보기", Toast.LENGTH_SHORT).show();
                        setFragment(mainFragment3);
                        break;
                }
                return true;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
