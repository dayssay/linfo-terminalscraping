package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;

public class PNITVesselScheduleParser extends VesselScheduleParser{
	public void SetBerthInfo(String pHtml){
        BufferedReader buffer;
        
        try
        {
            StringReader sr = new StringReader(pHtml);
				
            buffer = new BufferedReader(sr);
            
            String line;
            int iStart = 0;
//            int iRow = 0;
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
        					System.out.print(getVSLName(buffer.readLine())+"*");
        					System.out.print(getRoute(buffer.readLine())+"*");
        					buffer.readLine();
        					buffer.readLine();
        					buffer.readLine();
        					buffer.readLine();
        					buffer.readLine();
        					buffer.readLine();
        					buffer.readLine();
        					buffer.readLine();
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
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        } 
	}
	
	protected String getINVVDforShippingCom(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split("/");
		if(sTemp.length > 1){
			String[] sVVD = sTemp[0].trim().split("[(]");
			if(sVVD.length > 1)
				return sVVD[1];
			else
				return "INVVD";
		}
		else
			return "INVVD";
	}
	
	protected String getOUTVVDforShippingCom(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split("/");
		if(sTemp.length > 1){
			String[] sVVD = sTemp[1].trim().split("[)]");
			if(sVVD.length > 1)
				return sVVD[0];
			else
				return "OUTVVD";
		}
		else
			return "OUTVVD";
	}
	
	
	protected String getVVD(String pHtml) {
		// TODO Auto-generated method stub
		return pHtml.trim();
	}
	
	
	
	protected String getShiftCnt(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return ((sTemp[1].split("/"))[2].split("<"))[0].trim();
		else
			return "SHIFT";
	}
	
	protected String getRoute(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split("[(]");
		if(sTemp.length > 1)
			return (sTemp[1].split("[)]"))[0];
		else
			return "ROU";
	}
	
	
	protected String getLOADCnt(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("/"))[1].trim();
		else
			return "LOAD";
	}
	
	
	
	protected String getDISCnt(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("/"))[0].trim();
		else
			return "DIS";
	}
	
	protected String getBerthNo(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return sTemp[1].substring(0, 2);
		else
			return "BNO";
	}
	
	
	protected String getATD(String pHtml) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected String getATB(String pHtml) {
		// TODO Auto-generated method stub
		return null;
	}
}
