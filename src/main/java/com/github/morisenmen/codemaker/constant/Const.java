package com.github.morisenmen.codemaker.constant;

import java.io.File;

/**
 * 描述：常量
 * Created by hushijun on 2018/2/19.
 */
public class Const {
    /**
     * windows的{@link File#separator} 与这里的不同，但是可以支持/
     * 并且，windows获取到的路径路径并不全是File.separator的值
     * 最优办法把所有的windows获取到的路径链接全部转换为这里的值
     */
    public static final String SEPARATOR = "/";

    public static final String FTL = "ftl";

    public static final String DOT = ".";

    public static final String EMPTY_STRING = "";

    public static final String JAVA_SUFFIX = ".java";

    public static final String UTF8 = "utf-8";

    public static final String SRC_PATH = "src" + Const.SEPARATOR + "main" + Const.SEPARATOR + "java" + Const.SEPARATOR;

    public static final String RESOURCE_PATH = "src" + Const.SEPARATOR + "main" + Const.SEPARATOR + "resources" + Const.SEPARATOR;

    public static final String FTL_SUFFIX = DOT + FTL;

    public static final String FTL_PREFIX = FTL + Const.SEPARATOR;

    public static final int WRITER_BASE_SIZE = 10240;

    public static final String JAR_FILE = "jar";

    public static final String LIB_MODULE_NAME = "lib";
}
