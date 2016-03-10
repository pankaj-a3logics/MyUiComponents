package com.pkj.wow.myuicomponents.view;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;


import java.util.Calendar;


/**____________________ Custom Date Picker ______________________
 *
 * - A replacement of Android widget "Date Picker"
 * - Easy to use
 * - Set font at one place
 * - You can customize it according to your requirement
 *
 * @author Pankaj
 *
 */
public class CustomDatePicker extends EditText implements OnClickListener{

	private DatePickerFragment dpFragment;
	private Context mContext;
	private String date="";
	private Long timeMilliSec = 0l;

	public CustomDatePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public CustomDatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);

	}
	public CustomDatePicker(Context context) {
		super(context);
		init(null);
	}

	@SuppressLint({ "InflateParams", "Recycle" })
	private void init(AttributeSet attrs) {
		mContext = this.getContext();
		dpFragment = new DatePickerFragment();
		dpFragment.setOnDateSelection(new DatePickerFragment.OnDateSelection() {
			@Override
			public void onDateSelection(int year, int month, int day) {
				date = day + "/" + (month + 1) + "/" + year;
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, month, day);
				timeMilliSec = calendar.getTimeInMillis();
				CustomDatePicker.this.setText(date);
			}
		});
		this.setKeyListener(null);
		this.setOnClickListener(this);
		if (!isInEditMode()) {
			setCustomFont();
		}
	}

	/**
	 *  Set your font here
	 */
	private void setCustomFont() {
		Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/PKJ_FANKY.ttf");
		this.setTypeface(typeFace);
	}

	/**
	 *  Set date
	 * @param _date Date in format ( 22/03/1990 )
	 */
	public void setDate(String _date){
		date = _date;
		CustomDatePicker.this.setText(date);
	}

	public String getSelectedDate(){
		return date;
	}

	public long getSelectedTimeMillisec(){
		return timeMilliSec;
	}

	public DatePicker getDatePicker(){
		return dpFragment.getDatePicker();
	}

	public void showDatePickerDialog(){
		dpFragment.show(((AppCompatActivity)mContext).getSupportFragmentManager(), "datePicker");
	}

	@Override
	public void onClick(View v) {
		showDatePickerDialog();
	}

	/**
	 *  Date Picker Fragment
	 *  - Dialog in which Date picker shown
	 */
	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		OnDateSelection OnDateSelection;
		private DatePickerDialog datePickerDialog;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
	//		datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
			datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
			return datePickerDialog;
		}

		public DatePicker getDatePicker(){
			return datePickerDialog.getDatePicker();
		}

		public void setOnDateSelection(OnDateSelection OnDateSelection) {
			this.OnDateSelection = OnDateSelection;
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			if (OnDateSelection != null) {
				OnDateSelection.onDateSelection(year, month, day);
			}
		}

		public interface OnDateSelection {
			public void onDateSelection(int year, int month, int day);
		}
	}
}

