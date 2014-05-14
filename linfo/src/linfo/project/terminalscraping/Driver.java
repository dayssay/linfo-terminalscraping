package linfo.project.terminalscraping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Formatter;

import oracle.jdbc.pool.OracleDataSource;
import linfo.project.terminalscraping.objects.TerminalWebSite;
import linfo.project.terminalscraping.scrapper.Scraper;
import linfo.project.terminalscraping.vesselschedule.parser.VesselScheduleParser;
import linfo.project.terminalscraping.vesselschedule.parser.VesselScheduleParserFactory;

public class Driver {
	public static void main(String[] args) throws Exception{
		Scraper s = new Scraper();
		OracleDataSource ods = new OracleDataSource();
		ods.setURL("jdbc:oracle:thin:@hnctech73.iptime.org:1521:ora11g");
		ods.setUser("biguser");
		ods.setPassword("biguser");
		Connection conn = ods.getConnection();
		Calendar c = Calendar.getInstance();
		
		for(String item : s.getItems()){
			for(TerminalWebSite t : s.getTerminalList(item)){
				//각 터미널 ID에 맞는 VesselScheduleParser를 가져옴
				VesselScheduleParser parser = new VesselScheduleParserFactory().getParser(t.getId());
				
				if(parser != null){
					String sql = "INSERT INTO T_RAW_HTML(SCRAP_DT, SCRAP_TM, TERMINAL_ID, HTML)"
								+ "VALUES(?, ?, ?, ?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					String dt = String.format("%04d", c.YEAR) 
							+ String.format("%02d", c.MONTH+1)
							+ String.format("%02d", c.DATE);
					String tm = String.format("%02", c.HOUR_OF_DAY)
							+ String.format("%02", c.MINUTE);
					
					
//					String a = String.format("%02d", 12);
					
					
					
					
					
//					Scraper가 html 코드를 가져오고
//					Parser 각 html 코드로 부터 데이터를 뽑아옴
//					System.out.println("=====================" + t.getId() + "=====================================");
//					System.out.println(s.getHtml(t));
//					parser.SetBerthInfo(s.getHtml(t));
				}
			}
		}
	}
}
