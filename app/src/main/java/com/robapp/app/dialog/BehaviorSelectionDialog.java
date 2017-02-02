package com.robapp.app.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.robapp.R;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.tools.Utils;

import java.util.ArrayList;

/**
 * A pop-up dialog that asks the user to select a particular behavior
 * Based on the RobDeviceSelectionDialog from the Robobo project
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


            builder.setTitle(R.string.SelectBehavior)
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

    public interface Listener {

        public void behaviorSelected(BehaviorItemI item);

        public void selectionCancelled();

    }

}