package com.gdutelc.snp.controller;
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
public class AdminApiController {
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
}
