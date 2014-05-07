package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import linfo.project.terminalscraping.objects.VesselSchedule;
import linfo.project.util.Util;

public class DPCTVesselScheduleParser extends VesselScheduleParser {
	@Override
	public void SetBerthInfo(String pHtml){
        BufferedReader buffer;
        
        try{
            StringReader sr = new StringReader(pHtml);
				
            buffer = new BufferedReader(sr);
            
            String line;
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("<td><font color=\"#368A94\"><b>Shift</b></font></td>")){
            		break;
            	}
            }
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("</tr>")){
            		break;
            	}
            }
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("</tr>")){
            		break;
            	}
            }
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("</tr>")){
            		break;
            	}
            }
            
//            <tr align="center" valign="bottom" bgcolor="#D2E8ED"> 
//            <td><font color="#368A94"><b>No</b></font></td> - 0
//            <td width="1"><img src="https://pus.dpct.co.kr/images/info/tab_bar_1.gif" width="1" height="25"></td> - 1
//            <td><font color="#368A94"><b>선 석</b></font></td> - 2
//            <td width="1"><img src="https://pus.dpct.co.kr/images/info/tab_bar_1.gif" width="1" height="25"></td> - 3
//            <td><font color="#368A94"><b>모선항차</b></font></td> - 4
//            <td width="1"><img src="https://pus.dpct.co.kr/images/info/tab_bar_1.gif" width="1" height="25"></td> - 5
//            <td><font color="#368A94"><b>선박명</b></font></td> - 6
//            <td width="1"><img src="https://pus.dpct.co.kr/images/info/tab_bar_1.gif" width="1" height="25"></td> - 7
//            <td><font color="#368A94"><b>접안(예정)일시</b></font></td> - 8
//            <td width="1"><img src="https://pus.dpct.co.kr/images/info/tab_bar_1.gif" width="1" height="25"></td> - 9
//            <td><font color="#368A94"><b>반입마감시간(종료)</b></font></td> - 10
//            <td width="1"><img src="https://pus.dpct.co.kr/images/info/tab_bar_1.gif" width="1" height="25"></td> - 11
//            <td><font color="#368A94"><b>출항(예정)일시</b></font></td> - 12
//            <td width="1"><img src="https://pus.dpct.co.kr/images/info/tab_bar_1.gif" width="1" height="25"></td> - 13
//            <td><font color="#368A94"><b>선 사</b></font></td> - 14
//            <td width="1"><img src="https://pus.dpct.co.kr/images/info/tab_bar_1.gif" width="1" height="25"></td> - 15    
//            <td><font color="#368A94"><b>Bitt</b></font></td> - 16
//            <td width="1"><img src="https://pus.dpct.co.kr/images/info/tab_bar_1.gif" width="1" height="25"></td> - 17    
//            <td><font color="#368A94"><b>양 하</b></font></td>     - 18
//            <td width="1"><img src="https://pus.dpct.co.kr/images/info/tab_bar_1.gif" width="1" height="25"></td> - 19
//            <td><font color="#368A94"><b>적 하</b></font></td> - 20    
//            <td width="1"><img src="https://pus.dpct.co.kr/images/info/tab_bar_1.gif" width="1" height="25"></td> - 21
//            <td><font color="#368A94"><b>Shift</b></font></td> - 22
//          </tr>
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("</TABLE>")){
            		break;
            	}
            	
            	if(line.contains("<tr")){
            		int dataIndex = 0;
            		
            		while(!(line = buffer.readLine().trim()).contains("</tr")){
            			if(line.trim().contains("<td") && !line.trim().contains("<td height=\"1\" colspan=\"23\" bgcolor=\"#CCCCCC\"></td>")){
            				String dt;
	            			switch(dataIndex){
		            			case 2:
		            				System.out.print(this.getBerthNo(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 4:
		            				System.out.print(this.getVVD(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 6:
		            				System.out.print(this.getVSLName(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 8:
		            				dt = this.removeTags(line);
		            				if (!dt.trim().equals("") && dt.trim().charAt(0) == '('){
		            					System.out.print(this.getETB(line) + "*");
		            				}else{
		            					System.out.print(this.getATB(line) + "*");
		            				}
		            				dataIndex++;
		            				break;
		            			case 10:
		            				dt = this.removeTags(line);
		            				if (!dt.trim().equals("") && dt.trim().charAt(0) != '('){
		            					System.out.print(this.getCCT(line) + "*");
		            				}
		            				dataIndex++;
		            				break;
		            			case 12:
		            				dt = this.removeTags(line);
		            				if (!dt.trim().equals("") && dt.trim().charAt(0) == '('){
		            					System.out.print(this.getETD(line) + "*");
		            				}else{
		            					System.out.print(this.getATD(line) + "*");
		            				}
		            				dataIndex++;
		            				break;
		            			case 14:
		            				System.out.print(this.getOPR(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 18:
		            				System.out.print(this.getDISCnt(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 20:
		            				System.out.print(this.getLOADCnt(line) + "*");
		            				dataIndex++;
		            				break;
		            			case 22:
		            				System.out.print(this.getShiftCnt(line) + "*");
		            				dataIndex++;
		            				break;
		            			default:
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
            	if(line.contains("<td><font color=\"#368A94\"><b>Shift</b></font></td>")){
            		break;
            	}
            }
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("</tr>")){
            		break;
            	}
            }
            
            while((line = buffer.readLine()) != null){
            	if(line.contains("</tr>")){
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
            			if(line.trim().contains("<td") && !line.trim().contains("<td height=\"1\" colspan=\"23\" bgcolor=\"#CCCCCC\"></td>")){
            				String dt;
            				isVessel = true;
	            			
            				switch(dataIndex){
		            			case 2:
		            				vs.setBerthNo(this.getBerthNo(line));
		            				dataIndex++;
		            				break;
		            			case 4:
		            				vs.setVvd(this.getVVD(line));
		            				dataIndex++;
		            				break;
		            			case 6:
		            				vs.setVslName(this.getVSLName(line));
		            				dataIndex++;
		            				break;
		            			case 8:
		            				dt = this.removeTags(line);
		            				if (!dt.trim().equals("") && dt.trim().charAt(0) == '('){
		            					vs.setEtb(this.getETB(line));
		            				}else{
		            					vs.setAtb(this.getATB(line));
		            				}
		            				dataIndex++;
		            				break;
		            			case 10:
		            				dt = this.removeTags(line);
		            				if (!dt.trim().equals("") && dt.trim().charAt(0) != '('){
		            					vs.setCct(this.getCCT(line));
		            				}
		            				dataIndex++;
		            				break;
		            			case 12:
		            				dt = this.removeTags(line);
		            				if (!dt.trim().equals("") && dt.trim().charAt(0) == '('){
		            					vs.setEtd(this.getETD(line));
		            				}else{
		            					vs.setAtd(this.getATD(line));
		            				}
		            				dataIndex++;
		            				break;
		            			case 14:
		            				vs.setOpr(this.getOPR(line));
		            				dataIndex++;
		            				break;
		            			case 18:
		            				vs.setDisCnt(Integer.parseInt(this.getDISCnt(line)));
		            				dataIndex++;
		            				break;
		            			case 20:
		            				vs.setLoadCnt(Integer.parseInt(this.getLOADCnt(line)));
		            				dataIndex++;
		            				break;
		            			case 22:
		            				vs.setShiftCnt(Integer.parseInt(this.getShiftCnt(line)));
		            				dataIndex++;
		            				break;
		            			default:
		            				dataIndex++;
		            				break;
	            			}
            			}
            		}
            		
            		if (isVessel){
            			vesselScheduleList.add(vs);
            		}
            	}
            }
        }catch (Exception e){
        	Util.exceptionProc(e);
        }
		
		return vesselScheduleList;
	}
	
	
	
}
