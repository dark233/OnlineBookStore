package com.taunton.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.logging.*;

import org.apache.commons.logging.impl.Log4JLogger;
import org.junit.Test;

import com.taobao.api.internal.toplink.logging.Log4jLogger;
import com.taobao.api.internal.toplink.logging.Log4jLoggerFactory;

/**
 * 默认将log文件输出到D:\apache-tomcat-7.0.69\logs\BookStore\xxxx年\x月\xxxx-xx-xx.log
 * 路径不存在的话会自动创建
 * 可通过修改getLogFilePath修改生成的log路径
 *
 * author:tangdong
 */
public class LogUtils {

    private static Calendar now = Calendar.getInstance();

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final int year = now.get(Calendar.YEAR);

    private static final int month = now.get(Calendar.MONTH) + 1;

    private static final String LOG_FOLDER_NAME = "BookStore";

    private static final String LOG_FILE_SUFFIX = ".log";

    private static Logger logger = Logger.getLogger("MyLogger");

    //使用唯一的fileHandler，保证当天的所有日志写在同一个文件里,
    private static FileHandler fileHandler = getFileHandler();

    private static MyLogFormatter myLogFormatter = new MyLogFormatter();

    private synchronized static String getLogFilePath() {
        StringBuffer logFilePath = new StringBuffer();
//        logFilePath.append(System.getProperty("user.home"));
        logFilePath.append("D:\\apache-tomcat-7.0.69\\logs");
        logFilePath.append(File.separatorChar);
        logFilePath.append(LOG_FOLDER_NAME);
        logFilePath.append(File.separatorChar);
        logFilePath.append(year);
        logFilePath.append(File.separatorChar);
        logFilePath.append(month);

        File dir = new File(logFilePath.toString());
        if (!dir.exists()) {
            dir.mkdirs();
        }

        logFilePath.append(File.separatorChar);
        logFilePath.append(sdf.format(new Date()));
        logFilePath.append(LOG_FILE_SUFFIX);

//        System.out.println(logFilePath.toString());
        return logFilePath.toString();
    }

    private static FileHandler getFileHandler() {
        FileHandler fileHandler = null;
        boolean APPEND_MODE = true;
        try {
            //文件日志内容标记为可追加
            fileHandler = new FileHandler(getLogFilePath(), APPEND_MODE);
            return fileHandler;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized static Logger setLoggerHanlder() {
        return setLoggerHanlder(Level.ALL);
    }

    //    SEVERE > WARNING > INFO > CONFIG > FINE > FINER > FINESET
    public synchronized static Logger setLoggerHanlder(Level level) {

        try {
            //以文本的形式输出
//            fileHandler.setFormatter(new SimpleFormatter());
        	FileHandler fileHandler = getFileHandler();
            fileHandler.setFormatter(myLogFormatter);
            
            logger.addHandler(fileHandler);
            logger.setLevel(level);
        } catch (SecurityException e) {
            logger.severe(populateExceptionStackTrace(e));
        }
        return logger;
    }

    private synchronized static String populateExceptionStackTrace(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString()).append("\n");
        for (StackTraceElement elem : e.getStackTrace()) {
            sb.append("\tat ").append(elem).append("\n");
        }
        return sb.toString();
    }

    public static void main(String [] args) {
//        Logger logger = LogUtils.setLoggerHanlder(Level.INFO);
//        logger.info("Hello, world!");
//        logger.severe("What are you doing?");
//        logger.warning("Warning !");
    	 Float f1 = null;
    	 float f2 = f1;
    	 System.out.println(f2);
    }
}
