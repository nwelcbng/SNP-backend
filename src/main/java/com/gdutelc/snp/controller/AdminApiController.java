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
    public Result<Object> getDsignByGender(@RequestBody String gender){
        JSONObject jsonObject = JSON.parseObject(gender);
        Boolean newGender = jsonObject.getBoolean("gender");
        List<Dsign> data = adminApiService.getDsignByGender(newGender);
        if (data.isEmpty()){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }

    @AdminJwt
    @GetMapping("/admin/getbycolle")
    public Result<Object> getDsignByCollege(@RequestBody String college){
        JSONObject jsonObject = JSON.parseObject(college);
        Integer newCollege = jsonObject.getInteger("college");
        System.out.println(college);
        List<Dsign> data = adminApiService.getDsignByCollege(newCollege);
        if (data.isEmpty()){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }

    @AdminJwt
    @GetMapping("/admin/getbydno")
    public Result<Object> getDsignByDno(@RequestBody String dno){
        JSONObject jsonObject = JSON.parseObject(dno);
        Integer newDno = jsonObject.getInteger("dno");
        List<Dsign> data = adminApiService.getDsignByDno(newDno);
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
        boolean judge = adminApiService.checkSign(uid);
        if (!judge){
            return Return.error(Status.CHECKSIGNFAIL);
        }
        return Return.success();
        //TODO 塞入kafka给用户消息推送说明报名成功了
    }
    @AdminJwt
    @PostMapping("/admin/closeSign")
    public Result<Object> closeSign(){
        boolean judge = adminApiService.closeSign();
        if (!judge){
            return Return.error(Status.CLOSESIGNERROR);
        }
        return  Return.success();
    }
    @AdminJwt
    @PostMapping("/admin/firstAudition")
    public Result<Object> firstAudition(@RequestBody Audition audition){
        boolean judge = adminApiService.firstAudition(audition);
        if (!judge){
            return Return.error(Status.POSTFIRSTAUDITIONFAIL);
        }
        return Return.success();
    }
    @AdminJwt
    @GetMapping("/admin/closeFirst")
    public Result<Object> closeFirst(){
        Audition audition = adminApiService.closeFirst();
        return Return.success(audition);
    }

}
