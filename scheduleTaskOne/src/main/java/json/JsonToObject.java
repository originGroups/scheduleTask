package json;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;

import java.lang.Object;
import java.util.*;

/**
 * @author 袁强
 * @data 2021/5/15 17:00
 * @Description
 */
public class JsonToObject {

    public static void main(String[] args){
        String str = "{\n" +
                "\t\"a\": \"a\",\n" +
                "\t\"b\": [{\n" +
                "\t\t\"b1\": [{\n" +
                "\t\t\t\"b11\": \"b11\"\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"b2\": [{\n" +
                "\t\t\t\"b22\": [{\n" +
                "\t\t\t\t\"b222\": \"b222\"\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}],\n" +
                "\t\"c\": {\n" +
                "\t\t\"c1\": [{\n" +
                "\t\t\t\"c11\": \"c11\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"c111\": \"c111\"\n" +
                "\t\t}],\n" +
                "\t\t\"c2\": \"c2\"\n" +
                "\t}\n" +
                "}";

        JSONObject jsonObject = JSON.parseObject(str);
        List<User> childTree = getChildTree(jsonObject);
        System.out.println(childTree);
    }

    public static List<User> getChildTree(JSONObject jsonObject) {
        List<User> list = new ArrayList<>();
        Map<String, Object> map = jsonObject.getInnerMap();
        //该json总共有parents.size()个父节点
        Set<String> parents = map.keySet();
        for (String parentName: parents) {
            User user = new User();
            user.setName(parentName);
            Object child = map.get(parentName);
            //子节点可能是HashMap,JSONArray,JSONObject
            /**
             * HashMap key:value
             * JSONArray 数组 []
             * JSONObject {}
             */
            /*if(child instanceof HashMap){
                user.setValue(child.toString());
                list.add(user);
                continue;
            }*/
            if(child instanceof String){
                user.setValue(child.toString());
                user.setType("String");
                list.add(user);
                continue;
            }
            if(child instanceof JSONObject){
                JSONObject object = (JSONObject) child;
                List<User> childTree = getChildTree(object);
                user.setType("JSONObject");
                user.setChild(childTree);
                list.add(user);
            }
            if(child instanceof JSONArray){
                JSONArray array = (JSONArray) child;
                user.setType("JSONArray");
                for (Object object : array) {
                    JSONObject json = (JSONObject) object;
                    List<User> childTree = getChildTree(json);
                    if(CollectionUtils.isNotEmpty(user.getChild())){
                        user.getChild().addAll(childTree);
                    }else{
                        user.setChild(childTree);
                        list.add(user);
                    }
                }
            }
        }
        return list;
    }


}
