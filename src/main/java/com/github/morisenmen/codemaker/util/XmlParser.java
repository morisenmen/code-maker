package com.github.morisenmen.codemaker.util;

import com.github.morisenmen.codemaker.conf.CodeMakerConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 描述：xml解析器
 * Created by hushijun on 2018/2/19.
 */
public class XmlParser {

    public static CodeMakerConfig parseXml(String path)
            throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CodeMakerConfig.class);
        //marshaller是类到XML 的转化，那么 unmashaller是XML到类的转化。
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (CodeMakerConfig) jaxbUnmarshaller.unmarshal(new FileInputStream(path));
    }

}
