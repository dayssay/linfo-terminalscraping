package linfo.project.terminalscraping.vesselschedule.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import linfo.project.terminalscraping.objects.VesselSchedule;

public class VesselScheduleParser{
	public void SetBerthInfo(String html){}
	
	
	public ArrayList<VesselSchedule> extractVesselSchedule(String html){
		return new ArrayList<>();
	}
	
	
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

	// ����
	protected String getVVD(String html) {
		return getData(html);
	}

	// ���� In ����
	protected String getINVVDforShippingCom(String html) {
		return getData(html);
	}

	// ���� Out ����
	protected String getOUTVVDforShippingCom(String html) {
		return getData(html);
	}

	// ���ڸ�
	protected String getVSLName(String html) {
		return getData(html);
	}

	// Route
	protected String getRoute(String html) {
		return getData(html);
	}

	// Cargo Closing Time(���� �����ð�)
	protected String getCCT(String html) {
		if (getData(html).trim().equals("")){
			return "";
		}else{
			return getData(html).replace("/", "").replace(" ", "").replace(":", "").replace("(", "").replace(")", "").replace("-", "").substring(0, 12);
		}
	}

	// Expected Time of Berthing(���� �����ð�)
	protected String getETB(String html) {
		if (getData(html).trim().equals("")){
			return "";
		}else{
			return getData(html).replace("/", "").replace(" ", "").replace(":", "").replace("(", "").replace(")", "").replace("-", "").substring(0, 12);
		}
	}

	// Expected Time of Departure(���� ���� �ð�)
	protected String getETD(String html) {
		if (getData(html).trim().equals("")){
			return "";
		}else{
			return getData(html).replace("/", "").replace(" ", "").replace(":", "").replace("(", "").replace(")", "").replace("-", "").substring(0, 12);
		}
	}

	// Actual Time of Berthing(���� ���� �ð�)
	protected String getATB(String html) {
		if (getData(html).trim().equals("")){
			return "";
		}else{
			return getData(html).replace("/", "").replace(" ", "").replace(":", "").replace("(", "").replace(")", "").replace("-", "").substring(0, 12);
		}
	}

	// Actual Time of Departure(���� ���� �ð�)
	protected String getATD(String html) {
		if (getData(html).trim().equals("")){
			return "";
		}else{
			return getData(html).replace("/", "").replace(" ", "").replace(":", "").replace("(", "").replace(")", "").replace("-", "").substring(0, 12);
		}
	}

	// ���� ����
	protected String getLOADCnt(String html) {
		return getData(html);
	}

	// ���� ����
	protected String getDISCnt(String html) {
		return getData(html);
	}

	// Shifting ����
	protected String getShiftCnt(String html) {
		return getData(html);
	}

	// ���� ����(������, ������, ����)
	protected String getVVDStatus(String html) {
		return getData(html);
	}
	
}
