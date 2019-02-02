package tk.aimeschneider.classroomquizz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import tk.aimeschneider.classroomquizz.Fragment.ChallengeFragment;
import tk.aimeschneider.classroomquizz.Fragment.SensorFragment;
import tk.aimeschneider.classroomquizz.R;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    FragmentManager fm;
    NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        drawer = (DrawerLayout) findViewById(R.id.drawerMain);
        nav = (NavigationView) findViewById(R.id.nvMain);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fm = getSupportFragmentManager();
        nav.setNavigationItemSelectedListener(this);
        nav.getMenu().performIdentifierAction(R.id.nav_challenge, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                Intent intent = new Intent(Main2Activity.this, ChatActivity.class);
                startActivity(intent);
                break;
            }
        }
        drawer.closeDrawers();
        return true;
    }

}
