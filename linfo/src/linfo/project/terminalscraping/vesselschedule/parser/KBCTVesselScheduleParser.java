package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import linfo.project.terminalscraping.objects.VesselSchedule;
import linfo.project.terminalscraping.objects.VesselSchedule.VVD_STATUS;
import linfo.project.util.Util;

public class KBCTVesselScheduleParser extends VesselScheduleParser{
	@Override
	public void SetBerthInfo(String pHtml){
        BufferedReader buffer;
        
        try{
            StringReader sr = new StringReader(pHtml);
				
            buffer = new BufferedReader(sr);
            
            String line;
            
            boolean bStart = false;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("<tbody>")){
            		bStart = true;
            	}
            	
            	if(line.contains("</tbody>")){
            		break;
            	}
            	
            	if(bStart){
            		int iStart = 0;
            		if(line.contains("<td"))
            			iStart++;
    			
            		if(iStart > 0){
            			System.out.print(getBerthNo(line) + "*");
            			System.out.print(getVVD(buffer.readLine()) + "*");
            			System.out.print(getVSLName(buffer.readLine()) + "*");
            			buffer.readLine();
//            			System.out.print(getVVDStatus(buffer.readLine()) + "*");
            			System.out.print(getOPR(buffer.readLine()) + "*");
            			System.out.print(getETB(buffer.readLine()) + "*");
            			System.out.print(getATB(buffer.readLine()) + "*");
            			System.out.print(getETD(buffer.readLine()) + "*");
            			System.out.print(getATD(buffer.readLine()) + "*");
            			System.out.print(getCCT(buffer.readLine()) + "*");
            			System.out.print(getDISCnt(buffer.readLine()) + "*");
            			System.out.print(getLOADCnt(buffer.readLine()) + "*");
            			System.out.print(getShiftCnt(buffer.readLine()) + "*");
            			buffer.readLine();
            			
            			System.out.println("");
            			iStart = 0;
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
            
            boolean bStart = false;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("<tbody>")){
            		bStart = true;
            	}
            	
            	if(line.contains("</tbody>")){
            		break;
            	}
            	            	
            	if(bStart){
            		int iStart = 0;
            		if(line.contains("<td"))
            			iStart++;
    			
            		if(iStart > 0){
            			VesselSchedule vs = new VesselSchedule();
            			
            			vs.setBerthNo(getBerthNo(line));
            			vs.setVvd(getVVD(buffer.readLine()));
            			vs.setVslName(getVSLName(buffer.readLine()));
            			buffer.readLine();
            			vs.setOpr(getOPR(buffer.readLine()));
            			vs.setEtb(getETB(buffer.readLine()));
            			vs.setAtb(getATB(buffer.readLine()));
            			vs.setEtd(getETD(buffer.readLine()));
            			vs.setAtd(getATD(buffer.readLine()));
            			vs.setCct(getCCT(buffer.readLine()));
            			vs.setDisCnt(Integer.parseInt(getDISCnt(buffer.readLine())));
            			vs.setLoadCnt(Integer.parseInt(getLOADCnt(buffer.readLine())));
            			vs.setShiftCnt(Integer.parseInt(getShiftCnt(buffer.readLine())));
            			buffer.readLine();
            			
            			if(vs.getEtb() != "" && vs.getEtd() != "" && vs.getAtb() != "" && vs.getAtd() != ""){
                			vs.setVvdStatus(VVD_STATUS.DEPARTED);
                		}else if(vs.getEtb() != "" && vs.getEtd() != "" && vs.getAtb() != "" && vs.getAtd() == ""){
                			vs.setVvdStatus(VVD_STATUS.BERTHING);
                		}else if(vs.getEtb() != "" && vs.getEtd() != "" && vs.getAtb() == "" && vs.getAtd() == ""){
                			vs.setVvdStatus(VVD_STATUS.PLANNED);
                		}else{
                			vs.setVvdStatus(VVD_STATUS.UNKNOWN);
                		}
            			
            			vesselScheduleList.add(vs);
            			iStart = 0;
            		}
            	}
            }
        }catch (Exception e){
        	Util.exceptionProc(e);
        } 
        
		return vesselScheduleList;
	}
	
	
	
}
