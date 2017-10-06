package com.popland.pop.rsswithjsoupdemo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by hai on 24/07/2016.
 */
public class RSSparser {

    public ArrayList<RSSitems> getRSSFromFeed(String theurl)
    {
        ArrayList<RSSitems> items = new ArrayList<RSSitems>();
        String RSSxml = this.getXMLFromUrl(theurl);
        if(RSSxml != null){
            Document doc = this.getDOMElement(RSSxml);
            NodeList nodeList = doc.getElementsByTagName("item");
            for(int i=0;i<nodeList.getLength();i++){
                Element element = (Element) nodeList.item(i);
                String title = this.getValue(element,"title");
                String link = this.getValue(element,"link");
                String description = this.getValue(element,"description");
                String pubDate = this.getValue(element,"pubDate");

                org.jsoup.nodes.Document docHtml = Jsoup.parse(description);
                Elements img = docHtml.select("img");
                String anh = img.attr("src");
                RSSitems rssItems = new RSSitems(title,pubDate,link,anh);
                items.add(rssItems);
            }
        }
        return items;
    }
    public String getXMLFromUrl(String url){
        String xml ="";
        try{
        DefaultHttpClient  defaultHttpClient = new DefaultHttpClient();
        HttpGet httpGet =  new HttpGet(url);
        HttpResponse response = defaultHttpClient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
            xml = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public Document getDOMElement(String xml){
        Document document = null;
        DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            InputSource input = new InputSource();
            input.setCharacterStream(new StringReader(xml));
            input.setEncoding("UTF-8");
            document = documentBuilder.parse(input);
            document.normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public String  getValue(Element element,String title){
        NodeList nodeList = element.getElementsByTagName(title);
        return this.getTextNodeValue(nodeList.item(0));
    }
    public String getTextNodeValue(Node elem){
        Node child;
        if(elem != null){
            if(elem.hasChildNodes()){
                for(child = elem.getFirstChild(); child !=null; child.getNextSibling()){
                    if((child.getNodeType()==Node.TEXT_NODE)|| (child.getNodeType()==Node.CDATA_SECTION_NODE)){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}
