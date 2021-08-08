package com.gdutelc.snp.util;

import com.alibaba.fastjson.JSON;
import com.gdutelc.snp.entity.Sign;
import com.gdutelc.snp.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kid
 */
@Component
public class FormUtil {
    public static String tranMapFromSign(Sign userinfo){
        Map<String,Object> data = new HashMap<>(8);
        data.put("name",userinfo.getName());
        data.put("grade",userinfo.getGrade());
        data.put("college",userinfo.getCollege());
        data.put("major",userinfo.getMajor());
        data.put("userclass",userinfo.getUserclass());
        data.put("description",userinfo.getDescription());
        data.put("dno",userinfo.getDno());
        data.put("secdno",userinfo.getSecdno());
        data.put("gender",userinfo.getGender());
        data.put("sno",userinfo.getSno());
        data.put("qq",userinfo.getQq());
        data.put("domitory",userinfo.getDomitory());
        data.put("knowr",userinfo.getKnow());
        data.put("party",userinfo.getParty());
        return JSON.toJSONString(data);

    }
    public static String tranMapFromUser(User user){
        Map<String,Object> data = new HashMap<>(4);
        data.put("enroll",user.getEnroll());
        data.put("check",user.getCheck());
        data.put("result",user.getResult());
        return JSON.toJSONString(data);
    }
}
