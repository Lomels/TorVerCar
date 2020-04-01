package logic.controller.httpClient;

import javax.xml.parsers.*;

import java.io.StringReader;
import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;


public class XmlDecoder {

	private static XmlDecoder instance = null;
	private DocumentBuilder builder;

	// constructor
	private XmlDecoder() {
		try {
			this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// singleton
	public static XmlDecoder getInstance() {
		if(instance == null)
			return new XmlDecoder();
		return instance;
	}

	// return the list of element of name name with parent
	public List<Element> getElemFromNameParent(String xml, String name, String parent){
		List<Element> result = new ArrayList<Element>();
		try {
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

}
