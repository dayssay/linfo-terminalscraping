package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;

public class PNITVesselScheduleParser extends VesselScheduleParser{
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
	protected String getVVD(String pHtml) {
		// TODO Auto-generated method stub
		return pHtml.trim();
	}
	
	@Override	
	protected String getShiftCnt(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return ((sTemp[1].split("/"))[2].split("<"))[0].trim();
		else
			return "SHIFT";
	}
	
	@Override
	protected String getRoute(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split("[(]");
		if(sTemp.length > 1)
			return (sTemp[1].split("[)]"))[0];
		else
			return "ROU";
	}
	
	@Override
	protected String getLOADCnt(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("/"))[1].trim();
		else
			return "LOAD";
	}
	
	@Override
	protected String getDISCnt(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("/"))[0].trim();
		else
			return "DIS";
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
}
