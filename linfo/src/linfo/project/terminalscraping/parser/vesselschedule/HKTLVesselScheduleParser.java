package linfo.project.terminalscraping.parser.vesselschedule;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import linfo.project.terminalscraping.objects.VesselSchedule;
import linfo.project.terminalscraping.objects.VesselSchedule.VVD_STATUS;
import linfo.project.util.Util;
import static linfo.project.util.Util.getSystemDateTime;

public class HKTLVesselScheduleParser extends VesselScheduleParser{
	@Override
	public void SetBerthInfo(String pHtml){
        BufferedReader buffer;
        
        try{
            StringReader sr = new StringReader(pHtml);
				
            buffer = new BufferedReader(sr);
            
            String line;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("list_td8")){
            		break;
            	}
            }
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("</tr>")){
            		break;
            	}
            }
            
            
//			<tr align="center" class="list_td8"> 
//			<td width=80 height=25>터미널</td> - 0
//			<td width=120 height=25>선사항차</td> - 1
//			<td width=50 height=25>선석</td> - 2
//			<td width=80 height=25>Bitt. F/A</td> - 3
//			<td width=105 height=25>접안예정<BR>일시</td> - 4
//			<td width=105 height=25>작업예정<BR>일시</td> - 5
//			<td width=105 height=25>출항예정<BR>일시</td> - 6
//			<td width=105 height=25>Closing Time</td> - 7
//			<td width=50 height=25>Port/ STBD</td> - 8
//			<td width=80 height=25>총물량 IN/OUT</td> - 9
//			<td width=150 height=25>선명</td> - 10
//			<td width=50 height=25>선사</td> - 11
//	</tr>
            while((line = buffer.readLine()) != null){
            	if(line.contains("</TABLE>")){
            		break;
            	}
            	
            	if(line.contains("<tr")){
            		int dataIndex = 0;
            		
            		while(!(line = buffer.readLine().trim()).contains("</tr")){
            			if(line.trim().contains("<td")){
	            			switch(dataIndex){
		            			case 0:
		            				System.out.print(this.getVVD(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 1:
		            				dataIndex++;
		            				break;
		            			case 2:
		            				System.out.print(this.getBerthNo(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 3:
		            				dataIndex++;
		            				break;
		            			case 4:
		            				System.out.print(this.getETB(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 5:
		            				dataIndex++;
		            				break;
		            			case 6:
		            				System.out.print(this.getETD(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 7:
		            				System.out.print(this.getCCT(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 8:
		            				dataIndex++;
		            				break;
		            			case 9:
		            				System.out.print(this.getLOADCnt(line) + "*");
		            				System.out.print(this.getDISCnt(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 10:
		            				System.out.print(this.getVSLName(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 11:
		            				System.out.print(this.getOPR(line) + "*");
		            				dataIndex++;
		            				break;
	            			}
            			}
            		}
            		System.out.println();
            		
            		
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
            
             while((line = buffer.readLine()) != null){
            	if(line.contains("list_td8")){
            		break;
            	}
            }
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("</tr>")){
            		break;
            	}
            }
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("</TABLE>")){
            		break;
            	}
            	
            	if(line.contains("<tr")){
            		int dataIndex = 0;
            		boolean isVessel = false;
            		VesselSchedule vs = new VesselSchedule();
            		
            		while(!(line = buffer.readLine().trim()).contains("</tr")){
            			if(line.trim().contains("<td")){
            				isVessel = true;
	            			switch(dataIndex){
		            			case 0:
		            				vs.setVvd(this.getVVD(line));
		            				dataIndex++;
		            				break;
		            			case 1:
		            				dataIndex++;
		            				break;
		            			case 2:
		            				vs.setBerthNo(this.getBerthNo(line));
		            				dataIndex++;
		            				break;
		            			case 3:
		            				dataIndex++;
		            				break;
		            			case 4:
		            				vs.setEtb(this.getETB(line));
		            				if (Long.parseLong(this.getETB(line).length() < 1 ? "999999999999":this.getETB(line)) <= Long.parseLong(getSystemDateTime().substring(0, 12))){
		            					vs.setVvdStatus(VVD_STATUS.BERTHING);
		            				}else{
		            					vs.setVvdStatus(VVD_STATUS.PLANNED);
		            				}
		            				dataIndex++;
		            				break;
		            			case 5:
		            				dataIndex++;
		            				break;
		            			case 6:
		            				vs.setEtd(this.getETD(line));
		            				if (Long.parseLong(this.getETD(line).length() < 1 ? "999999999999":this.getETD(line)) <= Long.parseLong(getSystemDateTime().substring(0, 12))){
		            					vs.setVvdStatus(VVD_STATUS.DEPARTED);
		            				}
		            				dataIndex++;
		            				break;
		            			case 7:
		            				vs.setCct(this.getCCT(line));
		            				dataIndex++;
		            				break;
		            			case 8:
		            				dataIndex++;
		            				break;
		            			case 9:
		            				vs.setLoadCnt(Integer.parseInt(this.getLOADCnt(line)));
		            				vs.setDisCnt(Integer.parseInt(this.getDISCnt(line)));
		            				dataIndex++;
		            				break;
		            			case 10:
		            				vs.setVslName(this.getVSLName(line));
		            				dataIndex++;
		            				break;
		            			case 11:
		            				vs.setOpr(this.getOPR(line));
		            				dataIndex++;
		            				break;
	            			}
            			}
            		}
            		if (isVessel){
            			if (vs.getVvdStatus() == VVD_STATUS.BERTHING || vs.getVvdStatus() == VVD_STATUS.DEPARTED){
            				vs.setAtb(vs.getEtb());
            				vs.setEtb("");
            			}
            			
            			if (vs.getVvdStatus() == VVD_STATUS.DEPARTED){
            				vs.setAtd(vs.getEtd());
            				vs.setEtd("");
            			}
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
		return super.removeTags(html).trim();
	}


	@Override
	protected String getOPR(String html) {
		return super.removeTags(html).trim();
	}


	@Override
	protected String getVVD(String html) {
		String tmpVvd = super.removeTags(html).trim();
		
		return tmpVvd.substring(0, 4) + "-" + String.format("%03d", Integer.parseInt(tmpVvd.substring(4, 6)));
	}


	@Override
	protected String getINVVDforShippingCom(String html) {
		return super.removeTags(html);
	}


	@Override
	protected String getOUTVVDforShippingCom(String html) {
		return super.removeTags(html);
	}


	@Override
	protected String getVSLName(String html) {
		return super.removeTags(html.trim());
	}


	@Override
	protected String getRoute(String html) {
		return super.removeTags(html);
	}


	@Override
	protected String getCCT(String html) {
		return !super.removeTags(html).trim().equals("")?super.removeTags(html).replace("/", "").replace(":", "").replace(" ", "").substring(0, 12):"";
	}


	@Override
	protected String getETB(String html) {
		return !super.removeTags(html).trim().equals("")?super.removeTags(html).replace("/", "").replace(":", "").replace(" ", "").substring(0, 12):"";
	}

	
	@Override
	protected String getETD(String html) {
		return !super.removeTags(html).trim().equals("")?super.removeTags(html).replace("/", "").replace(":", "").replace(" ", "").substring(0, 12):"";
	}


	@Override
	protected String getATB(String html) {
		return !super.removeTags(html).trim().equals("")?super.removeTags(html).replace("/", "").replace(":", "").replace(" ", "").substring(0, 12):"";
	}


	@Override
	protected String getATD(String html) {
		return !super.removeTags(html).trim().equals("")?super.removeTags(html).replace("/", "").replace(":", "").replace(" ", "").substring(0, 12):"";
	}


	@Override
	protected String getShiftCnt(String html) {
		return super.removeTags(html);
	}

	
	@Override
	protected String getVVDStatus(String html) {
		return super.removeTags(html);
	}


	@Override
	protected String getLOADCnt(String html) {
		String rawStr = removeTags(html);
		String[] loadNdis = rawStr.trim().split("/");
		String load = "0";
		
		if (loadNdis.length > 1){
			load = loadNdis[1];
		}
		
		return load;
	}

	
	@Override
	protected String getDISCnt(String html) {
		String rawStr = removeTags(html);
		String[] loadNdis = rawStr.trim().split("/");
		String dis = "0";
		
		if (loadNdis.length > 0){
			dis = loadNdis[0];
		}
		
		return dis;
	}
	
	
}
