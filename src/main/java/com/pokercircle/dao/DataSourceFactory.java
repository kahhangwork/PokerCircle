package com.pokercircle.dao;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

public class DataSourceFactory {
    private static DataSourceFactory instance = new DataSourceFactory("./resources/db.properties");
    private Properties props;

    private DataSourceFactory(String fname) {
        props = new Properties();
        try{
            props.load(new FileInputStream(fname));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static DataSourceFactory instance () {
        return instance;
    }


    public DataSource getDataSource() {        
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(props.getProperty("url"));
        ds.setUser(props.getProperty("user"));
        ds.setPassword(props.getProperty("password"));


    try (Connection conn = ds.getConnection()) {
        System.out.println("DB Connection Successful");
    } catch (SQLException ex) {
        System.out.println("DB Connection Failed");
        ex.printStackTrace();
    }
       
    return ds;
    }
}