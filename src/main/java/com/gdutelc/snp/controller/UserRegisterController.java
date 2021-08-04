package com.gdutelc.snp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kid
 */
@RestController
@RequestMapping("/user")
public class UserRegisterController {

    @PostMapping("/register")
    public void getcode(@RequestParam(value = "code") String code){

    }
}
