package linfo.project.terminalscraping;

import linfo.project.terminalscraping.objects.TerminalWebSite;
import linfo.project.terminalscraping.scrapper.Scraper;
import linfo.project.terminalscraping.vesselschedule.parser.VesselScheduleParser;
import linfo.project.terminalscraping.vesselschedule.parser.VesselScheduleParserFactory;

public class Driver {
	public static void main(String[] args){
		Scraper s = new Scraper();
		
		for(String item : s.getItems()){
			for(TerminalWebSite t : s.getTerminalList(item)){
				//각 터미널 ID에 맞는 VesselScheduleParser를 가져옴
				VesselScheduleParser parser = new VesselScheduleParserFactory().getParser(t.getId());
				
				if(parser != null && t.getId().equals("PNIT")){
//					Scraper가 html 코드를 가져오고
//					Parser 각 html 코드로 부터 데이터를 뽑아옴
					System.out.println("=====================" + t.getId() + "=====================================");
//					System.out.println(s.getHtml(t).toString());
					parser.SetBerthInfo(s.getHtml(t).toString());
				}
			}
		}
	}
}
