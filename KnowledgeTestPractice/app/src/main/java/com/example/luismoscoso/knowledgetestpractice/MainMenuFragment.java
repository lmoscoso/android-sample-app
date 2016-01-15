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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainMenuFragment.OnModeSelectionListener} interface
 * to handle interaction events.
 * Use the {@link MainMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment
    implements Button.OnClickListener {

    private OnModeSelectionListener mListener;

    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();
        return fragment;
    }

    public MainMenuFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        Button knowledgeTestBtn = (Button)view.findViewById(R.id.knowledge_test_btn);
        knowledgeTestBtn.setOnClickListener(this);

        Button moreQuestionsBtn = (Button)view.findViewById(R.id.more_questions_btn);
        moreQuestionsBtn.setOnClickListener(this);

        Button licensingLocationsBtn = (Button)view.findViewById(R.id.licensing_locations_btn);
        licensingLocationsBtn.setOnClickListener(this);

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
            mListener = (OnModeSelectionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnModeSelectionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnModeSelectionListener {
        void onKnowledgeTestSelected();

        void onMoreQuestionsSelected();

        void onLicensingLocationsSelected();
    }

    @Override
    public void onClick(View v) {
        // Listener setup?
        if (mListener == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.knowledge_test_btn:
                mListener.onKnowledgeTestSelected();
                break;
            case R.id.more_questions_btn:
                mListener.onMoreQuestionsSelected();
                break;
            case R.id.licensing_locations_btn:
                mListener.onLicensingLocationsSelected();
                break;
        }
    }
}
