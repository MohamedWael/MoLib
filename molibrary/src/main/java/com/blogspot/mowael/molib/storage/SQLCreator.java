package com.blogspot.mowael.molib.storage;

import com.blogspot.mowael.molib.utilities.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by moham on 8/4/2017.
 */

public class SQLCreator {

    public enum DBDataTypes {
        PRIMARY_KEY("PRIMARY KEY"), UNIQUE("UNIQUE"), AUTO_INCREMENT("AUTO_INCREMENT"), NOT_NULL("NOT NULL"),
        TEXT("TEXT"), TEXT_UNIQUE(TEXT + " " + UNIQUE.getDataType()),
        INTEGER("INTEGER"), INTEGER_PRIMARY_KEY(INTEGER.getDataType() + " " + PRIMARY_KEY.getDataType()),
        DOUBLE("DOUBLE"), DOUBLE_PRIMARY_KEY(DOUBLE.getDataType() + " " + PRIMARY_KEY.getDataType());

        private String dataType;

        DBDataTypes(String dataType) {
            this.dataType = dataType;
        }

        public String getDataType() {
            return dataType;
        }
    }

    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    private static final String SELECT_ALL_FROM = "SELECT * FROM ";
    private static final String DISTINCT = "DISTINCT";
    private static final String WHERE = "WHERE";
    private static final String SELECT_DISTINCT_ALL_FROM = "SELECT DISTINCT * FROM ";
    private static final String DELETE_FROM = "DELETE FROM ";
    private static final String INSERT_INTO = "INSERT INTO ";

    public static String createTable(String tableName, HashMap<String, DBDataTypes> columns) {
        String createTable = CREATE_TABLE + tableName + "(";
        ArrayList<String> columnNames = new ArrayList<>();
        for (String key : columns.keySet()) {
            columnNames.add(key + " " + columns.get(key).getDataType());
        }
        createTable += columnNames.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ")";
        Logger.d("SQL_STATEMENT", createTable);
        return createTable;
    }

    public static String selectAllFrom(String tableName) {
        return SELECT_ALL_FROM + tableName;
    }

    public static String selectAllFrom(String tableName, String where) {
        return selectAllFrom(tableName) + " " + WHERE + " " + where;
    }

    public static String selectDistinctAllFrom(String tableName) {
        return SELECT_DISTINCT_ALL_FROM + tableName;
    }

    public static String selectDistinctAllFrom(String tableName, String where) {
        return selectDistinctAllFrom(tableName) + " " + WHERE + " " + where;
    }


//    public static void insertInto(String tableName, HashMap<String, String> data) {
//        String insertstmnt = INSERT_INTO + tableName;
//
////        INSERT INTO Persons (FirstName,LastName) VALUES ('Lars','Monsen');
//    }

    public static String dropTableIfExists(String tableName) {
        return DROP_TABLE_IF_EXISTS + tableName;
    }

    public static String deleteAllFrom(String tableName) {
        return DELETE_FROM + tableName;
    }
}
