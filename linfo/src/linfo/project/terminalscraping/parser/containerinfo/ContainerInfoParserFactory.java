package linfo.project.terminalscraping.parser.containerinfo;

import linfo.project.util.Terminal;

public class ContainerInfoParserFactory {
	public ContainerInfoParser getParser(String id){
		ContainerInfoParser vsParser = null;
		
		switch (Terminal.valueOf(id)){
			case BIT:
				vsParser = new BITContainerInfoParser();
				break;
			
			case BNCT:
				vsParser = new BNCTContainerInfoParser();
				break;
			
			case DPCT:
				vsParser = new DPCTContainerInfoParser();
				break;
			
			case HJNC:
				vsParser = new HJNCContainerInfoParser();
				break;
			
			case HKTL:
				vsParser = new HKTLContainerInfoParser();
				break;
			
			case PNC:
				vsParser = new PNCContainerInfoParser();
				break;
			
			case PNIT:
				vsParser = new PNITContainerInfoParser();
				break;
			
			case HPNT:
				vsParser = new HPNTContainerInfoParser();
				break;
				
			case KBCT:
				vsParser = new KBCTContainerInfoParser();
				break;
		}
		
		return vsParser;
	}
}
