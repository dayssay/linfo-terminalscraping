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
//    				<th>����</th> getBerthNo 
//    				<th>������</th> getVVD 
//    				<th>���ڸ�</th> getVSLName 
//    				<th>����</th> getVVDStatus 
//    				<th>����</th> getOPR 
//    				<th>���� �����Ͻ�</th> getETB 
//    				<th>�����Ͻ�</th> getATB
//    				<th>���� �����Ͻ�</th> getETD 
//    				<th>�����Ͻ�</th> getATD
//    				<th>���� �����Ͻ�</th> getCCT 
//    				<th>����</th> getDISCnt 
//    				<th>����</th> getLOADCnt 
//    				<th>S/H</th>	getShiftCnt 
//    				<th>����</th>
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
