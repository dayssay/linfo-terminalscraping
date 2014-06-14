package linfo.project.terminalscraping.containerinfo.parser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import linfo.project.terminalscraping.objects.ContainerInformation;
import linfo.project.util.Util;

public class DPCTContainerInfoParser extends ContainerInfoParser{

	private static final int CNTR_NO_TITLE = 0;
	private static final int CNTR_NO = 1;
	private static final int SIZE_TYPE_TITLE = 2;
	private static final int SIZE_TYPE = 3;
	private static final int VVD_TITLE = 4;
	private static final int VVD = 5;
	private static final int VSL_NAME = 6;
	private static final int OPR_TITLE = 7;
	private static final int OPR = 8;
	private static final int OPR_NAME = 9;
	private static final int STATUS_TITLE = 10;
	private static final int STATUS = 11;
	private static final int TS_TITLE = 12;
	private static final int TS = 13;
	private static final int RETURN_CANCEL_TITLE = 14;
	private static final int RETURN_CANCEL = 15;
	private static final int SEAL_NO_TITLE = 16;
	private static final int SEAL_NO = 17;
	private static final int TRUCK_NO_TITLE = 18;
	private static final int TRUCK_NO = 19;
	private static final int WEIGHT_TITLE = 20;
	private static final int WEIGHT = 21;
	private static final int IMDG_TITLE = 22;
	private static final int IMDG = 23;
	private static final int REEFER_TITLE = 24;
	private static final int REEFER = 25;
	private static final int VSL_LOCATION_TITLE = 26;
	private static final int VSL_LOCATION = 27;
	private static final int YARD_LOCATION_TITLE = 28;
	private static final int YARD_LOCATION = 29;
	private static final int POD_POL_TITLE = 30;
	private static final int POD_POL = 31;
	private static final int CNTR_INSP_TITLE = 32;
	private static final int CNTR_INSP = 33;
	private static final int ETB_TITLE = 34;
	private static final int ETB = 35;
	private static final int ETD_TITLE = 36;
	private static final int ETD = 37;
	private static final int TML_IN_DTE_TITLE = 38;
	private static final int TML_IN_DTE = 39;
	private static final int TML_OUT_DTE_TITLE = 40;
	private static final int TML_OUT_DTE = 41;
	private static final int STACK_DTE_TITLE = 42;
	private static final int STACK_DTE = 43;
	private static final int FREETIME_TITLE = 44;
	private static final int FREETIME = 45;
	private static final int CARGO_MANAGE_NO = 46;
	private static final int BONDED_TITLE = 47;
	private static final int BONDED = 48;
	private static final int CIS_TITLE = 49;
	private static final int CIS = 50;
	private static final int CIS_PRICE_TITLE = 51;
	private static final int CIS_PRICE = 52;
	private static final int OVER_STORAGE_TITLE = 53;
	private static final int CUSTOMS_TITLE = 54;
	private static final int ELECTRIC_FEE_TITLE = 55;
	private static final int DELIVERY_STATUS = 56;
	private static final int COMMENT1 = 57;
	private static final int DELIVERY_STATUS2 = 58;
	private static final int FINISH = 59;
	
	private ArrayList<Integer> skipIndex;
	
	public DPCTContainerInfoParser(){
		skipIndex = new ArrayList<Integer>();
		skipIndex.add(CNTR_NO_TITLE);
		skipIndex.add(SIZE_TYPE_TITLE);
		skipIndex.add(VVD_TITLE);
		skipIndex.add(OPR_TITLE);
		skipIndex.add(STATUS_TITLE);
		skipIndex.add(TS_TITLE);
		skipIndex.add(RETURN_CANCEL_TITLE);
		skipIndex.add(SEAL_NO_TITLE);
		skipIndex.add(TRUCK_NO_TITLE);
		skipIndex.add(WEIGHT_TITLE);
		skipIndex.add(IMDG_TITLE);
		skipIndex.add(REEFER_TITLE);
		skipIndex.add(VSL_LOCATION_TITLE);
		skipIndex.add(YARD_LOCATION_TITLE);
		skipIndex.add(POD_POL_TITLE);
		skipIndex.add(CNTR_INSP_TITLE);
		skipIndex.add(ETB_TITLE);
		skipIndex.add(ETD_TITLE);
		skipIndex.add(TML_IN_DTE_TITLE);
		skipIndex.add(TML_OUT_DTE_TITLE);
		skipIndex.add(STACK_DTE_TITLE);
		skipIndex.add(FREETIME_TITLE);
		skipIndex.add(BONDED_TITLE);
		skipIndex.add(CIS_TITLE);
		skipIndex.add(CIS_PRICE_TITLE);
		skipIndex.add(OVER_STORAGE_TITLE);
		skipIndex.add(CUSTOMS_TITLE);
		skipIndex.add(ELECTRIC_FEE_TITLE);
	}
	
	@Override
	public ContainerInformation extractContainerInformation(String pHtml){
        BufferedReader buffer;
        ContainerInformation ci = new ContainerInformation(); 
        
        try{
            StringReader sr = new StringReader(pHtml);
            buffer = new BufferedReader(sr);
            
            String line;
            int idx = 0;
            
            while((line = buffer.readLine()) != null){
            	if(idx == FINISH)
            		break;
            	
            	if(line.contains("#3A74A0") || line.contains("darkred")){
            		if(skipIndex.indexOf(idx) < 0){
//            			System.out.println(line);
            			
            			switch(idx){
            				case CNTR_NO:
            					System.out.println(getCntr_no(line));
            					ci.setCntr_no(getCntr_no(line));
            					break;
            				case SIZE_TYPE:
            					System.out.println(getCntr_siz(line));
            					System.out.println(getCntr_typ(line));
            					break;
            				case VVD:
            					System.out.println(getVvd_year(line));
            					System.out.println(getVsl_cod(line));
            					System.out.println(getVvd(line));
            					break;
            				case VSL_NAME:
            					System.out.println(getVsl_name(line));
            					break;
            				case OPR:
            					System.out.println(getOpr(line));
            					break;
            				case OPR_NAME:
            					System.out.println(getOpr_name(line));
            					break;
            				case STATUS:
            					System.out.println(getCls(line));
            					System.out.println(getSts(line));
            					System.out.println(getCntr_foe(line));
            					break;
            				case TS:
            					System.out.println(getTs(line));
            					break;
            				case RETURN_CANCEL:
            					System.out.println(getCnl(line));
            					break;
            				case SEAL_NO:
            					System.out.println(getSeal1(line));
            					break;
            				case TRUCK_NO:
            					System.out.println(getIn_truck(line));
            					break;
            				case WEIGHT:
            					System.out.println(getCntr_wgt(line));
            					break;
            				case IMDG:
            					System.out.println(getDg_imdg(line));
            					System.out.println(getDg_unno(line));
            					System.out.println(getDg_cls(line));
            					break;
            				case REEFER:
            					// 일단 여기까지... 일단 자자.. 세시다.. ㅋ
            					System.out.println(getCntr_no(line));
            					break;
            				case VSL_LOCATION:
            					System.out.println(getCntr_no(line));
            					break;
            				case YARD_LOCATION:
            					System.out.println(getCntr_no(line));
            					break;
            				case POD_POL:
            					System.out.println(getCntr_no(line));
            					break;
            				case CNTR_INSP:
            					System.out.println(getCntr_no(line));
            					break;
            				case ETB:
            					System.out.println(getCntr_no(line));
            					break;
            				case ETD:
            					System.out.println(getCntr_no(line));
            					break;
            				case TML_IN_DTE:
            					System.out.println(getCntr_no(line));
            					break;
            				case TML_OUT_DTE:
            					System.out.println(getCntr_no(line));
            					break;
            				case STACK_DTE:
            					System.out.println(getCntr_no(line));
            					break;
            				case FREETIME:
            					System.out.println(getCntr_no(line));
            					break;
            				case CARGO_MANAGE_NO:
            					System.out.println(getCntr_no(line));
            					break;
            				case BONDED:
            					System.out.println(getCntr_no(line));
            					break;
            				case CIS:
            					System.out.println(getCntr_no(line));
            					break;
            				case CIS_PRICE:
            					System.out.println(getCntr_no(line));
            					break;
            				case DELIVERY_STATUS:
            					System.out.println(getCntr_no(line));
            					break;
            				case COMMENT1:
            					System.out.println(getCntr_no(line));
            					break;
            				case DELIVERY_STATUS2:
            					System.out.println(getCntr_no(line));
            					break;
            			}
            			
            		}
            		
            		idx++;
            	}
            }
        }catch (Exception e){
        	Util.exceptionProc(e);
        }
        
        return ci;
	}
}
