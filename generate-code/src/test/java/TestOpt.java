import org.junit.Test;

import java.util.UUID;

/**
 * @author liangc
 * @version V1.0
 * @date ${DATE}
 **/
public class TestOpt {

    @Test
    public void testOpt(){


//        Collections.reverse();

//        List<Long> idList = null;
//        List<Long> longs = Optional.ofNullable(idList)..orElse(idList);
//        System.out.println(longs);
    }

    @Test
    public void testUUidLength(){
        System.out.println(UUID.randomUUID().toString().replace("-","").length());
    }
}
