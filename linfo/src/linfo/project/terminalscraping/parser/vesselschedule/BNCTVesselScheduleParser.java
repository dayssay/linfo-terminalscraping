package linfo.project.terminalscraping.parser.vesselschedule;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import linfo.project.terminalscraping.objects.VesselSchedule;
import linfo.project.terminalscraping.objects.VesselSchedule.VVD_STATUS;
import linfo.project.util.Util;

public class BNCTVesselScheduleParser extends VesselScheduleParser{
	
	@Override
	public void SetBerthInfo(String pHtml) {
		BufferedReader buffer;
        
        try{
            StringReader sr = new StringReader(pHtml);
				
            buffer = new BufferedReader(sr);
            
            String line;
            int iStart = 0;
            boolean bStart = false;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("CONTENTS")){
            		bStart = true;
            	}
            	
            	if(bStart){
            		if(line.contains("<table"))
            			iStart++;
            		
            		if(iStart == 3){
            			if(line.contains("td") && (line.contains("#F5F5F5") || line.contains("#EFFBF3") || line.contains("#FEDFDF"))){
            				
        					System.out.print(getBerthNo(line)+"*");
        					System.out.print(getOPR(buffer.readLine())+"*");
        					buffer.readLine();
        					System.out.print(getVVD(buffer.readLine())+"*");
        					String sINOUTVVD = buffer.readLine();
        					System.out.print(getINVVDforShippingCom(sINOUTVVD)+"*");
        					System.out.print(getOUTVVDforShippingCom(sINOUTVVD)+"*");
        					buffer.readLine();
        					buffer.readLine();
        					String sVVDInfo = buffer.readLine();
        					System.out.print(getVSLName(sVVDInfo)+"*");
        					System.out.print(getRoute(sVVDInfo)+"*");
        					System.out.print(getCCT(buffer.readLine())+"*");
        					System.out.print(getETB(buffer.readLine())+"*");
        					System.out.print(getETD(buffer.readLine())+"*");
        					String sJobCnt = buffer.readLine();
        					System.out.print(getLOADCnt(sJobCnt)+"*");
        					System.out.print(getDISCnt(sJobCnt)+"*");
        					System.out.print(getShiftCnt(sJobCnt)+"*");
        					buffer.readLine();
        					System.out.print(getVVDStatus(buffer.readLine())+"*");
        					System.out.println("");
            			}
            		}
            	}
            }
        }catch (Exception e){
        	Util.exceptionProc(e);
        } 
	}


	
	
	@Override
	public ArrayList<VesselSchedule> extractVesselSchedule(String html) {
		ArrayList<VesselSchedule> vesselScheduleList = new ArrayList<>();
		BufferedReader buffer;
        
        try
        {
            StringReader sr = new StringReader(html);
				
            buffer = new BufferedReader(sr);
            
            String line;
            int iStart = 0;
            boolean bStart = false;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("CONTENTS")){
            		bStart = true;
            	}
            	
            	if(bStart){
            		if(line.contains("<table"))
            			iStart++;
            		
            		if(iStart == 3){
            			if(line.contains("td") && (line.contains("#F5F5F5") || line.contains("#EFFBF3") || line.contains("#FEDFDF"))){
            				VesselSchedule vs = new VesselSchedule();
            				vs.setBerthNo(getBerthNo(line));
        					vs.setOpr(getOPR(buffer.readLine()));
        					buffer.readLine();
        					vs.setVvd(getVVD(buffer.readLine()));
        					String sINOUTVVD = buffer.readLine();
        					vs.setInVvdForShippingCom(getINVVDforShippingCom(sINOUTVVD));
        					vs.setOutVvdForShippingCom(getOUTVVDforShippingCom(sINOUTVVD));
        					buffer.readLine();
        					buffer.readLine();
        					String sVVDInfo = buffer.readLine();
        					vs.setVslName(getVSLName(sVVDInfo));
        					vs.setRoute(getRoute(sVVDInfo));
        					vs.setCct(getCCT(buffer.readLine()));
        					vs.setEtb(getETB(buffer.readLine()));
        					vs.setEtd(getETD(buffer.readLine()));
        					String sJobCnt = buffer.readLine();
        					vs.setLoadCnt(Integer.parseInt(getLOADCnt(sJobCnt)));
        					vs.setDisCnt(Integer.parseInt(getDISCnt(sJobCnt)));
        					vs.setShiftCnt(Integer.parseInt(getShiftCnt(sJobCnt)));
        					buffer.readLine();
        					String vvdStatus = removeTags(buffer.readLine()).trim();
        					if (vvdStatus.equals("DEPARTED")){
        						vs.setVvdStatus(VVD_STATUS.DEPARTED);
        						vs.setAtb(vs.getEtb());
        						vs.setAtd(vs.getEtd());
        						vs.setEtb("");
        						vs.setEtd("");
        					}else if (vvdStatus.equals("ARRIVED")){
        						vs.setVvdStatus(VVD_STATUS.BERTHING);
        						vs.setAtb(vs.getEtb());
        						vs.setEtb("");
        					}else if (vvdStatus.equals("PLANNED")){
        						vs.setVvdStatus(VVD_STATUS.PLANNED);
        					}else {
        						vs.setVvdStatus(VVD_STATUS.UNKNOWN);
        					}
        					vesselScheduleList.add(vs);
            			}
            		}
            	}
            }
        }catch (Exception e){
        	Util.exceptionProc(e);
        }
        
        return vesselScheduleList;
	}
	
	


	@Override
	protected String getINVVDforShippingCom(String pHtml) {
		String[] sTemp = pHtml.split("/");
		if(sTemp.length > 1){
			String[] sVVD = sTemp[0].trim().split("[(]");
			if(sVVD.length > 1)
				return sVVD[1];
			else
				return "";
		}
		else
			return "";
	}
	
	@Override
	protected String getOUTVVDforShippingCom(String pHtml) {
		String[] sTemp = pHtml.split("/");
		if(sTemp.length > 1){
			String[] sVVD = sTemp[1].trim().split("[)]");
			if(sVVD.length > 1)
				return sVVD[0];
			else
				return "";
		}
		else
			return "";
	}
	
	@Override
	protected String getVVD(String pHtml) {
		return pHtml.trim().substring(0, 4) + "-" + pHtml.trim().substring(4, 7);
	}
	
	@Override
	protected String getShiftCnt(String pHtml) {
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return ((sTemp[1].split("/"))[2].split("<"))[0].trim();
		else
			return "";
	}
	
	@Override
	protected String getRoute(String pHtml) {
		String[] sTemp = pHtml.split("[(]");
		if(sTemp.length > 1)
			return (sTemp[1].split("[)]"))[0];
		else
			return "";
	}
	
	@Override
	protected String getLOADCnt(String pHtml) {
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("/"))[1].trim();
		else
			return "";
	}
	
	@Override
	protected String getETB(String pHtml) {
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("<"))[0].replace("/", "").replace(" ", "").replace(":", "").substring(0, 12);
		else
			return "";
	}
	
	@Override
	protected String getDISCnt(String pHtml) {
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("/"))[0].trim();
		else
			return "";
	}
	
	@Override
	protected String getBerthNo(String pHtml) {
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return sTemp[1].substring(0, 1);
		else
			return "";
	}
	
	@Override
	protected String getATD(String pHtml) {
		return null;
	}
	
	@Override
	protected String getATB(String pHtml) {
		return null;
	}
}
