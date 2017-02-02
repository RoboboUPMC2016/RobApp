package com.robapp.app.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.robapp.R;
import com.robapp.app.dialog.BehaviorSelectionDialog;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.behaviors.item.BehaviorFileItem;
import com.robapp.tools.Utils;

/**
 * The activity for displaying the behaviorQRCode
 */
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
                //If the behavior is present in the social network
                //generate the QRCode for reaching the behavior details page
                if(selectedBehavior instanceof BehaviorFileItem)
                    Utils.generateQRCode(((BehaviorFileItem) selectedBehavior).getUrl(),img);
                else // Else generate a default QRCode for reaching the social network homepage
                    Utils.generateQRCode(Utils.defaultUrl,img);
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
