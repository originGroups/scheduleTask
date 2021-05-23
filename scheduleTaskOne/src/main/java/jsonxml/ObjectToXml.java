package jsonxml;

import com.google.common.collect.Lists;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * @author 袁强
 * @data 2021/5/23 16:00
 * @Description xml反结构化
 */
public class ObjectToXml {

    public static void main(String[] args) throws Exception{
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><Service_header><name>张三</name></Service_header></Service>";
        Map<String,String> map = new HashMap<>();
        map.put("Service.Service_header.name","lisi");
        String xmlMsg = getXmlMsg(xml, map);
        System.out.println(xmlMsg);
    }


    /**
     * @auther: 袁强
     * @Description: xml反结构化获取报文
     * @param: xmlMsg: xml原始报文  map: 替换的节点值 todo 增加节点/属性删除节点/属性
     * @return:
     * @date: 2021/5/23 16:20
     */
    public static String getXmlMsg(String xmlMsg ,Map<String,String> map) throws Exception{

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(xmlMsg.getBytes()));
        //获取根节点
        Element rootElement = document.getRootElement();
        Set<String> keys = map.keySet();
        for (String fldNm:keys) {
            String[] split = fldNm.split("\\.");
            String s = split[split.length - 1];
            int i = 1;
            while (i < split.length){
                String nodeName = split[i];
                rootElement = rootElement.element(nodeName);
                if(s.equals(rootElement.getName())){
                    rootElement.setText(map.get(fldNm));
                    i = 1;
                    rootElement = document.getRootElement();
                    break;
                }
                i ++;
            }
        }
        String xml = document.asXML();
        return xml;
    }
}
