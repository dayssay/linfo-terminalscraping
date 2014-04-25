package linfo.project.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class Util {
	
	public static ArrayList<String> getKeyList(HashMap<String, String> h){
		ArrayList<String> keyList = new ArrayList<>();
		
		Object[] keys = h.keySet().toArray();
		
		for(int i =0 ; i < keys.length ; i++){
			keyList.add(keys[i].toString());
		}
		
		return keyList;
	}
	
	
	public static Document getXMLDocument(String file){
		Document doc = null;
		
		try{
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			File xmlFile = new File(file);
			doc = docBuilder.parse(xmlFile);
			
		}catch(Exception e){
			Util.exceptionProc(e);
		}
		
		return doc;
	}
	
	
	
	
	
	public static void exceptionProc(Exception e){
		System.out.println(e.getMessage());
	}
}
