package com.example.luismoscoso.knowledgetestpractice;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnPostPracticeChoiceListener} interface
 * to handle interaction events.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment
    implements Button.OnClickListener {

    private OnPostPracticeChoiceListener mListener = null;

    private float mPercentage = 0;
    private boolean mDataNeedsUpdate = false;

    private TextView mPercentageTb =  null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ResultFragment.
     */
    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        return fragment;
    }

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        Button retryBtn = (Button)view.findViewById(R.id.retry_btn);
        retryBtn.setOnClickListener(this);

        Button continueBtn = (Button)view.findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(this);

        mPercentageTb = (TextView)view.findViewById(R.id.result_percentage_tv);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFinishPractice();
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            onAttachContext(context);
        }
    }

    // onAttach(Activity activity) is deprecated on API 23. Use onAttach(Context context) instead.
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // There is a bug on Android where the old and new versions of onAttach will be called.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachContext(activity);
        }
    }

    protected void onAttachContext(Context context) {
        try {
            mListener = (OnPostPracticeChoiceListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnPostPracticeChoiceListener");
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPostPracticeChoiceListener {
        void onFinishPractice();

        void onRedoPractice();
    }

    @Override
    public void onClick(View v) {
        if (mListener == null) {
            return;
        }

        // Which button was clicked?
        if (R.id.retry_btn == v.getId()) {
            mListener.onRedoPractice();
        }
        else if (R.id.continue_btn == v.getId()) {
            mListener.onFinishPractice();
        }
    }

    public void setWidgetData(float percentage) {
        mPercentage = percentage;
        mDataNeedsUpdate = true;
    }

    @Override
    public void onResume() {
        if (mDataNeedsUpdate) {
            mPercentageTb.setText(String.format("%.02f", mPercentage) + " %");

            mDataNeedsUpdate = false;
        }

        super.onResume();
    }
}
