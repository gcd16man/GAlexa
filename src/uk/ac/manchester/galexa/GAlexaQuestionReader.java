package uk.ac.manchester.galexa;
/* 
 *  GAlexaQuestionReader.java
 */

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.ArrayList;

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
    private static ArrayList<GAlexaQuestion> qlist = new ArrayList<GAlexaQuestion>();
        
    public ArrayList<GAlexaQuestion> getQuestionList() {
	return this.qlist;
  }

  public static void main (String[] args){
      String questionURL = "C:/Research/Explanations/code/NeticaJ_504/examples/GAlexa/Data/questions.xml";
      xmlParse(questionURL);
  }

public static void xmlParse(String questionURL) {
 
    try {
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
Document document = builder.parse(new File(questionURL));

document.getDocumentElement().normalize();
//Here comes the root node
Element root = document.getDocumentElement();
//System.out.println(root.getNodeName());
 
//Get all questions
NodeList nList = document.getElementsByTagName("Question");


    for (int temp = 0; temp < nList.getLength(); temp++){
	Node node = nList.item(temp);
	GAlexaQuestion question = new GAlexaQuestion();

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

	if (childNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

			Element eElement = (Element) childNode;
			Text elText = (Text) eElement.getFirstChild();
			String theValue = elText.getNodeValue().trim();
			
			if (elementName.equals("Predicate")) {
			    pName=theValue;
		
			    question.setPredicate(pName); 
			}
			else if (elementName.equals("Subject")) {
			    sName=theValue;
		
			    question.setSubj(sName);
			    state = eElement.getAttribute("state");
		
			    question.setSubjState(state);
					}
			else if (elementName.equals("Object")) {
			    oName=theValue;
		
			    state = eElement.getAttribute("state");
		
			    question.addObj(oName,state);
			  			}
	}
	    }
	qlist.add(question);
	    
    }

     } catch (Exception e) {
         e.printStackTrace();
     }
}
}
