package com.robapp.app.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mytechia.robobo.framework.RoboboManager;
import com.mytechia.robobo.framework.service.RoboboServiceHelper;
import com.mytechia.robobo.rob.BluetoothRobInterfaceModule;
import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.R;
import com.robapp.app.adapter.BehaviorAdapter;
import com.robapp.app.dialog.BehaviorSelectionDialog;
import com.robapp.app.dialog.RobDeviceSelectionDialog;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.behaviors.listener.RobHelperListener;
import com.robapp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Arthur on 03/11/2016.
 */

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected static boolean initied = false;
    public static boolean robStarted = false;

    protected RoboboServiceHelper roboboHelper;
    private ProgressDialog dial;
    private BehaviorAdapter adapter;

    protected static BehaviorItemI selectedBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setupDrawer()
    {
        Utils.setCurrentActivity(this);
        if(!initied)
        {
            Utils.init(getApplicationContext());
            selectedBehavior = Utils.getAllItem().get(0);
            initied = true;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        getMenuInflater().inflate(R.menu.options, menu);
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

        if(Utils.isBehaviorStarted())
            return false;

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_bluetooth) {
            robStarted = false;
            showRoboboDeviceSelectionDialog();


        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(Utils.getCurrentActivity(),FileExplorerActivity.class);
            Utils.getCurrentActivity().startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Utils.getCurrentActivity(),QRCodeActivity.class);
            Utils.getCurrentActivity().startActivity(intent);
        } else if (id == R.id.nav_behavior) {
           final BehaviorSelectionDialog dialog = new BehaviorSelectionDialog();
            dialog.setListener(new BehaviorSelectionDialog.Listener() {
                @Override
                public void behaviorSelected(BehaviorItemI item) {
                   selectedBehavior = item;
                    Intent intent = new Intent(Utils.getCurrentActivity(),BehaviorActivity.class);
                    Utils.getCurrentActivity().startActivity(intent);
                }

                public void selectionCancelled()
                {}
            });
            dialog.show(getFragmentManager(),"Selection Comportement");
        }
        else if(id == R.id.nav_reset)
        {
            try{
                RoboboManager manager = Utils.getRoboboManager();
                IRobMovementModule module = manager.getModuleInstance(IRobMovementModule.class);
                IRobInterfaceModule rob  =   manager.getModuleInstance(IRobInterfaceModule.class);
                rob.getRobInterface().setOperationMode((byte)1);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void showRoboboDeviceSelectionDialog() {

        RobDeviceSelectionDialog dialog = new RobDeviceSelectionDialog();
        dialog.setListener(new RobDeviceSelectionDialog.Listener() {
            @Override
            public void roboboSelected(String roboboName) {

                final String roboboBluetoothName = roboboName;
                launchAndConnectRoboboService(roboboBluetoothName);

            }

            @Override
            public void selectionCancelled() {
                showErrorDialog("No device selected.");
            }

            @Override
            public void bluetoothIsDisabled() {
                finish();
            }

        });
        dialog.show(getFragmentManager(),"BLUETOOTH-DIALOG");

    }

    private void launchAndConnectRoboboService(String roboboBluetoothName) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //wait to dialog shown during the startup of the framework and the bluetooth connection
                dial = ProgressDialog.show(Utils.getCurrentActivity(),
                        getString(R.string.DialogConnexionTitle),
                        getString(R.string.DialogConnexionMsg));
            }
        });

        roboboHelper = new RoboboServiceHelper(this,new RobHelperListener());

        Bundle options = new Bundle();
        options.putString(BluetoothRobInterfaceModule.ROBOBO_BT_NAME_OPTION,roboboBluetoothName);
        roboboHelper.bindRoboboService(options);



    }

    public void showErrorDialog(final String msg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getCurrentActivity());

                builder.setTitle(R.string.ErrorDialogTitle).
                        setMessage(msg);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                        .setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void dismissProgessDial()
    {
        dial.dismiss();
    }
}
