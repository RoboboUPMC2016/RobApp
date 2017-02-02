package com.robapp.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.robapp.R;
import com.robapp.behaviors.item.NativeBehaviorItem;
import com.robapp.behaviors.interfaces.BehaviorItemI;

import com.robapp.tools.Utils;

import java.util.ArrayList;


/**
 * Created by Arthur on 19/10/2016.
 */

public class BehaviorAdapter extends BaseAdapter {

    ArrayList<BehaviorItemI>items;
    private static LayoutInflater inflater;
    BehaviorItemI selectedItem;
    Context context;

    public BehaviorAdapter(Context cont) {

        this.context = cont;
        inflater = (LayoutInflater) (LayoutInflater.from(context));
        items = Utils.getAllItem();

        selectedItem = items.get(0);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public BehaviorItemI getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


            convertView = inflater.inflate(R.layout.behavioritem,null);

        BehaviorItemI item =  getItem(position);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgB);
        TextView txt = (TextView) convertView.findViewById(R.id.nameB);

        if(item instanceof NativeBehaviorItem)
            img.setImageResource(R.drawable.ic_native);
        else
            img.setImageResource(R.drawable.ic_downloaded);

        txt.setText(item.getName());

        return convertView;
    }

    /**
     * Get the selected item
     * @return The selected item
     */
    public BehaviorItemI getSelectedItem()
    {
        return selectedItem;
    }

    /**
     * Set the selected item
     * @param i The item id
     */
    public void setSelectedItem(int i)
    {
        selectedItem = getItem(i);
    }
}
