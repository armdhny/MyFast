package com.motiolabs.android.myfast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.motiolabs.android.myfast.adapter.CategoryFragmentPagerAdapter;
import com.motiolabs.android.myfast.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView userImageView;
    NavigationView navigationView;
    private TextView nama, email, headline;
    private static final String TAG = LoginActivity.class.getSimpleName();

    Button logoutButton, shareButton, openMyProfileButton, openOtherProfileButton;

    //In URL pass whatever data from user you want for more values check below link
    //LINK : https://developer.linkedin.com/docs/fields/basic-profile
    private static final String host = "api.linkedin.com";
    private static final String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline," +
            "public-profile-url,picture-url,email-address,picture-urls::(original))";


    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        // Temukan tampilan pager yang akan memungkinkan kita menggeser antar fragmen
        viewPager = findViewById(R.id.viewpager);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        // Set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        navigationView =(NavigationView) findViewById(R.id.nav_view);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);
        navigationView.setNavigationItemSelectedListener(this);

        // menngatur fragmen dengan default saat memulai aplikasi
        onNavigationItemSelected(navigationView.getMenu().getItem(0).setChecked(true));

        // menngatur kategori2 fragment pager adapter
        CategoryFragmentPagerAdapter pagerAdapter =
                new CategoryFragmentPagerAdapter(this, getSupportFragmentManager());

        // Set the pager adapter onto the view pager
        viewPager.setAdapter(pagerAdapter);

        //Linkedin
        nama = (TextView) header.findViewById(R.id.name);
        headline = (TextView) header.findViewById(R.id.headline);
        email = (TextView) header.findViewById(R.id.email);
        userImageView = (ImageView) header.findViewById(R.id.profile_image);


        fetchBasicProfileData();
    }

    public void fetchBasicProfileData() {

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(MainActivity.this, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try{
                    // Success!
                    updateUI(result.getResponseDataAsJson());
                    Log.d(TAG, "API Res : " + result.getResponseDataAsString() + "\n" + result.getResponseDataAsJson().toString());
                    Toast.makeText(MainActivity.this, "Successfully fetched LinkedIn profile data.", Toast.LENGTH_SHORT).show();



                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making GET request!
                Log.e(TAG, "Fetch profile Error   :" + liApiError.getLocalizedMessage());
                Toast.makeText(MainActivity.this, "Failed to fetch basic profile data. Please try again.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void updateUI(JSONObject response) {
        try {

                //menampilkan nama depan dan nama belakang mengubahnya ke STRING
                nama.setText(response.get("firstName").toString()+ " " + response.get("lastName").toString());

            //menampilkan headline pada linkedin
            // mengubahnya ke STRING
                headline.setText(response.get("headline").toString());

                email.setText(response.getString("emailAddress"));

                // menangkap gambar yang ada di profile linkedin dan menampilkan nya
            // menggunakan picasso
                Picasso.with(this).load(response.getString("pictureUrl"))
                                .into(userImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Switch Fragments in a ViewPager on clicking items in Navigation Drawer
        if (id == R.id.nav_home) {
            viewPager.setCurrentItem(Constants.HOME);
        } else if (id == R.id.nav_world) {
            viewPager.setCurrentItem(Constants.WORLD);
        } else if (id == R.id.nav_science) {
            viewPager.setCurrentItem(Constants.SCIENCE);
        } else if (id == R.id.nav_sport) {
            viewPager.setCurrentItem(Constants.SPORT);
        } else if (id == R.id.nav_environment) {
            viewPager.setCurrentItem(Constants.ENVIRONMENT);
        } else if (id == R.id.nav_society) {
            viewPager.setCurrentItem(Constants.SOCIETY);
        } else if (id == R.id.nav_fashion) {
            viewPager.setCurrentItem(Constants.FASHION);
        } else if (id == R.id.nav_business) {
            viewPager.setCurrentItem(Constants.BUSINESS);
        } else if (id == R.id.nav_culture) {
            viewPager.setCurrentItem(Constants.CULTURE);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    // Initialize the contents of the Activity's options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    // This method is called whenever an item in the options menu is selected.
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        } else if (id == R.id.logout){
            LISessionManager.getInstance(this).clearSession();
            //show toast
            Toast.makeText(this, "Logout successfully.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this , LoginActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}
