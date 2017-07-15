import com.bigzhang.socket.simple.client.ClientImpl;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by abc on 2017/7/15.
 */
public class ClientImplTest  extends TestCase {

    Logger logger = LoggerFactory.getLogger(ServerImplTest.class);

    public void setUp() throws Exception {
        super.setUp();
    }

    public void test(){
        ClientImpl client = new ClientImpl();
        client.start();
    }
}
