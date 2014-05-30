package linfo.project.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
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
//		System.out.println(e.getMessage());
		e.printStackTrace();
	}
	
	
	/**
	* 현재 시스템의 날자를 반환한다
	* @return yyyymmdd
	*/
	public static String getSystemDate(){
		Calendar c = Calendar.getInstance();
		return String.format("%04d", c.get(Calendar.YEAR)) 
				+ String.format("%02d", c.get(Calendar.MONTH) + 1)
				+ String.format("%02d", c.get(Calendar.DAY_OF_MONTH));
	}
	
	
	/**
	* 며칠 후 의 날자를 반환한다
	* @return yyyymmdd
	*/
	public static String getDateAfter(int amount){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, amount);
		return String.format("%04d", c.get(Calendar.YEAR)) 
				+ String.format("%02d", c.get(Calendar.MONTH) + 1)
				+ String.format("%02d", c.get(Calendar.DAY_OF_MONTH));
	}
	
	
	
	/**
	* 며칠 후 의 날자 요소 (년, 월, 일)을 반환한다
	* @param field Calendar.YEAR 또는 Calendar.MONTH 또는 Calendar.DAY_OF_MONTH
	* @param amount 며칠 후 를 조회 할 것인가
	* @return yyyy 또는 mm 또는 dd
	*/
	public static String getDateAfter(int field, int amount){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, amount);
		
		if (field == Calendar.YEAR){
			return String.format("%04d", c.get(Calendar.YEAR));
		}else if (field == Calendar.MONTH){
			return String.format("%02d", c.get(Calendar.MONTH) + 1);
		}else if (field == Calendar.DAY_OF_MONTH){
			return String.format("%02d", c.get(Calendar.DAY_OF_MONTH));
		}else{
			return String.format("%04d", c.get(Calendar.YEAR)) 
					+ String.format("%02d", c.get(Calendar.MONTH) + 1)
					+ String.format("%02d", c.get(Calendar.DAY_OF_MONTH));
		}
		
	}
	
	
	
	/**
	* 며칠 후 의 날자를 반환한다
	* @param separator 년, 월, 일 구분자
	* @param amount 며칠 후 를 조회 할 것인가
	* @return yyyy (separator) mm (separator) dd 의 형태(separator로 년,월,일 을 구분함)
	*/
	public static String getDateAfter(String separator, int amount){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, amount);
		return String.format("%04d", c.get(Calendar.YEAR)) 
				+ separator + String.format("%02d", c.get(Calendar.MONTH) + 1)
				+ separator + String.format("%02d", c.get(Calendar.DAY_OF_MONTH));
	}
	
	
	/**
	* 현재 시스템의 시간을 반환한다
	* @return hh24miss
	*/
	public static String getSystemTime(){
		Calendar c = Calendar.getInstance();
		return String.format("%02d", c.get(Calendar.HOUR_OF_DAY))
				+ String.format("%02d", c.get(Calendar.MINUTE))
				+ String.format("%02d", c.get(Calendar.SECOND));
	}
	
	
	/**
	* 현재 시스템의 날자와 시간을 반환한다
	* @return yyyymmddhh24miss
	*/
	public static String getSystemDateTime(){
		Calendar c = Calendar.getInstance();
		return String.format("%04d", c.get(Calendar.YEAR)) 
				+ String.format("%02d", c.get(Calendar.MONTH) + 1)
				+ String.format("%02d", c.get(Calendar.DAY_OF_MONTH))
				+ String.format("%02d", c.get(Calendar.HOUR_OF_DAY))
				+ String.format("%02d", c.get(Calendar.MINUTE))
				+ String.format("%02d", c.get(Calendar.SECOND));
	}
	
}
