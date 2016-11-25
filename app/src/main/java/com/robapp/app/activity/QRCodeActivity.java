package com.robapp.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.google.zxing.WriterException;
import com.robapp.R;
import com.robapp.app.adapter.FileExplorerAdapter;
import com.robapp.app.dialog.BehaviorSelectionDialog;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.behaviors.item.BehaviorFileItem;
import com.robapp.utils.Utils;

import java.io.File;


public class QRCodeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qrcode);
        setupDrawer();

       final ImageView img = (ImageView) findViewById(R.id.qrcode);

        try{

            if(selectedBehavior != null)
            {
                if(selectedBehavior instanceof BehaviorFileItem)
                    Utils.generateQRCode(((BehaviorFileItem) selectedBehavior).getUrl(),img);
            }
            else
            {
                final BehaviorSelectionDialog dialog = new BehaviorSelectionDialog();
                dialog.setListener(new BehaviorSelectionDialog.Listener() {
                    @Override
                    public void behaviorSelected(BehaviorItemI item) {
                        selectedBehavior = item;
                        try {
                            Utils.generateQRCode(((BehaviorFileItem) selectedBehavior).getUrl(),img);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                    }

                    public void selectionCancelled()
                    {}
                });
                dialog.show(getFragmentManager(),"Selection Comportement");
            }
        }
        catch(Exception e){
           e.printStackTrace();
        }
    }

}
