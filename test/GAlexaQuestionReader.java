/* 
 *  GAlexaQuestionReader.java
 *
 * Copyright (C) 2020, UoM.
 * The software in this file may be copied, modified, and/or included in 
 * derivative works without charge or obligation.
 * This file contains example software only, and Norsys makes no warranty that 
 * it is suitable for any particular purpose, or without defects.
 */

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *  GAlexaQuestionReader works with a question file specifying the
 *  set of questions in XML format.
 *
 * The format of the XML question file is as in the following example:
 *  (please note: matching is *case-sensitive*)
 * 
 *  <?xml version="1.0" encoding="UTF-8"?>
 *  <Questions>
 *  <Question>
 *  	<Predicate>probability_of</SourceName>
 *      <Subject>tuberculosis</Subject>
 *  </Question>
 *  <Question>
 *  	<Predicate>probability_of</SourceName>
 *      <Subject>tuberculosis</Subject>
 *  	<Object state="abnormal">Xray</Object>
 *  </Question>
 *  <Question>
 *  	<Predicate>probability_of</SourceName>
 *      <Subject>tuberculosis</Subject>
 *  	<Object state="abnormal">Xray</Object>
 *  	<Object state="visit">VisitAsia</Object>
 *  </Question>
*  <Question>
 *  	<Predicate>probability_of</SourceName>
 *      <Subject>Tuberculosis</Subject>
 *  	<Object state="abnormal">Xray</Object>
 *  	<Object state="visit">VisitAsia</Object>
 *  	<Object state="present">Cancer</Object>
 *  </Question>
 *  </Questions>
 * 
 */
     
public class GAlexaQuestionReader {

  public static void main (String[] args){
      String questionURL = "questions.xml";
      xmlParse(questionURL);
  }

private static void xmlParse(String questionURL) {
    try {
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
Document document = builder.parse(new File(questionURL));
//Normalize the XML Structure; It's just too important !!
document.getDocumentElement().normalize();
//Here comes the root node
Element root = document.getDocumentElement();
System.out.println(root.getNodeName());
 
//Get all questions
NodeList nList = document.getElementsByTagName("Question");
System.out.println("No. of questions:"+ nList.getLength());

    for (int temp = 0; temp < nList.getLength(); temp++){
	Node node = nList.item(temp);
	
	    // get the child nodes of a class node 
            NodeList classChildNodeList = node.getChildNodes();
	    // get the Predicate and Subject values 

	       String elementName  = null;
	       String pName = null;
	       String sName = null;
	       String oName = null;
	       String state = null;
	    for(int j=0; j<classChildNodeList.getLength(); j++) {
	
               org.w3c.dom.Node childNode = classChildNodeList.item(j);
	       elementName = childNode.getNodeName();
	       //System.out.println("\nCurrent Element :" + elementName);
	if (childNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

			Element eElement = (Element) childNode;
			Text elText = (Text) eElement.getFirstChild();
			String theValue = elText.getNodeValue().trim();
			
			if (elementName.equals("Predicate")) {
			    pName=theValue;
			    System.out.println("Predicate:"+pName);
			}
			else if (elementName.equals("Subject")) {
			    sName=theValue;
			    System.out.println("Subject:"+sName);
					}
			else if (elementName.equals("Object")) {
			    oName=theValue;
			    System.out.println("Object:"+oName);
			    state = eElement.getAttribute("state");
			    System.out.println("state:"+state);
			  			}
		}
	    }
    }

     } catch (Exception e) {
         e.printStackTrace();
     }
}
    

}
