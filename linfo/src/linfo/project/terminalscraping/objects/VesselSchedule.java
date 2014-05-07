package linfo.project.terminalscraping.objects;

public class VesselSchedule {
	public enum VVD_STATUS{
		UNKNOWN, PLANNED, BERTHING, DEPARTED
	}
	
//	선석 번호
	private String berthNo;

//	선사코드
	private String opr;
	
//	터미널항차
	private String vvd;
	
//	입항항차
	private String inVvdForShippingCom;
	
//	출항항차
	private String outVvdForShippingCom;
	
//	선명
	private String vslName;
	
//	route
	private String route;
	
//	반입 마감일시 (yyyymmddhh24mi 12자리)
	private String cct;
	
//	접안 예정 일시 (yyyymmddhh24mi 12자리)
	private String etb;
	
//	출항 예정 일시 (yyyymmddhh24mi 12자리)
	private String etd;
	
//	실 접안 일시 (yyyymmddhh24mi 12자리)
	private String atb;
	
//	실 출항 일시(yyyymmddhh24mi 12자리)
	private String atd;
	
//	적하 물량
	private int loadCnt;
	
//	양하 물량
	private int disCnt;
	
//	shifting 물량
	private int shiftCnt;
	
//	현 접안 상태
	private VVD_STATUS vvdStatus;
	
	
	public VesselSchedule(){
		
	}
	
	
	public VesselSchedule(String berthNo, String opr, String vvd,
			String inVvdForShippingCom, String outVvdForShippingCom,
			String vslName, String route, String cct, String etb, String etd,
			String atb, String atd, int loadCnt, int disCnt, int shiftCnt,
			VVD_STATUS vvdStatus) {
		super();
		this.berthNo = berthNo;
		this.opr = opr;
		this.vvd = vvd;
		this.inVvdForShippingCom = inVvdForShippingCom;
		this.outVvdForShippingCom = outVvdForShippingCom;
		this.vslName = vslName;
		this.route = route;
		this.cct = cct;
		this.etb = etb;
		this.etd = etd;
		this.atb = atb;
		this.atd = atd;
		this.loadCnt = loadCnt;
		this.disCnt = disCnt;
		this.shiftCnt = shiftCnt;
		this.vvdStatus = vvdStatus;
	}
	
	
	
	public String getBerthNo() {
		return berthNo;
	}
	public void setBerthNo(String berthNo) {
		this.berthNo = berthNo;
	}
	public String getOpr() {
		return opr;
	}
	public void setOpr(String opr) {
		this.opr = opr;
	}
	public String getVvd() {
		return vvd;
	}
	public void setVvd(String vvd) {
		this.vvd = vvd;
	}
	public String getInVvdForShippingCom() {
		return inVvdForShippingCom;
	}
	public void setInVvdForShippingCom(String inVvdForShippingCom) {
		this.inVvdForShippingCom = inVvdForShippingCom;
	}
	public String getOutVvdForShippingCom() {
		return outVvdForShippingCom;
	}
	public void setOutVvdForShippingCom(String outVvdForShippingCom) {
		this.outVvdForShippingCom = outVvdForShippingCom;
	}
	public String getVslName() {
		return vslName;
	}
	public void setVslName(String vslName) {
		this.vslName = vslName;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getCct() {
		return cct;
	}
	public void setCct(String cct) {
		this.cct = cct;
	}
	public String getEtb() {
		return etb;
	}
	public void setEtb(String etb) {
		this.etb = etb;
	}
	public String getEtd() {
		return etd;
	}
	public void setEtd(String etd) {
		this.etd = etd;
	}
	public String getAtb() {
		return atb;
	}
	public void setAtb(String atb) {
		this.atb = atb;
	}
	public String getAtd() {
		return atd;
	}
	public void setAtd(String atd) {
		this.atd = atd;
	}
	public int getLoadCnt() {
		return loadCnt;
	}
	public void setLoadCnt(int loadCnt) {
		this.loadCnt = loadCnt;
	}
	public int getDisCnt() {
		return disCnt;
	}
	public void setDisCnt(int disCnt) {
		this.disCnt = disCnt;
	}
	public int getShiftCnt() {
		return shiftCnt;
	}
	public void setShiftCnt(int shiftCnt) {
		this.shiftCnt = shiftCnt;
	}
	public VVD_STATUS getVvdStatus() {
		return vvdStatus;
	}
	public void setVvdStatus(VVD_STATUS vvdStatus) {
		this.vvdStatus = vvdStatus;
	}
	
	
	
}
