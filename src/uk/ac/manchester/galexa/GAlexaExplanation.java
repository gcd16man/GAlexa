package uk.ac.manchester.galexa;
/* 
 *  GAlexaExplanation.java
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;  
import java.io.IOException;
import norsys.netica.*;
     
public class GAlexaExplanation {

    private ArrayList<String> queries = new ArrayList<String>();
    private ArrayList<String> answers = new ArrayList<String>();
    private static HashMap<String,String> objects = null;   
    private static FileWriter outputFileWriter;

  public static void answerQuestions (String netPath,
				      String outputPath,
				      ArrayList<GAlexaQuestion> qlist){

      try {
	  outputFileWriter = new FileWriter(outputPath);
          System.out.println("Output file path:" + outputPath);
			     System.out.println("Net file path: " + netPath);
			     }
	  catch (IOException e) {
	      e.printStackTrace();
	  }
       try {
	      Environ env = new Environ (null);

	// Read in the net created by the BuildNet.java example program
	Net net = new Net (new Streamer (netPath));

for (int i = 0; i < qlist.size(); i++) {
    GAlexaQuestion q = qlist.get(i);
    String query = "";
    String answer = "";

    String predicate = q.getPredicate();
    boolean predicateExists = false;
    if (predicate !=null && !predicate.isEmpty()) {
	predicateExists = true;
	boolean subjectExists = false;
	String subject = q.getSubj();
	String subjState = q.getSubjState();
	boolean objectExists = false;
	if (subject !=null && !subject.isEmpty()) {
	    subjectExists = true;
	    Node subjectNode    = net.getNode( subject    );
	    	    query = "What is the " + predicate + " that " + subject 
			+ " is " + "\"" + subjState + "\""; 

	    //check for objects
	    objects = q.getObjects();
	    int k = 0;
	    if (objects.size()>0) {
		objectExists = true;
   for (Map.Entry<String, String> entry : objects.entrySet()) {
		String obj = entry.getKey();
		String state = (String) entry.getValue();
		if (obj !=null && !obj.isEmpty()
		    && state !=null && !state.isEmpty()){
		    k++;
		    Node objectNode    = net.getNode( obj    );
		    if (k<=1) {
		    query += " given that " + obj + " is " + "\"" + state + "\"";
		    } else if (k < objects.size()){
			query += ", " + obj + " is " + "\"" + state + "\"";
		    } else {
			query += " and " + obj + " is " + "\"" + state + "\"";	
		    } 
	    }
   }
	    }
	System.out.println("Original query: " + query + "?");
	outputFileWriter.write("Original query: " + query + "?"); 
	outputFileWriter.write(System.getProperty( "line.separator" ));  
	}
		    net.compile();

	if (predicate.equals("probability") && subjectExists && objectExists==false) {
	    	   Node subjectNode    = net.getNode( subject    );
		    double belief = subjectNode.getBelief (subjState);
		    answer = "The " + predicate + " that " + subject 
			+ " is " + "\"" + subjState  + "\"" + " is " + belief + ".";
		    System.out.println("Answer: " + answer);
		    outputFileWriter.write("Answer: " + answer);
		    outputFileWriter.write(System.getProperty( "line.separator" ));  
	}
	else if (predicate.equals("probability") && subjectExists && objectExists) {
	    Node subjectNode    = net.getNode( subject    );
	    answer = "The " + predicate + " that " + subject + " is " + "\"" + subjState + "\"";
	    int k = 0;
	    for (Map.Entry<String, String> entry : objects.entrySet()) {
			String obj = entry.getKey();
			String state = (String) entry.getValue();
			Node objectNode    = net.getNode( obj    );
			k++;
			objectNode.finding().enterState(state);
	
			if (k<=1) {
			    answer += " given that " + obj + " is " + "\"" + state + "\"";
			} else if (k < objects.size()){
			    answer += ", " + obj + " is " + "\"" + state + "\"";
			} else {
			    answer += " and " + obj + " is " + "\"" + state + "\"";	
		    }	
  }   
		    double belief = subjectNode.getBelief (subjState);
		
		    answer += " is " + belief + ".";
		    System.out.println("Answer: " + answer);
		    outputFileWriter.write("Answer: " + answer);
		    outputFileWriter.write(System.getProperty( "line.separator" ));  
	}
    }
    outputFileWriter.write(System.getProperty( "line.separator" ));  
}
outputFileWriter.close();

	net.finalize();  
}
    catch (Exception e) {
	e.printStackTrace();
    }
  }
}
