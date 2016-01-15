package com.example.luismoscoso.knowledgetestpractice;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luismoscoso on 15-11-24.
 */
public class DatabaseAccess {

    private SQLiteOpenHelper openHelper = null;
    private SQLiteDatabase database = null;

    private static final String TABLE_NAME = "practice_question";
    private static final String QUESTION_TEXT_COL_NAME = "question_text";
    private static final String IMAGE_URL_COL_NAME = "image_url";
    private static final String ANSWER_1_COL_NAME = "answer_1";
    private static final String ANSWER_2_COL_NAME = "answer_2";
    private static final String ANSWER_3_COL_NAME = "answer_3";
    private static final String ANSWER_4_COL_NAME = "answer_4";
    private static final String CORRECT_ANSWER_INDEX_COL_NAME = "correct_answer_index";
    private static final String EXPLANATION_COL_NAME = "explanation";

    private static DatabaseAccess instance =  null;

    private DatabaseAccess(Context context) {
        this.openHelper =  new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance =  new DatabaseAccess(context);
        }

        return instance;
    }

    /*
        Open database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /*
        Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public Cursor getQuestionFragmentData(int questionID) {
        Cursor cursor = database.rawQuery("SELECT " +   QUESTION_TEXT_COL_NAME + ", " +
                                                        IMAGE_URL_COL_NAME + ", " +
                                                        ANSWER_1_COL_NAME + ", " +
                                                        ANSWER_2_COL_NAME + ", " +
                                                        ANSWER_3_COL_NAME + ", " +
                                                        ANSWER_4_COL_NAME +
                                        " FROM " + TABLE_NAME +
                                        " WHERE id = " + Integer.toString(questionID), null);

        return cursor;
    }

    public Cursor getExplanationFragmentData(int questionID) {
        Cursor cursor = database.rawQuery("SELECT " +   QUESTION_TEXT_COL_NAME + ", " +
                                                        EXPLANATION_COL_NAME + ", " +
                                                        CORRECT_ANSWER_INDEX_COL_NAME +
                                            " FROM " + TABLE_NAME +
                                            " WHERE id = " + Integer.toString(questionID), null);

        return cursor;
    }
}
