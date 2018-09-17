package com.innominds.reporting;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLCSVConverter {

	public static void main(String[] args) throws TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, IOException, TransformerException {

		String xsl = "C:/wspace/XSLCSVConverter/converter.xsl";
        String xml = "D:/Microsoft/Result3.xml";
    	String outfile = "D:/Microsoft/out22.csv";
    	XMLCSVConverter xmcsv = new XMLCSVConverter();
        xmcsv.convert(xml, xsl, outfile);
	}
	
	public void convert(String xmlfile, String xslfile, String outcsvfile) throws TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, IOException, TransformerException {

		File stylesheet = new File(xslfile);
        File xmlSource = new File(xmlfile);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlSource);

        StreamSource stylesource = new StreamSource(stylesheet);
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer(stylesource);
        Source source = new DOMSource(document);
        Result outputTarget = new StreamResult(new File(outcsvfile));
        transformer.transform(source, outputTarget);
	}

}
