package com.gdutelc.snp.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdutelc.snp.annotation.AdminJwt;
import com.gdutelc.snp.dto.Audition;
import com.gdutelc.snp.dto.Dsign;
import com.gdutelc.snp.result.Result;
import com.gdutelc.snp.result.Return;
import com.gdutelc.snp.result.Status;
import com.gdutelc.snp.service.AdminApiService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author kid
 */
@RestController
@CrossOrigin
public class AdminApiController extends BaseController{
    @Resource
    private AdminApiService adminApiService;



    @PostMapping("/admin/login")
    public Result<Object> adminLogin(@RequestParam("username") String username,
                                     @RequestParam("password") String password){
        String jwt = adminApiService.adminLogin(username, password);
        if (jwt == null){
            Return.error(Status.JWTCREATEERROR);
        }
        return Return.success(jwt);
    }

    @AdminJwt
    @GetMapping("admin/getbygen")
    public Result<Object> getDsignByGender(@RequestParam("gender") Boolean gender){
        List<Dsign> data = adminApiService.getDsignByGender(gender);
        if (data.isEmpty()){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }

    @AdminJwt
    @GetMapping("/admin/getbycolle")
    public Result<Object> getDsignByCollege(@RequestParam("college") Integer college){
        List<Dsign> data = adminApiService.getDsignByCollege(college);
        if (data.isEmpty()){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }

    @AdminJwt
    @GetMapping("/admin/getbydno")
    public Result<Object> getDsignByDno(@RequestParam("dno") Integer dno){
        List<Dsign> data = adminApiService.getDsignByDno(dno);
        if (data.isEmpty()){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }

    @AdminJwt
    @GetMapping("/admin/confirm")
    public Result<Object> confirm(@RequestHeader("Authorization") String jwt, @RequestParam("uid") Integer uid,@RequestParam("check") boolean check){
        adminApiService.confirm(jwt, uid, check);
        return Return.success();
    }
    @AdminJwt
    @PostMapping("/admin/checkSign")
    public Result<Object> checkSign(@RequestParam("uid") Integer uid){
        boolean judge = adminApiService.checkSignService(uid);
        if (!judge){
            return Return.error(Status.CHECKSIGNFAIL);
        }
        return Return.success();
        //TODO 塞入kafka给用户消息推送说明报名成功了
    }
    @AdminJwt
    @PostMapping("/admin/closeSign")
    public Result<Object> closeSign(){
        boolean judge = adminApiService.closeSignService();
        if (!judge){
            return Return.error(Status.CLOSESIGNERROR);
        }
        return  Return.success();
    }
    @AdminJwt
    @PostMapping("/admin/firstAudition")
    public Result<Object> firstAudition(@RequestBody Audition audition){
        boolean judge = adminApiService.firstAuditionService(audition);
        if (!judge){
            return Return.error(Status.POSTFIRSTAUDITIONFAIL);
        }
        return Return.success();
    }
    @AdminJwt
    @GetMapping("/admin/closeFirst")
    public Result<Object> closeFirst(){
        Audition audition = adminApiService.closeFirstService();
        return Return.success(audition);
    }
    @AdminJwt
    @PostMapping("/admin/updateResult")
    public Result<Object> updateResult(@RequestParam("uid") Integer uid,@RequestParam("result") String result){
        boolean judge = adminApiService.updateResultService(uid, result);
        if (!judge){
            return Return.error(Status.UPDATERESULTERROR);
        }
        return Return.success();
    }
    @AdminJwt
    @GetMapping("/admin/getResult")
    public Result<Object> getResult(@RequestParam("uid") Integer uid){
        return Return.success(adminApiService.getResultService(uid));
    }

    @AdminJwt
    @PutMapping("/admin/updatePassword")
    public Result<Object> updatePassword(@RequestHeader("Authorization") String jwt,@RequestParam("password") String password){
        String data = adminApiService.updatePassword(jwt, password);
        if (data == null){
            return Return.error(Status.UPDATEPASSWORDERROR);
        }
        return Return.success(data);
    }
    @AdminJwt
    @GetMapping("/admin/getName")
    public Result<Object> getAdminName(@RequestHeader("Authorization") String jwt){
        String adminNameService = adminApiService.getAdminNameService(jwt);
        return Return.success(adminNameService);
    }

    @AdminJwt
    @PostMapping("/admin/changeStatus")
    public Result<Object> changeStatus(@RequestBody String message){
        JSONObject jsonObject = JSON.parseObject(message);
        Integer oldEnroll = jsonObject.getInteger("oldEnroll");
        Integer newEnroll = jsonObject.getInteger("newEnroll");
        Boolean judge = adminApiService.changeStatusService(oldEnroll, newEnroll);
        if(!judge){
            return Return.error(Status.CHANGSTATUSERROR);
        }
        return Return.success();
    }
    @AdminJwt
    @GetMapping("/admin/getStatusTime")
    public Result<Object> getStatusTime(){
        Integer statusTimeService = adminApiService.getStatusTimeService();
        return Return.success(statusTimeService);
    }
    @AdminJwt
    @GetMapping("/admin/getUserStatus")
    public Result<Object> getUserStatus(@RequestParam("uid") int uid){
        Integer userStatusService = adminApiService.getUserStatusService(uid);
        return Return.success(userStatusService);
    }
    @AdminJwt
    @PutMapping("/admin/updateUserStatus")
    public Result<Object> updateUserStatus(@RequestBody String message){
        JSONObject jsonObject = JSON.parseObject(message);
        Integer uid = jsonObject.getInteger("uid");
        Integer enroll = jsonObject.getInteger("enroll");
        Boolean judge = adminApiService.updateUserStatusService(uid, enroll);
        if (!judge){
            return Return.error(Status.UPDATEUSERSTATUSERROR);
        }
        return Return.success();
    }
}
