package com.robapp.app.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.mytechia.robobo.framework.RoboboManager;
import com.mytechia.robobo.framework.service.RoboboServiceHelper;
import com.mytechia.robobo.rob.BluetoothRobInterfaceModule;
import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.R;
import com.robapp.app.dialog.BehaviorSelectionDialog;
import com.robapp.app.dialog.RobDeviceSelectionDialog;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.behaviors.item.BehaviorFileItem;
import com.robapp.behaviors.listener.RobHelperListener;
import com.robapp.tools.Utils;

import java.io.IOException;

/**
 * Created by Arthur on 03/11/2016.
 * This is an activity who manage the natigation drawer.
 * All activity in the application extends this class
 */

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected static boolean initiaed = false;
    public static boolean robStarted = false;
    private ProgressDialog dial;

    protected static BehaviorItemI selectedBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Setup the navigation drawer, if you reuse this class you should
     * use this fonction to define the navigation drawer.
     * In the create method you have to set your the content view
     * and after call this method.
     */
    public void setupDrawer()
    {
        Utils.setCurrentActivity(this);
        if(!initiaed)
        {
            try {
                //Initiate  the application
                //Load downloaded behaviors and native behaviors
                Utils.init(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Choose a default behavior
            selectedBehavior = Utils.getAllItem().get(0);
            initiaed = true;
        }

        //The  toolbar title is the behavior name
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(selectedBehavior != null)
            toolbar.setTitle(selectedBehavior.getName());
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

        //If a behavior is started, users are not allowed to change the current activity
        if(Utils.isBehaviorStarted())
            return false;

        //The action when the user click on the bluetooth menu item
        // Launch Dialog for selecting the bluetooth device
        if (id == R.id.nav_bluetooth) {
            robStarted = false;
            showRoboboDeviceSelectionDialog();
        }//The action when the user click on the manage menu item (Launch the FileExplorerActivity)
        else if (id == R.id.nav_manage) {
            Intent intent = new Intent(Utils.getCurrentActivity(),FileExplorerActivity.class);
            Utils.getCurrentActivity().startActivity(intent);
        }//The action when the user click on the share menu item (Launch th QRCodeActivity)
        else if (id == R.id.nav_share) {
            Intent intent = new Intent(Utils.getCurrentActivity(),QRCodeActivity.class);
            Utils.getCurrentActivity().startActivity(intent);
        }//The action when the user click on the download menu item (Launch the DownloadBehaviorActivity)
        else if(id == R.id.nav_download)
        {
            Intent intent = new Intent(Utils.getCurrentActivity(),DownloadBehaviorActivity.class);
            Utils.getCurrentActivity().startActivity(intent);
        }//The action when the user click on the behavior selection menu item
        // Launch Dialog for selecting a behavior to launch
        else if (id == R.id.nav_behavior) {
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
            dialog.show(getFragmentManager(),"Select Behavior");
        }
        //The action when the user click on the behavior removal menu item
        // Launch Dialog for selecting a behavior to remove
        else if (id == R.id.nav_behavior_delete) {
            final BehaviorSelectionDialog dialog = new BehaviorSelectionDialog();
            dialog.setListener(new BehaviorSelectionDialog.Listener() {
                @Override
                public void behaviorSelected(BehaviorItemI item) {
                    if(!(item instanceof BehaviorFileItem))
                        return;

                    Utils.removeBehavior((BehaviorFileItem) item);
                    if(selectedBehavior == item)
                    {
                        selectedBehavior = Utils.getAllItem().get(0);
                        Intent intent = new Intent(Utils.getCurrentActivity(),BehaviorActivity.class);
                        Utils.getCurrentActivity().startActivity(intent);
                    }

                }

                public void selectionCancelled()
                {}
            });
            dialog.show(getFragmentManager(),"Delete Behavior");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Show a dialog box for selecting a bluetooth device
     */
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
                Toast.makeText(BaseActivity.this, "Bluetooth disabled", Toast.LENGTH_SHORT).show();

            }

        });
        dialog.show(getFragmentManager(),"BLUETOOTH-DIALOG");

    }

    /**
     * Connect the smartphone to the robobo
     * @param roboboBluetoothName The device name
     */
    private void launchAndConnectRoboboService(String roboboBluetoothName) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //wait to dialog shown during the startup of the framework and the bluetooth connection
                if(dial != null)
                    return;

                dial = ProgressDialog.show(Utils.getCurrentActivity(),
                        getString(R.string.DialogConnexionTitle),
                        getString(R.string.DialogConnexionMsg));
            }
        });

        try{
            RoboboServiceHelper roboboHelper = new RoboboServiceHelper(this,new RobHelperListener());
            Bundle options = new Bundle();
            options.putString(BluetoothRobInterfaceModule.ROBOBO_BT_NAME_OPTION,roboboBluetoothName);
            roboboHelper.bindRoboboService(options);
        }
        catch(Exception e)
        {
            if(dial != null)
                dial.dismiss();
            Toast.makeText(BaseActivity.this, "Connexion failure", Toast.LENGTH_LONG).show();
            finish();
        }



    }

    /**
     * Show an error dialog
     * @param msg Message to display
     */
    public void showErrorDialog(final String msg) {

        if(dial != null)
            dial.dismiss();
        dial = null;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getCurrentActivity());

                builder.setTitle(R.string.ErrorDialogTitle).
                        setMessage(msg);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(BaseActivity.this, "Connexion failure", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        finish();
                    }
                })
                        .setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /**
     * Dismiss the activity progress dial
     */
    public void dismissProgessDial()
    {
        dial.dismiss();
    }
}
