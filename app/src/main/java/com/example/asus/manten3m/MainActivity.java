package com.example.asus.manten3m;

import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewFlipper vf;
    private boolean viewIsAtHome;
    public static final String TAG = "EasysoftChat";

    private Toolbar toolbar;

    private boolean is_in_mode_action = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dummyData();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        vf = (ViewFlipper) findViewById(R.id.vf);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!viewIsAtHome) { //if the current view is not the News fragment
            vf.setDisplayedChild(1); //display the News fragment
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title = getString(R.string.app_name);
        if (id == R.id.nav_user) {
            title = getString(R.string.profile);
            vf.setDisplayedChild(1);
            viewIsAtHome = true;
            // Handle the camera action
        } else if (id == R.id.nav_hostory) {
            title = getString(R.string.history);
            vf.setDisplayedChild(2);
            viewIsAtHome = false;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void dummyData() {
        /**
         * Dummy Data
         */

        long insert = 0;
        WoHolder cholder = new WoHolder();
        cholder.set_Id(1);
        cholder.setPersonGroup(100);
        cholder.setChatGroupMessage(200);
        cholder.setFlag(0);
        cholder.setUnreadCounter(4);
        WoDB dbs = new WoDB(getApplicationContext());
        insert = dbs.insertRecord(cholder.toCommValues(), true);

        cholder.set_Id(2);
        cholder.setPersonGroup(200);
        cholder.setChatGroupMessage(300);
        cholder.setFlag(1);
        cholder.setUnreadCounter(5);
        insert = dbs.insertRecord(cholder.toCommValues(), true);

        long insert1 = 0;
        PersonHolder pholder = new PersonHolder();
        pholder.set_Id(20001);
        pholder.setName("Jano Laskar");
        pholder.setOnline(1);
        pholder.setUpdated(0);
        PersonDB pdb = new PersonDB(getApplicationContext());
        insert1 = pdb.insertRecord(pholder.toCommValues(), true);

        pholder.set_Id(20002);
        pholder.setName("Ciara magnaimi");
        pholder.setOnline(0);
        pholder.setUpdated(1);
        insert1 = pdb.insertRecord(pholder.toCommValues(), true);

        long insert2 = 0;
        FriendHolder fholder = new FriendHolder();
        fholder.setPerson(1);
        fholder.setFriend(2);
        FriendDB fdb = new FriendDB(getApplicationContext());
        insert2 = fdb.insertRecord(fholder.toCommValues(), true);

        long insert3 = 0;
        GroupHolder gholder = new GroupHolder();
        gholder.set_Id(300);
        gholder.setName("Wedding 3M");
        GroupDB gdb = new GroupDB(getApplicationContext());
        insert3 = gdb.insertRecord(gholder.toCommValues(), true);

        long insert4 = 0;
        GroupMemberHolder gmholder = new GroupMemberHolder();
        gmholder.setGroup(300);
        gmholder.setPerson(2);
        GroupMemberDB gmdb = new GroupMemberDB(getApplicationContext());
        insert4 = gmdb.insertRecord(gmholder.toCommValues(), true);

        long insert5 = 0;
        PrivateChatHolder holders = new PrivateChatHolder();
        holders.setId(200);
        holders.setPerson(20001);
        holders.setFriend(20002);
        holders.setMessage("Berbagai Macam Pake yang Tersedia");
        holders.setWhen("13/Sep/2017 11:18:18");
        holders.setDelivered("13/Sep/2017");
        holders.setRead("13/Sep/2017");
        holders.setMine(false);
        PrivateChatDB dbss = new PrivateChatDB(getApplicationContext());
        insert5 = dbss.insertRecord(holders.toCommValues(), true);

        long insert6 = 0;
        GroupChatHolder holderss = new GroupChatHolder();
        holderss.setId(300);
        holderss.setPerson(20001);
        holderss.setFriend(20002);
        holderss.setMessage("Berbagai Macam Pake yang Tersedia");
        holderss.setWhen("13/Sep/2017 07:20:18");
        holderss.setDelivered("13/Sep/2017");
        holderss.setRead("13/Sep/2017");
        holderss.setMine(false);
        GroupChatDB dbsss = new GroupChatDB(getApplicationContext());
        insert6 = dbsss.insertRecord(holderss.toCommValues(), true);
        Log.e("MainActivity", "-------> masuk");

    }



}



