package com.github.morisenmen.codemaker.generater;

import com.github.morisenmen.codemaker.conf.Config;
import com.github.morisenmen.codemaker.constant.Const;
import com.github.morisenmen.codemaker.util.CodeMakerUtil;
import com.github.morisenmen.codemaker.util.FreeMarkerTemplateUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.morisenmen.codemaker.constant.Const.DOT;
import static com.github.morisenmen.codemaker.constant.Const.FTL_PREFIX;
import static com.github.morisenmen.codemaker.constant.Const.LIB_MODULE_NAME;
import static com.github.morisenmen.codemaker.constant.Const.RESOURCE_PATH;
import static com.github.morisenmen.codemaker.constant.Const.SRC_PATH;
import static com.github.morisenmen.codemaker.constant.Const.UTF8;
import static com.github.morisenmen.codemaker.constant.Const.WRITER_BASE_SIZE;

/**
 * 描述：代码生成器
 * Created by hushijun on 2018/2/19.
 */
public class CodeGenerator {

    // 生成文件相关
    private String  author;
    private String  email;
    private String  tableName;
    private String  beanName;
    private Config  config;
    private String  diskPath;
    private String  rangeKey;
    private String  desc;
    private boolean realDelete;

    private TableInfo tableInfo;

    public CodeGenerator(TableInfo tableInfo, String diskPath, String author, String email, Config config, String tableName, String beanName, String desc, boolean realDelete, String rangeKey) {
        this.tableInfo = tableInfo;
        this.author = author;
        this.email = email;
        this.tableName = tableName;
        this.beanName = null == beanName ? CodeMakerUtil.replaceUnderLineAndUpperCase(tableName) : beanName;
        this.config = config;
        this.diskPath = diskPath;
        this.desc = desc;
        this.realDelete = realDelete;
        this.rangeKey = rangeKey;
    }

    private Map<String, Object> getDataMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("model_column", tableInfo.getFileds());
        dataMap.put("table_name_small", tableName);
        dataMap.put("bean_name", beanName);
        dataMap.put("under_bean_name", CodeMakerUtil.underscoreName(beanName));
        dataMap.put("author", author);
        dataMap.put("email", email);
        dataMap.put("date", CodeMakerUtil.getCurrentDate());
        dataMap.put("package_name", config.getBasePackage());
        dataMap.put("table_desc", Objects.nonNull(desc) ? desc : tableInfo.getComment());
        dataMap.put("table_comment", tableInfo.getComment());
        dataMap.put("key_type", tableInfo.getPrimaryKeyType());
        dataMap.put("key", tableInfo.getPrimaryKeyBeanName());
        dataMap.put("key_column", tableInfo.getPrimaryKeyName());
        dataMap.put("realDelete", realDelete);
        dataMap.put("range_key", rangeKey);

        return dataMap;
    }

    private void generateFileByTemplate(String pathPrefix, Map<String, Object> dataMap, final String templateFileName)
            throws IOException, TemplateException {
        Template template;
        if (Objects.isNull(pathPrefix)) {
            template = FreeMarkerTemplateUtils.getTemplate(templateFileName);
        } else {
            template = FreeMarkerTemplateUtils.getTemplate(pathPrefix, templateFileName);
        }

        template.process(dataMap, getBufferedWriter(templateFileName));
    }

    private BufferedWriter getBufferedWriter(final String templateFileName)
            throws FileNotFoundException, UnsupportedEncodingException {
        String packagePath = CodeMakerUtil.getParentPath(templateFileName);
        String suffix      = CodeMakerUtil.getFtlFileSuffix(templateFileName);

        String filePath;
        String moduleName = config.getModule();
        if (templateFileName.startsWith(FTL_PREFIX + LIB_MODULE_NAME)) {
            moduleName = config.getLibPath();
        }
        if (suffix.endsWith(Const.JAVA_SUFFIX)) {
            filePath = getCodePath(moduleName, packagePath, suffix);
        } else {
            filePath = getResourcePath(moduleName, packagePath, suffix);
        }

        File outFile = new File(filePath);
        if (!outFile.getParentFile().exists()) {
            boolean result = outFile.getParentFile().mkdirs();
            if (!result) {
                System.out.println("创建失败");
            }
        }

        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), UTF8), WRITER_BASE_SIZE);
    }

    /**
     * 根据模板路径生成代码
     *
     * @param ftlFileList 模板路径集合
     */
    public void generate(String prefix, List<String> ftlFileList) throws IOException, TemplateException {
        Map<String, Object> dataMap = getDataMap();

        if (null != ftlFileList && ftlFileList.size() > 0) {
            for (String ftlFileName : ftlFileList) {
                generateFileByTemplate(prefix, dataMap, ftlFileName);
            }
        }
    }

    /**
     * 根据包名和文件后缀获取生成文件的生成路径
     *
     * @param moduleName  模块名
     * @param packagePath 包路径
     * @param suffix      文件后缀
     * @return 文件路径
     */
    private String getCodePath(String moduleName, String packagePath, String suffix) {
        return getBasePath(moduleName) +
                SRC_PATH +
                config.getBasePackage().replace(DOT, Const.SEPARATOR) +
                Const.SEPARATOR +
                packagePath +
                Const.SEPARATOR +
                beanName +
                suffix;
    }

    /**
     * 根据资源路径和文件后缀获取资源文件生成路径
     *
     * @param moduleName   模块名
     * @param resourcePath 资源文件路径
     * @param suffix       文件后缀
     * @return 文件路径
     */
    private String getResourcePath(String moduleName, String resourcePath, String suffix) {
        return getBasePath(moduleName) + RESOURCE_PATH + resourcePath + Const.SEPARATOR + beanName + suffix;
    }

    private String getBasePath(String moduleName) {
        String path = diskPath;
        if (null != moduleName && moduleName.length() > 0) {
            path = path + moduleName + Const.SEPARATOR;
        }

        return path;
    }
}
