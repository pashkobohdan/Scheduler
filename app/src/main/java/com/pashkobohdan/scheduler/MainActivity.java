package com.pashkobohdan.scheduler;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import com.pashkobohdan.scheduler.library.dateBaseHelper.ReadData;

import layout.Events;
import layout.Lectures;
import layout.Subjects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int currentPosition;
    Lectures lectures;
    Events events;
    Subjects subjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        events = new Events();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, events).commit();

        currentPosition = 0;
        navigationView.setCheckedItem(R.id.events);

        ReadData.setContext(getBaseContext());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (currentPosition == 0) {
            getMenuInflater().inflate(R.menu.events_menu, menu);
        } else {
            if (currentPosition == 1) {
                getMenuInflater().inflate(R.menu.lectures_menu, menu);
            } else {
                getMenuInflater().inflate(R.menu.subjects_menu, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            // lectures
            case R.id.add_lecture:
                lectures.clickOptionsMenu(1);
                break;

            // events
            case R.id.resort_all:
                events.clickOptionsMenu(1);
                break;
            case R.id.sort_by_time:
                events.clickOptionsMenu(2);
                break;
            case R.id.sort_by_name:
                events.clickOptionsMenu(3);
                break;
            case R.id.sort_by_type:
                events.clickOptionsMenu(5);
                break;

            // subjects
            case R.id.sort_by_name_subjects:
                subjects.clickOptionsMenu(1);
                break;
            case R.id.sort_by_hours:
                subjects.clickOptionsMenu(2);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.events) {
            if (events == null) {
                events = new Events();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, events).commit();

            currentPosition = 0;
        } else if (id == R.id.shedule) {
            if (lectures == null) {
                lectures = new Lectures();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, lectures).commit();

            currentPosition = 1;
        } else if (id == R.id.subjects) {
            if (subjects == null) {
                subjects = new Subjects();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, subjects).commit();

            currentPosition = 2;
        } else if (id == R.id.nav_share) {
            //
        } else if (id == R.id.nav_send) {
            //
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.subjectList) {
            menu.add(1, 1, 0, "Edit");
            menu.add(1, 2, 0, "Delete");
            menu.add(1, 3, 0, "Open");

            subjects.currentListCheckPosition = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        }

        if (v.getId() == R.id.listView) {
            menu.add(2, 1, 0, "Edit");
            menu.add(2, 2, 0, "Delete");
            menu.add(2, 3, 0, "Open");

            events.currentListCheckPosition = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getGroupId()) {
            case 1:
                switch (item.getItemId()) {
                    case 1:
                        subjects.edit();
                        break;
                    case 2:
                        subjects.delete();
                        break;
                    case 3:
                        subjects.open();
                        break;
                }
                break;
            case 2:
                switch (item.getItemId()) {
                    case 1:
                        events.edit();
                        break;
                    case 2:
                        events.delete();
                        break;
                    case 3:
                        events.open();
                        break;
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

//        Toast.makeText(getBaseContext(), "rotate !!!", Toast.LENGTH_LONG).show();
//
        switch (currentPosition) {
            case 0:
                break;
            case 1:
                Toast.makeText(getBaseContext(), "resize !" , Toast.LENGTH_LONG).show();
                lectures.resizeAll(newConfig.orientation);
                break;
            case 2:
                break;
//            case 0 : getSupportFragmentManager().beginTransaction().remove(events).commit(); break;
//            case 1 : getSupportFragmentManager().beginTransaction().remove(lectures).commit(); break;
//            case 2 : getSupportFragmentManager().beginTransaction().remove(subjects).commit(); break;
        }
//        switch (currentPosition){
//            case 0 : getSupportFragmentManager().beginTransaction().replace(R.id.content_main, events).commit();
//            case 1 : getSupportFragmentManager().beginTransaction().replace(R.id.content_main, lectures).commit();
//            case 2 : getSupportFragmentManager().beginTransaction().replace(R.id.content_main, subjects).commit();
//        }

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getBaseContext(), "landscape " , Toast.LENGTH_LONG).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(getBaseContext(), "portair", Toast.LENGTH_LONG).show();
        }
    }
}
