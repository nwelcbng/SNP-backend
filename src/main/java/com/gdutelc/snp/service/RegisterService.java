package com.gdutelc.snp.service;

import org.springframework.stereotype.Component;

/**
 * @author kid
 */

@Component
public interface RegisterService {

    /**
     * 获取code返回userjwt
     * @param   code  前端发送的code
     * @return  生成的userjwt
     */
    public String registerService(String code);

}
