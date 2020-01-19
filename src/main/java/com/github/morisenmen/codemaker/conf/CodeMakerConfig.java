package com.github.morisenmen.codemaker.conf;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 描述：配置信息
 * Created by hushijun on 2018/2/19.
 */
@XmlRootElement
public class CodeMakerConfig {
    private JdbcConnection jdbcConnection;
    private Config config;
    private List<Table> table;
    private Author author;
    private FreeMakerTemplate template;

    public JdbcConnection getJdbcConnection() {
        return jdbcConnection;
    }

    @XmlElement
    public void setJdbcConnection(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    public Config getConfig() {
        return config;
    }

    @XmlElement
    public void setConfig(Config config) {
        this.config = config;
    }

    public List<Table> getTable() {
        return table;
    }

    @XmlElement
    public void setTable(List<Table> table) {
        this.table = table;
    }

    public Author getAuthor() {
        return author;
    }

    @XmlElement
    public void setAuthor(Author author) {
        this.author = author;
    }

    public FreeMakerTemplate getTemplate() {
        return template;
    }

    @XmlElement
    public void setTemplate(FreeMakerTemplate template) {
        this.template = template;
    }
}
