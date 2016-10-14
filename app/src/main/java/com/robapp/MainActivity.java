package com.robapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mytechia.robobo.framework.RoboboManager;
import com.mytechia.robobo.framework.service.RoboboServiceHelper;
import com.mytechia.robobo.rob.BluetoothRobInterfaceModule;
import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;

public class MainActivity extends AppCompatActivity {

    private static ProgressDialog waitDial = null;
    private RoboboServiceHelper roboboHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.example.arthur.roboboapp.R.layout.mainactivitylayout);
        Toolbar toolbar = (Toolbar) findViewById(com.example.arthur.roboboapp.R.id.toolbar);
        //setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        roboboHelper = new RoboboServiceHelper(this, new RoboboServiceHelper.Listener() {
            @Override
            public void onRoboboManagerStarted(RoboboManager roboboManager) {
                try {
                    dissmisProgressDial();
                    System.out.println("AHAHAHAHAHA");
                    IRobMovementModule mod = roboboManager.getModuleInstance(IRobMovementModule.class);
                    IRobInterfaceModule mod2 = roboboManager.getModuleInstance(IRobInterfaceModule.class);
                    mod2.getRobInterface().setOperationMode((byte)1);
                    mod.moveForwardsTime(new Short("50"),10000);

                } catch (Exception e) {
                        System.out.println(e);
                }
            }
            @Override
            public void onError(String errorMsg) {
                showErrorDialog(errorMsg);
            }
        });

        System.out.println("RobName : "+getIntent().getStringExtra(BluetoothRobInterfaceModule.ROBOBO_BT_NAME_OPTION));
        Bundle options = new Bundle();
        options.putString(BluetoothRobInterfaceModule.ROBOBO_BT_NAME_OPTION, getIntent().
                getStringExtra(BluetoothRobInterfaceModule.ROBOBO_BT_NAME_OPTION));
        roboboHelper.bindRoboboService(options);

    }

    public static void setProgessDialog(ProgressDialog waitDial)
    {
        MainActivity.waitDial = waitDial;
    }

    private static void dissmisProgressDial()
    {
        MainActivity.waitDial.dismiss();
        MainActivity.waitDial=null;
    }

    protected void showErrorDialog(final String msg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle(com.example.arthur.roboboapp.R.string.ErrorDialogTitle).
                        setMessage(msg);
                builder.setPositiveButton(com.example.arthur.roboboapp.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                        .setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
