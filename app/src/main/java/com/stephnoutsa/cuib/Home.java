package com.stephnoutsa.cuib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.stephnoutsa.cuib.adapters.HomeAdapter;
import com.stephnoutsa.cuib.models.Student;
import com.stephnoutsa.cuib.utils.MyDBHandler;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context = this;
    MyDBHandler dbHandler;
    String status;
    List<Integer> list;
    GridView gridView;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbHandler = new MyDBHandler(context, null, null, 1);
        status = dbHandler.getSubscribed();
        if (status.equals("pending")) {
            Intent i = new Intent(context, JoinUs.class);
            startActivity(i);
        }

        list = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.gridView);

        int a = 0;
        list.add(a);
        int b = 1;
        list.add(b);
        int c = 2;
        list.add(c);
        int d = 3;
        list.add(d);
        int e = 4;
        list.add(e);
        int f = 5;
        list.add(f);
        int g = 6;
        list.add(g);
        int h = 7;
        list.add(h);
        int i = 8;
        list.add(i);

        listAdapter = new HomeAdapter(this, list);
        gridView.setAdapter(listAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int item = (Integer) parent.getItemAtPosition(position);
                Intent i = new Intent(context, URLDisplay.class);

                switch (item) {
                    case 0:
                        i.putExtra("url", getString(R.string.about_url));
                        startActivity(i);
                        break;

                    case 1:
                        i.putExtra("url", getString(R.string.academics_url));
                        startActivity(i);
                        break;

                    case 2:
                        i.putExtra("url", getString(R.string.admission_url));
                        startActivity(i);
                        break;

                    case 3:
                        i.putExtra("url", getString(R.string.enp_url));
                        startActivity(i);
                        break;

                    case 4:
                        i.putExtra("url", getString(R.string.campus_url));
                        startActivity(i);
                        break;

                    case 5:
                        i.putExtra("url", getString(R.string.sports_url));
                        startActivity(i);
                        break;

                    case 6:
                        i.putExtra("url", getString(R.string.partners_url));
                        startActivity(i);
                        break;

                    case 7:
                        i.putExtra("url", getString(R.string.donate_url));
                        startActivity(i);
                        break;

                    case 8:
                        i.putExtra("url", getString(R.string.contact_url));
                        startActivity(i);
                        break;
                }
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CircleImageView icon = (CircleImageView) view.findViewById(R.id.icon);

                Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                icon.startAnimation(rotate);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(context).
                    setTitle(getString(R.string.exit_title)).
                    setMessage(getString(R.string.exit_message)).
                    setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_curriculum) {
            Student student = dbHandler.getStudent();

            String name = student.getName();
            String matricule = student.getMatricule();
            String enrolled = student.getEnrolYr();
            String level = student.getLevel();
            String school = student.getSchool();
            String department = student.getDepartment();
            String email = student.getEmail();
            String phone = student.getPhone();
            String password = student.getPassword();

            if (name.equals("null") && matricule.equals("null") && enrolled.equals("null") &&
                    level.equals("null") && school.equals("null") && department.equals("null") &&
                    email.equals("null") && phone.equals("null") && password.equals("null")) {
                Toast.makeText(context, getString(R.string.not_student), Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(context, Curriculum.class);
                startActivity(i);
            }
        } else if (id == R.id.nav_messages) {
            Intent i = new Intent(this, Messages.class);
            startActivity(i);
        } else if (id == R.id.nav_payments) {

        } else if (id == R.id.nav_account) {
            Student student = dbHandler.getStudent();

            String name = student.getName();
            String matricule = student.getMatricule();
            String enrolled = student.getEnrolYr();
            String level = student.getLevel();
            String school = student.getSchool();
            String department = student.getDepartment();
            String email = student.getEmail();
            String phone = student.getPhone();
            String password = student.getPassword();

            if (name.equals("null") && matricule.equals("null") && enrolled.equals("null") &&
                    level.equals("null") && school.equals("null") && department.equals("null") &&
                    email.equals("null") && phone.equals("null") && password.equals("null")) {
                Intent i = new Intent(context, MyAccount.class);
                startActivity(i);
            } else {
                Intent i = new Intent(context, StudentAccount.class);
                startActivity(i);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
