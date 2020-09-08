package uk.ac.manchester.galexa;
/* 
 *  GAlexaQuestion.java
 *
 */

import java.util.HashMap;

public class GAlexaQuestion {

    private String predicate = null;
    private String subject= null;
    private String subjectState = null;

    // structure is key = object name and value = state
    private HashMap<String,String> objects = new HashMap<String,String>(); 

    public void setPredicate(String p) {
	this.predicate = p;
    }

    public String getPredicate() {
    return this.predicate;
  }

    public void setSubj(String s) {
	this.subject = s;
    }

    public String getSubj() {
    return this.subject;
  }

    public void setSubjState(String s) {
	this.subjectState = s;
    }

    public String getSubjState() {
    return this.subjectState;
  }

    public void addObj(String o, String s) {
	this.objects.put(o,s);
    }

    public HashMap<String,String> getObjects() {
    return this.objects;
  }

}
