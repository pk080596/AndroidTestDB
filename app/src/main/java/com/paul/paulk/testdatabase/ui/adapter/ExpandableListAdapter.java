package com.paul.paulk.testdatabase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.paul.paulk.testdatabase.R;
import com.paul.paulk.testdatabase.ui.model.ExpandableMenuItem;

import java.util.List;

/**
 * Created by paulk on 12/22/2016.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ExpandableMenuItem> menuItems;
    private ExpandableListView expandableListView;

    public ExpandableListAdapter (Context c, List<ExpandableMenuItem> items, ExpandableListView view) {
        context = c;
        menuItems = items;
        expandableListView = view;
    }

    @Override
    public int getGroupCount () {
        return menuItems.size ();
    }

    @Override
    public int getChildrenCount (int groupPosition) {
        return menuItems.get (groupPosition).size ();
    }

    @Override
    public Object getGroup (int groupPosition) {
        return menuItems.get (groupPosition);
    }

    @Override
    public Object getChild (int groupPosition, int childPosition) {
        return menuItems.get (groupPosition).get (childPosition);
    }

    @Override
    public long getGroupId (int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId (int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds () {
        return false;
    }

    @Override
    public View getGroupView (int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate (R.layout.item_expandable_menu, null);
        }

        TextView groupText = (TextView) convertView.findViewById (R.id.group_name);
        groupText.setText (menuItems.get(groupPosition).getGroupName ());

        return convertView;
    }

    @Override
    public View getChildView (int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate (R.layout.item_menu, null);
        }

        TextView childText = (TextView) convertView.findViewById (R.id.child_name);
        childText.setText (menuItems.get(groupPosition).get(childPosition).getName ());

        return convertView;
    }

    @Override
    public boolean isChildSelectable (int groupPosition, int childPosition) {
        return true;
    }
}
