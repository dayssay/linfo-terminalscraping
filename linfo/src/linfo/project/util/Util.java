package linfo.project.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class Util {
	
	/**
	* HashMap<String, String> 자료 구조로 부터 key 목록을 ArrayList<String> 형태로 추출 한다.
	* @param h <String, String> 타입의 HashMap
	* @return ArrayList<String> key 목록
	*/
	public static ArrayList<String> getKeyList(HashMap<String, String> h){
		ArrayList<String> keyList = new ArrayList<>();
		
		Object[] keys = h.keySet().toArray();
		
		for(int i =0 ; i < keys.length ; i++){
			keyList.add(keys[i].toString());
		}
		
		return keyList;
	}
	
	
	/**
	* XML File로 부터 Document 객체를 생성한다.
	* @param file file 명
	* @return Document
	*/
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
	
	
	
	/**
	* Exception 이 발생 했을때 처리 프로세스
	* @param e Exception
	*/
	public static void exceptionProc(Exception e){
		System.out.println(e.getMessage());
	}
}
