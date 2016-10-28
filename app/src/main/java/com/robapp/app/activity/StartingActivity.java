package com.robapp.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.mytechia.robobo.rob.BluetoothRobInterfaceModule;
import com.robapp.R;
import com.robapp.app.dialog.RobDeviceSelectionDialog;

public class StartingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showRoboboDeviceSelectionDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showRoboboDeviceSelectionDialog() {

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
                ProgressDialog waitDialog = ProgressDialog.show(StartingActivity.this,
                        getString(R.string.DialogConnexionTitle),
                        getString(R.string.DialogConnexionMsg));
                MainActivity.setProgessDialog(waitDialog);
            }
        });

        Intent i = new Intent(this,MainActivity.class);
        i.putExtra(BluetoothRobInterfaceModule.ROBOBO_BT_NAME_OPTION,roboboBluetoothName);
        this.startActivity(i);
    }

    protected void showErrorDialog(final String msg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartingActivity.this);

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
}
