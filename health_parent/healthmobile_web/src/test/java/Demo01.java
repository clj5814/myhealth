import com.itheima.health.pojo.Member;
import org.junit.Test;

import java.util.Date;

public class Demo01 {
    @Test
    public void fun(){
//        Member member=null;
        Member member=new Member();
        member.setRegTime(new Date());
        System.out.println(member);
    }
}
