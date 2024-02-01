package org.example.impl;

import org.example.interfaces.FileParserServices;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.example.utils.FileReaderUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class XmlParserProvider<T> implements FileParserServices<T> {
    public static final String XML = ".xml";

    @Override
    public List<T> parse(File file, Class<T> clazz) {
        List<T> result = new ArrayList<>();

        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            String xmlString = FileReaderUtil.readAllLinesFromFile(file);
            Document doc = builder.parse(new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8)));
            doc.getDocumentElement().normalize();

            String[] cash = clazz.getName().toLowerCase().split("\\.");
            String name = cash[cash.length - 1];

            NodeList nodeList = doc.getElementsByTagName(name);

            for (int i = 0; i < nodeList.getLength(); i++) {
                T obj = clazz.newInstance();
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);
                        try {
                            field.set(obj, element.getElementsByTagName(field.getName()).item(0).getTextContent());
                            // TODO: 2/1/2024 add method to set any type of fields
                        } catch (IllegalArgumentException e) {
                            throw new UnsupportedOperationException("Type " + field.getType().getName() + " is not provided right now !");
                        }
                    }
                    result.add(obj);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException |
                 IllegalAccessException | InstantiationException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return result;
    }

    @Override
    public Boolean isProvided(File file) {
        return file.getName().toLowerCase().endsWith(XML);
    }


}
