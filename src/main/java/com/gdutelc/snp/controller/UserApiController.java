package com.gdutelc.snp.controller;
import com.gdutelc.snp.annotation.UserJwt;
import com.gdutelc.snp.dto.Dsign;
import com.gdutelc.snp.result.Result;
import com.gdutelc.snp.result.Return;
import com.gdutelc.snp.result.Status;
import com.gdutelc.snp.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kid
 */
@RestController
public class UserApiController  extends BaseController{
    @Autowired
    private UserApiService userApiService;



    @CrossOrigin
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
    @CrossOrigin
    @GetMapping("/user/getform")
    public Result<Object> getForm(@RequestHeader("Cookie") String cookie){
        String data = userApiService.getFormService(cookie);
        if (data == null){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }
    @UserJwt
    @CrossOrigin
    @GetMapping("/user//user/getWebForm")
    public Result<Object> getWebForm(@RequestHeader("Cookie") String cookie){
        String data = userApiService.getWebFormService(cookie);
        if (data == null){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }


    @UserJwt
    @CrossOrigin
    @PutMapping("/user/setstatus")
    public Result<Object> setStatus(@RequestHeader("Cookie") String cookie, @RequestBody String request){
        boolean judge = userApiService.setStatusService(cookie, request);
        if(!judge){
            return Return.error(Status.SETSTATUSERROR);
        }
        return Return.success();
    }
    @CrossOrigin
    @GetMapping("/user/getQRCode")
    public  Result<Object> getQrCode(){
        String data = userApiService.getQrcodeService();
        if(data == null){
            return Return.error(Status.GETQRCODEERROR);
        }
        return Return.success(data);
    }
    @UserJwt
    @CrossOrigin
    @PostMapping("/user/loginByCode")
    public Result<Object> loginByCode(@RequestHeader("Cookie") String jwt, @RequestBody String uuid){
        boolean judge = userApiService.loginByCodeService(jwt, uuid);
        if (!judge){
            return Return.error(Status.CHECKQRCODEERROR);
        }
        return Return.success();
    }
    @CrossOrigin
    @GetMapping("/user/weblogin")
    public Result<Object> weLogin(){
        String jwt = userApiService.webLogin();
        if(jwt == null){
            return Return.error(Status.WEBLOGINFALL);
        }else{
            return Return.success(jwt);
        }
    }
    @UserJwt
    @CrossOrigin
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
    @CrossOrigin
    @PostMapping("/user/appsign")
    public Result<Object> appSign(@RequestHeader("Cookie") String jwt,@RequestParam("name") String name, @RequestParam("grade") Integer grade,
                               @RequestParam("college") Integer college, @RequestParam("major") String major,
                               @RequestParam("class") String userclass, @RequestParam("dsp") String dsp,
                               @RequestParam("dno") Integer dno, @RequestParam("secdno") Integer secdno,
                               @RequestParam("gender") Boolean gender, @RequestParam("sno") String sno,
                               @RequestParam("qq") String qq, @RequestParam("domitory") String domitory,
                               @RequestParam("know") String know, @RequestParam("party") String party) {

        Dsign dsign = new Dsign(name, grade, college, major, userclass, dsp, dno, secdno, gender, sno, qq, domitory, know, party);
        boolean judge = userApiService.sign(jwt,dsign,true);
        if(!judge){
            return Return.error(Status.POSTAPPSIGNERROR);
        }else{
            return Return.success();
        }

    }
    @UserJwt
    @CrossOrigin
    @PostMapping("/user/websign")
    public Result<Object> webSign(@RequestHeader("Cookie") String jwt,@RequestParam("name") String name, @RequestParam("grade") Integer grade,
                               @RequestParam("college") Integer college, @RequestParam("major") String major,
                               @RequestParam("class") String userclass, @RequestParam("dsp") String dsp,
                               @RequestParam("dno") Integer dno, @RequestParam("secdno") Integer secdno,
                               @RequestParam("gender") Boolean gender, @RequestParam("sno") String sno,
                               @RequestParam("qq") String qq, @RequestParam("domitory") String domitory,
                               @RequestParam("know") String know, @RequestParam("party") String party) {

        Dsign dsign = new Dsign(name, grade, college, major, userclass, dsp, dno, secdno, gender, sno, qq, domitory, know, party);
        boolean judge = userApiService.sign(jwt,dsign,false);
        if(!judge){
            return Return.error(Status.POSTAPPSIGNERROR);
        }else{
            return Return.success();
        }

    }



}
