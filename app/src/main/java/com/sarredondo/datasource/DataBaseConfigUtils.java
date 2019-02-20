package com.sarredondo.datasource;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

import java.io.IOException;
import java.sql.SQLException;

public class DataBaseConfigUtils extends OrmLiteConfigUtil {
    public static void main(String[] args) throws IOException, SQLException {
        writeConfigFile("orm_config.txt");
    }
}