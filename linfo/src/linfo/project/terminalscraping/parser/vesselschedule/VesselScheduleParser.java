package linfo.project.terminalscraping.parser.vesselschedule;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import linfo.project.terminalscraping.objects.VesselSchedule;

public class VesselScheduleParser{
	/**
	* html 코드에서 데이터를 추출하여 화면에 출력 한다.
	* @param html html 코드
	*/
	public void SetBerthInfo(String html){}
	
	
	/**
	* html 코드에서 데이터를 추출하여 변수에 담아 ArrayList 형태로 반환 한다.
	* @param html html 코드
	* @return ArrayList<VesselSchedule> 모선 입항 정보 ArrayList
	*/
	public ArrayList<VesselSchedule> extractVesselSchedule(String html){
		return new ArrayList<>();
	}
	
	
	/**
	* html 코드에서 데이터를 추출하여 반환한다.
	* @param html html 코드
	* @return String html 태그 사이의 값
	*/
	private String getData(String html){
		String[] sTemp = html.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("<"))[0].replace("&nbsp;", "").trim();
		else
			return "";
	}
	
	/**
	* html 코드에서 테그를 모두 제거 하여 반환한다.
	* @param html html 코드
	* @return String html 태그 가 모두 제거된 값
	*/
	public String removeTags(String html){
		String result = "";
		
		Pattern tag = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");  
		Matcher mat = tag.matcher(html);  
		result = mat.replaceAll(""); 
		return result.replace("&nbsp;", "");
	}

	/**
	* 선석 번호를 추출한다.
	* @param html html 코드
	* @return String 선석 번호
	*/
	protected String getBerthNo(String html) {
		return getData(html).trim();
	}

	/**
	* 선사코드를 추출한다.
	* @param html html 코드
	* @return String 선사 코드
	*/
	protected String getOPR(String html) {
		return getData(html).trim();
	}

	/**
	* 모선항차를 추출한다.
	* @param html html 코드
	* @return String 모선항차
	*/
	protected String getVVD(String html) {
		return getData(html).trim();
	}

	/**
	* 선사 입항항차를 추출한다.
	* @param html html 코드
	* @return String 선사 입항항차
	*/
	protected String getINVVDforShippingCom(String html) {
		return getData(html).trim();
	}

	/**
	* 선사 출항항차를 추출한다.
	* @param html html 코드
	* @return String 선사 출항항차
	*/
	protected String getOUTVVDforShippingCom(String html) {
		return getData(html).trim();
	}

	/**
	* 선명을 추출한다.
	* @param html html 코드
	* @return String 선명
	*/
	protected String getVSLName(String html) {
		return getData(html).trim();
	}

	/**
	* Route를 추출한다.
	* @param html html 코드
	* @return String Route
	*/
	protected String getRoute(String html) {
		return getData(html).trim();
	}

	/**
	* 임항 마감일시를 추출한다.
	* @param html html 코드
	* @return String 입항 마감일시 (12자리)
	*/
	protected String getCCT(String html) {
		if (getData(html).trim().equals("")){
			return "";
		}else{
			return getData(html).replace("/", "").replace(" ", "").replace(":", "").replace("(", "").replace(")", "").replace("-", "").substring(0, 12).trim();
		}
	}

	/**
	* 접안 예쩡일시를 추출한다.
	* @param html html 코드
	* @return String 접안 예정일시 (12자리)
	*/
	protected String getETB(String html) {
		if (getData(html).trim().equals("")){
			return "";
		}else{
			return getData(html).replace("/", "").replace(" ", "").replace(":", "").replace("(", "").replace(")", "").replace("-", "").substring(0, 12).trim();
		}
	}

	/**
	* 출항 예정일시를 추출한다.
	* @param html html 코드
	* @return String 출항 예정일시 (12자리)
	*/
	protected String getETD(String html) {
		if (getData(html).trim().equals("")){
			return "";
		}else{
			return getData(html).replace("/", "").replace(" ", "").replace(":", "").replace("(", "").replace(")", "").replace("-", "").substring(0, 12).trim();
		}
	}

	/**
	* 실제 접안일시를 추출한다.
	* @param html html 코드
	* @return String 실제 접안일시 (12자리)
	*/
	protected String getATB(String html) {
		if (getData(html).trim().equals("")){
			return "";
		}else{
			return getData(html).replace("/", "").replace(" ", "").replace(":", "").replace("(", "").replace(")", "").replace("-", "").substring(0, 12).trim();
		}
	}

	/**
	* 실제 출항일시를 추출한다.
	* @param html html 코드
	* @return String 실제 출항일시 (12자리)
	*/
	protected String getATD(String html) {
		if (getData(html).trim().equals("")){
			return "";
		}else{
			return getData(html).replace("/", "").replace(" ", "").replace(":", "").replace("(", "").replace(")", "").replace("-", "").substring(0, 12).trim();
		}
	}

	/**
	* 적하 작업 수량을 추출한다.
	* @param html html 코드
	* @return String 적하 작업 수량
	*/
	protected String getLOADCnt(String html) {
		return getData(html).trim().equals("") ? "0":getData(html).trim();
	}

	/**
	* 양하 작업 수량을 추출한다.
	* @param html html 코드
	* @return String 양하 작업 수량
	*/
	protected String getDISCnt(String html) {
		return getData(html).trim().equals("") ? "0":getData(html).trim();
	}

	/**
	* Shifting 작업 수량을 추출한다.
	* @param html html 코드
	* @return String Shifting 작업 수량
	*/
	protected String getShiftCnt(String html) {
		return getData(html).trim().equals("") ? "0":getData(html).trim();
	}

	/**
	* 모선 접안 상태를 추출한다.
	* @param html html 코드
	* @return String 모선 접안 상태
	*/
	protected String getVVDStatus(String html) {
		return getData(html).trim();
	}
	
}
