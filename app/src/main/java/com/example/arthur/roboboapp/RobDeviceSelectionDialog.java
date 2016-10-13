package com.example.arthur.roboboapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/** A pop-up dialog that asks the user to select a particular Robobo bluetooth device
 *
 * @author Gervasio Varela
 */
public class RobDeviceSelectionDialog extends DialogFragment {


    private Listener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] robNames = getBtPairedDevicesNames();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (robNames.length == 0) {

            builder.setTitle(R.string.BluetoothDeviceSelectionTitle)
                    .setMessage("No device found or bluetooth disabled.")
                    .setNegativeButton(getText(R.string.ok_msg), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.bluetoothIsDisabled();
                        }
                    }).setCancelable(false);

        }
        else {
            builder.setTitle(R.string.BluetoothDeviceSelectionTitle)
                    .setItems(robNames, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if (listener != null) listener.roboboSelected(robNames[which]);

                        }
                    });

        }

        return builder.create();

    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (listener != null) listener.selectionCancelled();
    }

    public String[] getBtPairedDevicesNames() {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            //bluetooth disabled
            return new String[0];
        }
        else {
            //bluetooth enabled
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            String[] devicesNames = new String[pairedDevices.size()];
            int i = 0;
            for (BluetoothDevice btDev : pairedDevices) {
                devicesNames[i] = btDev.getName();
                i++;
            }

            return devicesNames;
        }

    }


    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void removeListener(Listener listener) {
        this.listener = null;
    }


    /** Receives notifications of the selection of a Robobo bluetooth device
     * from the selection dialog.
     *
     */
    public interface Listener {


        /** Notifies the name of the Robobo device selected by the user
         *
         * @param roboboName the name of the Robobo device selected by the user
         */
        public void roboboSelected(String roboboName);


        public void bluetoothIsDisabled();


        public void selectionCancelled();

    }

}