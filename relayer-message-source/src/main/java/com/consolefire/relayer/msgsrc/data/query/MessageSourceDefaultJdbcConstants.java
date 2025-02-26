package com.consolefire.relayer.msgsrc.data.query;

public class MessageSourceDefaultJdbcConstants {

    // Table Name
    public static final String TABLE_NAME = "message_source";

    // Column Names
    public static final String COLUMN_IDENTIFIER = "identifier";
    public static final String COLUMN_CONFIGURATION = "configuration";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    // Parameter Indices
    public static final int PARAM_INDEX_IDENTIFIER = 1;
    public static final int PARAM_INDEX_CONFIGURATION = 2;
    public static final int PARAM_INDEX_STATE = 3;
    public static final int PARAM_INDEX_CREATED_AT = 4;
    public static final int PARAM_INDEX_UPDATED_AT = 5;
    public static final int PARAM_INDEX_CONFIGURATION_UPDATE = 6;
    public static final int PARAM_INDEX_STATE_UPDATE = 7;
    public static final int PARAM_INDEX_UPDATED_AT_UPDATE = 8;
}