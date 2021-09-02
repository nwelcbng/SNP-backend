package com.gdutelc.snp.controller;
import com.gdutelc.snp.annotation.AdminJwt;
import com.gdutelc.snp.result.Result;
import com.gdutelc.snp.result.Return;
import com.gdutelc.snp.result.Status;
import com.gdutelc.snp.service.AdminApiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public Result<Object> getDsignByGender(@RequestBody boolean gender){
        String data = adminApiService.getDsignByGender(gender);
        if (data == null){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }

    @AdminJwt
    @GetMapping("/admin/getbycolle")
    public Result<Object> getDsignByCollege(@RequestBody Integer college){
        String data = adminApiService.getDsignByCollege(college);
        if (data == null){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }

    @AdminJwt
    @GetMapping("/admin/getbydno")
    public Result<Object> getDsignByDno(@RequestBody Integer dno){
        String data = adminApiService.getDsignByDno(dno);
        if (data == null){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }

    @AdminJwt
    @GetMapping("/admin/confirm")
    public Result<Object> confirm(@RequestHeader("Cookie") String jwt, @RequestParam("uid") Integer uid,@RequestParam("check") boolean check){
        adminApiService.confirm(jwt, uid, check);
        return Return.success();
    }
}
