package jsonxml;

import com.google.common.collect.Lists;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
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
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><Service_header><name>张三</name></Service_header></Service>";
        User user = parseXml(xml);
        System.out.println(user);
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
        //获取根节点
        String name = nodes.get(0).getName();
        //从根节点遍历对应的子节点
        List<User> childTree = getChildTree(name, nodes);
        User user = new User();
        user.setName(name);
        user.setChild(childTree);
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
