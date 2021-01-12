

package com.shubham.odsadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shubham.odsadmin.R;
import com.shubham.odsadmin.model.workerList;

import java.util.ArrayList;

public class WorkerAdapter extends ArrayAdapter<workerList> {

    public WorkerAdapter(@NonNull Context context, ArrayList<workerList> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_worker_list, parent, false);
        }
        workerList currentList = getItem(position);
        TextView nameText = listItemView.findViewById(R.id.swl_name_tv);
        TextView phoneText = listItemView.findViewById(R.id.swl_phone_tv);
        TextView addressText = listItemView.findViewById(R.id.swl_address_tv);
        TextView distanceText = listItemView.findViewById(R.id.swl_services_tv);
        RatingBar ratingBar = listItemView.findViewById(R.id.swl_ratingBar);

        nameText.setText(currentList.getName());
        phoneText.setText(currentList.getPhone());
        addressText.setText(currentList.getAddress());
        distanceText.setText("Services: "+currentList.getServices());
        ratingBar.setRating(Float.parseFloat(currentList.getRatings()));

        return listItemView;
    }
}
