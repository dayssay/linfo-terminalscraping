package linfo.project.terminalscraping.containerinfo.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import linfo.project.terminalscraping.objects.ContainerInformation;

public class ContainerInfoParser {
	/**
	* html 코드에서 데이터를 추출하여 화면에 출력 한다.
	* @param html html 코드
	*/
	public void SetContainerInfo(String html){}
	
	
	/**
	* html 코드에서 데이터를 추출하여 변수에 담아 ArrayList 형태로 반환 한다.
	* @param html html 코드
	* @return ArrayList<ContainerInformation> 모선 입항 정보 ArrayList
	*/
	public ArrayList<ContainerInformation> extractContainerInformation(String html){
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

	protected String getTerminal(String html) {
		return getData(html).trim();
	}
	protected String getCntr_no(String html) {
		return getData(html).trim();
	}
	protected String getCntr_seq(String html) {
		return getData(html).trim();
	}
	protected String getVvd_year(String html) {
		return getData(html).trim();
	}
	protected String getVsl_cod(String html) {
		return getData(html).trim();
	}
	protected String getVsl_name(String html) {
		return getData(html).trim();
	}
	protected String getVvd(String html) {
		return getData(html).trim();
	}
	protected String getCls(String html) {
		return getData(html).trim();
	}
	protected String getSts(String html) {
		return getData(html).trim();
	}
	protected String getTs(String html) {
		return getData(html).trim();
	}
	protected String getOpr(String html) {
		return getData(html).trim();
	}
	protected String getOpr_name(String html) {
		return getData(html).trim();
	}
	protected String getCnl(String html) {
		return getData(html).trim();
	}
	protected String getSft(String html) {
		return getData(html).trim();
	}
	protected String getBundlt_sts(String html) {
		return getData(html).trim();
	}
	protected String getIso_siz(String html) {
		return getData(html).trim();
	}
	protected String getIso_typ(String html) {
		return getData(html).trim();
	}
	protected String getCntr_siz(String html) {
		return getData(html).trim();
	}
	protected String getCntr_typ(String html) {
		return getData(html).trim();
	}
	protected String getCargo_typ(String html) {
		return getData(html).trim();
	}
	protected String getCntr_foe(String html) {
		return getData(html).trim();
	}
	protected String getAct_opr(String html) {
		return getData(html).trim();
	}
	protected String getCom_opr(String html) {
		return getData(html).trim();
	}
	protected String getSeal1(String html) {
		return getData(html).trim();
	}
	protected String getSeal2(String html) {
		return getData(html).trim();
	}
	protected String getSeal3(String html) {
		return getData(html).trim();
	}
	protected String getSeal4(String html) {
		return getData(html).trim();
	}
	protected String getSeal5(String html) {
		return getData(html).trim();
	}
	protected String getCntr_wgt(String html) {
		return getData(html).trim();
	}
	protected String getDg_imdg(String html) {
		return getData(html).trim();
	}
	protected String getDg_cls(String html) {
		return getData(html).trim();
	}
	protected String getDg_unno(String html) {
		return getData(html).trim();
	}
	protected String getRf_temp(String html) {
		return getData(html).trim();
	}
	protected String getRf_cof(String html) {
		return getData(html).trim();
	}
	protected String getRf_conn_dte(String html) {
		return getData(html).trim();
	}
	protected String getYblk(String html) {
		return getData(html).trim();
	}
	protected String getYbay(String html) {
		return getData(html).trim();
	}
	protected String getYrow(String html) {
		return getData(html).trim();
	}
	protected String getYtir(String html) {
		return getData(html).trim();
	}
	protected String getVbay(String html) {
		return getData(html).trim();
	}
	protected String getVrow(String html) {
		return getData(html).trim();
	}
	protected String getVtir(String html) {
		return getData(html).trim();
	}
	protected String getPod(String html) {
		return getData(html).trim();
	}
	protected String getPol(String html) {
		return getData(html).trim();
	}
	protected String getCntr_insp(String html) {
		return getData(html).trim();
	}
	protected String getLeasing_term(String html) {
		return getData(html).trim();
	}
	protected String getCntr_hold(String html) {
		return getData(html).trim();
	}
	protected String getGate_in_dte(String html) {
		return getData(html).trim();
	}
	protected String getGate_out_dte(String html) {
		return getData(html).trim();
	}
	protected String getDischarge_dte(String html) {
		return getData(html).trim();
	}
	protected String getLoad_dte(String html) {
		return getData(html).trim();
	}
	protected String getStorage_term(String html) {
		return getData(html).trim();
	}
	protected String getStorage_freetime(String html) {
		return getData(html).trim();
	}
	protected String getBooking_no(String html) {
		return getData(html).trim();
	}
	protected String getDo_no(String html) {
		return getData(html).trim();
	}
	protected String getBl_no(String html) {
		return getData(html).trim();
	}
	protected String getSoc(String html) {
		return getData(html).trim();
	}
	protected String getSoc_shr(String html) {
		return getData(html).trim();
	}
	protected String getCargo_manage_no(String html) {
		return getData(html).trim();
	}
	protected String getOver_hgt(String html) {
		return getData(html).trim();
	}
	protected String getOver_wdthp(String html) {
		return getData(html).trim();
	}
	protected String getOver_wdths(String html) {
		return getData(html).trim();
	}
	protected String getOver_leng(String html) {
		return getData(html).trim();
	}
	protected String getFds(String html) {
		return getData(html).trim();
	}
	protected String getGte_lock(String html) {
		return getData(html).trim();
	}
	protected String getInput_psn(String html) {
		return getData(html).trim();
	}
	protected String getInput_dte(String html) {
		return getData(html).trim();
	}
	protected String getUpdate_psn(String html) {
		return getData(html).trim();
	}
	protected String getUpdate_dte(String html) {
		return getData(html).trim();
	}
	protected String getCntr_dlt(String html) {
		return getData(html).trim();
	}
	protected String getShr(String html) {
		return getData(html).trim();
	}
	protected String getFee(String html) {
		return getData(html).trim();
	}
	protected String getCustoms(String html) {
		return getData(html).trim();
	}
	protected String getCis(String html) {
		return getData(html).trim();
	}
	protected String getXray(String html) {
		return getData(html).trim();
	}
	protected String getRemark(String html) {
		return getData(html).trim();
	}
}
