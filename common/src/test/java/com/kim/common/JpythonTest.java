package com.kim.common;

import com.kim.common.util.ExecCmdUtil;
import org.junit.jupiter.api.Test;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author kim
 * @Since 2021/4/22
 * java执行python代码的两种方式
 */
public class JpythonTest {

    /**
     * 方式一，使用Jython框架的方式。此方式支持的python类库有限，外部引用的类库不支持
     */
    @Test
    public void useJython() {
        //直接执行字符串python脚本的字符串
        PythonInterpreter pythonInterpreter = new PythonInterpreter();
        pythonInterpreter.exec("a=[5,4,3,2,1]; ");
        pythonInterpreter.exec("print(sorted(a));");


        //执行写在文件的python脚本
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("python/add.py");
        //加载脚本
        pythonInterpreter.execfile(in);
        //获取脚本中的函数，传入函数名，返回py函数
        PyFunction fun = pythonInterpreter.get("add", PyFunction.class);
        int a = 5, b = 10;
        //给函数传参，要把java的数据类型转换成py数据类型，调用函数
        PyObject pyObject = fun.__call__(new PyInteger(a), new PyInteger(b));
        //输出结果
        System.out.println("执行结果:" + pyObject);

    }

    /**
     * 方式二:执行shell脚本的方式执行python脚本,可以执行第三方py类库，推荐此种方法
     */
    @Test
    public void executePyByRuntime() throws IOException, InterruptedException {

        String exe = ExecCmdUtil.exec(new String[]{"python", "add.py", "5", "6"});
        System.out.println(exe);

    }


}
