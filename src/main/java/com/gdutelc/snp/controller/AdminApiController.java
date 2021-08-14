package com.gdutelc.snp.controller;
import com.gdutelc.snp.annotation.AdminJwt;
import com.gdutelc.snp.result.Result;
import com.gdutelc.snp.result.Return;
import com.gdutelc.snp.result.Status;
import com.gdutelc.snp.service.impl.AdminApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kid
 */
@RestController
public class AdminApiController extends BaseController{
    @Autowired
    private  AdminApiServiceImpl adminApiService;




    @CrossOrigin
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
    @CrossOrigin
    @GetMapping("admin/getbygen")
    public Result<Object> getDsignByGender(@RequestBody boolean gender){
        String data = adminApiService.getDsignByGender(gender);
        if (data == null){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }

    @AdminJwt
    @CrossOrigin
    @GetMapping("/admin/getbycolle")
    public Result<Object> getDsignByCollege(@RequestBody Integer college){
        String data = adminApiService.getDsignByCollege(college);
        if (data == null){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }

    @AdminJwt
    @CrossOrigin
    @GetMapping("/admin/getbydno")
    public Result<Object> getDsignByDno(@RequestBody Integer dno){
        String data = adminApiService.getDsignByDno(dno);
        if (data == null){
            return Return.error(Status.GETFORMERROR);
        }
        return Return.success(data);
    }
}
