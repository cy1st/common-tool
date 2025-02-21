import com.cxy.tool.DateTool;
import com.cxy.tool.ResponseResult;
import org.junit.Test;

import java.util.Date;

/**
 * @author CXY
 * @className Test
 * @description 测试类
 * @date 2025/01/16 11:14
 */
public class ResponseTest {

    @Test
    public void ResponseTest(){
        Date date = new Date();
        String f1 = DateTool.format(date, DateTool.FormatSymbol.Day);
        String f2 = DateTool.format(date, DateTool.FormatSymbol.Time);
        System.out.println(f1);
        System.out.println(f2);
    }

    private ResponseResult<String> myRequest(){
        return new ResponseResult<String>().success("成功");
    }

}
