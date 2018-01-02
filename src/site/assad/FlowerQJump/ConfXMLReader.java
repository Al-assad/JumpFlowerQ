package site.assad.FlowerQJump;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Author: Al-assad 余林颖
 * E-mail: yulinying_1994@outlook.com
 * Date: 2018/1/2 23:43
 * Description: xml配置文件解析器，conf.xml
 */
public class ConfXMLReader {

    private String adbPath = null;
    private double magicNumber = 1.43;
    private int internelTime = 1000;
    private int screenHeight = 1000;

    public ConfXMLReader(String confPath){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(confPath));
            Element root = document.getDocumentElement();
            adbPath = root.getElementsByTagName("adb-path").item(0).getFirstChild().getNodeValue();
            magicNumber = Double.parseDouble(root.getElementsByTagName("magic-number").item(0).getFirstChild().getNodeValue());
            internelTime = Integer.parseInt(root.getElementsByTagName("internal-time").item(0).getFirstChild().getNodeValue());
            screenHeight = Integer.parseInt(root.getElementsByTagName("frame-height").item(0).getFirstChild().getNodeValue());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
    }

    public String getAdbPath() {
        return adbPath;
    }

    public double getMagicNumber() {
        return magicNumber;
    }

    public int getInternelTime() {
        return internelTime;
    }

    public int getScreenHeight() {
        return screenHeight;
    }



}
