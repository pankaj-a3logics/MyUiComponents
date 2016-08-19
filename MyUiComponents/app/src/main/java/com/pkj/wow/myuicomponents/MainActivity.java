package com.pkj.wow.myuicomponents;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.pkj.wow.myuicomponents.view.CustomSpinner;
import com.pkj.wow.myuicomponents.view.PinLockDialog;
import com.pkj.wow.pkjlib.utils.MyLog;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText                mFullNameEt;
    private DialogFragment          mPinLockDialog;
    private FloatingActionButton    mFabBtn;
    private CustomSpinner           mSpinnerStats;
    private CustomSpinner           mSpinnerCities;
    private String[] stats          = {"Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal","Andaman and Nicobar","Chandigarh","Dadra and Nagar Haveli","Daman and Diu","Lakshadweep","Delhi","Puducherry"};
    private String[] smartCities    = {"Goa","Mumbai","Bangluru","Delhi","Gurgaon","Noida","Pune","Hydrabaad","Jaipur"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFullNameEt         = (EditText) findViewById(R.id.et_full_name);
        mFabBtn             = (FloatingActionButton) findViewById(R.id.fab);
        mSpinnerStats       = (CustomSpinner) findViewById(R.id.spinner_states);
        mSpinnerCities      = (CustomSpinner) findViewById(R.id.spinner_cities);

        mFabBtn.setOnClickListener(this);

        // Set list of items to show on custom spinner
        mSpinnerStats.setListItems(new ArrayList<String>(Arrays.asList(stats)));

        mSpinnerCities.setListItems(new ArrayList<String>(Arrays.asList(smartCities)));
        mSpinnerCities.setUnselectable(true);
    }



    private void openPinDialog(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Create and show the dialog.
        mPinLockDialog = PinLockDialog.newInstance("1234", new PinLockDialog.PinCallBack() {
            @Override
            public void onUnlockSuccess() {
                MyLog.m("unlock success");
                if(mPinLockDialog!=null)
                    mPinLockDialog.dismiss();
                Snackbar.make(mFullNameEt, getString(R.string.unlock_success), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onUnlockFail() {
                MyLog.m("unlock fail");
                Snackbar.make(mFullNameEt, getString(R.string.unlock_fail), Snackbar.LENGTH_LONG).show();
            }
        });
        mPinLockDialog.show(ft, "dialog");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.fab :
                openPinDialog();
                break;

            default :

                break;
        }
    }
}
