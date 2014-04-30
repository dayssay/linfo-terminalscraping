package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;

public class BITVesselScheduleParser extends VesselScheduleParser{
	
	private String g_sBerth = "CC0033";		// 접안중
	private String g_sDepart = "0066CC";	// 출항
	private String g_sPlan = "333333";		// 접안전
	private String g_sCancel = "";			// 전배
	
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
            	if(line.contains("접안중") || line.contains("출항") || line.contains("접안예정")){

            		if(iStart < 1){
            			buffer.readLine();
            		}
            		
            		iStart++;
            		
            		if (iStart > 1)
            		{
            			buffer.readLine();
            			String sInfo = buffer.readLine();
            			System.out.print(getVVDStatus(sInfo)+"*");
            			System.out.print(getOPR(sInfo)+"*");
            			System.out.print(getVVD(sInfo)+"*");
            			
            			
            			buffer.readLine();
            			System.out.print(getVSLName(buffer.readLine())+"*");
            			
            			buffer.readLine();
            			String sChkEmptyLine = buffer.readLine();
            			if(sChkEmptyLine.trim().length() < 1){
            				System.out.print(getETB(buffer.readLine())+"*");
            			}
            			else
            				System.out.print(getETB(sChkEmptyLine)+"*");
            			
            			buffer.readLine();
            			System.out.print(getETD(buffer.readLine())+"*");
            			buffer.readLine();
            			System.out.print(getCCT(buffer.readLine())+"*");
            			
            			buffer.readLine();
            			System.out.print(getINVVDforShippingCom(buffer.readLine())+"*");
            			buffer.readLine();
    					System.out.print(getOUTVVDforShippingCom(buffer.readLine())+"*");
            			
    					buffer.readLine();
            			System.out.print(getBerthNo(buffer.readLine())+"*");
            			buffer.readLine();
    					System.out.print(getDISCnt(buffer.readLine())+"*");
    					buffer.readLine();
    					System.out.print(getLOADCnt(buffer.readLine())+"*");
    					buffer.readLine();
    					buffer.readLine();
    					buffer.readLine();
    					System.out.print(getRoute(buffer.readLine())+"*");
    					buffer.readLine();
    					buffer.readLine();
    					buffer.readLine();
    					buffer.readLine();
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
	protected String getBerthNo(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}
	
	@Override
	protected String getOPR(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		
		if((sTemp[4].split("<"))[0].equals("출항")){
			return (sTemp[9].split("<"))[0].trim();
		}else if((sTemp[4].split("<"))[0].equals("접안")){
			return (sTemp[9].split("'"))[0].trim();
		}else{
			return (sTemp[9].split("'"))[0].trim();
		}
	}

	@Override
	protected String getVVD(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		
		if((sTemp[4].split("<"))[0].equals("출항")){
			return (sTemp[23].split("<"))[0].trim();
		}else if((sTemp[4].split("<"))[0].equals("접안")){
			return (sTemp[10].split("<"))[0].trim();
		}else{
			return (sTemp[11].split("<"))[0].trim();
		}
	}

	@Override
	protected String getINVVDforShippingCom(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	@Override
	protected String getOUTVVDforShippingCom(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	@Override
	protected String getVSLName(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if((sTemp[4].split("<"))[0].equals("출항")){
			return (sTemp[23].split("<"))[0].trim();
		}else if((sTemp[4].split("<"))[0].equals("접안")){
			return (sTemp[12].split("<"))[0].trim();
		}else{
			return (sTemp[12].split("<"))[0].trim();
		}
	}

	@Override
	protected String getRoute(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 4)
			return (sTemp[4].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	@Override
	protected String getCCT(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	@Override
	protected String getETB(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	@Override
	protected String getETD(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	@Override
	protected String getATB(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	@Override
	protected String getATD(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	@Override
	protected String getLOADCnt(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	@Override
	protected String getDISCnt(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	@Override
	protected String getShiftCnt(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}

	@Override
	protected String getVVDStatus(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 4)
			return (sTemp[4].split("<"))[0].trim();
		else
			return "!!NONE!!";
	}
}
