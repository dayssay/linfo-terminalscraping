package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import linfo.project.terminalscraping.objects.VesselSchedule;
import linfo.project.util.Util;

public class HJNCVesselScheduleParser extends VesselScheduleParser{

	private String g_sBerth = "FFCCCC";
	private String g_sDepart = "CCFFCC";
	
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
        
        try
        {
            StringReader sr = new StringReader(html);
            buffer = new BufferedReader(sr);
            
            String line;
            int iStart = 0;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("#FFCCCC") || line.contains("#FFFFFF") || line.contains("#CCFFCC")){

            		iStart++;
            		
            		if (iStart > 3){
            			VesselSchedule vs = new VesselSchedule();
            			
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
    					vs.setEtb(getETB(buffer.readLine()));
    					buffer.readLine();
    					vs.setEtd(getETD(buffer.readLine()));
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
    					
    					vesselScheduleList.add(vs);
    					System.out.println("");
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
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split("#");
		if(sTemp.length > 1)
			if(sTemp[1].substring(0, 6).equals(g_sBerth))
				return "Berth";
			else if(sTemp[1].substring(0, 6).equals(g_sDepart))
				return "Depart";
			else
				return "Plan";
		else
			return "STS";
	}
	
	@Override
	protected String getBerthNo(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return sTemp[1].substring(0, 2);
		else
			return "BNO";
	}
	
	@Override
	protected String getATD(String pHtml) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected String getATB(String pHtml) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getShiftCnt(String html) {
		return super.getShiftCnt(html).equals("&nbsp;")?"0":super.getShiftCnt(html);
	}
	
	
}
