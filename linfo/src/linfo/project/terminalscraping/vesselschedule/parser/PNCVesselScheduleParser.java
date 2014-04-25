package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;

public class PNCVesselScheduleParser extends VesselScheduleParser{

	private String g_sBerth = "DEF2FC";
	private String g_sDepart = "F2E3ED";
	private String g_sPlan = "FFFFFF";
	
	public void SetBerthInfo(String pHtml){
        BufferedReader buffer;
        
        try
        {
            StringReader sr = new StringReader(pHtml);
            buffer = new BufferedReader(sr);
            
            String line;
            int iStart = 0;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains(g_sBerth) || line.contains(g_sDepart) || line.contains(g_sPlan)){

            		if(iStart < 2){
            			buffer.readLine();
            			buffer.readLine();
            		}
            		
            		iStart++;
            		
            		if (iStart > 2)
            		{
            			System.out.print(getVVDStatus(line)+"*");
            			if(line.contains(g_sBerth)){
            				buffer.readLine();
            				buffer.readLine();
            			}
            				
            			if(line.contains(g_sDepart))
            				buffer.readLine();
            			
            			buffer.readLine();		// Empty Space..
            			buffer.readLine();		// No.
    					System.out.print(getVSLName(buffer.readLine())+"*");
    					System.out.print(getVVD(buffer.readLine())+"*");
    					String sINOUTVVD = buffer.readLine();
    					System.out.print(getINVVDforShippingCom(sINOUTVVD)+"*");
    					System.out.print(getOUTVVDforShippingCom(sINOUTVVD)+"*");
    					System.out.print(getOPR(buffer.readLine())+"*");
    					System.out.print(getRoute(buffer.readLine())+"*");
    					buffer.readLine();		// Berthing Direction
    					System.out.print(getETB(buffer.readLine())+"*");
    					System.out.print(getETD(buffer.readLine())+"*");
    					System.out.print(getBerthNo(buffer.readLine())+"*");
    					System.out.print(getCCT(buffer.readLine())+"*");
    					System.out.print(getDISCnt(buffer.readLine())+"*");
    					System.out.print(getLOADCnt(buffer.readLine())+"*");
    					System.out.println("");
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
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1){
			if(sTemp[1].indexOf("/") == 0)
				return "";
			else
				return ((sTemp[1].split("<"))[0].split("/"))[0];			
		}
		else
			return "INVVD";
	}
	
	@Override
	protected String getOUTVVDforShippingCom(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1){
			String[] sVVD = (sTemp[1].split("<"))[0].split("/");
			if(sVVD.length > 1)
				return sVVD[1];
			else
				if(sTemp[1].indexOf("/") == 0)
					return sVVD[0];
				else
					return "";
		}
		else
			return "OUTVVD";
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
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0];
		else
			return "ROU";
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
