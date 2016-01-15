package com.example.luismoscoso.knowledgetestpractice;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnQuestionAnsweredListener} interface
 * to handle interaction events.
 * Use the {@link StandardQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StandardQuestionFragment extends Fragment
    implements Button.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private OnQuestionAnsweredListener mListener;

    private int mImageID = 0;
    private String mQuestionTxt = null;
    private String mAnswer1Txt = null;
    private String mAnswer2Txt = null;
    private String mAnswer3Txt = null;
    private String mAnswer4Txt = null;

    private int mAnswerIndex = -1;

    private Button mAnswerBtn = null;
    private RadioGroup mAnswersRg = null;

    private RadioButton mAnswer1Rb = null;
    private RadioButton mAnswer2Rb = null;
    private RadioButton mAnswer3Rb = null;
    private RadioButton mAnswer4Rb = null;

    private boolean mDataNeedsUpdate = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment StandardQuestionFragment.
     */
    public static StandardQuestionFragment newInstance() {
        StandardQuestionFragment fragment = new StandardQuestionFragment();

        return fragment;
    }

    public StandardQuestionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_standard_question, container, false);

        if (mImageID > 0 ) {
            // TODO Set image here
        }

        TextView questionTv = (TextView)view.findViewById(R.id.question_textview);
        questionTv.setText(mQuestionTxt);

        mAnswersRg = (RadioGroup)view.findViewById(R.id.standard_answer_radio_group);
        mAnswersRg.clearCheck();
        mAnswersRg.setOnCheckedChangeListener(this);

        mAnswer1Rb = (RadioButton)view.findViewById(R.id.standard_answer_1);
        mAnswer2Rb = (RadioButton)view.findViewById(R.id.standard_answer_2);
        mAnswer3Rb = (RadioButton)view.findViewById(R.id.standard_answer_3);
        mAnswer4Rb = (RadioButton)view.findViewById(R.id.standard_answer_4);

        mAnswerBtn = (Button)view.findViewById(R.id.bottom_button);
        mAnswerBtn.setEnabled(false);
        mAnswerBtn.setOnClickListener(this);

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
            mListener = (OnQuestionAnsweredListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnQuestionAnsweredListener");
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
     */
    public interface OnQuestionAnsweredListener {
        void onQuestionAnswered(int answerIndex);
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onQuestionAnswered(mAnswerIndex);
        }
    }

    public void setWidgetData(int imageID, String question,
                        String answer1, String answer2, String answer3, String answer4) {

        mImageID        = imageID;
        mQuestionTxt    = question;
        mAnswer1Txt     = answer1;
        mAnswer2Txt     = answer2;
        mAnswer3Txt     = answer3;
        mAnswer4Txt     = answer4;

        mDataNeedsUpdate = true;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
        View radioButton = radioGroup.findViewById(checkedID);
        mAnswerIndex = radioGroup.indexOfChild(radioButton);

        mAnswerBtn.setEnabled(true);
    }

    @Override
    public void onResume() {
        if (mDataNeedsUpdate) {
            mAnswersRg.clearCheck();
            mAnswerBtn.setEnabled(false);

            mAnswer1Rb.setText(mAnswer1Txt);
            mAnswer2Rb.setText(mAnswer2Txt);
            mAnswer3Rb.setText(mAnswer3Txt);
            mAnswer4Rb.setText(mAnswer4Txt);

            mDataNeedsUpdate = false;
        }

        super.onResume();
    }
}
