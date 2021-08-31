package com.gdutelc.snp.controller;
import com.alibaba.fastjson.JSON;
import com.gdutelc.snp.annotation.UserJwt;
import com.gdutelc.snp.annotation.UserWebJwt;
import com.gdutelc.snp.dto.Dsign;
import com.gdutelc.snp.result.Result;
import com.gdutelc.snp.result.Return;
import com.gdutelc.snp.result.Status;
import com.gdutelc.snp.service.UserApiService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

/**
 * @author kid
 */
@RestController
@CrossOrigin
public class UserApiController  extends BaseController{
    @Resource
    private UserApiService userApiService;


    @PostMapping("/user/register")
    public Result<Object> getCode(@RequestBody String code){
        String jwt = userApiService.registerService(code);
        if (jwt.isEmpty()){
            return Return.error(Status.JWTCREATEERROR);
        }
        if (jwt.contains("errcode")){
            System.out.println(jwt);
            return Return.error(Status.GETOPENIDERROR,jwt);
        }
        System.out.println(jwt.indexOf("errcode"));
        return  Return.success(jwt);
    }

    @UserJwt
    @GetMapping("/user/getform")
    public Result<Object> getForm(@RequestHeader("Cookie") String cookie){
        String data = userApiService.getFormService(cookie);
        if (data == null){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }
    @UserWebJwt
    @GetMapping("/user//user/getWebForm")
    public Result<Object> getWebForm(@RequestHeader("Cookie") String cookie){
        String data = userApiService.getWebFormService(cookie);
        if (data == null){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }


    @UserJwt
    @PutMapping("/user/setstatus")
    public Result<Object> setStatus(@RequestHeader("Cookie") String cookie, @RequestBody String request){
        boolean judge = userApiService.setStatusService(cookie, request);
        if(!judge){
            return Return.error(Status.SETSTATUSERROR);
        }
        return Return.success();
    }

    @GetMapping("/user/getQRCode")
    public  Result<Object> getQrCode(){
        String data = userApiService.getQrcodeService();
        if(data == null){
            return Return.error(Status.GETQRCODEERROR);
        }
        return Return.success(data);
    }
    @UserJwt

    @PostMapping("/user/loginByCode")
    public Result<Object> loginByCode(@RequestHeader("Cookie") String jwt, @RequestBody String uuid){
        boolean judge = userApiService.loginByCodeService(jwt, uuid);
        if (!judge){
            return Return.error(Status.CHECKQRCODEERROR);
        }
        return Return.success();
    }


    @GetMapping("/user/weblogin")
    public Result<Object> weLogin(){
        Map<Integer,String> response = userApiService.webLogin();
        if(response == null){
            return Return.error(Status.WEBLOGINFALL);
        }else{
            return Return.success(JSON.toJSONString(response));
        }
    }

    @UserJwt
    @PostMapping("/user/applogin")
    public Result<Object> appLogin(@RequestHeader("Cookie") String jwt){
        String newjwt = userApiService.appLogin(jwt);
        if(newjwt == null){
            return Return.error(Status.JWTUPDATEERROR);
        }else {
            return Return.success(newjwt);
        }
    }


    @UserJwt
    @PostMapping("/user/appsign")
    public Result<Object> appSign(@RequestHeader("Cookie") String jwt,@RequestParam("name") String name, @RequestParam("grade") Integer grade,
                               @RequestParam("college") Integer college, @RequestParam("major") String major,
                               @RequestParam("class") String userclass, @RequestParam("dsp") String dsp,
                               @RequestParam("dno") Integer dno, @RequestParam("secdno") Integer secdno,
                               @RequestParam("gender") Boolean gender, @RequestParam("sno") String sno,
                               @RequestParam("qq") String qq, @RequestParam("domitory") String domitory,
                               @RequestParam("know") String know, @RequestParam("party") String party,@RequestParam(value = "checkCode",required = false) String checkCode) {

        Dsign dsign = new Dsign(name, grade, college, major, userclass, dsp, dno, secdno, gender, sno, qq, domitory, know, party);
        String newJwt = userApiService.sign(jwt,dsign,true,checkCode);
        if(newJwt == null){
            return Return.error(Status.POSTAPPSIGNERROR);
        }else{
            return Return.success(newJwt);
        }

    }


    @UserWebJwt
    @PostMapping("/user/websign")
    public Result<Object> webSign(@RequestHeader("Cookie") String jwt,@RequestParam("name") String name, @RequestParam("grade") Integer grade,
                               @RequestParam("college") Integer college, @RequestParam("major") String major,
                               @RequestParam("class") String userclass, @RequestParam("dsp") String dsp,
                               @RequestParam("dno") Integer dno, @RequestParam("secdno") Integer secdno,
                               @RequestParam("gender") Boolean gender, @RequestParam("sno") String sno,
                               @RequestParam("qq") String qq, @RequestParam("domitory") String domitory,
                               @RequestParam("know") String know, @RequestParam("party") String party,@RequestParam(value = "checkCode",required = false) String checkCode) {

        Dsign dsign = new Dsign(name, grade, college, major, userclass, dsp, dno, secdno, gender, sno, qq, domitory, know, party);
        String newJwt = userApiService.sign(jwt,dsign,false,checkCode);
        if(newJwt == null){
            return Return.error(Status.POSTAPPSIGNERROR);
        }else{
            return Return.success(jwt);
        }
    }

    @UserJwt
    @PostMapping("/user/appGetPhone")
    public Result<Object> appGetPhone(@RequestHeader("Cookie") String jwt,@RequestParam("phone") String phone ){
        boolean judge = userApiService.getPhone(jwt, phone, true);
        if (!judge){
            return Return.error(Status.POSTPHONEERROR);
        }else{
            return Return.success();
        }


    }

    @UserWebJwt
    @PostMapping("/user/webGetPhone")
    public Result<Object> webGetPhone(@RequestHeader("Cookie") String jwt,@RequestParam("phone") String phone ){
        boolean judge = userApiService.getPhone(jwt, phone, false);
        if (!judge){
            return Return.error(Status.POSTPHONEERROR);
        }else{
            return Return.success();
        }

    }






}
