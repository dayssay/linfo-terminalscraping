package linfo.project.terminalscraping.vesselschedule.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VesselScheduleParser{
	public void SetBerthInfo(String html){}
	
	
	
	
	private String getData(String html){
		String[] sTemp = html.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}
	
	
	public String removeTags(String html){
		String result = "";
		
		Pattern tag = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");  
		Matcher mat = tag.matcher(html);  
		result = mat.replaceAll(""); 
		
		return result;
	}

	// Berth Number
	protected String getBerthNo(String html) {
		return getData(html);
	}

	// Operator
	protected String getOPR(String html) {
		return getData(html);
	}

	// 항차
	protected String getVVD(String html) {
		return getData(html);
	}

	// 선사 In 항차
	protected String getINVVDforShippingCom(String html) {
		return getData(html);
	}

	// 선사 Out 항차
	protected String getOUTVVDforShippingCom(String html) {
		return getData(html);
	}

	// 선박명
	protected String getVSLName(String html) {
		return getData(html);
	}

	// Route
	protected String getRoute(String html) {
		return getData(html);
	}

	// Cargo Closing Time(반입 마감시간)
	protected String getCCT(String html) {
		return getData(html);
	}

	// Expected Time of Berthing(입항 예정시간)
	protected String getETB(String html) {
		return getData(html);
	}

	// Expected Time of Departure(출할 예정 시간)
	protected String getETD(String html) {
		return getData(html);
	}

	// Actual Time of Berthing(실제 입항 시간)
	protected String getATB(String html) {
		return getData(html);
	}

	// Actual Time of Departure(실제 출항 시간)
	protected String getATD(String html) {
		return getData(html);
	}

	// 선적 물량
	protected String getLOADCnt(String html) {
		return getData(html);
	}

	// 양하 물량
	protected String getDISCnt(String html) {
		return getData(html);
	}

	// Shifting 물량
	protected String getShiftCnt(String html) {
		return getData(html);
	}

	// 접안 상태(접안전, 접안중, 출항)
	protected String getVVDStatus(String html) {
		return getData(html);
	}
	
}
