package org.fedorahosted.freeotp;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.lucasurbas.listitemview.ListItemView;

import org.fedorahosted.freeotp.add.ScanActivity;
import org.fedorahosted.freeotp.edit.DeleteActivity;
import org.fedorahosted.freeotp.edit.EditActivity;

import java.util.HashMap;
import java.util.Map;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab1,fab2,fab3;
    private Animation fabOpen, fabClose, rotateForward, rotateBackward;
    private boolean isOpen=false;
    private TokenAdapter mTokenAdapter;
    public static final String ACTION_IMAGE_SAVED = "org.fedorahosted.freeotp.ACTION_IMAGE_SAVED";
    private DataSetObserver mDataSetObserver;
    private final int PERMISSIONS_REQUEST_CAMERA = 1;
    private Main2Activity.RefreshListBroadcastReceiver receiver;

    private DrawerLayout mDrawer;
    private NavigationView mNavView;
    ListView simpleList;

    private ListView listOrgsYCods;


    String orgsList[] = {"AFIP", "ANSES", "PAMI"};
    String codigosList[] = {"234245", "42424","434455"};

    private class RefreshListBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mTokenAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onNewIntent(getIntent());

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       codDeOrganismos();

       cargarBotones();

                this.initialize();

        this.attachListeners();

    }



    private void codDeOrganismos() {

        listOrgsYCods = (ListView) findViewById(R.id.list_cod_organismos);
        OrgsYCodsAdapter orgsYCodsAdapter = new OrgsYCodsAdapter(getApplicationContext(), codigosList, orgsList);
        listOrgsYCods.setAdapter(orgsYCodsAdapter);

        registerForContextMenu(listOrgsYCods);
    }


    private void cargarBotones() {
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);



        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                animateFab();
                Toast.makeText(Main2Activity.this, "Escanear código QR", Toast.LENGTH_SHORT).show();
                tryOpenCamera();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class activityClass=null;
                animateFab();
                Toast.makeText(Main2Activity.this, "Cargar Manualmente", Toast.LENGTH_SHORT).show();
                activityClass=FormEditActivity.class;
                Intent nextIntent = new Intent(Main2Activity.this, activityClass);
                startActivity(nextIntent);
            }
        });


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.token,menu);
    }

    private void animateFab()
    {
        if(isOpen)
        {
            fab1.startAnimation(rotateForward);
            fab2.startAnimation(fabClose);
            fab3.startAnimation(fabClose);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isOpen=false;
        }else
        {

            fab1.startAnimation(rotateBackward);
            fab2.startAnimation(fabOpen);
            fab3.startAnimation(fabOpen);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isOpen=true;

        }
    }





    private void initialize() {

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        // Find our drawer view
        this.mDrawer = this.findViewById(R.id.drawer_layout);
        this.mNavView = this.findViewById(R.id.navigation_view);

        View headerView = getLayoutInflater().inflate(R.layout.nav_header_main, mNavView, false);
        mNavView.addHeaderView(headerView);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    private void attachListeners() {

        this.mNavView.setNavigationItemSelectedListener(this);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        this.selectDrawerItem(item);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new Activity and specify the fragment to show based on nav item clicked

        Class activityClass = null;
        int item = menuItem.getItemId();

        switch(menuItem.getItemId()) {

            case R.id.escan_cod_qr:
                activityClass = ScanActivity.class;
                break;
            case R.id.ing_manual:
                activityClass = FormEditActivity.class;
                break;
            case R.id.about_us:
                activityClass = AboutActivity.class;
                break;
            case R.id.terminos_y_cond:
                activityClass = DeleteActivity.class;
                break;
            default:
                break;
        }
        if (activityClass != null) {
            Intent nextIntent = new Intent(this, activityClass);
            this.startActivity(nextIntent);
        }
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);

        // Close the navigation drawer
        this.mDrawer.closeDrawers();

    }





    @RequiresApi(api = Build.VERSION_CODES.M)
    private void tryOpenCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
        }
        else {
            // permission is already granted
            openCamera();
        }
    }

    private void openCamera() {
        startActivity(new Intent(this, ScanActivity.class));
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(Main2Activity.this, R.string.error_permission_camera_open, Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Uri uri = intent.getData();
        if (uri != null) {
            try {
                TokenPersistence.saveAsync(this, new Token(uri));
            } catch (Token.TokenUriInvalidException e) {
                e.printStackTrace();
            }
        }

    }

}
