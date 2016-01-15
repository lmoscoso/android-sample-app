package com.example.luismoscoso.knowledgetestpractice;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by luismoscoso on 15-11-24.
 */
public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "question_data.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
