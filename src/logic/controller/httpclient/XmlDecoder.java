package logic.controller.httpclient;

import javax.xml.parsers.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlDecoder {

	private XmlDecoder() {

	}

	// return the list of element of name name with parent
	public static List<Element> getElemFromNameParent(String xml, String name, String parent) {
		List<Element> result = new ArrayList<Element>();
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xml)));
			// get desired results
			Element root = doc.getDocumentElement();
			Element results = (Element) root.getElementsByTagName(parent).item(0);
			NodeList listOfResults = results.getElementsByTagName(name);
			for (int i = 0; i < listOfResults.getLength(); i++) {
				Node node = listOfResults.item(i);
				if (node.getParentNode().getNodeName() == parent) {
					// TODO: implement build of position
					Element element = (Element) node;
					result.add(element);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	// return the root element of the xml
	public static Element getRoot(String xml) {
		Element root = null;

		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xml)));
			root = doc.getDocumentElement();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}

	public static String getStringByTag(Element element, String tag) {
		String result = null;

		result = element.getElementsByTagName(tag).item(0).getTextContent();

		return result;
	}

}
