package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;

public class HKTLVesselScheduleParser extends VesselScheduleParser{
	@Override
	public void SetBerthInfo(String pHtml){
        BufferedReader buffer;
        
        try
        {
            StringReader sr = new StringReader(pHtml);
				
            buffer = new BufferedReader(sr);
            
            String line;
            
            boolean bStart = false;
            
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
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        } 
	}
	
	
	
	
	
	


	@Override
	protected String getBerthNo(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}


	@Override
	protected String getOPR(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}


	@Override
	protected String getVVD(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}


	@Override
	protected String getINVVDforShippingCom(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}


	@Override
	protected String getOUTVVDforShippingCom(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}


	@Override
	protected String getVSLName(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}


	@Override
	protected String getRoute(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}


	@Override
	protected String getCCT(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}


	@Override
	protected String getETB(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}

	
	@Override
	protected String getETD(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}


	@Override
	protected String getATB(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}


	@Override
	protected String getATD(String html) {
		// TODO Auto-generated method stub
		return super.removeTags(html);
	}


	@Override
	protected String getShiftCnt(String html) {
		// TODO Auto-generated method stub
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
