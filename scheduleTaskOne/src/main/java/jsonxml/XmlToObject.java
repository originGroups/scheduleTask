package jsonxml;

import com.google.common.collect.Lists;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 袁强
 * @data 2021/5/23 16:00
 * @Description  xml结构化
 */
public class XmlToObject {

    public static void main(String[] args) throws Exception{
        //String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service addr=\"北京\">哈哈哈</Service>";

       String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><Service_header xxx=\"HHH\"><name addr=\"北京\" age=\"20\">张三</name></Service_header></Service>";
        User user = parseXml(xml);
        System.out.println(">>>>>>>>>>>>>>原始报文:");
        System.out.println(xml);
        List<User> list = new ArrayList<>();
        User user1 = new User();
        user1.setName("Service_body");
        List<User> list1 = new ArrayList<>();
        User user2 = new User();
        user2.setName("bodyA");
        user2.setValue("body属性");
        list1.add(user2);
        user1.setChild(list1);
        list.add(user1);
        user.getChild().addAll(list);
        String msg = getXmlMsgFromStruc(xml, user);
        System.out.println("<<<<<<<<<<<<<<新报文:");
        System.out.println(msg);
    }

    /**
     * @auther: 袁强
     * @Description: xml反结构化获取报文
     * @param: xmlMsg: xml原始报文:只为获取编码  user 完整树型结构化节点
     * @return:
     * @date: 2021/5/23 16:20
     */
    public static String getXmlMsgFromStruc(String xmlMsg ,User user) throws Exception{

        SAXReader saxReader = new SAXReader();
        DocumentFactory factory = saxReader.getDocumentFactory();
        Document document = factory.createDocument();
        document.setXMLEncoding(saxReader.read(new ByteArrayInputStream(xmlMsg.getBytes())).getXMLEncoding());
        //设置根节点
        String name = user.getName();
        Element element = document.addElement(name);
        createXml(element,user);
        String xml = document.asXML();
        return xml;
    }
    /**
     * @auther: 袁强
     * @Description: 迭代拼接xml
     * @param:
     * @return:
     * @date: 2021/5/23 16:20
     */
    public static void createXml(Element element, User child){
        List<User> childs = child.getChild();
        if(childs != null){
            for (User chil:childs) {
                //去掉添加的value节点
                String name = chil.getName();
                String value = chil.getValue();
                if("value".equals(name)){
                    element.setText(chil.getValue());
                }else if(value != null && !"value".equals(name)){
                    //这是属性
                    element.addAttribute(name,chil.getValue());
                }else{
                    Element element1 = element.addElement(name);
                    createXml(element1, chil);
                }

            }
        }
    }

    /**
     * @auther: 袁强
     * @Description: 解析xml报文
     * @param:   xmlMsg xml格式的报文
     * @return:
     * @date: 2021/5/23 16:20
     */
    public static User parseXml(String xmlMsg) throws Exception{

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(xmlMsg.getBytes()));
        //获取所有节点
        List<Node> nodes = document.selectNodes("//*");
        User user = new User();
        //该xml报文只有根节点
        if(nodes.size() == 1){
            //只有根节点
            //节点值设置value节点表示,和属性区分
            List<User> list = new ArrayList<>();
            //属性也视为一个节点
            List<User> list1 = new ArrayList<>();
            Element element = (Element) nodes.get(0);
            String value = element.getTextTrim();
            if(!"".equals(value)){
                //节点值使用value节点表示
                User userValue = new User();
                userValue.setName("value");
                userValue.setValue(value);
                list.add(userValue);
            }
            user.setName(element.getName());
            //获取当前节点属性
            List<Attribute> attributes = element.attributes();
            if(attributes.size() != 0){
                for (Attribute attribute: attributes) {
                    String userAttributeName = attribute.getName();
                    String userAttributeValue = attribute.getText();
                    User user1 = new User();
                    user1.setName(userAttributeName);
                    user1.setValue(userAttributeValue);
                    list1.add(user1);
                }
            }
            list.addAll(list1);
            user.setChild(list);
        }else{
            //获取根节点,处理根节点下面的子节点
            String name = nodes.get(0).getName();
            //从根节点遍历对应的子节点
            List<User> childTree = getChildTree(name, nodes);
            user.setName(name);
            user.setChild(childTree);
        }


        return user;
    }

    /**
     * @auther: 袁强
     * @Description: 将解析的xml报文格式化
     * @param:   xmlMsg xml格式的报文
     * @return:
     * @date: 2021/5/23 16:20
     */
    public static List<User> getChildTree(String name, List<Node> nodes) {

        List<User> resNodes = Lists.newArrayListWithCapacity(nodes.size());
        for (Node node : nodes){
            Element parent = node.getParent();
            if(parent != null){
                //节点值设置value节点表示,和属性区分
                List<User> list = new ArrayList<>();
                //属性也视为一个节点
                List<User> list1 = new ArrayList<>();
                String fName = parent.getName();
                if(name.equals(fName)){
                    User user = new User();
                    Element element = (Element) node;
                    String value = element.getTextTrim();
                    if(!"".equals(value)){
                        //节点值使用value节点表示
                        User userValue = new User();
                        userValue.setName("value");
                        userValue.setValue(value);
                        list.add(userValue);
                    }
                    user.setName(element.getName());
                    //user.setFieldPath(element.getName())  字段全路径
                    //获取当前节点属性
                    List<Attribute> attributes = element.attributes();
                    if(attributes.size() != 0){
                        for (Attribute attribute: attributes) {
                            String userAttributeName = attribute.getName();
                            String userAttributeValue = attribute.getText();
                            User user1 = new User();
                            user1.setName(userAttributeName);
                            user1.setValue(userAttributeValue);
                            list1.add(user1);
                        }
                    }
                    List<User> child = getChildTree(node.getName(), nodes);
                    child.addAll(list);
                    child.addAll(list1);
                    user.setChild(child);
                    resNodes.add(user);
                }
            }
        }
        return resNodes;
    }
}
