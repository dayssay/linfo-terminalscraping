package linfo.project.terminalscraping.objects;

import java.util.HashMap;

// Terminal Web Site
public class TerminalWebSite {
	private String id;
	private String terminalName;
	private String url;
	private String encoding;
	private String cookieUrl;
	
//	url 과 함께 전송될 parameter
	private HashMap<String, String> param;
	
//	request 헤더에 작성될 값들 (쿠키, content type 같은 것들...)
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
	public String getCookieUrl() {
		return cookieUrl;
	}
	public void setCookieUrl(String cookieUrl) {
		this.cookieUrl = cookieUrl;
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
