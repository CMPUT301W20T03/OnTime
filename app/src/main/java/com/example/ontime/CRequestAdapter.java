package com.example.ontime;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.ontime.CurrentRequests;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


/**
 * This is a class that implements CRequestAdapter object
 */
// adapter for driver view of active requests
public class CRequestAdapter extends ArrayAdapter<CurrentRequests> {
    public CRequestAdapter(Context context, List<CurrentRequests> object){
        super(context,0, object);
    }

    /**
     * This method formats the view of the object and returns converted view for the object
     * @param position
     *      set the position of object in the list
     * @param convertView
     *      set the view of the object and convert it
     * @param parent
     *      set the parent of the view
     * @return
     *      returns the converted view for the object
     *
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null) {
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.request_listview,parent,false);
        }

        TextView RiderTextView = (TextView) convertView.findViewById(R.id.requestRider);
        TextView srcTextView = (TextView) convertView.findViewById(R.id.requestSrc);
        TextView dstTextView = (TextView) convertView.findViewById(R.id.requestDst);

        CurrentRequests requests = getItem(position);

        String riderText = "Rider:  " + requests.getName();
        String SrcText = "Currently at:  " + requests.getSrcLocation();
        String DstText = "Destination:  " + requests.getDestination();

        RiderTextView.setText(riderText);
        srcTextView.setText(SrcText);
        dstTextView.setText(DstText);


        return convertView;
    }

}