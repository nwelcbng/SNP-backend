package com.gdutelc.snp.controller;
import com.gdutelc.snp.annotation.UserJwt;
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
public class UserApiController {
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
    @PostMapping("/user/getphone")
    public Result<Object> getPhone(@RequestHeader("Cookie") String cookie, @RequestBody String request){
        return null;

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
        String data = userApiService.getFormService(cookie);
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
    @CrossOrigin
    @PostMapping("/user/loginByCode")
    public Result<Object> loginByCode(@RequestHeader("Cookie") String jwt, @RequestBody String uuid){
        boolean judge = userApiService.loginByCodeService(jwt, uuid);
        if (!judge){
            return Return.error(Status.CHECKQRCODEERROR);
        }
        return Return.success();

    }



}
