package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import linfo.project.terminalscraping.objects.VesselSchedule;
import linfo.project.terminalscraping.objects.VesselSchedule.VVD_STATUS;
import linfo.project.util.Util;

public class BITVesselScheduleParser extends VesselScheduleParser{
	
	@Override
	public void SetBerthInfo(String pHtml){
        BufferedReader buffer;
        
        try{
            StringReader sr = new StringReader(pHtml);
            buffer = new BufferedReader(sr);
            
            String line;
            int iStart = 0;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("출항") || line.contains("접안중") || line.contains("접안예정")){
            		if(iStart < 1){
            			buffer.readLine();
            		}
            		
            		iStart++;
            		
            		if (iStart > 1){
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
            	if(line.contains("출항") || line.contains("접안중") || line.contains("접안예정")){
            		if(iStart < 1){
            			buffer.readLine();
            		}
            		
            		iStart++;
            		
            		if (iStart > 1){
            			VesselSchedule vs = new VesselSchedule();
            			
            			buffer.readLine();
            			String sInfo = buffer.readLine();
            			
            			if (getVVDStatus(sInfo).equals("출항")){
            				vs.setVvdStatus(VVD_STATUS.DEPARTED);
            			}else if (getVVDStatus(sInfo).equals("접안")){
            				vs.setVvdStatus(VVD_STATUS.BERTHING);
            			}else if (getVVDStatus(sInfo).contains("접안예정")){
            				vs.setVvdStatus(VVD_STATUS.PLANNED);
            			}else{
            				vs.setVvdStatus(VVD_STATUS.UNKNOWN);
            			}
            			
            			vs.setOpr(getOPR(sInfo));
            			vs.setVvd(getVVD(sInfo));
            			
            			buffer.readLine();
            			vs.setVslName(getVSLName(buffer.readLine()));
            			            			
            			buffer.readLine();
            			String sChkEmptyLine = buffer.readLine();
            			if(sChkEmptyLine.trim().length() < 1){
            				if(vs.getVvdStatus() == VVD_STATUS.PLANNED){
            					vs.setEtb(getETB(buffer.readLine()));
            				}else{
            					vs.setAtb(getETB(buffer.readLine()));
            				}
            			}else{
            				if(vs.getVvdStatus() == VVD_STATUS.PLANNED){
            					vs.setEtb(getETB(sChkEmptyLine));
            				}else{
            					vs.setAtb(getETB(sChkEmptyLine));
            				}
            			}
            				
            			
            			buffer.readLine();
            			if(vs.getVvdStatus() == VVD_STATUS.DEPARTED){
            				vs.setAtd(getETD(buffer.readLine()));
        				}else{
        					vs.setEtd(getETD(buffer.readLine()));
        				}
            			buffer.readLine();
            			vs.setCct(getCCT(buffer.readLine()));
            			
            			buffer.readLine();
            			vs.setInVvdForShippingCom(getINVVDforShippingCom(buffer.readLine()));
            			buffer.readLine();
            			vs.setOutVvdForShippingCom(getOUTVVDforShippingCom(buffer.readLine()));
    					buffer.readLine();
    					vs.setBerthNo(getBerthNo(buffer.readLine()).substring(0, 1));
            			buffer.readLine();
            			vs.setDisCnt(Integer.parseInt(getDISCnt(buffer.readLine())));
    					buffer.readLine();
    					vs.setLoadCnt(Integer.parseInt(getLOADCnt(buffer.readLine())));
    					buffer.readLine();
    					buffer.readLine();
    					buffer.readLine();
    					vs.setRoute(getRoute(buffer.readLine()));
    					buffer.readLine();
    					buffer.readLine();
    					buffer.readLine();
    					buffer.readLine();
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
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "";
	}

	@Override
	protected String getOUTVVDforShippingCom(String html) {
		// TODO Auto-generated method stub
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "";
	}

	@Override
	protected String getVSLName(String html) {
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
		String[] sTemp = html.split(">");
		if(sTemp.length > 4)
			return (sTemp[4].split("<"))[0].trim();
		else
			return "";
	}

	@Override
	protected String getCCT(String html) {
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim().replace("/", "").replace(" ", "").replace(":", "").substring(0, 12);
		else
			return "";
	}

	@Override
	protected String getETB(String html) {
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim().replace("-", "").replace(" ", "").replace(":", "").substring(0, 12);
		else
			return "";
	}

	@Override
	protected String getETD(String html) {
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim().replace("-", "").replace(" ", "").replace(":", "").substring(0, 12);
		else
			return "";
	}

	@Override
	protected String getATB(String html) {
		String[] sTemp = html.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("<"))[0].trim().replace("-", "").replace(" ", "").replace(":", "").substring(0, 12);
		else
			return "";
	}

	@Override
	protected String getATD(String html) {
		String[] sTemp = html.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("<"))[0].trim().replace("-", "").replace(" ", "").replace(":", "").substring(0, 12);
		else
			return "";
	}

	@Override
	protected String getLOADCnt(String html) {
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "";
	}

	@Override
	protected String getDISCnt(String html) {
		String[] sTemp = html.split(">");
		if(sTemp.length > 2)
			return (sTemp[2].split("<"))[0].trim();
		else
			return "";
	}

	@Override
	protected String getShiftCnt(String html) {
		String[] sTemp = html.split(">");
		if(sTemp.length > 1)
			return (sTemp[1].split("<"))[0].trim();
		else
			return "";
	}

	@Override
	protected String getVVDStatus(String html) {
		String[] sTemp = html.split(">");
		if(sTemp.length > 4)
			return (sTemp[4].split("<"))[0].trim();
		else
			return "";
	}
}
