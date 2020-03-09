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

public class RequestList extends ArrayAdapter<Requests> {
    private ArrayList<Requests> requests;
    private Context context;

    public RequestList(Context context, ArrayList<Requests> requests) {
        super(context, 0, requests);
        this.requests = requests;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content,parent,false);
        }

        Requests requests = requests.get(position);

        TextView nameText = view.findViewById(R.id.name_text);
        TextView phoneText = view.findViewById(R.id.phone_text);
        TextView emailText = view.findViewById(R.id.email_text);
        TextView srclocationText = view.findViewById(R.id.srclocation_text);
        TextView destinationText = view.findViewById(R.id.destination_text);
        TextView amountText = view.findViewById(R.id.amount_text);

        nameText.setText(requests.getName());
        phoneText.setText(requests.getPhone());
        emailText.setText(requests.getEmail());
        srclocationText.setText(requests.getSrclocation());
        destinationText.setText(requests.getDestination());
        amountText.setText(requests.getAmount());

        return view;
    }
}
