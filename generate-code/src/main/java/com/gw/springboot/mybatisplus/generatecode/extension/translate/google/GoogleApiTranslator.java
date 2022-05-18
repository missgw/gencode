package com.gw.springboot.mybatisplus.generatecode.extension.translate.google;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 借助google的翻译功能
 * 经过检测，该接口有流量限制，用脚本跑是有问题的
 *
 * <pre>
 * 我们的系统检测到您的计算机网络中存在异常流量。请稍后重新发送请求。为什么会这样？
 *
 * 如果 Google 自动检测到从您的计算机网络发出的请求可能违反服务条款，就会显示此网页。这些请求一旦停止，此阻断即会失效。
 * 该流量可能是由恶意软件、浏览器插件或发送自动请求的脚本发出的。
 * 如果您与他人共享网络连接，请向管理员寻求帮助 — 这可能是由多台计算机使用同一 IP 地址导致的。了解详情
 * 有时，如果您使用的是自动程序普遍使用的高级条款或者正在快速连续地发送请求，则可能会出现此网页。
 * </pre>
 */
@Slf4j
public class GoogleApiTranslator {
    public static String translate(String langFrom, String langTo, String word) throws Exception {
        String url = "https://translate.googleapis.com/translate_a/single?" +
                "client=gtx&" +
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;

        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return parseResult(response.toString());
    }

    private static String parseResult(String inputJson) {
        /*
         * inputJson for word 'hello' translated to language Hindi from English-
         * [[["नमस्ते","hello",,,1]],,"en"]
         * We have to get 'नमस्ते ' from this json.
         */
        log.info(inputJson);

        JSONArray jsonArray = JSON.parseArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);

        StringBuilder result = new StringBuilder();
        for (Object o : jsonArray2) {
            result.append(((JSONArray) o).get(0).toString());
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            String translate = GoogleApiTranslator.translate("zh-CN", "en", "法定代表人");
            log.info(translate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
