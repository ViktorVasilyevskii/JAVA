package com.company;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.HashMap;

public class Main {

    private static final String URL_CBR_RU = "http://www.cbr.ru/scripts/XML_daily.asp";
    private static final String CHAR_CODE_CURRENCY = "HKD";

    private static HashMap<String, NodeList> result = new HashMap<>();
    
    private static final int INDEX_NOMINAL = 2;
    private static final int INDEX_VALUE = 4;

    public static void main(String[] args) throws Exception {

        parseCurrency();
        int nominal = Integer.parseInt(result.get(CHAR_CODE_CURRENCY).item(INDEX_NOMINAL).getTextContent());
        double value = Double.parseDouble(result.get(CHAR_CODE_CURRENCY).item(INDEX_VALUE).getTextContent().replace(',', '.'));

        System.out.println("1 Гонконгский доллар = " + (value / nominal) + " Российский рубль");

    }

    private static void parseCurrency() throws Exception {
        Document doc = loadDocument(URL_CBR_RU);
        NodeList nodeList = doc.getElementsByTagName("Valute");

        for(int indexNodeList = 0; indexNodeList < nodeList.getLength(); indexNodeList++){

            Node node = nodeList.item(indexNodeList);
            NodeList nodeListChild = node.getChildNodes();

            for(int indexNodeListChild =  0; indexNodeListChild < nodeListChild.getLength(); indexNodeListChild++){

                if (nodeListChild.item(indexNodeListChild).getNodeName().equals("CharCode")){
                    result.put(nodeListChild.item(indexNodeListChild).getTextContent(), nodeListChild);

                }
            }
        }
    }


    private static Document loadDocument(String url) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder().parse(new URL(url).openStream());
    }
}
