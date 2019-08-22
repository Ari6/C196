package com.ntlts.c196;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class Popup extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("TEST")
                .setPositiveButton("Button", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }})
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){

                        }
        }).show();
        return builder.create();
    }
}
