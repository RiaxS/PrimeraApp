package cr.ac.ucr.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cr.ac.ucr.app.adapters.MainViewPagerAdapter;
import cr.ac.ucr.app.fragments.ProfileFragment;
import cr.ac.ucr.app.fragments.ToDoListFragment;
import cr.ac.ucr.app.utilis.AppPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager vpPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vpPager = findViewById(R.id.vp_pager);
        bottomNavigationView = findViewById(R.id.bnv_botton_menu);

        setupViewPagerListener();
        setupBottomNavViewListener();
        setupViewPager();


        //setupListViewListener();
    }

    public void setupViewPager(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(ToDoListFragment.newInstance());
        fragments.add(ProfileFragment.newInstance());

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);

        vpPager.setAdapter(mainViewPagerAdapter);

    }
    public void setupBottomNavViewListener(){

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.my_task:
                        vpPager.setCurrentItem(0);
                        return true;

                    case R.id.profile:
                        vpPager.setCurrentItem(1);
                        return true;
                    default:
                }
                return false;
            }
        });

    }
    public void setupViewPagerListener(){
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(prevMenuItem != null){
                    prevMenuItem.setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout:
                logout();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }





    private void logout() {

        AppPreferences.getInstance(this).clear();

        //editor.clear().commit();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onClick(View view) {

    }


}