package com.gdutelc.snp.service.impl;
import com.gdutelc.snp.cache.AdminCache;
import com.gdutelc.snp.cache.SignCache;
import com.gdutelc.snp.cache.UserCache;
import com.gdutelc.snp.config.jwt.AdminJwtConfig;
import com.gdutelc.snp.dao.IAdminDao;
import com.gdutelc.snp.dao.ISignDao;
import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.dto.Audition;
import com.gdutelc.snp.dto.Dsign;
import com.gdutelc.snp.entity.Admin;
import com.gdutelc.snp.entity.Message;
import com.gdutelc.snp.exception.AdminServiceException;
import com.gdutelc.snp.result.Enroll;
import com.gdutelc.snp.result.Status;
import com.gdutelc.snp.service.AdminApiService;
import com.gdutelc.snp.util.JwtUtil;
import com.gdutelc.snp.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author kid
 */
@Service
public class AdminApiServiceImpl implements AdminApiService {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    AdminJwtConfig adminJwtConfig;

    @Resource
    private AdminCache adminCache;

    @Resource
    private SignCache signCache;

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Resource
    private UserCache userCache;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private ISignDao signDao;
    @Resource
    private IUserDao userDao;

    @Resource
    private IAdminDao adminDao;





    @Override
    public String adminLogin(String username, String password) {
        try{
            String passowrdByUsername = adminCache.getPassowrdByUsername(username);
            Integer aid = adminCache.getAdminByUsername(username).getAid();
            if (!password.equals(passowrdByUsername)){
                throw new AdminServiceException(Status.PASSWORDERROR,null);
            }
            return jwtUtil.adminJwtCreate(aid, username, passowrdByUsername);
        }catch (Exception e){
            throw new AdminServiceException(Status.GETPASSWORDERROR,e.getMessage());
        }


    }

    @Override
    public List<Dsign> getDsignByGender(Boolean gender) {
        try{
            return signDao.getDsignByGender(gender);
        }catch (Exception e){
            throw new AdminServiceException(Status.GETFORMERROR,e.getMessage());
        }

    }

    @Override
    public List<Dsign> getDsignByCollege(Integer college) {
        try{
            return signDao.getDsignByCollege(college);
        }catch (Exception e){
            throw new AdminServiceException(Status.GETFORMERROR,e.getMessage());
        }

    }

    @Override
    public List<Dsign> getDsignByDno(Integer dno) {
        try {
            return signDao.getDsignByDno(dno);
        }catch (Exception e){
            throw new AdminServiceException(Status.GETFORMERROR,e.getMessage());
        }

    }

    @Override
    public boolean confirm(String jwt, Integer uid, boolean check) {
        try{
            String username = adminJwtConfig.getPayload(jwt).get("username");
            Admin admin = adminCache.getAdminByUsername(username);
            String opneid = userCache.getOpenidByUid(uid);

            String session = (String) redisUtil.get("msg" + opneid);
            Dsign dsign = signCache.getDsignByUidCache(uid);

            Message message = new Message(dsign.getName(),admin.getAdno(),session,opneid);
            if (check){
                kafkaTemplate.send("success",message).addCallback(success-> log.info("成功往kafka传入confirm信息"), fail->{
                    throw new AdminServiceException(Status.CONFIRMFAIL,Status.CONFIRMFAIL.getMsg());
                });
            }else{
                kafkaTemplate.send("fail",message).addCallback(success-> log.info("成功往kafka传入confirm信息"), fail->{
                    throw new AdminServiceException(Status.CONFIRMFAIL,Status.CONFIRMFAIL.getMsg());
                });
            }
            return true;
        }catch (Exception e){
            throw new AdminServiceException(Status.CONFIRMFAIL,e.getMessage());
        }
    }

    @Override
    public boolean checkSignService(Integer uid) {
        try{
            userCache.updateEnrollByUid(Enroll.HAVECHECKING.getCode(), uid);
            return true;
        }catch (Exception e){
            throw new AdminServiceException(Status.CHECKSIGNFAIL,e.getMessage());
        }
    }

    @Override
    public boolean closeSignService() {
        //数据库冻结check
        try{
            userCache.closeSign(Enroll.NOPUSHSIGN.getCode());
            userCache.updateEnrollByitself(Enroll.HAVESIGN.getCode(),Enroll.CHECKING.getCode());
            return true;
        }catch (Exception e){
            throw new AdminServiceException(Status.CLOSESIGNERROR,Status.CLOSESIGNERROR.getMsg());
        }
        //TODO 塞入kafka告诉用户
    }

    @Override
    public boolean firstAuditionService(Audition audition) {
        try{
            //扔到redis
            redisUtil.set("firstAudition",audition);
            //冻结小于111的user
            userCache.closeSign(Enroll.HAVESIGN.getCode());
            userCache.closeSign(Enroll.CHECKING.getCode());
            //为111的用户推送
            userCache.updateEnrollByitself(Enroll.HAVECHECKING.getCode(),Enroll.FIRSTNOCHECK.getCode());
            kafkaTemplate.send("firstAudition",audition);
            //TODO 塞入kafka
            return true;
        }catch(Exception e){
            throw new AdminServiceException(Status.POSTFIRSTAUDITIONFAIL,e.getMessage());
        }
    }

    @Override
    public Audition closeFirstService() {
            boolean judge = redisUtil.hasKey("firstAudition");
            if (!judge){
                throw new AdminServiceException(Status.NOFIRSTDATELOCATION,Status.NOFIRSTDATELOCATION.getMsg());
            }
            Audition firstAudition = (Audition)redisUtil.get("firstAudition");
            //冻结状态为202的用户
            userCache.closeSign(Enroll.FIRSTGIVEUP.getCode());
            //将状态为201的用户修改为210
            userCache.updateEnrollByitself(Enroll.FIRSTCHECKED.getCode(),Enroll.FIRSTNOSIGN.getCode());
            return firstAudition;
    }

    @Override
    public boolean updateResultService(Integer uid, String result) {
        try{
            userCache.updateResultByUidCache(result, uid);
            return true;
        }catch (Exception e){
            throw new AdminServiceException(Status.UPDATERESULTERROR,e.getMessage());
        }
    }

    @Override
    public String getResultService(Integer uid) {
        try{
            return userDao.getUserByUid(uid).getResult();
        }catch (Exception e){
            throw new AdminServiceException(Status.GETRESULTERROR,e.getMessage());
        }
    }

    @Override
    public String updatePassword(String jwt, String password) {
        try{
            String aid = adminJwtConfig.getPayload(jwt).get("aid");
            String username = adminDao.getAdminByAid(Integer.parseInt(aid)).getUsername();
            adminCache.updatePasswordCache(password, Integer.parseInt(aid));
            return jwtUtil.adminJwtCreate(Integer.parseInt(aid), username, password);
        }catch (Exception e){
            throw new AdminServiceException(Status.UPDATEPASSWORDERROR,e.getMessage());
        }
    }

    @Override
    public String getAdminNameService(String jwt) {
        try {
            String aid = adminJwtConfig.getPayload(jwt).get("aid");
            return adminCache.getAdminName(Integer.parseInt(aid));
        }catch (Exception e){
            throw new AdminServiceException(Status.GETADMINNAMEERROR,e.getMessage());
        }
    }

    @Override
    public Boolean changeStatusService(Integer oldEnroll, Integer newEnroll) {
        try{
            int number = newEnroll / 100;
            redisUtil.set("StatusTime",number);
            userCache.updateEnrollByitself(oldEnroll, newEnroll);
            userCache.changeSatusCache(oldEnroll);
            return true;
        }catch (Exception e){
            throw new AdminServiceException(Status.CHANGSTATUSERROR,e.getMessage());
        }
    }

    @Override
    public Integer getStatusTimeService() {
        boolean statusTime = redisUtil.hasKey("StatusTime");
        if (!statusTime){
            throw new AdminServiceException(Status.GETSTATUSTIMEERROR,null);
        }
        return (Integer) redisUtil.get("StatusTime");
    }

    @Override
    public Integer getUserStatusService(Integer uid) {
        try{
            return  userCache.getUserByUid(uid).getEnroll();
        }catch (Exception e){
            throw new AdminServiceException(Status.GETUSERSTATUSERROR,e.getMessage());
        }

    }

    @Override
    public Boolean updateUserStatusService(Integer uid,Integer enroll) {
        try {

            Integer integer = userCache.updateEnrollByUid(enroll, uid);
            return integer.equals(1);
        }catch (Exception e){
            throw new AdminServiceException(Status.UPDATEUSERSTATUSERROR,e.getMessage());
        }

    }

}
