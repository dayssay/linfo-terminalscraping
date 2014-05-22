package linfo.project.terminalscraping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import oracle.jdbc.pool.OracleDataSource;
import linfo.project.terminalscraping.objects.TerminalWebSite;
import linfo.project.terminalscraping.objects.VesselSchedule;
import linfo.project.terminalscraping.scrapper.Scraper;
import linfo.project.terminalscraping.vesselschedule.parser.VesselScheduleParser;
import linfo.project.terminalscraping.vesselschedule.parser.VesselScheduleParserFactory;
import linfo.project.util.Util;
import static linfo.project.util.Util.*;

public class Driver implements Job {
	

	public void runScrapper() throws Exception{
		Scraper s = new Scraper();
		OracleDataSource ods = new OracleDataSource();
		ods.setURL("jdbc:oracle:thin:@hnctech73.iptime.org:1521:ora11g");
		ods.setUser("biguser");
		ods.setPassword("biguser");
		Connection conn = ods.getConnection();
		
		for(String item : s.getItems()){
			for(TerminalWebSite t : s.getTerminalList(item)){
				//각 터미널 ID에 맞는 VesselScheduleParser를 가져옴
				VesselScheduleParser parser = new VesselScheduleParserFactory().getParser(t.getId());
				
				if(t.getId().equals("KBCT") && parser != null){
					System.out.println("++++" + getSystemDateTime() + " : " + t.getId() + " Prcessed");
					String htmlCode = s.getHtml(t);
					
					String sql = "INSERT INTO T_RAW_HTML(SCRAP_DT, SCRAP_TM, TERMINAL_ID, HTML)"
								+ "VALUES(?, ?, ?, ?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, getSystemDate());
					pstmt.setString(2, getSystemTime());
					pstmt.setString(3, t.getId());
					pstmt.setString(4, htmlCode);
					pstmt.executeUpdate();
					
					for(VesselSchedule vs : parser.extractVesselSchedule(htmlCode)){
						sql = "INSERT INTO T_VESSEL_SCHEDULE_HISTORY "
								+ " (INS_DT, INS_TM, TERMINAL_ID, BERTH_NO, OPR"
								+ ", VVD, IN_VVD_OPR, OUT_VVD_OPR, VSL_NAME, ROUTE"
								+ ", CCT, ETB, ETD, ATB, ATD"
								+ ", LOAD_CNT, DIS_CNT, SHIFT_CNT, VVD_STATUS)"
								+ " VALUES(?, ?, ?, ?, ?"
								+ "        , ?, ?, ?, ?, ?"
								+ "        , ?, ?, ?, ?, ?"
								+ "        , ?, ?, ?, ?)";
						
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, getSystemDate());
						pstmt.setString(2, getSystemTime());
						pstmt.setString(3, t.getId());
						pstmt.setString(4, vs.getBerthNo());
						pstmt.setString(5, vs.getOpr());
						pstmt.setString(6, vs.getVvd());
						pstmt.setString(7, vs.getInVvdForShippingCom());
						pstmt.setString(8, vs.getOutVvdForShippingCom());
						pstmt.setString(9, vs.getVslName());
						pstmt.setString(10, vs.getRoute());
						pstmt.setString(11, vs.getCct());
						pstmt.setString(12, vs.getEtb());
						pstmt.setString(13, vs.getEtd());
						pstmt.setString(14, vs.getAtb());
						pstmt.setString(15, vs.getAtd());
						pstmt.setString(16, String.valueOf(vs.getLoadCnt()));
						pstmt.setString(17, String.valueOf(vs.getDisCnt()));
						pstmt.setString(18, String.valueOf(vs.getShiftCnt()));
						pstmt.setString(19, vs.getVvdStatus().name());
						pstmt.executeUpdate();
					}
		
				}
			}
		}
		
		conn.close();
	}
	
	
	public static void main(String[] args){
		try{
			SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
	
			Scheduler sched = schedFact.getScheduler();
			
			sched.start();
			JobDetail job = newJob(Driver.class).withIdentity("vesselScheduleJob", "vesselSchedule").build();
			Trigger trigger = newTrigger().withIdentity("vesselScheduleTrigger", "vesselSchedule").withSchedule(cronSchedule("0 0,30 * * * ?")).forJob("vesselScheduleJob", "vesselSchedule").build();
			
			sched.scheduleJob(job, trigger);
		}catch(Exception e){
			Util.exceptionProc(e);
		}
		
	}
	
	

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			runScrapper();
		} catch (Exception e) {
			Util.exceptionProc(e);
		}
	}
}
