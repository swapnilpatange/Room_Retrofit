package com.example.ruchir.android_test_ms;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    ArrayList<ParentDataModel> listModels;
    Activity activity;

    public ExpandableAdapter(ArrayList<ParentDataModel> listModels, Activity activity) {
        this.listModels = listModels;
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        return listModels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listModels.get(groupPosition).getExpandableDataModels().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listModels.get(groupPosition).getUserid();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listModels.get(groupPosition).getExpandableDataModels().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        View groupView = LayoutInflater.from(activity).inflate(R.layout.group_item, null);
        TextView userId = groupView.findViewById(R.id.userId);
        userId.setText((String) getGroup(groupPosition));
        return groupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        View childView = LayoutInflater.from(activity).inflate(R.layout.child_item, null);
        TextView id = childView.findViewById(R.id.id);
        TextView title = childView.findViewById(R.id.title);
        TextView body = childView.findViewById(R.id.body);
        id.setText(((ExpandableDataModel) getChild(groupPosition, childPosition)).getId());
        title.setText(((ExpandableDataModel) getChild(groupPosition, childPosition)).getTitle());
        body.setText(((ExpandableDataModel) getChild(groupPosition, childPosition)).getBody());
        return childView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}