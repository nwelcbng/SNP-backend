package com.gdutelc.snp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdutelc.snp.annotation.UserJwt;
import com.gdutelc.snp.result.Result;
import com.gdutelc.snp.result.Return;
import com.gdutelc.snp.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kid
 */
@RestController
public class UserApiController {
    @Autowired
    private UserApiService registerService;

    @CrossOrigin
    @PostMapping("/user/register")
    public Result<Object> getCode(@RequestBody String code){
        String jwt = registerService.registerService(code);
        return  Return.success(jwt);
    }
    @CrossOrigin
    @PostMapping("/user/getphone")
    public Result<Object> getPhone(@RequestHeader("Cookie") String Cookie, @RequestBody String request){
        return null;

    }

}
