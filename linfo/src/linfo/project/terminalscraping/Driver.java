package linfo.project.terminalscraping;

import linfo.project.terminalscraping.objects.TerminalWebSite;
import linfo.project.terminalscraping.scrapper.Scraper;
import linfo.project.terminalscraping.vesselschedule.parser.VesselScheduleParser;
import linfo.project.terminalscraping.vesselschedule.parser.VesselScheduleParserFactory;

public class Driver {
	public static void main(String[] args){
		Scraper s = new Scraper();
		
		for(String item : s.getItems()){
			int i = 0;
			for(TerminalWebSite t : s.getTerminalList(item)){
				//�� �͹̳� ID�� �°� parser�� �����Ѵ�.
				VesselScheduleParser parser = new VesselScheduleParserFactory().getParser(t.getId());
				
				if(parser != null && ( t.getId().equals("HPNT") )){
//					html �ڵ带 �����ٰ�(s.getHtml)
//					�ļ��� �ְ� ����� ����Ѵ�.
					System.out.println("=====================" + t.getId() + "=====================================");
					System.out.println(s.getHtml(t).toString());
//					parser.SetBerthInfo(s.getHtml(t).toString());
				}
			}
		}
	}
}
