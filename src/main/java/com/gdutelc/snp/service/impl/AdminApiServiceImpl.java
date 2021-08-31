package com.gdutelc.snp.service.impl;
import com.alibaba.fastjson.JSON;
import com.gdutelc.snp.cache.AdminCache;
import com.gdutelc.snp.cache.SignCache;
import com.gdutelc.snp.cache.UserCache;
import com.gdutelc.snp.config.jwt.AdminJwtConfig;
import com.gdutelc.snp.dto.Dsign;
import com.gdutelc.snp.entity.Admin;
import com.gdutelc.snp.entity.Message;
import com.gdutelc.snp.exception.AdminServiceException;
import com.gdutelc.snp.result.Status;
import com.gdutelc.snp.service.AdminApiService;
import com.gdutelc.snp.util.RedisUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kid
 */
@Service
public class AdminApiServiceImpl implements AdminApiService {
    @Resource
    AdminJwtConfig adminJwtConfig;

    @Resource
    private AdminCache adminCache;

    @Resource
    private SignCache signCache;

    @Resource
    private KafkaTemplate kafkaTemplate;

    @Resource
    private UserCache userCache;

    @Resource
    private RedisUtil redisUtil;




    @Override
    public String adminLogin(String username, String password) {
        String passowrdByUsername = adminCache.getPassowrdByUsername(username);
        try{
            Assert.notNull(passowrdByUsername, Status.GETPASSWORDERROR.getMsg());
        }catch (Exception e){
            throw new AdminServiceException(Status.GETPASSWORDERROR);
        }

        if (!password.equals(passowrdByUsername)){
            throw new AdminServiceException(Status.PASSWORDERROR);

        }
        Map<String,Object> data = new HashMap<>(4);
        data.put("username",username);
        return adminJwtConfig.createJwt(data, password);
    }

    @Override
    public String getDsignByGender(Boolean gender) {
        List<Dsign> dsigns = signCache.getDsignByGender(gender);
        try{
            Assert.notNull(dsigns,Status.GETFORMERROR.getMsg());
        }catch (Exception e){
            throw new AdminServiceException(Status.GETFORMERROR);
        }
        return JSON.toJSONString(dsigns);
    }

    @Override
    public String getDsignByCollege(Integer college) {
        List<Dsign> dsigns = signCache.getDsignByCollege(college);
        try{
            Assert.notNull(dsigns,Status.GETFORMERROR.getMsg());
        }catch (Exception e){
            throw new AdminServiceException(Status.GETFORMERROR);
        }
        return JSON.toJSONString(dsigns);
    }

    @Override
    public String getDsignByDno(Integer dno) {
        List<Dsign> dsigns = signCache.getDsignByDno(dno);
        try {
            Assert.notNull(dsigns, Status.GETFORMERROR.getMsg());
        }catch (Exception e){
            throw new AdminServiceException(Status.GETFORMERROR);
        }
        return JSON.toJSONString(dsigns);
    }

    @Override
    public boolean confirm(String jwt, Integer uid, boolean check) {
        try{
            String username = adminJwtConfig.getPayload(jwt).get("username");
            Admin admin = adminCache.getAdminByUsername(username);
            String opneid = userCache.getOpenidByUid(uid);

            String session = (String) redisUtil.get("msg" + opneid);
            Dsign dsign = signCache.getDsignByUid(uid);

            Message message = new Message(dsign.getName(),admin.getAdno(),session,opneid);
            if (check){
                kafkaTemplate.send("success",message);
            }else{
                kafkaTemplate.send("fail",message);
            }
            return true;
        }catch (Exception e){
            throw new AdminServiceException(Status.CONFIRMFAIL);
        }
    }
}
