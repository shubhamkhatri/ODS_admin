

package com.shubham.odsadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shubham.odsadmin.R;
import com.shubham.odsadmin.model.billList;
import com.shubham.odsadmin.model.userList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends ArrayAdapter<billList> {

    public BillAdapter(@NonNull Context context, ArrayList<billList> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_bill_list, parent, false);
        }
        billList currentList = getItem(position);
        TextView phoneText = listItemView.findViewById(R.id.sbl_phone_tv);
        TextView typeText = listItemView.findViewById(R.id.sbl_from_tv);
        TextView dateText = listItemView.findViewById(R.id.sbl_date_tv);
        TextView amountText = listItemView.findViewById(R.id.sbl_cash_tv);

        phoneText.setText(currentList.getPhone());
        typeText.setText(currentList.getType());
        dateText.setText(currentList.getDate());
        amountText.setText("₹ "+currentList.getAmount());

        return listItemView;
    }
}
