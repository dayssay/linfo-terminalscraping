package linfo.project.terminalscraping.parser.vesselschedule;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import linfo.project.terminalscraping.objects.VesselSchedule;
import linfo.project.terminalscraping.objects.VesselSchedule.VVD_STATUS;
import linfo.project.util.Util;

public class HJNCVesselScheduleParser extends VesselScheduleParser{

	private String g_sBerth = "FFCCCC";
	private String g_sDepart = "CCFFCC";
	
	@Override
	public void SetBerthInfo(String pHtml){
        BufferedReader buffer;
        
        try{
            StringReader sr = new StringReader(pHtml);
            buffer = new BufferedReader(sr);
            
            String line;
            int iStart = 0;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("#FFCCCC") || line.contains("#FFFFFF") || line.contains("#CCFFCC")){

            		iStart++;
            		
            		if (iStart > 3)
            		{
            			System.out.print(getVVD(line)+"*");
    					System.out.print(getVSLName(buffer.readLine())+"*");
    					buffer.readLine();
    					System.out.print(getOPR(buffer.readLine())+"*");
    					buffer.readLine();
    					System.out.print(getINVVDforShippingCom(buffer.readLine())+"*");
    					buffer.readLine();
    					System.out.print(getOUTVVDforShippingCom(buffer.readLine())+"*");
    					buffer.readLine();
    					System.out.print(getCCT(buffer.readLine())+"*");
    					buffer.readLine();
    					System.out.print(getETB(buffer.readLine())+"*");
    					buffer.readLine();
    					System.out.print(getETD(buffer.readLine())+"*");
    					buffer.readLine();
    					System.out.print(getDISCnt(buffer.readLine())+"*");
    					buffer.readLine();
    					System.out.print(getLOADCnt(buffer.readLine())+"*");
    					buffer.readLine();
    					System.out.print(getShiftCnt(buffer.readLine())+"*");
    					buffer.readLine();
    					System.out.print(getBerthNo(buffer.readLine())+"*");
    					buffer.readLine();
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
            	if(line.contains("#FFCCCC") || line.contains("#FFFFFF") || line.contains("#CCFFCC")){

            		iStart++;
            		
            		if (iStart > 3){
            			VesselSchedule vs = new VesselSchedule();
            			if(line.contains("#CCFFCC")){
            				vs.setVvdStatus(VVD_STATUS.DEPARTED);
            			}else if(line.contains("#FFCCCC")){
            				vs.setVvdStatus(VVD_STATUS.BERTHING);
            			}else if(line.contains("#FFFFFF")){
            				vs.setVvdStatus(VVD_STATUS.PLANNED);
            			}else{
            				vs.setVvdStatus(VVD_STATUS.UNKNOWN);
            			}
            			
            			vs.setVvd(getVVD(line));
            			vs.setVslName(getVSLName(buffer.readLine()));
            			buffer.readLine();
            			vs.setOpr(getOPR(buffer.readLine()));
    					buffer.readLine();
    					vs.setInVvdForShippingCom(getINVVDforShippingCom(buffer.readLine()));
    					buffer.readLine();
    					vs.setOutVvdForShippingCom(getOUTVVDforShippingCom(buffer.readLine()));
    					buffer.readLine();
    					vs.setCct(getCCT(buffer.readLine()));
    					buffer.readLine();
    					
    					if(vs.getVvdStatus() == VVD_STATUS.PLANNED){
	    					vs.setEtb(getETB(buffer.readLine()));
    					}else if(vs.getVvdStatus() == VVD_STATUS.BERTHING || vs.getVvdStatus() == VVD_STATUS.DEPARTED){
    						vs.setAtb(getETB(buffer.readLine()));
    					}else{
    						vs.setEtb(getETB(buffer.readLine()));
    					}
    					
    					buffer.readLine();
    					if(vs.getVvdStatus() == VVD_STATUS.PLANNED || vs.getVvdStatus() == VVD_STATUS.BERTHING){
    						vs.setEtd(getETD(buffer.readLine()));
    					}else if(vs.getVvdStatus() == VVD_STATUS.DEPARTED){
    						vs.setAtd(getETD(buffer.readLine()));
    					}else{
    						vs.setEtd(getETD(buffer.readLine()));
    					}
    					
    					buffer.readLine();
    					vs.setDisCnt(Integer.parseInt(getDISCnt(buffer.readLine())));
    					buffer.readLine();
    					vs.setLoadCnt(Integer.parseInt(getLOADCnt(buffer.readLine())));
    					buffer.readLine();
    					vs.setShiftCnt(Integer.parseInt(getShiftCnt(buffer.readLine())));
    					buffer.readLine();
    					vs.setBerthNo(getBerthNo(buffer.readLine()));
    					buffer.readLine();
    					vs.setRoute(getRoute(buffer.readLine()));
    					
    					if (vs.getVvd().trim().length() > 0){
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
	protected String getVVDStatus(String pHtml) {
		String[] sTemp = pHtml.split("#");
		if(sTemp.length > 1)
			if(sTemp[1].substring(0, 6).equals(g_sBerth))
				return "Berth";
			else if(sTemp[1].substring(0, 6).equals(g_sDepart))
				return "Depart";
			else
				return "Plan";
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

	@Override
	protected String getShiftCnt(String html) {
		return super.getShiftCnt(html).equals("&nbsp;")?"0":super.getShiftCnt(html);
	}
	
	
}
