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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlParserProvider implements FileParserServices {
    public static final String XML = ".xml";

    @Override
    public List<Map<String, String>> parse(File file) {
        List<Map<String, String>> peopleList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            String xmlString = FileReaderUtil.readAllLinesFromFile(file);
            Document doc = builder.parse(new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8)));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Map<String, String> personMap = new HashMap<>();
                    personMap.put("name", element.getElementsByTagName("name").item(0).getTextContent());
                    personMap.put("age", element.getElementsByTagName("age").item(0).getTextContent());
                    personMap.put("email", element.getElementsByTagName("email").item(0).getTextContent());
                    peopleList.add(personMap);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return peopleList;
    }

    @Override
    public Boolean isProvided(File file) {
        return file.getName().toLowerCase().endsWith(XML);
    }
}
