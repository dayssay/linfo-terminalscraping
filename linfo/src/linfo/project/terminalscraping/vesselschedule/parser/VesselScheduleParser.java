package linfo.project.terminalscraping.vesselschedule.parser;

public class VesselScheduleParser{
	public void SetBerthInfo(String html){}
	
	
	private String getData(String html){
		String[] sTemp = html.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	protected String getBerthNo(String html) {
		return getData(html);
	}

	protected String getOPR(String html) {
		return getData(html);
	}

	protected String getVVD(String html) {
		return getData(html);
	}

	protected String getINVVDforShippingCom(String html) {
		return getData(html);
	}

	protected String getOUTVVDforShippingCom(String html) {
		return getData(html);
	}

	protected String getVSLName(String html) {
		return getData(html);
	}

	protected String getRoute(String html) {
		return getData(html);
	}

	protected String getCCT(String html) {
		return getData(html);
	}

	protected String getETB(String html) {
		return getData(html);
	}

	protected String getETD(String html) {
		return getData(html);
	}

	protected String getATB(String html) {
		return getData(html);
	}

	protected String getATD(String html) {
		return getData(html);
	}

	protected String getLOADCnt(String html) {
		return getData(html);
	}

	protected String getDISCnt(String html) {
		return getData(html);
	}

	protected String getShiftCnt(String html) {
		return getData(html);
	}

	protected String getVVDStatus(String html) {
		return getData(html);
	}
	
}
