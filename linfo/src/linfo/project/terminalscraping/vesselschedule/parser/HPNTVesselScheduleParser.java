package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;

public class HPNTVesselScheduleParser extends VesselScheduleParser{

	private String g_sBerth = "FFFAC4";
	private String g_sDepart = "e0e0e0";
	private String g_sPlan = "6FB7E9";
	private String g_sRealPlan = "9DCEFF";
	
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
            		
            		if (iStart > 1)
            		{
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
				return sVVD[1].trim();
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
		return pHtml.trim();
	}
	
	
	protected String getShiftCnt(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return ((sTemp[1].split("<"))[0].split("/"))[2];
		else
			return "SHIFT";
	}
	
	@Override
	protected String getRoute(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 2)
			return (sTemp[3].split("<"))[0];
		else
			return "ROU";
	}
	
	@Override
	protected String getLOADCnt(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return ((sTemp[1].split("<"))[0].split("/"))[1];
		else
			return "LOAD";
	}
	
	@Override
	protected String getDISCnt(String pHtml) {
		// TODO Auto-generated method stub
		String[] sTemp = pHtml.split(">");
		if(sTemp.length > 1)
			return ((sTemp[1].split("<"))[0].split("/"))[0];
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
