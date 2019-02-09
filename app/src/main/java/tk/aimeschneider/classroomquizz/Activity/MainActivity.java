package tk.aimeschneider.classroomquizz.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import tk.aimeschneider.classroomquizz.Fragment.ChallengeFragment;
import tk.aimeschneider.classroomquizz.Fragment.ChatFragment;
import tk.aimeschneider.classroomquizz.Fragment.HomeFragment;
import tk.aimeschneider.classroomquizz.Fragment.SensorFragment;
import tk.aimeschneider.classroomquizz.ModelOnly.Controller;
import tk.aimeschneider.classroomquizz.R;

import static tk.aimeschneider.classroomquizz.ModelOnly.Controller.WEB_FRIENDS_IMG;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    FragmentManager fm;
    NavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout) findViewById(R.id.drawerMain);
        nav = (NavigationView) findViewById(R.id.nvMain);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fm = getSupportFragmentManager();
        nav.setNavigationItemSelectedListener(this);
        nav.getMenu().performIdentifierAction(R.id.nav_home, 0);
        ImageView imgAvatar = nav.getHeaderView(0).findViewById(R.id.imgnav) ;
        if (!Controller.me.getPhotoPath().isEmpty() && Controller.me.getPhotoPath()!= "null")
            Picasso.with(imgAvatar.getContext()).load(WEB_FRIENDS_IMG  + Controller.me.getPhotoPath()).into(imgAvatar);
        else
        {
            Drawable color = new ColorDrawable(Color.parseColor(Controller.me.getRandomColor()));
            imgAvatar.setImageDrawable(color);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA}, 99);
        }
        fm.beginTransaction().replace(R.id.contentFL, new HomeFragment()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
            {
                fm.beginTransaction().replace(R.id.contentFL, new HomeFragment()).commit();
                break;
            }
            case R.id.nav_challenge: {
                fm.beginTransaction().replace(R.id.contentFL, new ChallengeFragment()).commit();
                break;
            }
            case R.id.nav_sensor: {
                fm.beginTransaction().replace(R.id.contentFL, new SensorFragment()).commit();
                break;
            }
            case R.id.nav_chat:
            {
                fm.beginTransaction().replace(R.id.contentFL, new ChatFragment()).commit();
                break;
            }
            case R.id.nav_quit:
            {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        }
        drawer.closeDrawers();
        return true;
    }




}
