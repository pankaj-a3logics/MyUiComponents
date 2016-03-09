package com.pkj.wow.myuicomponents;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pkj.wow.myuicomponents.view.PinLockDialog;
import com.pkj.wow.pkjlib.utils.MyLog;

import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private DialogFragment mPinLockDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView   =   (TextView) findViewById(R.id.textView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                // Create and show the dialog.
                mPinLockDialog = PinLockDialog.newInstance("1234", new PinLockDialog.PinCallBack() {
                    @Override
                    public void onUnlockSuccess() {
                        MyLog.m("unlock success");
                        if(mPinLockDialog!=null)
                            mPinLockDialog.dismiss();
                        Snackbar.make(mTextView, getString(R.string.unlock_success), Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onUnlockFail() {
                        MyLog.m("unlock fail");
                        Snackbar.make(mTextView, getString(R.string.unlock_fail), Snackbar.LENGTH_LONG).show();
                    }
                });
                mPinLockDialog.show(ft, "dialog");

            }
        });
    }
}
