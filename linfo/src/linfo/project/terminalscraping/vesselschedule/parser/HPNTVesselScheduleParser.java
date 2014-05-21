package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import linfo.project.terminalscraping.objects.VesselSchedule;
import linfo.project.terminalscraping.objects.VesselSchedule.VVD_STATUS;
import linfo.project.util.Util;

public class HPNTVesselScheduleParser extends VesselScheduleParser{

	private String g_sBerth = "FFFAC4";
	private String g_sDepart = "e0e0e0";
	private String g_sPlan = "6FB7E9";
	private String g_sRealPlan = "9DCEFF";
	
	@Override
	public void SetBerthInfo(String pHtml){
        BufferedReader buffer;
        
        try
        {
            StringReader sr = new StringReader(pHtml);
            buffer = new BufferedReader(sr);
            
            String line;
            int iStart = 0;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains(g_sBerth) || line.contains(g_sDepart) || line.contains(g_sPlan) || line.contains(g_sRealPlan)){

            		if(iStart < 1){
            			buffer.readLine();
            			buffer.readLine();
            		}
            		
            		iStart++;
            		
            		if (iStart > 1){
            			buffer.readLine();
            			System.out.print(getVVDStatus(buffer.readLine())+"*");
            			buffer.readLine();
            			System.out.print(getBerthNo(buffer.readLine())+"*");
            			System.out.print(getOPR(buffer.readLine())+"*");
            			System.out.print(getVVD(buffer.readLine())+"*");
            			String sINOUTVVD = buffer.readLine();
    					System.out.print(getINVVDforShippingCom(sINOUTVVD)+"*");
    					System.out.print(getOUTVVDforShippingCom(sINOUTVVD)+"*");
    					System.out.print(getVSLName(buffer.readLine())+"*");
    					System.out.print(getCCT(buffer.readLine())+"*");
    					buffer.readLine();
    					buffer.readLine();
    					buffer.readLine();
    					System.out.print(getETB(buffer.readLine())+"*");
    					System.out.print(getETD(buffer.readLine())+"*");
    					String sJobCnt = buffer.readLine();
    					System.out.print(getDISCnt(sJobCnt)+"*");
    					System.out.print(getLOADCnt(sJobCnt)+"*");
    					System.out.print(getShiftCnt(sJobCnt)+"*");
    					System.out.print(getRoute(buffer.readLine())+"*");
    					System.out.println("");
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
        
        try{
            StringReader sr = new StringReader(html);
            buffer = new BufferedReader(sr);
            
            String line;
            int iStart = 0;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains(g_sBerth) || line.contains(g_sDepart) || line.contains(g_sPlan) || line.contains(g_sRealPlan)){

            		if(iStart < 1){
            			buffer.readLine();
            			buffer.readLine();
            		}
            		
            		iStart++;
            		
            		if (iStart > 1){
            			VesselSchedule vs = new VesselSchedule();
            			
            			buffer.readLine();
            			String vvdStatus = getVVDStatus(buffer.readLine());
            			if (vvdStatus.contains("DEPART")){
            				vs.setVvdStatus(VVD_STATUS.DEPARTED);
            			}else if (vvdStatus.contains("BERTH")){
            				vs.setVvdStatus(VVD_STATUS.BERTHING);
            			}else if (vvdStatus.contains("PLAN")){
            				vs.setVvdStatus(VVD_STATUS.PLANNED);
            			}else {
            				vs.setVvdStatus(VVD_STATUS.UNKNOWN);
            			}
            			
            			buffer.readLine();
            			vs.setBerthNo(getBerthNo(buffer.readLine()));
            			vs.setOpr(getOPR(buffer.readLine()));
            			vs.setVvd(getVVD(buffer.readLine()));
            			
            			String sINOUTVVD = buffer.readLine();
            			vs.setInVvdForShippingCom(getINVVDforShippingCom(sINOUTVVD));
            			vs.setOutVvdForShippingCom(getOUTVVDforShippingCom(sINOUTVVD));
            			vs.setVslName(getVSLName(buffer.readLine()));
            			vs.setCct(getCCT(buffer.readLine()));
    					buffer.readLine();
    					buffer.readLine();
    					buffer.readLine();
    					
    					if(vs.getVvdStatus() == VVD_STATUS.PLANNED){
    						vs.setEtb(getETB(buffer.readLine()));
    					}else if(vs.getVvdStatus() == VVD_STATUS.BERTHING || vs.getVvdStatus() == VVD_STATUS.DEPARTED){
    						vs.setAtb(getETB(buffer.readLine()));
    					}else{
    						vs.setEtb(getETB(buffer.readLine()));
    					}
    					
    					if(vs.getVvdStatus() == VVD_STATUS.PLANNED || vs.getVvdStatus() == VVD_STATUS.BERTHING){
    						vs.setEtd(getETD(buffer.readLine()));
    					}else if(vs.getVvdStatus() == VVD_STATUS.DEPARTED){
    						vs.setAtd(getETD(buffer.readLine()));
    					}else{
    						vs.setEtd(getETD(buffer.readLine()));
    					}
    					
    					String sJobCnt = buffer.readLine();
    					vs.setDisCnt(Integer.parseInt(getDISCnt(sJobCnt)));
    					vs.setLoadCnt(Integer.parseInt(getLOADCnt(sJobCnt)));
    					vs.setShiftCnt(Integer.parseInt(getShiftCnt(sJobCnt)));
    					vs.setRoute(getRoute(buffer.readLine()));
    					vesselScheduleList.add(vs);
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
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1){
			if(sTemp[1].indexOf("/") == 0)
				return "";
			else
				return ((sTemp[1].split("<"))[0].split("/"))[0];			
		}
		else
			return "";
	}
	
	@Override
	protected String getOUTVVDforShippingCom(String pHtml) {
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1){
			String[] sVVD = (sTemp[1].split("<"))[0].split("/");
			if(sVVD.length > 1)
				return sVVD[1].trim();
			else
				if(sTemp[1].indexOf("/") == 0)
					return sVVD[0];
				else
					return "";
		}
		else
			return "";
	}
	
	@Override
	protected String getVVDStatus(String pHtml) {
		return pHtml.trim();
	}
	
	
	protected String getShiftCnt(String pHtml) {
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return ((sTemp[1].split("<"))[0].split("/"))[2];
		else
			return "";
	}
	
	@Override
	protected String getRoute(String pHtml) {
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 2)
			return (sTemp[3].split("<"))[0];
		else
			return "";
	}
	
	@Override
	protected String getLOADCnt(String pHtml) {
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return ((sTemp[1].split("<"))[0].split("/"))[1];
		else
			return "";
	}
	
	@Override
	protected String getDISCnt(String pHtml) {
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return ((sTemp[1].split("<"))[0].split("/"))[0];
		else
			return "";
	}
	
	@Override
	protected String getBerthNo(String pHtml) {
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return sTemp[1].substring(0, 2);
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
