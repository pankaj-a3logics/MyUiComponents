package com.pkj.wow.myuicomponents.view;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ListPopupWindow;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/** -- Custom Spinner --
 *
 * - Best Material UI
 * - Customize Font
 * - Customize UI
 *
 * Created by Pankaj on 21-07-2016.
 */
public class CustomSpinner extends EditText implements View.OnClickListener, View.OnFocusChangeListener{

    private ListPopupWindow         mPopupWindow;
    private Context                 mContext;
    private ArrayList<String>       mListItems;
    private int                     mSelectedPosition = -1;
    private Typeface                mTypeFace;

    public CustomSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }
    public CustomSpinner(Context context) {
        super(context);
        init();
    }

    private void init(){
        mContext    = getContext();

        Drawable arrowDrawable = mContext.getResources().getDrawable(android.R.drawable.arrow_down_float);
        this.setCompoundDrawablesWithIntrinsicBounds(null,null,arrowDrawable, null);

        setCustomFont("fonts/PKJ_FANKY.ttf");

        this.setKeyListener(null);
        this.setOnClickListener(this);
        this.setOnFocusChangeListener(this);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            showPopupWindow();
        }
    }

    /**
     *  Set your font here
     */
    public void setCustomFont(String fontPath) {
        mTypeFace = Typeface.createFromAsset(getContext().getAssets(), fontPath);
        this.setTypeface(mTypeFace);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /** -- Show List Popup window --
     *
     * @return
     */
    private ListPopupWindow showPopupWindow() {

        // initialize a pop up window type
        if(mPopupWindow == null)
            mPopupWindow = new ListPopupWindow(mContext);

        mPopupWindow.setAnchorView(this);
        mPopupWindow.setModal(true);

        if(mListItems != null){
            MyFontAdapter adapter = new MyFontAdapter(
                    mContext,
                    android.R.layout.simple_spinner_dropdown_item,
                    mListItems, mTypeFace);
            mPopupWindow.setAdapter(adapter);
        }

        mPopupWindow.setWidth(getMeasuredWidth());
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedPosition   =   position;
                CustomSpinner.this.setText( mListItems.get(mSelectedPosition));
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.show();
        return mPopupWindow;
    }

    /** -- Set list items to show on spinner
     *
     * @param listItems
     */
    public void setListItems(ArrayList<String> listItems){
        this.mListItems = listItems;
    }

    /** -- Get selected item text
     *
     * @return  selected item
     */
    public String getSelectedText(){
        if(mListItems!=null && mSelectedPosition != -1){
            return mListItems.get(mSelectedPosition);
        }else{
            return "";
        }
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        showPopupWindow();

    }

    /**
     *  -- Spinner adapter with custom font
     */
    private static class MyFontAdapter extends ArrayAdapter<String> {
        Typeface mTypeFace;

        private MyFontAdapter(Context context, int resource, List<String> items, Typeface typeface) {
            super(context, resource, items);
            mTypeFace = typeface;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTypeface(mTypeFace);
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setTypeface(mTypeFace);
            return view;
        }
    }
}
