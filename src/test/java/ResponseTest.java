import com.cxy.tool.ResponseResult;
import org.junit.Test;

/**
 * @author CXY
 * @className Test
 * @description 测试类
 * @date 2025/01/16 11:14
 */
public class ResponseTest {

    @Test
    public void ResponseTest(){
        System.out.println(myRequest());
    }

    private ResponseResult<String> myRequest(){
        return new ResponseResult<String>().success("成功");
    }

}
