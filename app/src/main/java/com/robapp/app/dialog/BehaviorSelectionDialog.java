package com.robapp.app.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;

import com.robapp.R;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.utils.Utils;

import java.util.ArrayList;
import java.util.Set;

/** A pop-up dialog that asks the user to select a particular Robobo bluetooth device
 *
 * @author Gervasio Varela
 */
public class BehaviorSelectionDialog extends DialogFragment {


    private Listener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ArrayList<BehaviorItemI> behaviors = Utils.getAllItem();
        final String[] behaviorName = new String[behaviors.size()];
        for(BehaviorItemI b : behaviors)
            behaviorName[behaviors.indexOf(b)]=b.getName();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


            builder.setTitle(R.string.BluetoothDeviceSelectionTitle)
                    .setItems(behaviorName, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if (listener != null) listener.behaviorSelected(Utils.getAllItem().get(which));

                        }
                    });

        return builder.create();

    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (listener != null) listener.selectionCancelled();
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

        public void behaviorSelected(BehaviorItemI item);

        public void selectionCancelled();

    }

}