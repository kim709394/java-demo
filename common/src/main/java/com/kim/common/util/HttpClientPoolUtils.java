package com.kim.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author kim
 * httpclient连接池工具类
 * @Since 2021/7/12
 */
public class HttpClientPoolUtils {

    //请求客户端
    private static CloseableHttpClient httpClient;

    //初始化httpclient连接池
    static {
        try {
            //设置https证书支持
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf)
                    .build();
            // 初始化连接管理器
            PoolingHttpClientConnectionManager poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolConnManager.setMaxTotal(640);// 同时最大连接数
            // 设置最大路由,同一url连接对象的最大并发数量
            poolConnManager.setDefaultMaxPerRoute(320);
            /**
             * MaxtTotal和DefaultMaxPerRoute的区别：
             * MaxtTotal指整个池子的所有连接数的最大值，DefaultMaxPerRoute是指同一个url请求的最大并发数
             * 比如一个池子里MaxtTotal设置为20，DefaultMaxPerRoute设置为10
             * 先执行100次url为a的请求，后执行100次url为b的请求，那么连接池里只有10个a连接，10个b连接，总数是20个。
             * 如果继续执行url为c的请求，那么池子里的a和b连接将会分一部分作为c的连接
             */
            //封装池化的httpclient
            RequestConfig config = RequestConfig
                    .custom()
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000)
                    .build();
            httpClient = HttpClients.custom()
                    .setConnectionManager(poolConnManager)
                    .setDefaultRequestConfig(config)
                    // 设置重试次数
                    .setRetryHandler(new DefaultHttpRequestRetryHandler(2, false)).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }


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
    public static String doGet(String url, Map<String,String> headers) {
        return doGet(httpClient,url,headers);
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
    public static String doGetWithParam(String url, Map<String, String> param,Map<String,String> headers){
        return doGetWithParam(httpClient,url,param,headers);
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
    public static String doPost(String url, Map<String, String> param,Map<String,String> headers){
        return doPost(httpClient,url,param,headers);
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
    public static String doPostByJson(String url, String jsonString,Map<String,String> headers){
        return doPostByJson(httpClient,url,jsonString,headers);
    }

    /**
     * httpclient发送文件，无其他参数
     * @param url 接口url
     * @param in    文件流
     * @return 返回值
     * */
    public static String httpClientUploadFile(String url, InputStream in){
        return httpClientUploadFile(url,in,null);
    }

    /**
     * httpclient发送文件，含其他参数
     * @param url 接口url
     * @param param 参数
     * @param in    文件流
     * @return 返回值
     * */
    public static String httpClientUploadFile(String url, InputStream in,Map<String,String> param){
        return httpClientUploadFile(httpClient,url,in,param);
    }

    private static String doGet(CloseableHttpClient httpClient,String url,Map<String,String> headers) {
        String result = null;
        if(httpClient == null){
            httpClient = HttpClients.createDefault();
        }
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
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String doGetWithParam(CloseableHttpClient httpClient,String url, Map<String, String> param,Map<String,String> headers) {
        String result = null;
        if(httpClient == null){
            httpClient = HttpClients.createDefault();
        }
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
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static String doPost(CloseableHttpClient httpClient,String url, Map<String, String> param,Map<String,String> headers) {
        String result = null;
        if(httpClient == null){
            httpClient = HttpClients.createDefault();
        }
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
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private static String doPostByJson(CloseableHttpClient httpClient,String url, String jsonString,Map<String,String> headers) {
        String result = null;
        if(httpClient == null){
            httpClient = HttpClients.createDefault();
        }
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
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String httpClientUploadFile(CloseableHttpClient httpClient,String url, InputStream in,Map<String,String> param) {

        if(httpClient == null){
            httpClient = HttpClients.createDefault();
        }
        String result = "";
        HttpResponse response=null;
        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", in, ContentType.MULTIPART_FORM_DATA, null);// 文件流，类似于表单的<input name="file" type="file"/>
            if(param != null && param.size()>0){
                param.forEach((k, v) -> builder.addTextBody(k,v));//类似浏览器表单提交，对应input的name和value
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
