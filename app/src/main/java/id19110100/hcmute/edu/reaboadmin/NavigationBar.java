package id19110100.hcmute.edu.reaboadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import id19110100.hcmute.edu.reaboadmin.Fragment.FavoriteFragment;
import id19110100.hcmute.edu.reaboadmin.Fragment.HistoryFragment;
import id19110100.hcmute.edu.reaboadmin.Fragment.HomeFragment;
import id19110100.hcmute.edu.reaboadmin.Fragment.MyProfileFragment;
import id19110100.hcmute.edu.reaboadmin.databinding.NavBarBinding;

public class NavigationBar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //navigation drawer
    private DrawerLayout drawerLayout;
    private static final int HOME_FRAGMENT=0;
    private static final int ORDER_LIST_FRAGMENT=1;
    private static final int COUPON_LIST_FRAGMENT=2;
    private static final int CART_FRAGMENT=3;
    private static final int MY_PROFILE_FRAGMENT=4;
    private int currentFragment=HOME_FRAGMENT;
    private Toolbar toolbar;

    //view binding
    private NavBarBinding binding;

    TextView user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NavBarBinding.inflate(getLayoutInflater());
        setContentView(R.layout.nav_bar);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView=findViewById(R.id.navigation_view);

        LinearLayout nav_header = (LinearLayout) navigationView.inflateHeaderView(R.layout.nav_header);



        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_home)
        {
            if(currentFragment!=HOME_FRAGMENT)
            {
                replaceFragment(new HomeFragment());
                currentFragment=HOME_FRAGMENT;
            }
        }
        else if(id==R.id.nav_history)
        {
            if(currentFragment!=ORDER_LIST_FRAGMENT)
            {
                replaceFragment(new HistoryFragment());
                currentFragment=ORDER_LIST_FRAGMENT;
            }
        }
        else if(id==R.id.nav_coupon_list)
        {
            if(currentFragment!=COUPON_LIST_FRAGMENT)
            {
                replaceFragment(new FavoriteFragment());
                currentFragment=COUPON_LIST_FRAGMENT;
            }
        }

        else if(id==R.id.nav_my_profile)
        {
            if(currentFragment!=MY_PROFILE_FRAGMENT)
            {
                replaceFragment(new MyProfileFragment());
                currentFragment=MY_PROFILE_FRAGMENT;
            }
        }
        else if(id==R.id.nav_logout)
        {

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }


}