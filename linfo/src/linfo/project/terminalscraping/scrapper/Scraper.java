package linfo.project.terminalscraping.scrapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import linfo.project.terminalscraping.objects.TerminalWebSite;
import linfo.project.util.Util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Scraper {
	private final String ITEM_FILE_NAME = "scrap-list.xml";
	private final String TERMINAL_LIST_FILE_NAME = "terminal-list.xml";
	
	private HashMap<String, String>items;
	ArrayList<TerminalWebSite> terminalList;
	private String filePath;
	
	
	public Scraper(){
		this.filePath = Scraper.class.getProtectionDomain().getCodeSource().getLocation().getPath() 
				+ "/" + Scraper.class.getPackage().getName().replace(".", "/") + "/";
		
		items = new HashMap<>();
		terminalList = new ArrayList<>();
		this.setItems();
	}
	
	
	
	
	private void setItems(){
		try{
			Document itemDoc = Util.getXMLDocument(this.filePath + this.ITEM_FILE_NAME);;
			NodeList itemNode = itemDoc.getElementsByTagName("scrap");
			
			for(int i = 0; i < itemNode.getLength() ; i++){
				Element itemElement = (Element)itemNode.item(i);
				items.put(itemElement.getAttribute("item"), itemElement.getTextContent());
			}
		}catch(Exception e){
			Util.exceptionProc(e);
		}
	}
	
	
	
	
	public ArrayList<String> getItems(){
		return Util.getKeyList(this.items);
	}
	
	
	
	
	
	private void setTerminalList(String item){
		try{
			Document terminalDoc = Util.getXMLDocument(this.filePath + this.TERMINAL_LIST_FILE_NAME);;
			NodeList itemNode = terminalDoc.getElementsByTagName(this.items.get(item));
			
			NodeList terminalNode = ((Element)itemNode.item(0)).getElementsByTagName("terminal");
			
			
			for(int i = 0; i < terminalNode.getLength() ; i++){
				Element terminalElement = (Element)terminalNode.item(i);
				TerminalWebSite t = new TerminalWebSite();
				t.setId(terminalElement.getAttribute("id"));
				
				if(terminalElement.getElementsByTagName("name").getLength() > 0){
					Element nameElement = (Element)terminalElement.getElementsByTagName("name").item(0);
					t.setTerminalName(nameElement.getTextContent());
				}
				
				if(terminalElement.getElementsByTagName("url").getLength() > 0){
					Element urlElement = (Element)terminalElement.getElementsByTagName("url").item(0);
					t.setUrl(urlElement.getTextContent());
				}
				
				if(terminalElement.getElementsByTagName("encoding").getLength() > 0){
					Element encodingElement = (Element)terminalElement.getElementsByTagName("encoding").item(0);
					t.setEncoding(encodingElement.getTextContent());
				}
				
				if(terminalElement.getElementsByTagName("cookie-url").getLength() > 0){
					Element encodingElement = (Element)terminalElement.getElementsByTagName("cookie-url").item(0);
					t.setCookieUrl(encodingElement.getTextContent());
				}
				
				NodeList paramNode = terminalElement.getElementsByTagName("param");
				for(int j = 0; j < paramNode.getLength() ; j++){
					Element paramElement = (Element)paramNode.item(j);
					t.putParam(paramElement.getAttribute("key"), paramElement.getTextContent());
				}
				
				NodeList requestPropertyNode = terminalElement.getElementsByTagName("request-property");
				for(int j = 0; j < requestPropertyNode.getLength() ; j++){
					Element requestPropertyElement = (Element)requestPropertyNode.item(j);
					t.putRequestProperty(requestPropertyElement.getAttribute("key"), requestPropertyElement.getTextContent());
				}
				
				this.terminalList.add(t);
			}
			
		}catch(Exception e){
			Util.exceptionProc(e);
		}
	}
	
	
	
	
	public ArrayList<TerminalWebSite> getTerminalList(String item){
		this.setTerminalList(item);
		return this.terminalList;
	}
	

		
	
	public StringBuffer getHtml(TerminalWebSite terminal){
		StringBuffer html = new StringBuffer();
		URL url;
		String urlParameters = this.mapToUrlParamString(terminal.getParam());
	    HttpURLConnection connection = null;  
	    
	    try {
	    	url = new URL(terminal.getUrl());
	    	connection = (HttpURLConnection)url.openConnection();
	    	
	    	connection.setRequestMethod("POST");
	    	connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
	        
	        
	        if(terminal.getCookieUrl() != null && terminal.getCookieUrl().length() > 0){
	        	URL cookieUrl = new URL(terminal.getCookieUrl());
		    	HttpURLConnection cookieCon = (HttpURLConnection) cookieUrl.openConnection();
		    	cookieCon.connect();
		    	
		    	StringBuilder sb = new StringBuilder();

		    	// find the cookies in the response header from the first request
		    	List<String> cookies = cookieCon.getHeaderFields().get("Set-Cookie");
		    	if (cookies != null) {
		    	    for (String cookie : cookies) {
		    	        if (sb.length() > 0) {
		    	            sb.append("; ");
		    	        }

		    	        // only want the first part of the cookie header that has the value
		    	        String value = cookie.split(";")[0];
		    	        sb.append(value);
		    	    }
		    	}

		    	// build request cookie header to send on all subsequent requests
		    	String cookieHeader = sb.toString();
		    	cookieCon.disconnect();
		    	connection.setRequestProperty("Cookie", cookieHeader);
	        }
	        
	        
	    	ArrayList<String> requestParameters = Util.getKeyList(terminal.getReqeustProperty());
	    	for(String requestParameter : requestParameters){
	    		connection.setRequestProperty(requestParameter, terminal.getReqeustProperty().get(requestParameter));
	    	}
	        
	    	connection.setUseCaches (false);
	    	connection.setDoInput(true);
	    	connection.setDoOutput(true);

	    	//Send request
	    	DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
	    	wr.writeBytes(urlParameters);
	    	wr.flush();
	    	wr.close();

	    	//Get Response	
	    	InputStream is = connection.getInputStream();
	    	BufferedReader rd = null;
	    	rd = new BufferedReader(new InputStreamReader(is, terminal.getEncoding()));
	    	
	    	
	    	String line;
	    	
	    	while((line = rd.readLine()) != null) {
	    		html.append(line);
	    		html.append('\r');
	    	}
	    	rd.close();

	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return null;

	    } finally {
	    	if(connection != null) {
	    		connection.disconnect(); 
	    	}
	    }
	    
	    
		return html;
	}
	
	
	
	
	private String mapToUrlParamString(HashMap<String, String> m){
		String urlParameter = "";
		ArrayList<String> params = Util.getKeyList(m);
		
		int counter = 0;
		
		for(String param : params){
			counter++;
			urlParameter += param + "=" + m.get(param);
			if(counter < params.size()){
				urlParameter += "&";
			}
		}
		
		return urlParameter;
	}

}
