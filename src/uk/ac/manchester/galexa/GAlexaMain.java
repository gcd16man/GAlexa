package uk.ac.manchester.galexa;
/* 
 *  GAlexaMain.java
 *
 * 
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import norsys.netica.*;
import norsys.neticaEx.aliases.Node;

     
public class GAlexaMain {

  public static void main (String[] args){
 
	System.out.println("Java environment:" + System.getProperty("sun.arch.data.model"));
	System.out.println("Questions file path: " + args[0]);
	System.out.println("Net file path: " + args[1]);
	System.out.println("Output file path: " + args[2]);

	GAlexaQuestionReader reader = new GAlexaQuestionReader();
	reader.xmlParse(args[0]);
	ArrayList<GAlexaQuestion> qlist = reader.getQuestionList();
	System.out.println("Question list size: "+ qlist.size());
	GAlexaExplanation.answerQuestions(args[1],args[2],qlist);	
  }
}
