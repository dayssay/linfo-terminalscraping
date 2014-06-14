package linfo.project.terminalscraping.containerinfo.parser;

import java.io.BufferedReader;
import java.io.StringReader;

import linfo.project.util.Util;

public class BITContainerInfoParser extends ContainerInfoParser{

	@Override
	public void SetContainerInfo(String pHtml){
        BufferedReader buffer;
        
        try{
            StringReader sr = new StringReader(pHtml);
            buffer = new BufferedReader(sr);
            
            String line;
            
            while((line = buffer.readLine()) != null){
            	System.out.println(line);
            }
        }catch (Exception e){
        	Util.exceptionProc(e);
        }
	}
}
