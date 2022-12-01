package com.kim.mini.tomcat.config;

import com.kim.mini.tomcat.servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huangjie
 * @description
 * @date 2022-12-01
 */
public class ServletContext {

    private static volatile ServletContext context;

    private Map<String, HttpServlet> servletMap;

    private ServletContext() throws Exception {
        servletMapInit();
    }

    private void servletMapInit() throws Exception {
        servletMap = new ConcurrentHashMap<>();
        InputStream webXMLStream = ServletContext.class.getClassLoader().getResourceAsStream("conf/web.xml");
        SAXReader webXML=new SAXReader();
        Document document = webXML.read(webXMLStream);
        Element rootElement = document.getRootElement();
        List<Element> servletMappings = rootElement.elements("servlet-mapping");
        List<Element> servlets = rootElement.elements("servlet");
        servletMappings.stream().forEach(element -> {
            String url= element.element("url-pattern").getTextTrim();
            String name=element.elementText("servlet-name");
            Element servlet = servlets.stream().filter(element1 -> element1.elementText("servlet-name").equalsIgnoreCase(name)).findFirst().get();
            String servletUrl = servlet.elementText("servlet-class");
            HttpServlet httpServlet = loadServlet(servletUrl);
            servletMap.put(url,httpServlet);
        });
    }

    private HttpServlet loadServlet(String servletUrl){
        try {
            Class<?> servletClass = ClassLoader.getSystemClassLoader().loadClass(servletUrl);
            HttpServlet httpServlet = (HttpServlet)servletClass.newInstance();
            httpServlet.init();
            return httpServlet;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ServletContext getInstance() throws Exception {
        if(context == null ){
            synchronized (ServletContext.class){
                if(context == null){
                    return new ServletContext();
                }

            }
        }
        return context;
    }


    public HttpServlet get(String url){
        return this.servletMap.get(url);
    }

}
