package com.tathao.orderingcoffee.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;
import com.tathao.orderingcoffee.NetworkAPI.Client;
import com.tathao.orderingcoffee.presenter.LoginStore;
import com.tathao.orderingcoffee.model.User;
import com.tathao.orderingcoffee.view.Activity.LoginActivity;
import com.tathao.orderingcoffee.view.Fragment.HomePage;
import com.tathao.orderingcoffee.view.Fragment.ProfileUser;
import com.tathao.orderingcoffee.view.Fragment.ShopingCart;
import com.tathao.orderingcoffee.Interface.AddFragment;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.NetworkAPI.UserDataStore;
import com.tathao.orderingcoffee.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddFragment {


    //private FragmentManager fragmentManager;

    private android.app.FragmentManager fragmentManager;

    private Toolbar toolbar;

    private FloatingActionButton fab;

    private ImageView imgUser;
    private TextView tvName, tvMail;
    private Menu nav_menu;

    private AccessToken accessToken;
    private int typeLogin = 0;
    private LoginStore loginStore;
    private UserDataStore store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //Kiểm tra kiểu đăng nhập

        Intent intent = getIntent();
        typeLogin = intent.getIntExtra("typeLogin", 0);

        if (typeLogin == 1) {// đăng nhập bằng tài khoản đăng ký
            // lấy thông tin user
            try {
                setProfileNavigationFromLoginWithAccount();

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (typeLogin == 2) { // đã đăng nhập bằng facebook
            accessToken = loginStore.getAccessTokenUserFacebook();
            setProfileNavigationFromFacebook(accessToken);

        } else if (typeLogin == 3) { // đã đăng nhập bằng google
            setProfileNavigationFromGoogle();
        } else {
            Toast.makeText(getApplicationContext(), "Có lỗi xảy ra\nvui lòng thử đăng nhập lại", Toast.LENGTH_LONG).show();
        }


    }

    //hàm khai báo và khởi tạo
    private void init() {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.home));
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                if(getFragmentManager().getBackStackEntryCount() >= 2){

                    getFragmentManager().popBackStack(getString(R.string.food_page), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportActionBar().setTitle(getString(R.string.category));
                }
                else if(getFragmentManager().getBackStackEntryCount() == 1){

                    getFragmentManager().popBackStack(getString(R.string.food_category_page), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportActionBar().setTitle(getString(R.string.home));
                    //remove back button
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    // show hamburger button
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.syncState();

                }
                else
                    drawer.openDrawer(GravityCompat.START);
              //  Log.d("count", getFragmentManager().getBackStackEntryCount()+"");
            }
        });



        NavigationView navigationView = findViewById(R.id.nav_view);
        View viewNav = navigationView.getHeaderView(0);
        imgUser = viewNav.findViewById(R.id.imgProfile);
        tvName = viewNav.findViewById(R.id.tvNameInNavHeader);
        tvMail = viewNav.findViewById(R.id.tvEmailInNavHeader);
        nav_menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(this);

        loginStore = new LoginStore();

        store = new UserDataStore(getApplicationContext());

        fragmentManager = getFragmentManager();

        //add fragment home page to content main
        HomePage fragmentHomePage = new HomePage();
        addFragment(fragmentHomePage, getString(R.string.home));
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


    private void setProfileNavigationFromLoginWithAccount() throws ExecutionException, InterruptedException, IOException {
        String url = Config.urlUserInfor;
        String jsonUser = new Client(url, Client.GET, null, getBaseContext())
                .execute()
                .get().toString();
       // Log.d("jsonUser", jsonUser);
        // ánh xạ chuỗi json về đối tượng user bằng thư viện moshi
        store.setUserData(jsonUser);
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<User> userJsonAdapter = moshi.adapter(User.class);
        User user = userJsonAdapter.fromJson(jsonUser);

        imgUser.setImageResource(R.drawable.profile_default);
        String name = user.getName();
        String email = user.getEmail();
        tvName.setText(name);
        tvMail.setText(email);

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
                            .load("https://graph.facebook.com/" + id + "/picture?type=large")
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

    private void setProfileNavigationFromGoogle() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Uri personPhoto = account.getPhotoUrl();
            Picasso.get().load(personPhoto).into(imgUser);

            String personName = account.getDisplayName();
            tvName.setText(personName);

            String personEmail = account.getEmail();
            tvMail.setText(personEmail);

            nav_menu.findItem(R.id.nav_checkin).setVisible(false);
            nav_menu.findItem(R.id.nav_chat).setVisible(false);

        }
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
            ShopingCart shopingCart = new ShopingCart();
            addFragment(shopingCart, getString(R.string.recent_order));
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

        } else if (id == R.id.nav_logout) {
            if (typeLogin == 1) {
                store.destroyToken();
                BackToLoginPage();
            } else if (typeLogin == 2) {
                //loginStore.destroyAccessTokenFacebook();
                LoginManager.getInstance().logOut();
                BackToLoginPage();
            } else if (typeLogin == 3) {
                GoogleSignInClient mGoogleSignInClient = loginStore.getmGoogleSignInClient(getApplicationContext());
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                BackToLoginPage();
                            }
                        });
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void BackToLoginPage() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void addFragment(Fragment fragment, String title) {
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
        toolbar.setTitle(title);
    }


}
