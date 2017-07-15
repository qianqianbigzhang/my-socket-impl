import com.bigzhang.socket.simple.server.ServerImpl;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by abc on 2017/7/15.
 */
public class ServerImplTest  extends TestCase {

    Logger logger = LoggerFactory.getLogger(ServerImplTest.class);

    public void setUp() throws Exception {
        super.setUp();
    }

    public void test(){
        ServerImpl server = new ServerImpl();
        server.start();
    }

}

