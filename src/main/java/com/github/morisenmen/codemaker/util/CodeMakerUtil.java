package com.github.morisenmen.codemaker.util;

import com.github.morisenmen.codemaker.constant.Const;
import com.github.morisenmen.codemaker.generater.FieldInfo;
import com.github.morisenmen.codemaker.generater.TableInfo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static com.github.morisenmen.codemaker.constant.Const.EMPTY_STRING;
import static com.github.morisenmen.codemaker.constant.Const.FTL_SUFFIX;

/**
 * 描述：代码生成器工具类
 * Created by hushijun on 2018/2/19.
 */
public class CodeMakerUtil {
    public static String replaceUnderLineAndUpperCase(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        int count = sb.indexOf("_");
        while (count != 0) {
            int num = sb.indexOf("_", count);
            count = num + 1;
            if (num != -1) {
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count, count + 1, ia + "");
            }
        }
        String result = sb.toString().replaceAll("_", "");
        return capitalize(result);
    }

    public static String capitalize(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            char firstChar = str.charAt(0);
            return Character.isTitleCase(firstChar) ? str : (new StringBuilder(strLen)).append(Character.toTitleCase(firstChar)).append(str.substring(1)).toString();
        } else {
            return str;
        }
    }

    public static Connection getConnection(String dbDriver, String dbUrl, String dbUser, String dbPwd)
            throws ClassNotFoundException, SQLException {
        Class.forName(dbDriver);
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        return connection;
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new java.util.Date());
    }

    public static TableInfo getTableInfo(Connection connection, String tableName) throws SQLException {
        TableInfo tableInfo = new TableInfo();

        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, "%", tableName, null);
        if (resultSet.next()) {
            //数据库表的备注
            tableInfo.setComment(resultSet.getString("REMARKS"));
        }

        resultSet = databaseMetaData.getPrimaryKeys(null, null, tableName);
        if (resultSet.next()) {
            //数据库表的主键
            tableInfo.setPrimaryKeyName(resultSet.getString("COLUMN_NAME"));
            tableInfo.setPrimaryKeyBeanName(CodeMakerUtil.replaceUnderLineAndUpperCase(tableInfo.getPrimaryKeyName()));
        }

        resultSet = databaseMetaData.getColumns(null, "%", tableName, "%");

        while (resultSet.next()) {
            FieldInfo fieldInfo = new FieldInfo();
            //获取字段类型
            fieldInfo.setColumnType(getColumnType(resultSet.getString("TYPE_NAME")));
            fieldInfo.setJavaType(getJavaType(resultSet.getString("TYPE_NAME")));

            //获取字段名称
            fieldInfo.setColumnName(resultSet.getString("COLUMN_NAME"));
            if (fieldInfo.getColumnName().equals(tableInfo.getPrimaryKeyName())) {
                tableInfo.setPrimaryKeyType(fieldInfo.getJavaType());
            }
            //获取字段是否允许为null
            fieldInfo.setNullable(resultSet.getBoolean("NULLABLE"));
            //获取字段最大长度
            fieldInfo.setColumnLength(resultSet.getInt("COLUMN_SIZE"));
            //字段在数据库的注释
            fieldInfo.setColumnComment(resultSet.getString("REMARKS"));
            //转换字段名称，如 sys_name 变成 SysName
            fieldInfo.setBeanFiledName(CodeMakerUtil.replaceUnderLineAndUpperCase(fieldInfo.getColumnName()));

            tableInfo.addField(fieldInfo);
        }

        return tableInfo;
    }

    private static String getColumnType(String jdbcType) {
        jdbcType = jdbcType.replace(" UNSIGNED", "");
        switch (jdbcType) {
            case "CHAR":
            case "VARCHAR":
            case "LONGVARCHAR":
            case "MEDIUMTEXT":
            case "TINYTEXT":
            case "TEXT":
            case "LONGTEXT":
                return "VARCHAR";
            case "NUMERIC":
            case "DECIMAL":
                return "DECIMAL";
            case "BIT":
            case "BOOLEAN":
                return "BOOLEAN";
            case "TINYINT":
            case "SMALLINT":
            case "INTEGER":
            case "INT":
                return "INTEGER";
            case "BIGINT":
                return "BIGINT";
            case "REAL":
            case "FLOAT":
            case "DOUBLE":
                return "DOUBLE";
            case "BINARY":
            case "VARBINARY":
            case "LONGVARBINARY":
                return "BINARY";
            case "DATETIME":
                return "TIMESTAMP";
        }

        return jdbcType;
    }

    private static String getJavaType(String jdbcType) {
        String javaType = jdbcType.replace(" UNSIGNED", "");
        switch (javaType) {
            case "CHAR":
            case "VARCHAR":
            case "LONGVARCHAR":
            case "MEDIUMTEXT":
            case "TINYTEXT":
            case "TEXT":
            case "LONGTEXT":
                return "String";
            case "NUMERIC":
            case "DECIMAL":
                return "java.math.BigDecimal";
            case "BIT":
            case "BOOLEAN":
                return "Boolean";
            case "TINYINT":
            case "SMALLINT":
            case "INTEGER":
            case "INT":
                return "Integer";
            case "BIGINT":
                return "Long";
            case "REAL":
            case "FLOAT":
            case "DOUBLE":
                return "Double";
            case "BINARY":
            case "VARBINARY":
            case "LONGVARBINARY":
                return "byte[]";
            case "DATE":
                return "java.util.Date";
            case "TIME":
                return "java.sql.Time";
            case "DATETIME":
            case "TIMESTAMP":
                return "java.sql.Timestamp";
        }

        return null;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }

        int length = str.length();

        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static String upperCaseFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static List<String> traverseFolder(String prefix, String path) throws URISyntaxException {
        List<String> fileList = new LinkedList<>();
        File parentFile = new File(path);
        if (parentFile.exists()) {
            File[] files = parentFile.listFiles();
            if (files.length > 0) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        fileList.addAll(traverseFolder(prefix, file.getAbsolutePath()));
                    } else if (file.getName().endsWith(Const.FTL_SUFFIX)) {
                        if (isWin()) {
                            fileList.add(file.getAbsolutePath().replaceAll("\\" + File.separator, Const.SEPARATOR).replace(prefix, Const.FTL));
                        } else {
                            fileList.add(file.getAbsolutePath().replace(prefix, Const.FTL));
                        }
                    }
                }
            }
        }

        return fileList;
    }

    public static List<String> traverseJarFolder(URL classResourceURL) {
        String classResourcePath = classResourceURL.getPath();
        String jarPath = classResourcePath.substring(classResourcePath.indexOf("/"), classResourceURL.getPath().indexOf("!"));

        List<String> resources = new ArrayList<>();

        try {
            JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration jarEntries = jarFile.entries();

            while (jarEntries.hasMoreElements()) {

                JarEntry jarEntry = (JarEntry) jarEntries.nextElement();
                String resourceName = jarEntry.getName();
                if (!jarEntry.isDirectory()
                        && resourceName.startsWith(Const.FTL_PREFIX)
                        && resourceName.endsWith(Const.FTL_SUFFIX)) {
                    resources.add(resourceName);
                }
            }
        } catch (IOException e) {
            // TODO 异常处理
            e.printStackTrace();
        }

        return resources;
    }

    public static ClassLoader getClassLoader() {
        return CodeMakerUtil.class.getClassLoader();
    }

    public static URL getFtlURL() {
        return getClassLoader().getResource(Const.FTL);
    }

    public static String getBaseFtlPath() {
        return getClassLoader().getResource(Const.FTL).getPath();
    }

    public static String getRelativePath(String path) {
        return path.replace(getBaseFtlPath(), Const.EMPTY_STRING);
    }

    public static String getParentPath(String path) {
        // 从第四位开始，去除[ftl/]
        return path.substring(4, path.lastIndexOf(Const.SEPARATOR));
    }

    public static String getFtlFileSuffix(String path) {
        return path.substring(path.lastIndexOf(Const.SEPARATOR) + 1).replace(FTL_SUFFIX, EMPTY_STRING);
    }

    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }

    private static boolean isWin() {
        String osName = System.getProperty("os.name");

        return osName.startsWith("Windows");
    }
}
