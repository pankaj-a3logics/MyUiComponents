package com.pkj.wow.myuicomponents.view;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.pkj.wow.myuicomponents.R;
import com.wheel.ArrayWheelAdapter;
import com.wheel.OnWheelChangedListener;
import com.wheel.OnWheelScrollListener;
import com.wheel.WheelView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Activities that contain this fragment must implement the
 * {@link PinLockDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PinLockDialog#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author Pankaj Jangid
 */
public class PinLockDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    final String digits[] = new String[]{ "0", "1",
            "2", "3", "4",
            "5", "6", "7",
            "8", "9", "*"};

    // TODO: Rename and change types of parameters
    private int mTheme;
    private String mPassword;



    private static final boolean IS_KEYBOARD_ENABLE = true;
    private static final int NO_OF_WHEELS = 4;

    // Wheel scrolled flag
    private boolean mWheelScrolled;
    private boolean mIsShowPassword;

    private View mRootView;

    private OnFragmentInteractionListener mListener;

    @Bind(R.id.btn0)
    Button mBtn0;
    @Bind(R.id.btn1)
    Button mBtn1;
    @Bind(R.id.btn2)
    Button mBtn2;
    @Bind(R.id.btn3)
    Button mBtn3;
    @Bind(R.id.btn4)
    Button mBtn4;
    @Bind(R.id.btn5)
    Button mBtn5;
    @Bind(R.id.btn6)
    Button mBtn6;
    @Bind(R.id.btn7)
    Button mBtn7;
    @Bind(R.id.btn8)
    Button mBtn8;
    @Bind(R.id.btn9)
    Button mBtn9;
    @Bind(R.id.btn_back)
    ImageButton mBtnBack;
    @Bind(R.id.btn_lock)
    ImageButton mBtnLock;


    @Bind(R.id.slot_1)
    WheelView mSlot1;
    @Bind(R.id.slot_2)
    WheelView mSlot2;
    @Bind(R.id.slot_3)
    WheelView mSlot3;
    @Bind(R.id.slot_4)
    WheelView mSlot4;
    @Bind(R.id.cbShowPassword)
    CheckBox mShowPassCb;

    private WheelView[] mWheels;
    private int[] digit = {0, 0, 0, 0};
    private String currentDigit = "0000";
    private int digitCounter = 0;

    private PinCallBack mPinCallBack;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param correctPin Correct Pin or password.
     * @return A new instance of fragment PinCodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PinLockDialog newInstance(String correctPin, PinCallBack pinCallBack) {
        PinLockDialog fragment = new PinLockDialog();
        fragment.setPinCallBack(pinCallBack);
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, 2);
        args.putString(ARG_PARAM2, correctPin);
        fragment.setArguments(args);
        return fragment;
    }

    public PinLockDialog() {
        // Required empty public constructor
    }

    public void setPinCallBack(PinCallBack pinCallBack) {
        // Required empty public constructor
        mPinCallBack = pinCallBack;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTheme = getArguments().getInt(ARG_PARAM1);
            mPassword = getArguments().getString(ARG_PARAM2);
        }


        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mTheme -1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mTheme -1)%6) {
            case 1: theme = R.style.transparent; break;
            case 21: theme = R.style.transparent; break;
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_pin_code, container, false);
        ButterKnife.bind(this, mRootView);
        mWheels = new WheelView[]{mSlot1, mSlot2, mSlot3, mSlot4};
        initView();
        return mRootView;
    }

    private void initView() {

        for (WheelView slot : mWheels) {
            initWheel(slot, digits);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (WheelView slot : mWheels) {
                    mixWheel(slot);
                }
            }
        }, 200);

        mShowPassCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mIsShowPassword = b;
                showPassword();
            }
        });
    }

    private void checkForPassword(){
        Log.d("password", "currentDigit : " + currentDigit + ", mPassword" + mPassword);
        if(currentDigit.equals(mPassword)){
            mBtnLock.setImageResource(R.drawable.ic_lock_open_24dp);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    mPinCallBack.onUnlockSuccess();
                }
            }, 1000);
        }else{
            mBtnLock.setImageResource(R.drawable.ic_lock_24dp);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    mPinCallBack.onUnlockFail();
                }
            },1000);
        }
    }


    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9})
    public void setOnButtonKeyPress(Button button) {

        int key = Integer.parseInt(button.getText().toString());
        if (digitCounter < 0) {
            digitCounter = 0;
        }
        if (digitCounter < NO_OF_WHEELS) {
            spinWheel(mWheels[digitCounter], key, false);
        }
        if (digitCounter >= 0) {
            digitCounter++;
        }
        currentDigit = (String)mSlot1.getTag() + (String)mSlot2.getTag() + (String)mSlot3.getTag() + (String)mSlot4.getTag() + "";
        if(digitCounter==NO_OF_WHEELS){
            checkForPassword();
        }
    }

    @OnClick(R.id.btn_back)
    public void setOnButtonKeyPress(ImageView button) {
        if (digitCounter > NO_OF_WHEELS - 1) {
            digitCounter = NO_OF_WHEELS - 1;
        } else {
            digitCounter--;
        }
        if(digitCounter==NO_OF_WHEELS-1){
            mBtnLock.setImageResource(R.drawable.ic_lock_default_24dp);
        }
        if (digitCounter >= 0) {
            spinWheel(mWheels[digitCounter], 0, true);
        }
        currentDigit = (String)mSlot1.getTag() + (String)mSlot2.getTag() + (String)mSlot3.getTag() + (String)mSlot4.getTag() + "";
    }

    private void showPassword(){
        for (WheelView slot : mWheels) {
            if(slot.getTag()!=null && !((String)slot.getTag()).equals("-1"))
                spinWheel(slot, Integer.parseInt((String) slot.getTag()),false);
        }
    }

    private void spinWheel(WheelView wheel, int key, boolean isBack) {
        int reduce = wheel.getCurrentItem();
        if (isBack) {
            wheel.setTag("-1");
            wheel.scroll(digits.length*18 - reduce + key, 500);
        } else {
            wheel.setTag(key+"");
            if(!mIsShowPassword)
                key = 10;
            wheel.scroll(-digits.length*18 - reduce + key, 500);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       /* try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    /**
     * Initializes wheel
     *
     * @param wheel the wheel widget Id
     */
    private void initWheel(WheelView wheel, String cities[]) {

        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(getActivity(),
                cities);

        wheel.setViewAdapter(adapter);
        wheel.setCurrentItem((int) (Math.random() * digits.length));
        if (IS_KEYBOARD_ENABLE) {
            wheel.setVisibleItems(2);
        } else {
            wheel.setVisibleItems(5);
        }


        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setTag("-1");
        if (IS_KEYBOARD_ENABLE) {
            wheel.setEnabled(false);
        } else {
            wheel.setEnabled(true);
        }
    }

    // Wheel scrolled listener
    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            mWheelScrolled = true;
        }

        public void onScrollingFinished(WheelView wheel) {
            mWheelScrolled = false;
            // updateStatus();

//             Toast.makeText(getActivity().getApplicationContext(), digits[wheel.getCurrentItem()], Toast.LENGTH_LONG).show();
        }
    };

    // Wheel changed listener
    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!mWheelScrolled) {
                // updateStatus();
            }
        }
    };


    /**
     * Mixes wheel
     *
     * @param wheel the wheel id
     */
    private void mixWheel(WheelView wheel) {
        int currentDigit = wheel.getCurrentItem();
        wheel.scroll(-digits.length*11 - currentDigit, 500 + (int) (Math.random() * 10) * 50);

    }

    public interface PinCallBack {
        void onUnlockSuccess();
        void onUnlockFail();
    }

}
