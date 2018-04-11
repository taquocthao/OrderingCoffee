package com.tathao.orderingcoffee;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.squareup.picasso.Picasso;
import com.tathao.orderingcoffee.DAO.LoginWithFacebook;
import com.tathao.orderingcoffee.FragmentApp.HomePage;
import com.tathao.orderingcoffee.FragmentApp.ProfileUser;
import com.tathao.orderingcoffee.InterfaceHandler.AddFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddFragment {


    //private FragmentManager fragmentManager;

    private android.app.FragmentManager fragmentManager;

    private Toolbar toolbar;

    private FloatingActionButton fab;

    private ImageView imgUser;
    private TextView tvName, tvMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //Kiểm tra kiểu đăng nhập

        Intent intent = getIntent();
        int type = intent.getIntExtra("typeLogin", 0);
        // đăng nhập bằng tài khoản đăng ký
        if (type == 1) {
            Toast.makeText(getApplicationContext(), "Bạn đã đăng nhập bằng tài đăng ký\nvui lòng thử đăng nhập lại", Toast.LENGTH_LONG).show();
        } else if (type == 2) { // đăng nhập bằng facebook

            LoginWithFacebook loginWithFacebook = new LoginWithFacebook();
            AccessToken accessToken = loginWithFacebook.getAccessTokenUser();
            setProfileNavigationFromFacebook(accessToken);

        } else if (type == 3) { // đăng nhập bằng google

        } else {
            Toast.makeText(getApplicationContext(), "Có lỗi xảy ra\nvui lòng thử đăng nhập lại", Toast.LENGTH_LONG).show();
        }


    }

    //hàm khai báo và khởi tạo
    private void init() {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Trang chủ");
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View viewNav = navigationView.getHeaderView(0);
        imgUser = viewNav.findViewById(R.id.imgProfile);
        tvName = viewNav.findViewById(R.id.tvNameInNavHeader);
        tvMail = viewNav.findViewById(R.id.tvEmailInNavHeader);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getFragmentManager();

        //add fragment home page to content main
        HomePage fragmentHomePage = new HomePage();
        addFragment(fragmentHomePage, "Trang chủ");
        navigationView.setCheckedItem(R.id.nav_home);

        fab.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void setProfileNavigationFromFacebook(AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String id = object.getString("id");
                    String name = object.getString("name");
                    String email = object.getString("email");

                    tvName.setText(name);
                    tvMail.setText(email);
                    Picasso.get()
                            .load("https://graph.facebook.com/" + id + "/picture?type=small")
                            .into(imgUser);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            //add fragment home page to content main
            HomePage fragmentHomePage = new HomePage();
            addFragment(fragmentHomePage, "Trang chủ");

        } else if (id == R.id.nav_order_history) {

            toolbar.setTitle("Lịch sử gọi món");
        } else if (id == R.id.nav_favorite_oder) {

            toolbar.setTitle("Món yêu thích");
        } else if (id == R.id.nav_profile) {

            // add fragment profile user to content main
            ProfileUser profileUser = new ProfileUser();
            addFragment(profileUser, "Thông tin cá nhân");
            fab.setVisibility(View.GONE);

        } else if (id == R.id.nav_checkin) {

        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_send_fellback) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void addFragment(Fragment fragment, String title) {
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
        toolbar.setTitle(title);
    }


}
