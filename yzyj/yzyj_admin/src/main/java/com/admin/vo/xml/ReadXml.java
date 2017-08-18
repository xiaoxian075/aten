package com.admin.vo.xml;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.StringReader;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/16.
 */
public class ReadXml {
    public static java.util.HashMap DealReturnXML(String xmlContent){
        java.util.HashMap map = new HashMap();
        Integer state = 0;
        String message = "";
        String responseCode = "";
        try{
            SAXBuilder builder = new SAXBuilder();
            StringReader in = new StringReader(xmlContent);
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            responseCode = root.getChildTextTrim("responseCode");
            message = root.getChildTextTrim("message");
            if(("000000").equals(responseCode)){
                state = 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("state",state);
        map.put("responseCode",responseCode);
        map.put("message",message);
        return map;
    }
}
