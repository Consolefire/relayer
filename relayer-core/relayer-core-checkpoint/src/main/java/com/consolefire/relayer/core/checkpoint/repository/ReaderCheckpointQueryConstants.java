package com.consolefire.relayer.core.checkpoint.repository;

import static java.lang.String.format;

public class ReaderCheckpointQueryConstants {

    public static final String TABLE_NAME = "READER_CHECKPOINTS";
    public static final String COLUMN_IDENTIFIER = "IDENTIFIER";
    public static final String COLUMN_IS_COMPLETED = "IS_COMPLETED";
    public static final String COLUMN_CREATED_AT = "CREATED_AT";
    public static final String COLUMN_EXPIRES_AT = "EXPIRES_AT";

    public static final String SELECT_COLUMNS = format(
        "SELECT %s, %s, %s, %s FROM %s ",
        COLUMN_IDENTIFIER, COLUMN_IS_COMPLETED, COLUMN_CREATED_AT, COLUMN_EXPIRES_AT, TABLE_NAME
    );

    public static final String SELECT_ALL_SQL = SELECT_COLUMNS;
    public static final String SELECT_BY_ID_SQL = SELECT_COLUMNS
        + format(" WHERE %s = ?", COLUMN_IDENTIFIER);

    public static final String INSERT_SQL = format(
        "INSERT INTO %s (%s, %s, %s, %s) VALUES (?,?,?,?)",
        TABLE_NAME, COLUMN_IDENTIFIER, COLUMN_IS_COMPLETED, COLUMN_CREATED_AT, COLUMN_EXPIRES_AT
    );

    public static final String UPDATE_SQL = format(
        "UPDATE %s SET %s=?, %s=?, %s=? WHERE %s=?",
        TABLE_NAME, COLUMN_IS_COMPLETED, COLUMN_CREATED_AT, COLUMN_EXPIRES_AT, COLUMN_IDENTIFIER
    );

    public static final String DELETE_BY_ID_SQL = format(
        "DELETE FROM %s WHERE %s = ?",
        TABLE_NAME, COLUMN_IDENTIFIER
    );

    public static final String DELETE_BY_ID_SET_SQL = format(
        "DELETE FROM %s WHERE %s IN (?)",
        TABLE_NAME, COLUMN_IDENTIFIER
    );

    public static final String DELETE_ALL_SQL = format(
        "DELETE FROM %s",
        TABLE_NAME
    );
}
