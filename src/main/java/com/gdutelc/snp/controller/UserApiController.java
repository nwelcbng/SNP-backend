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
    public Result<Object> getform(@RequestHeader("Cookie") String cookie){
        String data = userApiService.getFormService(cookie);
        if (data == null){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }

}
