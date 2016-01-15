package com.example.luismoscoso.knowledgetestpractice;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements MainMenuFragment.OnModeSelectionListener,
        StandardQuestionFragment.OnQuestionAnsweredListener,
        AnswerExplanationFragment.OnContinueListener,
        ResultFragment.OnPostPracticeChoiceListener {

    private static final int MAX_PRACTICE_QUESTION_COUNT = 3; // TODO change when data is setup
    private static final int MIN_QUESTION_ID = 1;
    private static final int MAX_QUESTION_ID = 3; // TODO change question id

    private static final String MORE_QUESTIONS_URL = "http://practicetest.icbc.com/#/";
    private static final String LOCATIONS_URL = "http://www.icbc.com/Pages/find-a-location.aspx?loctype=Driver+Licensing+Office&url=http%3a%2f%2fapps.icbc.com%2flocator%2fv3%2flicensing%2f%3flocation-addy%3dSearch%2bby%2baddress%252c%2bcity%2bor%2bpostal%2bcode";

    private DatabaseAccess mDatabaseAccess = null;

    private int mActiveQuestionID = 0;
    private int mCompletedQuestionCount = 0;
    private int mCorrectAnswerCount = 0;

    private ArrayList<Integer> mUsedQuestionIDs =  null;

    private MenuItem mSettingsMenuItem = null;
    private MenuItem mEndPracticeMenuItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseAccess = DatabaseAccess.getInstance(this);

        showMainMenuFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mSettingsMenuItem = menu.findItem(R.id.action_settings);
        mEndPracticeMenuItem =  menu.findItem(R.id.action_end_practice);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Already in practice?
        if(mActiveQuestionID > 0) {
            mSettingsMenuItem.setVisible(false);
            mEndPracticeMenuItem.setVisible(true);
        }
        else {
            mSettingsMenuItem.setVisible(true);
            mEndPracticeMenuItem.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // TODO show settings page
            return true;
        }
        else if (id == R.id.action_end_practice) {
            showEndPractiveConfirmDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onKnowledgeTestSelected() {

        if (mUsedQuestionIDs == null) {
            mUsedQuestionIDs = new ArrayList<>(MAX_PRACTICE_QUESTION_COUNT);
        }
        else {
            mUsedQuestionIDs.clear();
        }
        resetData();
        showStandardQuestionFragment();
    }

    @Override
    public void onMoreQuestionsSelected() {
        launchWebBrowser(MORE_QUESTIONS_URL);
    }

    @Override
    public void onLicensingLocationsSelected() {
        launchWebBrowser(LOCATIONS_URL);
    }

    private void launchWebBrowser(String pageUrl) {
        Intent webpageIntent = new Intent(Intent.ACTION_VIEW);
        webpageIntent.setData(Uri.parse(pageUrl));
        startActivity(webpageIntent);
    }

    private void showStandardQuestionFragment() {
        String questionFragmentTag = getString(R.string.standard_question_fragment_tag);

        StandardQuestionFragment questionFragment =
                (StandardQuestionFragment)getFragmentManager().findFragmentByTag(questionFragmentTag);

        if (questionFragment == null) {
            questionFragment = StandardQuestionFragment.newInstance();
        }

        int nextQuestionID = getRandomQuestionID();

        while (mUsedQuestionIDs.contains(nextQuestionID) )
        {
            nextQuestionID = getRandomQuestionID();
        }

        mActiveQuestionID = nextQuestionID;
        
        mDatabaseAccess.open();

        Cursor cursor = mDatabaseAccess.getQuestionFragmentData(mActiveQuestionID);

        cursor.moveToFirst();

        String question = cursor.getString(0);
        // TODO: Do something with the image url
        String imageUrl = cursor.getString(1);
        String answer1 = cursor.getString(2);
        String answer2 = cursor.getString(3);
        String answer3 = cursor.getString(4);
        String answer4 = cursor.getString(5);

        cursor.close();
        mDatabaseAccess.close();

        // TODO Figure out proper way to handle image id/url
        questionFragment.setWidgetData(0, question, answer1, answer2, answer3, answer4);

        mUsedQuestionIDs.add(mActiveQuestionID);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_fragment_layout, questionFragment, questionFragmentTag);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void showMainMenuFragment() {
        String fragmentTag = getString(R.string.main_menu_fragment_tag);
        MainMenuFragment mainMenuFragment =
                (MainMenuFragment)getFragmentManager().findFragmentByTag(fragmentTag);

        if (null == mainMenuFragment) {
            mainMenuFragment = MainMenuFragment.newInstance();
        }

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_fragment_layout, mainMenuFragment, fragmentTag);
        transaction.addToBackStack(fragmentTag);

        transaction.commit();
    }

    @Override
    public void onQuestionAnswered(int answerIndex) {
        showAnswerExplanationFragment(answerIndex);
    }

    private void showAnswerExplanationFragment(int answerIndex) {

        mDatabaseAccess.open();
        Cursor cursor = mDatabaseAccess.getExplanationFragmentData(mActiveQuestionID);
        cursor.moveToFirst();

        String question = cursor.getString(0);
        String explanation = cursor.getString(1);
        int correctAnswerIndex = cursor.getInt(2);

        mDatabaseAccess.close();

        String explanationFragmentTag = getString(R.string.answer_explanation_fragment_tag);

        AnswerExplanationFragment explanationFragment =
                (AnswerExplanationFragment)getFragmentManager().findFragmentByTag(explanationFragmentTag);

        if (null == explanationFragment) {
            explanationFragment = AnswerExplanationFragment.newInstance();
        }

        boolean isAnswerCorrect = correctAnswerIndex == answerIndex;

        if (isAnswerCorrect) {
            mCorrectAnswerCount++;
        }
        explanationFragment.setWidgetData(isAnswerCorrect, question, explanation);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_fragment_layout, explanationFragment, explanationFragmentTag);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void onContinue() {
        mCompletedQuestionCount++;

        if (mCompletedQuestionCount < MAX_PRACTICE_QUESTION_COUNT) {
            showStandardQuestionFragment();
        }
        else {
            showResultFragment();
        }
    }

    private void showResultFragment() {
        String resultFragmentTag = getString(R.string.result_fragment_tag);

        ResultFragment resultFragment =
                (ResultFragment)getFragmentManager().findFragmentByTag(resultFragmentTag);

        if (null == resultFragment) {
            resultFragment = ResultFragment.newInstance();
        }

        float resultPercentage = ((float)mCorrectAnswerCount / MAX_PRACTICE_QUESTION_COUNT) * 100;

        resultFragment.setWidgetData(resultPercentage);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_fragment_layout, resultFragment, resultFragmentTag);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void onFinishPractice() {
        endPracticeTest();
    }

    @Override
    public void onRedoPractice() {
        resetData();
        showStandardQuestionFragment();
    }

    private void resetData()
    {
        mActiveQuestionID = 0;
        mCompletedQuestionCount = 0;
        mCorrectAnswerCount = 0;
        mUsedQuestionIDs.clear();
    }

    private static int getRandomQuestionID() {
        int range = (MAX_QUESTION_ID - MIN_QUESTION_ID) + MIN_QUESTION_ID;

        return (int)(Math.random() * range) + MIN_QUESTION_ID;
    }

    @Override
    public void onBackPressed() {
        if(mActiveQuestionID > 0) {
            showEndPractiveConfirmDialog();
        }
    }

    private void showEndPractiveConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.cancel_confirm_title))
                .setMessage(getString(R.string.cancel_confirm_body))
                .setPositiveButton(getString(R.string.positive_btn_txt), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        endPracticeTest();
                    }
                })
                .setNegativeButton(getString(R.string.negative_btn_txt), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                })
                .show();
    }

    private void endPracticeTest() {
        resetData();
        showMainMenuFragment();
    }
}
