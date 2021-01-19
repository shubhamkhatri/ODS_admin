

package com.shubham.odsadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shubham.odsadmin.R;
import com.shubham.odsadmin.model.bookingList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BookingAdapter extends ArrayAdapter<bookingList> {

    public BookingAdapter(@NonNull Context context, ArrayList<bookingList> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_booking_history_list, parent, false);
        }
        bookingList currentList = getItem(position);
        TextView userText = listItemView.findViewById(R.id.sbhl_user_tv);
        TextView workerText = listItemView.findViewById(R.id.sbhl_worker_tv);
        TextView serviceText = listItemView.findViewById(R.id.sbhl_wash_tv);
        TextView dateTimeText = listItemView.findViewById(R.id.sbhl_date_tv);
        TextView priceText = listItemView.findViewById(R.id.sbhl_price_tv);
        ImageView carImage = listItemView.findViewById(R.id.sbhl_image);
        ImageView statusImage=listItemView.findViewById(R.id.sbhl_status_image);

        userText.setText("User: "+currentList.getUser());
        workerText.setText("Worker: "+currentList.getWorker());
        serviceText.setText(currentList.getService());
        dateTimeText.setText(currentList.getDateTime());
        priceText.setText("₹ " + currentList.getPrice());

        if (currentList.getCarType().equals("Mini"))
            carImage.setImageResource(R.drawable.car_mini);
        else if (currentList.getCarType().equals("Sedan"))
            carImage.setImageResource(R.drawable.car_sedan);
        else if (currentList.getCarType().equals("Suv"))
            carImage.setImageResource(R.drawable.car_suv);
        else
            carImage.setImageResource(R.drawable.car_premium);

        if(currentList.getStatus().equals("Ongoing"))
            statusImage.setImageResource(R.drawable.pending);
        else if(currentList.getStatus().equals("Cancel"))
            statusImage.setImageResource(R.drawable.cancel);
        else
            statusImage.setImageResource(R.drawable.complete);

        return listItemView;
    }
}
