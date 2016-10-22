package com.robapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.robapp.R;
import com.robapp.activity.MainActivity;
import com.robapp.behaviors.NativeBehaviorItem;
import com.robapp.interfaces.BehaviorItemI;

import com.robapp.utils.Utils;

import java.util.ArrayList;


/**
 * Created by Arthur on 19/10/2016.
 */

public class BehaviorAdapter extends BaseAdapter {

    ArrayList<BehaviorItemI>items;
    MainActivity act;
    private static LayoutInflater inflater;
    BehaviorItemI selectedItem;
    Spinner spinner;

    public BehaviorAdapter(Context cont)
    {
        act = (MainActivity)cont;

        inflater = (LayoutInflater) cont
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        items = Utils.getAllItem(cont);

        spinner = (Spinner)act.findViewById(R.id.spinnerBehavior);
        selectedItem = items.get(0);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;

        if (vi == null)
            vi = inflater.inflate(R.layout.behavioritem, null);

        ImageView view = (ImageView) vi.findViewById(R.id.imgB);

        final BehaviorItemI item = (BehaviorItemI) getItem(position);
        if(item instanceof NativeBehaviorItem)
            view.setImageResource(R.drawable.ic_native);
        else
            view.setImageResource(R.drawable.ic_downloaded);

        TextView text = (TextView) vi.findViewById(R.id.nameB);
        text.setText(item.getName());

        return vi;
    }

    public BehaviorItemI getSelectedItem()
    {
        return selectedItem;
    }

}
