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
 * {@link OnContinueListener} interface
 * to handle interaction events.
 * Use the {@link AnswerExplanationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnswerExplanationFragment extends Fragment
    implements Button.OnClickListener {

    private OnContinueListener mListener =  null;

    TextView mResultTv =  null;
    TextView mExplanationTv = null;
    TextView mQuestionTv =  null;

    boolean mDataNeedsUpdate = false;
    boolean mIsAnswerCorrect = false;
    String mExplanationText = null;
    String mQuestion =  null;

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment AnswerExplanationFragment.
     */
    public static AnswerExplanationFragment newInstance() {
        AnswerExplanationFragment fragment = new AnswerExplanationFragment();

        return fragment;
    }

    public AnswerExplanationFragment() {
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
        View view = inflater.inflate(R.layout.fragment_answer_explanation, container, false);

        mResultTv =  (TextView)view.findViewById(R.id.result_textview);

        mExplanationTv = (TextView)view.findViewById(R.id.explanation_textview);

        mQuestionTv = (TextView)view.findViewById(R.id.question_textview);

        Button continueBtn = (Button)view.findViewById(R.id.bottom_button);
        continueBtn.setText(getString(R.string.continue_btn_text));
        continueBtn.setOnClickListener(this);

        return view;
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
            mListener = (OnContinueListener) context;
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnContinueListener {
        void onContinue();
    }

    @Override
    public void onClick(View v) {
        mListener.onContinue();
    }

    public void setWidgetData(boolean isAnswerCorrect, String question, String explanationText) {
        mIsAnswerCorrect = isAnswerCorrect;
        mQuestion = question;
        mExplanationText = explanationText;

        mDataNeedsUpdate = true;
    }

    @Override
    public void onResume() {

        if (mDataNeedsUpdate) {
            if (mIsAnswerCorrect) {
                mResultTv.setText(getText(R.string.correct_text));
            }
            else {
                mResultTv.setText(getText(R.string.incorrect_text));
            }

            mQuestionTv.setText(mQuestion);
            mExplanationTv.setText(mExplanationText);

            mDataNeedsUpdate = false;
        }

        super.onResume();
    }
}
