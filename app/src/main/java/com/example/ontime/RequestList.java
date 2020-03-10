package com.example.ontime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RequestList extends ArrayAdapter<CurrentRequests> {
    private ArrayList<CurrentRequests> requests;
    private Context context;

    public RequestList(Context context, ArrayList<CurrentRequests> requests) {
        super(context, 0, requests);
        this.requests = requests;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.request_details,parent,false);
        }

        CurrentRequests request = requests.get(position);

        TextView nameText = view.findViewById(R.id.name_text);
        TextView phoneText = view.findViewById(R.id.phone_text);
        TextView emailText = view.findViewById(R.id.email_text);
        TextView srclocationText = view.findViewById(R.id.srclocation_text);
        TextView destinationText = view.findViewById(R.id.destination_text);
        TextView amountText = view.findViewById(R.id.amount_text);

        nameText.setText(request.getName());
        phoneText.setText(request.getPhone());
        emailText.setText(request.getEmail());
        srclocationText.setText(request.getSrclocation());
        destinationText.setText(request.getDestination());
        amountText.setText(request.getAmount());

        return view;
    }
}
