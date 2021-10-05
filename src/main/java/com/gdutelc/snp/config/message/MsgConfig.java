package com.gdutelc.snp.config.message;

import com.gdutelc.snp.exception.UserServiceException;
import com.gdutelc.snp.result.Status;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @author kid
 */
@Configuration
public class MsgConfig {

    public static boolean sendPhoneMessage(String phone, String vCode) {
        String url = "https://api.mysubmail.com/message/xsend.json";
        String appId = "66337";
        String signature = "58feab13c7c6661deb170db6d6f7ae07";
        String project = "FMf5B4";
        String vars = "{\"code\":\"" + vCode + "\"}";
        String requestBody = "appid=" + appId + "&to=" + phone + "&project=" + project + "&signature=" + signature + "&vars=" + vars;

        HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(5000)).build();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .timeout(Duration.ofMillis(5009))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var res = response.body();
            return res.contains("\"success\"");
        } catch (IOException | InterruptedException ignored) {
            throw new UserServiceException(Status.PHONECODEERROR,Status.PHONECODEERROR.getMsg());
        }

    }

}
