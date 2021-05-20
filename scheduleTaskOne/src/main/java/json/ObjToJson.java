package json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.Object;
import java.util.List;
import java.util.Map;

/**
 * @author 袁强
 * @data 2021/5/19 16:50
 * @Description json报文反结构化
 */
public class ObjToJson {

    public static void main(String[] args){
        String str = "{\n" +
                "\t\"a\": \"a\",\n" +
                "\t\"b\": [{\n" +
                "\t\t\"b1\": \"b1\"\n" +
                "\t}, {\n" +
                "\t\t\"b2\": \"b2\"\n" +
                "\t}, {\n" +
                "\t\t\"b3\": {\n" +
                "\t\t\t\"b4_1\": \"b4_1\",\n" +
                "\t\t\t\"b4_2\": \"b4_2\"\n" +
                "\t\t}\n" +
                "\t}],\n" +
                "\t\"c\": {\n" +
                "\t\t\"c1\": \"c1\",\n" +
                "\t\t\"c2\": [{\n" +
                "\t\t\t\"c3_1\": \"c3_1\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"c3_2\": \"c3_2\"\n" +
                "\t\t}]\n" +
                "\t}\n" +
                "}";

        JSONObject jsonObject = JSON.parseObject(str);
        List<User> childTree = JsonToObject.getChildTree(jsonObject);

        JSONObject jsonObjectNew = new JSONObject();

        for (User user:childTree) {
            JSONObject jsonMsg = getJsonMsg(user, user.getChild());

            jsonObjectNew.fluentPutAll(jsonMsg);
        }
        String msg = JSON.toJSONString(jsonObjectNew,true);
        System.out.println(msg);
    }

    public static JSONObject getJsonMsg(User parent , List<User> childTree){

        JSONObject jsonObject = new JSONObject();
        Map<String, Object> innerMap = jsonObject.getInnerMap();
        String parentType = parent.getType();

        if("String".equals(parentType)){
            innerMap.put(parent.getName(),parent.getValue());
        }
        if("JSONObject".equals(parentType)){

            JSONObject jo = new JSONObject();
            for (User user:childTree){
                List<User> child = user.getChild();

                JSONObject jsonMsg = getJsonMsg(user, child);
                jo.fluentPutAll(jsonMsg);
            }
            innerMap.put(parent.getName(),jo);
        }
        if("JSONArray".equals(parentType)){
            JSONArray array = new JSONArray();
            for (User user:childTree){

                List<User> child = user.getChild();

                JSONObject jsonMsg = getJsonMsg(user, child);
                array.add(jsonMsg);
            }
            innerMap.put(parent.getName(),array);
        }

        return jsonObject;
    }
}
