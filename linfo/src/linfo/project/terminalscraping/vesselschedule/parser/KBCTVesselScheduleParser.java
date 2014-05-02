package linfo.project.terminalscraping.vesselschedule.parser;

import java.io.BufferedReader;
import java.io.StringReader;

public class KBCTVesselScheduleParser extends VesselScheduleParser{
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
            	if(line.contains("<tbody>")){
            		bStart = true;
            	}
            	
            	if(line.contains("</tbody>")){
            		break;
            	}
            	
//            	<tr>
//    				<th>선석</th> getBerthNo 
//    				<th>모선항차</th> getVVD 
//    				<th>선박명</th> getVSLName 
//    				<th>접안</th> getVVDStatus 
//    				<th>선사</th> getOPR 
//    				<th>입항 예정일시</th> getETB 
//    				<th>입항일시</th> getATB
//    				<th>출항 예정일시</th> getETD 
//    				<th>출항일시</th> getATD
//    				<th>반입 마감일시</th> getCCT 
//    				<th>양하</th> getDISCnt 
//    				<th>선적</th> getLOADCnt 
//    				<th>S/H</th>	getShiftCnt 
//    				<th>전배</th>
//    			</tr>
            	
            	if(bStart){
            		int iStart = 0;
            		if(line.contains("<td"))
            			iStart++;
    			
            		if(iStart > 0){
            			System.out.print(getBerthNo(line) + "*");
            			System.out.print(getVVD(buffer.readLine()) + "*");
            			System.out.print(getVSLName(buffer.readLine()) + "*");
            			System.out.print(getVVDStatus(buffer.readLine()) + "*");
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
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        } 
	}
}
