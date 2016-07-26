# MyUiComponents
Custom and easy to implement UI Components

[Play Store Link](https://play.google.com/store/apps/details?id=com.pkj.wow.myuicomponents)

###1. Custom Date Picker
    - Easy to implement
    - Nice UI ( Material Floating touch )
    
    Usage
    -----
    
    ```
            // Example of custom date picker
            <android.support.design.widget.TextInputLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content">
                <com.pkj.wow.myuicomponents.view.CustomDatePicker
                    android:id="@+id/datepicker_dob"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="Date of Birth" />
            </android.support.design.widget.TextInputLayout> 
    ```

    ScreenShots
    -----
    ![Custom Date Picker](https://github.com/pkjvit/MyUiComponents/blob/master/device-2016-03-09-220147.jpg)
    ![Custom Date Picker](https://github.com/pkjvit/MyUiComponents/blob/master/device-2016-03-09-220208.jpg)


###2. Pin Lock Dialog
    - Easy to implement
    - Nice UI and Interface
    
    Usage
    -----
    
    ```
        // Example Create and show the date picker dialog.
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
    ```

    ScreenShots
    -----
    ![Pin Lock Dialog](https://github.com/pkjvit/MyUiComponents/blob/master/device-2016-03-09-220225.jpg)


###3. Custom Spinner
    - Easy to implement
    - Nice UI and Interface
    - Customize Font
    
    Usage
    -----
    
    ```
        // Example Create and use Customo spinner.
        mSpinner            = (CustomSpinner) findViewById(R.id.spinner_cities);
        // Set list of items to show on custom spinner
        mSpinner.setListItems(new ArrayList<String>(Arrays.asList(items)));
    ```

    ScreenShots
    -----
    ![Pin Lock Dialog](https://github.com/pkjvit/MyUiComponents/blob/master/device-2016-07-22-003910.jpg)
