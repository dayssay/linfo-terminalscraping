package linfo.project.terminalscraping;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
import linfo.project.terminalscraping.objects.VesselSchedule.VVD_STATUS;
import linfo.project.terminalscraping.scrapper.Scraper;
import linfo.project.terminalscraping.vesselschedule.parser.VesselScheduleParser;
import linfo.project.terminalscraping.vesselschedule.parser.VesselScheduleParserFactory;
import linfo.project.util.Util;
import static linfo.project.util.Util.*;

public class Driver implements Job {
	
	private final String insRawHtml = "INSERT INTO T_RAW_HTML(SCRAP_ITEM, SCRAP_DT, SCRAP_TM, SITE_ID, HTML) "
									+ " VALUES(?, ?, ?, ?, ?)";
	
	private final String insVslSchdlHis = "INSERT INTO T_VESSEL_SCHEDULE_HISTORY "
										+ " (INS_DT, INS_TM, TERMINAL_ID, BERTH_NO, OPR"
										+ ", VVD, IN_VVD_OPR, OUT_VVD_OPR, VSL_NAME, ROUTE"
										+ ", CCT, ETB, ETD, ATB, ATD"
										+ ", LOAD_CNT, DIS_CNT, SHIFT_CNT, VVD_STATUS)"
										+ " VALUES(?, ?, ?, ?, ?"
										+ "        , ?, ?, ?, ?, ?"
										+ "        , ?, ?, ?, ?, ?"
										+ "        , ?, ?, ?, ?)";
	
	private final String insVslSchd = "INSERT INTO T_VESSEL_SCHEDULE "
									+ " (TERMINAL_ID, VSL_COD, VVD, VVD_YEAR, OPR"
									+ " , IN_VVD_OPR, OUT_VVD_OPR, BERTH_NO, CCT, ETB"
									+ " , ETD, ATB, ATD, LOAD_CNT, DIS_CNT"
									+ " , SHIFT_CNT, VVD_STATUS, VSL_NAME, ROUTE)"
									+ " VALUES(?, ?, ?, ?, ?"
									+ "        , ?, ?, ?, ?, ?"
									+ "        , ?, ?, ?, ?, ?"
									+ "        , ?, ?, ?, ?)";

	private final String insVslSchdProc = "INSERT INTO T_VESSEL_SCHEDULE_PROC"
										+ " (TERMINAL_ID, VSL_COD, VVD, VVD_YEAR, VVD_STATUS)"
										+ " VALUES(?, ?, ?, ?, ?)";
	
	private final String updVslSchdProc = "UPDATE T_VESSEL_SCHEDULE_PROC"
										+ " SET VVD_STATUS = ?"
										+ " WHERE TERMINAL_ID = ?"
										+ "		AND VSL_COD = ?"
										+ "		AND VVD = ?";
	
	
	
	public void scrapVesselSchedule() throws Exception{
		Scraper s = new Scraper();
		OracleDataSource ods = new OracleDataSource();
		ods.setURL("jdbc:oracle:thin:@hnctech73.iptime.org:1521:ora11g");
		ods.setUser("biguser");
		ods.setPassword("biguser");
		Connection conn = ods.getConnection();
		
		for(String item : s.getItems()){
			if(item.equals("VesselSchedule")){
				Statement stmt = conn.createStatement();
				stmt.execute("DELETE T_VESSEL_SCHEDULE_PROC WHERE VVD_STATUS = '" + VVD_STATUS.DEPARTED.name() + "'");
				stmt.close();
				
				for(TerminalWebSite t : s.getTerminalList(item)){
					//각 터미널 ID에 맞는 VesselScheduleParser를 가져옴
					VesselScheduleParser parser = new VesselScheduleParserFactory().getParser(t.getTerminalId());
					
					if(parser != null){
						System.out.println("++++" + getSystemDateTime() + " : " + t.getId() + " Prcessed");
						String htmlCode = s.getHtml(t);
						
						PreparedStatement pstmt = conn.prepareStatement(this.insRawHtml);
						pstmt.setString(1, item);
						pstmt.setString(2, getSystemDate());
						pstmt.setString(3, getSystemTime());
						pstmt.setString(4, t.getId());
						pstmt.setString(5, htmlCode);
						pstmt.executeUpdate();
						pstmt.close();
						
						
						pstmt = conn.prepareStatement(this.insVslSchdlHis);
						
						PreparedStatement insVslSchdPstmt = conn.prepareStatement(this.insVslSchd);
						PreparedStatement insVslSchdProcPstmt = conn.prepareStatement(this.insVslSchdProc);
						PreparedStatement updVslSchdProcPstmt = conn.prepareStatement(this.updVslSchdProc);
						
						for(VesselSchedule vs : parser.extractVesselSchedule(htmlCode)){
//							System.out.println(t.getTerminalId()
//												+ " / " + vs.getBerthNo()
//												+ " / " + vs.getOpr()
//												+ " / " + vs.getVvd()
//												+ " / " + vs.getVvdYear()
//												+ " / " + vs.getInVvdForShippingCom()
//												+ " / " + vs.getOutVvdForShippingCom()
//												+ " / " + vs.getVslName()
//												+ " / " + vs.getRoute()
//												+ " / " + vs.getCct()
//												+ " / " + vs.getEtb()
//												+ " / " + vs.getEtd()
//												+ " / " + vs.getAtb()
//												+ " / " + vs.getAtd()
//												+ " / " + String.valueOf(vs.getLoadCnt())
//												+ " / " + String.valueOf(vs.getDisCnt())
//												+ " / " + String.valueOf(vs.getShiftCnt())
//												+ " / " + vs.getVvdStatus().name()
//												);
//							일단 히스토리 테이블에 먼저 저장
							pstmt.setString(1, getSystemDate());
							pstmt.setString(2, getSystemTime());
							pstmt.setString(3, t.getTerminalId());
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
							
							
//							기존에 등록된 모선 Update
							String updVslSchd = "UPDATE T_VESSEL_SCHEDULE";
							updVslSchd += " SET OPR = ?";
							updVslSchd += "		, IN_VVD_OPR = ?";
							updVslSchd += " 	, OUT_VVD_OPR = ?";
							updVslSchd += "		, BERTH_NO = ?";
							
							if (vs.getCct().trim().length() > 0){
								updVslSchd += "		, CCT = ?";
							}
							if (vs.getEtb().trim().length() > 0){
								updVslSchd += "		, ETB = ?";
							}
							if (vs.getEtd().trim().length() > 0){
								updVslSchd += "		, ETD = ?";
							}
							if (vs.getAtb().trim().length() > 0){
								updVslSchd += "		, ATB = ?";
							}
							if (vs.getAtd().trim().length() > 0){
								updVslSchd += "		, ATD = ?";
							}
							updVslSchd += "		, LOAD_CNT = ?";
							updVslSchd += "		, DIS_CNT = ?";
							updVslSchd += "		, SHIFT_CNT = ?";
							updVslSchd += "		, VVD_STATUS = ?";
							updVslSchd += "		, VSL_NAME = ?";
							updVslSchd += "		, ROUTE = ?";
							updVslSchd += " WHERE TERMINAL_ID = ?";
							updVslSchd += "		AND VSL_COD = ?";
							updVslSchd += "		AND VVD = ?";
							updVslSchd += "		AND VVD_YEAR = (SELECT VVD_YEAR FROM T_VESSEL_SCHEDULE_PROC WHERE TERMINAL_ID = ? AND VSL_COD = ? AND VVD = ?)";
							
							PreparedStatement updVslSchdPstmt = conn.prepareStatement(updVslSchd);
							int pstmtIndex = 0;
							pstmtIndex++;
							updVslSchdPstmt.setString(pstmtIndex, vs.getOpr().trim());
							pstmtIndex++;
							updVslSchdPstmt.setString(pstmtIndex, vs.getInVvdForShippingCom().trim());
							pstmtIndex++;
							updVslSchdPstmt.setString(pstmtIndex, vs.getOutVvdForShippingCom().trim());
							pstmtIndex++;
							updVslSchdPstmt.setString(pstmtIndex, vs.getBerthNo().trim());
							if (vs.getCct().trim().length() > 0){
								pstmtIndex++;
								updVslSchdPstmt.setString(pstmtIndex, vs.getCct().trim());
							}
							if (vs.getEtb().trim().length() > 0){
								pstmtIndex++;
								updVslSchdPstmt.setString(pstmtIndex, vs.getEtb());
							}
							if (vs.getEtd().trim().length() > 0){
								pstmtIndex++;
								updVslSchdPstmt.setString(pstmtIndex, vs.getEtd());
							}
							if (vs.getAtb().trim().length() > 0){
								pstmtIndex++;
								updVslSchdPstmt.setString(pstmtIndex, vs.getAtb());
							}
							if (vs.getAtd().trim().length() > 0){
								pstmtIndex++;
								updVslSchdPstmt.setString(pstmtIndex, vs.getAtd());
							}
							pstmtIndex++;
							updVslSchdPstmt.setInt(pstmtIndex, vs.getLoadCnt());
							pstmtIndex++;
							updVslSchdPstmt.setInt(pstmtIndex, vs.getDisCnt());
							pstmtIndex++;
							updVslSchdPstmt.setInt(pstmtIndex, vs.getShiftCnt());
							pstmtIndex++;
							updVslSchdPstmt.setString(pstmtIndex, vs.getVvdStatus().name());
							pstmtIndex++;
							updVslSchdPstmt.setString(pstmtIndex, vs.getVslName());
							pstmtIndex++;
							updVslSchdPstmt.setString(pstmtIndex, vs.getRoute());
							
							pstmtIndex++;
							updVslSchdPstmt.setString(pstmtIndex, t.getTerminalId());
							pstmtIndex++;
							updVslSchdPstmt.setString(pstmtIndex, vs.getVvd().split("-")[0]);
							pstmtIndex++;
							updVslSchdPstmt.setInt(pstmtIndex, Integer.parseInt(vs.getVvd().split("-")[1]));
							pstmtIndex++;
							updVslSchdPstmt.setString(pstmtIndex, t.getTerminalId());
							pstmtIndex++;
							updVslSchdPstmt.setString(pstmtIndex, vs.getVvd().split("-")[0]);
							pstmtIndex++;
							updVslSchdPstmt.setInt(pstmtIndex, Integer.parseInt(vs.getVvd().split("-")[1]));
							
							
							
							if (updVslSchdPstmt.executeUpdate() > 0) {
								updVslSchdProcPstmt.setString(1, vs.getVvdStatus().name());
								updVslSchdProcPstmt.setString(2, t.getTerminalId().trim());
								updVslSchdProcPstmt.setString(3, vs.getVvd().trim().split("-")[0]);
								updVslSchdProcPstmt.setInt(4, Integer.parseInt(vs.getVvd().trim().split("-")[1]));
								updVslSchdProcPstmt.executeUpdate();
								
							}else if (vs.getVvdStatus() != VVD_STATUS.DEPARTED){
								int year = Calendar.getInstance().get(Calendar.YEAR);
								
								if (vs.getEtb().trim().length() > 4){
									year = Integer.parseInt(vs.getEtb().trim().substring(0, 4));
								}else if (vs.getAtb().trim().length() > 4){
									year = Integer.parseInt(vs.getAtb().trim().substring(0, 4));
								}

//								t_vessel_schedule에 입력
								insVslSchdPstmt.setString(1, t.getTerminalId().trim());
								insVslSchdPstmt.setString(2, vs.getVvd().trim().split("-")[0]);
								insVslSchdPstmt.setInt(3, Integer.parseInt(vs.getVvd().trim().split("-")[1]));
								insVslSchdPstmt.setInt(4, year);
								insVslSchdPstmt.setString(5, vs.getOpr().trim());
								insVslSchdPstmt.setString(6, vs.getInVvdForShippingCom().trim());
								insVslSchdPstmt.setString(7, vs.getOutVvdForShippingCom().trim());
								insVslSchdPstmt.setString(8, vs.getBerthNo().trim());
								insVslSchdPstmt.setString(9, vs.getCct().trim());
								insVslSchdPstmt.setString(10, vs.getEtb().trim());
								insVslSchdPstmt.setString(11, vs.getEtd().trim());
								insVslSchdPstmt.setString(12, vs.getAtb().trim());
								insVslSchdPstmt.setString(13, vs.getAtd().trim());
								insVslSchdPstmt.setInt(14, vs.getLoadCnt());
								insVslSchdPstmt.setInt(15, vs.getDisCnt());
								insVslSchdPstmt.setInt(16, vs.getShiftCnt());
								insVslSchdPstmt.setString(17, vs.getVvdStatus().name());
								insVslSchdPstmt.setString(18, vs.getVslName().trim());
								insVslSchdPstmt.setString(19, vs.getRoute().trim());
								insVslSchdPstmt.executeUpdate();
								
//								t_vessel_schedule_proc에 입력
								insVslSchdProcPstmt.setString(1, t.getTerminalId().trim());
								insVslSchdProcPstmt.setString(2, vs.getVvd().trim().split("-")[0]);
								insVslSchdProcPstmt.setInt(3, Integer.parseInt(vs.getVvd().trim().split("-")[1]));
								insVslSchdProcPstmt.setInt(4, year);
								insVslSchdProcPstmt.setString(5, vs.getVvdStatus().name());
								insVslSchdProcPstmt.executeUpdate();
							}
							
							updVslSchdPstmt.close();
						}
						
						pstmt.close();
						updVslSchdProcPstmt.close();
						insVslSchdPstmt.close();
						insVslSchdProcPstmt.close();
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
			
//			Driver d = new Driver();
//			d.scrapVesselSchedule();
		}catch(Exception e){
			Util.exceptionProc(e);
		}
		
	}
	
	

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			scrapVesselSchedule();
		} catch (Exception e) {
			Util.exceptionProc(e);
		}
	}
}
