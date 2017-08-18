package com.admin.dto;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */
public class CreateXml {



    public static String CreateLklMoneySplit(java.util.HashMap map, List<HashMap<Object,Object>> list){
        String retXML = "";
        try{
            retXML = "<?xml version=\"1.0\" encoding=\"GBK\"?>"
                    +"  <ROOT> "
                    +"    <FunCod>"+map.get("FunCod")+"</FunCod>"
                    +"    <compOrgCode>"+map.get("compOrgCode")+"</compOrgCode>"
                    +"    <reqLogNo>"+map.get("reqLogNo")+"</reqLogNo>"
                    +"    <shopNo>"+map.get("shopNo")+"</shopNo>"
                    +"    <termNo>"+map.get("termNo")+"</termNo>"
                    +"    <splitAmt>"+map.get("splitAmt")+"</splitAmt>"
                    +"    <tranAmt>"+map.get("tranAmt")+"</tranAmt>"
                    +"    <redMark>"+map.get("redMark")+"</redMark>"
                    +"    <redAmt>"+map.get("redAmt")+"</redAmt>"
                    +"    <orderType>"+map.get("orderType")+"</orderType>"
                    +"    <orderLogNo>"+map.get("orderLogNo")+"</orderLogNo>"
                    +"    <orderDate>"+map.get("orderDate")+"</orderDate>"
                    +"    <orderTime>"+map.get("orderTime")+"</orderTime>"
                        +"    <ordContent>"
                                +"<sptShopNo>"+list.get(0).get("sptShopNo")+"</sptShopNo>"
                                +"<sptTermNo>"+list.get(0).get("sptTermNo")+"</sptTermNo>"
                                +"<sptAmt>"+list.get(0).get("sptAmt")+"</sptAmt>"
                                +"<feeMark>"+list.get(0).get("feeMark")+"</feeMark>"
                        +"    </ordContent>"
                        +"    <ordContent>"
                                +"<sptShopNo>"+list.get(1).get("sptShopNo")+"</sptShopNo>"
                                +"<sptTermNo>"+list.get(1).get("sptTermNo")+"</sptTermNo>"
                                +"<sptAmt>"+list.get(1).get("sptAmt")+"</sptAmt>"
                                +"<feeMark>"+list.get(1).get("feeMark")+"</feeMark>"
                        +"    </ordContent>"
                        +"    <ordContent>"
                                +"<sptShopNo>"+list.get(2).get("sptShopNo")+"</sptShopNo>"
                                +"<sptTermNo>"+list.get(2).get("sptTermNo")+"</sptTermNo>"
                                +"<sptAmt>"+list.get(2).get("sptAmt")+"</sptAmt>"
                                +"<feeMark>"+list.get(2).get("feeMark")+"</feeMark>"
                        +"    </ordContent>"
                    +"    <MAC>"+map.get("MAC")+"</MAC>"
                    +"  </ROOT>  ";
        }catch (Exception e){
            e.printStackTrace();
        }
        return retXML;
    }
}
