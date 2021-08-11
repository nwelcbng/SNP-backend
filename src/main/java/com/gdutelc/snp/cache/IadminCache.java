package com.gdutelc.snp.cache;

import org.springframework.stereotype.Component;

/**
 * @author kid
 */
@Component
public interface IadminCache {
    /**
     * 获取密码
     *
     * @param username username
     * @return 查到的密码
     */
    String getPassowrdByUsername(String username);
}
