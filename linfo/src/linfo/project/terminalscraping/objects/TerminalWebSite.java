package linfo.project.terminalscraping.objects;

import java.util.HashMap;

// Terminal Web Site
// KSC Comment
public class TerminalWebSite {
	private String id;
	private String terminalName;
	private String url;
	private String encoding;
	private HashMap<String, String> param;
	private HashMap<String, String> reqeustProperty;
	
	
	
	public TerminalWebSite(){
		this.param = new HashMap<String, String>();
		this.reqeustProperty = new HashMap<String, String>();
		
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTerminalName() {
		return terminalName;
	}
	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public HashMap<String, String> getParam() {
		return param;
	}
	public void setParam(HashMap<String, String> param) {
		this.param = param;
	}
	public void putParam(String key, String value){
		this.param.put(key, value);
	}
	public HashMap<String, String> getReqeustProperty() {
		return reqeustProperty;
	}
	public void setReqeustProperty(HashMap<String, String> reqeustProperty) {
		this.reqeustProperty = reqeustProperty;
	}
	public void putRequestProperty(String key, String value){
		this.reqeustProperty.put(key, value);
	}
	
	
}
