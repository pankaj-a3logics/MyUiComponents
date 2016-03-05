package com.pkj.wow.pkjlib.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

/**
 * Created by Pankaj on 05-02-2015.
 */
public class MyLog {

    public static void m(String msg){
        Log.d("pkj.me",msg);
    }

    public static void alert(Context context, String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    /**
     * Thansform an array of ASCII bytes to a string. the byte array should contains
     * only values in [0, 127].
     *
     * @param bytes The byte array to transform
     * @return The resulting string
     */
    public static String asciiBytesToString( int[] bytes )
    {
        if ( (bytes == null) || (bytes.length == 0 ) )
        {
            return "";
        }

        char[] result = new char[bytes.length];

        for ( int i = 0; i < bytes.length; i++ )
        {
            result[i] = (char)bytes[i];
        }

        return new String( result );
    }

}
