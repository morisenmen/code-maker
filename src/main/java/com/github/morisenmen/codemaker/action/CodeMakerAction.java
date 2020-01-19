package com.github.morisenmen.codemaker.action;

import com.github.morisenmen.codemaker.conf.Author;
import com.github.morisenmen.codemaker.conf.CodeMakerConfig;
import com.github.morisenmen.codemaker.conf.JdbcConnection;
import com.github.morisenmen.codemaker.conf.TableField;
import com.github.morisenmen.codemaker.generater.CodeGenerator;
import com.github.morisenmen.codemaker.generater.FieldInfo;
import com.github.morisenmen.codemaker.util.CodeMakerUtil;
import com.github.morisenmen.codemaker.conf.Config;
import com.github.morisenmen.codemaker.conf.Table;
import com.github.morisenmen.codemaker.constant.Const;
import com.github.morisenmen.codemaker.generater.TableInfo;
import com.github.morisenmen.codemaker.util.XmlParser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 描述：主类入口
 * Created by hushijun on 2018/2/19.
 */
public class CodeMakerAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project     = anActionEvent.getProject();
        String  projectPath = project.getBasePath() + Const.SEPARATOR;
        boolean flag        = true;

        try {
            CodeMakerConfig codeMakerConfig = XmlParser.parseXml(projectPath + "code-maker-config.xml");
            generate(projectPath, codeMakerConfig);
        } catch (Exception e) {
            String fullMessage = "";
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                fullMessage += stackTraceElement.toString() + "\r\n";
            }
            Messages.showErrorDialog(fullMessage, e.getMessage());
            Messages.showMessageDialog(project, e.getMessage(), "代码生成失败", Messages.getWarningIcon());
            flag = false;
        }

        if (flag) {
            Messages.showMessageDialog(project, "代码生成成功", "代码生成完成", null);
        }
    }

    private String trimPath(String path) {
        if (path.endsWith(Const.SEPARATOR + Const.FTL + Const.SEPARATOR)) {
            return path.substring(0, path.lastIndexOf(Const.SEPARATOR + Const.FTL + Const.SEPARATOR));
        }
        if (path.endsWith(Const.SEPARATOR + Const.FTL)) {
            return path.substring(0, path.lastIndexOf(Const.SEPARATOR + Const.FTL));
        }
        if (path.endsWith(Const.SEPARATOR)) {
            return path.substring(0, path.lastIndexOf(Const.SEPARATOR));
        }
        return path;
    }

    private void generate(String projectPath, CodeMakerConfig codeMakerConfig)
            throws SQLException, ClassNotFoundException, URISyntaxException, IOException, TemplateException {
        JdbcConnection jdbcConnection = codeMakerConfig.getJdbcConnection();
        Author         author         = codeMakerConfig.getAuthor();
        Config         config         = codeMakerConfig.getConfig();

        List<Table> tableList = codeMakerConfig.getTable();
        if (null == tableList || tableList.size() == 0) {
            throw new NullPointerException("数据表不能为空");
        }

        Connection connection = CodeMakerUtil.getConnection(jdbcConnection.getDriver(), jdbcConnection.getUrl(), jdbcConnection.getUser(), jdbcConnection.getPassword());

        List<String> ftlFileList;
        // 使用外部化配置，配置模板
        String path = null;
        if (Objects.nonNull(codeMakerConfig.getTemplate()) && Objects.nonNull(codeMakerConfig.getTemplate().getPath())) {
            // 拿到配置值
            path = trimPath(codeMakerConfig.getTemplate().getPath());
            if (!path.startsWith(Const.SEPARATOR)) {
                path = projectPath + path;
            }
            // 拿到ftl位置
            String resourcePath = path + Const.SEPARATOR + Const.FTL;

            ftlFileList = CodeMakerUtil.traverseFolder(resourcePath, resourcePath);
        } else {
            // 默认模板
            URL ftlUrl = CodeMakerUtil.getFtlURL();
            if (Const.JAR_FILE.equals(ftlUrl.getProtocol())) {
                ftlFileList = CodeMakerUtil.traverseJarFolder(ftlUrl);
            } else {
                ftlFileList = CodeMakerUtil.traverseFolder(ftlUrl.getPath(), ftlUrl.getPath());
            }
        }


        for (Table tableConfig : tableList) {
            if (Objects.isNull(tableConfig.isRealDelete())) {
                tableConfig.setRealDelete(true);
            }
            TableInfo tableInfo = CodeMakerUtil.getTableInfo(connection, tableConfig.getTableName());
            List<String> packageList = new ArrayList<>();
            if (Objects.nonNull(tableConfig.getPackages()) && tableConfig.getPackages().length() > 0) {
                packageList = Arrays.asList(tableConfig.getPackages().split(","));
            }
            // 没有配置，即表示所有模块全部生成
            if (packageList.size() == 0) {
                packageList = ftlFileList;
            } else {
                List<String> tmp = new ArrayList<>();
                for (String ftl : ftlFileList) {
                    for (String p : packageList) {
                        if (ftl.startsWith(Const.FTL_PREFIX + p)) {
                            tmp.add(ftl);
                        }
                    }
                }
                packageList = tmp;
            }
            if (Objects.nonNull(tableConfig.getFiledList()) && tableConfig.getFiledList().size() > 0) {
                for (FieldInfo field : tableInfo.getFileds()) {
                    for (TableField f : tableConfig.getFiledList()) {
                        if (field.getColumnName().equals(f.getKey())) {
                            field.setDim(f.isDim());
                            if (Objects.nonNull(f.getAlias()) && f.getAlias().length() > 0) {
                                field.setBeanFiledName(f.getAlias());
                            }
                        }
                    }
                }
            }

            CodeGenerator generator =
                    new CodeGenerator(
                            tableInfo,
                            projectPath,
                            author.getUsername(),
                            author.getEmail(),
                            config,
                            tableConfig.getTableName(),
                            tableConfig.getAlias(),
                            tableConfig.getDesc(),
                            tableConfig.isRealDelete(),
                            tableConfig.getRangeKey());

            generator.generate(path, packageList);
        }
    }
}
