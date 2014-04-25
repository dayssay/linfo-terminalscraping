package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;

public class HJNCVesselScheduleParser extends VesselScheduleParser{

	private String g_sBerth = "FFCCCC";
	private String g_sDepart = "CCFFCC";
	
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
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        } 
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
}
