package com.example.ontime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

import javax.annotation.Nullable;

/**
 * This is a class for adding a fragment in driver class to show the request detail
 */
public class AddFragment extends DialogFragment {
    private TextView username;
    private TextView phone;
    private TextView start;
    private TextView end;
    private TextView amount;
    private TextView email;
    private OnFragmentInteractionListener listener;
    private CurrentRequests current_request;

    /**
     * This interface is used for check accept button and refresh the fragment
     */
    public interface OnFragmentInteractionListener {
        void onOkPressed(RequestList new_request);
        //void refresh();
    }

    /**
     * This class makes sure that the container context has implemented
     * the callback interface. If not, it throws an exception
     * @param context container
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     *
     * @param requests current request
     * @return A new instance of fragment AddFragment
     */
    public static AddFragment newInstance(CurrentRequests requests){
        Bundle args = new Bundle();
        args.putSerializable("Request",requests);

        AddFragment fragment = new AddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This bundle will be passed to onCreate if the process is killed and restarted.
     * @param saveInstanceState Save UI state changes to the savedInstanceState.
     * @return builder
     */
    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle saveInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.request_details, null);
        username = view.findViewById(R.id.name_text);
        email = view.findViewById(R.id.email_text);
        phone = view.findViewById(R.id.phone_text);
        start = view.findViewById(R.id.srclocation_text);
        end= view.findViewById(R.id.destination_text);
        amount = view.findViewById(R.id.amount_text);
                Bundle arguments = getArguments();
        if(arguments !=null){
            current_request  = (CurrentRequests)arguments.getSerializable("Request");
            username.setText(current_request.getName());
            username.getPaint().setFakeBoldText(true);
            username.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            phone.setText(current_request.getPhone());
            phone.getPaint().setFakeBoldText(true);
            phone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            email.setText(current_request.getEmail());
            email.getPaint().setFakeBoldText(true);
            email.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            start.setText(current_request.getSrcLocation());
            start.getPaint().setFakeBoldText(true);
            start.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            end.setText(current_request.getDestination());
            end.getPaint().setFakeBoldText(true);
            end.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            amount.setText(current_request.getAmount());
            amount.getPaint().setFakeBoldText(true);
            amount.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Accept", null).create();
    }

}