package com.kim.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author kim
 * httpclient工具类
 * @Since 2021/7/9
 */
public class HttpClientUtils {

    /**
     * get请求，参数拼接在url后面,不设置headers
     * @param url String url地址
     * @return String 接口返回值
     * */
    public static String doGet(String url){
        return doGet(url,null);
    }

    /**
     * get请求，参数拼接在url后面,设置headers
     * @param url String url地址
     * @param headers Map<String,String> 请求头
     * @return String 接口返回值
     * */
    public static String doGet(String url,Map<String,String> headers) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        if(headers!= null && headers.size()>0){
            headers.forEach((k, v) -> get.addHeader(k,v) );
        }
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity,"UTF-8");
            }
            return result;

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * get请求，参数不拼接在url后面,参数封装在一个map对象里，不设置headers
     * @param url String url地址
     * @param param Map<String,String> 请求参数
     * @return String 接口返回值
     * */
    public static String doGetWithParam(String url,Map<String,String> param){
        return doGetWithParam(url,param,null);
    }

    /**
     * get请求，参数不拼接在url后面,参数封装在一个map对象里，设置headers
     * @param url String url地址
     * @param param Map<String,String> 请求参数
     * @param headers Map<String,String> 请求头
     * @return String 接口返回值
     * */
    public static String doGetWithParam(String url, Map<String, String> param,Map<String,String> headers) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            builder.setParameters(pairs);
            HttpGet get = new HttpGet(builder.build());
            if(headers!= null && headers.size()>0){
                headers.forEach((k, v) -> get.addHeader(k,v) );
            }
            response = httpClient.execute(get);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity,"UTF-8");
            }
            return result;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * post请求,参数封装在一个map对象里，不设置headers
     * @param url String url地址
     * @param param Map<String,String> 请求参数
     * @return String 接口返回值
     * */
    public static String doPost(String url, Map<String, String> param){
        return doPost(url,param,null);
    }

    /**
     * post请求,参数封装在一个map对象里，设置headers
     * @param url String url地址
     * @param param Map<String,String> 请求参数
     * @param headers Map<String,String> 请求头
     * @return String 接口返回值
     * */
    public static String doPost(String url, Map<String, String> param,Map<String,String> headers) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
            if(headers!= null && headers.size()>0){
                headers.forEach((k, v) -> post.addHeader(k,v) );
            }
            response = httpClient.execute(post);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity,"UTF-8");
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    /**
     * post请求,参数为json字符串，不设置headers
     * @param url String url地址
     * @param jsonString 请求参数
     * @return String 接口返回值
     * */
    public static String doPostByJson(String url, String jsonString){
        return doPostByJson(url,jsonString,null);
    }

    /**
     * post请求,参数为json字符串，不设置headers
     * @param url String url地址
     * @param jsonString 请求参数
     * @param headers Map<String,String> 请求头
     * @return String 接口返回值
     * */
    public static String doPostByJson(String url, String jsonString,Map<String,String> headers) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            if(headers!= null && headers.size()>0){
                headers.forEach((k, v) -> post.addHeader(k,v) );
            }
            post.setEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")));
            response = httpClient.execute(post);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity,"UTF-8");
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * httpclient发送文件，无其他参数
     * @param url 接口url
     * @param in    文件流
     * @return 返回值
     * */
    public static String httpClientUploadFile(String url,InputStream in){
        return httpClientUploadFile(url,in,null);
    }

    /**
     * httpclient发送文件，含其他参数
     * @param url 接口url
     * @param param 参数
     * @param in    文件流
     * @return 返回值
     * */
    public static String httpClientUploadFile(String url, InputStream in,Map<String,String> param) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", in, ContentType.MULTIPART_FORM_DATA, null);// 文件流，类似于表单的<input name="file" type="file"/>
            if(param != null && param.size()>0){
                param.forEach((k, v) -> builder.addTextBody(k,v));//类似浏览器表单提交，对应input的name和value
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //实体转换成字符串
    /*private static String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if (entity != null) {
            long lenth = entity.getContentLength();
            if (lenth != -1 && lenth < 2048) {
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
                InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while ((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }*/


}
