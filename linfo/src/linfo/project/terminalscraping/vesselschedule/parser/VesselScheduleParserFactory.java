package linfo.project.terminalscraping.vesselschedule.parser;

import linfo.project.util.Terminal;

public class VesselScheduleParserFactory {
	public VesselScheduleParser getParser(String id){
		VesselScheduleParser vsParser = null;
		
		switch (Terminal.valueOf(id)){
			case BIT:
				break;
			
			case BNCT:
				vsParser = new BNCTVesselScheduleParser();
				break;
			
			case DPCT:
				break;
			
			case HJNC:
				vsParser = new HJNCVesselScheduleParser();
				break;
			
			case HKTL:
				break;
			
			case PNC:
				vsParser = new PNCVesselScheduleParser();
				break;
			
			case PNIT:
				vsParser = new PNITVesselScheduleParser();
				break;
			
			case HPNT:
				vsParser = new HPNTVesselScheduleParser();
				break;
				
			case KBCT:
				break;
		}
		
		return vsParser;
	}
}
