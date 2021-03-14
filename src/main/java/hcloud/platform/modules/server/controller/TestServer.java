package hcloud.platform.modules.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hcloud.platform.base.view.ApiResponse;
import hcloud.platform.modules.server.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class TestServer {

    @Autowired
    private RestTemplate restTemplate;

    public ApiResponse<JSONObject> push(String message) {
        String url = "https://sc.ftqq.com/SCU43896Te0fffbe53bbee6af69bdceb54a0f52e75c53d305be2e6.send?text=" + message;
        String object = restTemplate.getForObject(url, String.class);
        return ApiResponse.success(JSON.parseObject(object));
    }

    public ApiResponse<JSONObject> pushPost(Message message) {
        String url = "https://sc.ftqq.com/SCU43896Te0fffbe53bbee6af69bdceb54a0f52e75c53d305be2e6.send";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("text", message.getText());
        params.add("desp", message.getDesp());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return ApiResponse.success(JSON.parseObject(response.getBody()));
    }
}
