import java.util.HashMap;

public class IDAndPasswords {

    //--------------------------------------------------------------------
    protected HashMap<String, String> loginInfo = new HashMap<>();

    //--------------------------------------------------------------------

    protected IDAndPasswords(){
        loginInfo.put("bro", "test0");
        loginInfo.put("matt", "test1");
    }

    //--------------------------------------------------------------------
    protected HashMap<String, String> getLoginInfo() {
        return loginInfo;
    }

    //--------------------------------------------------------------------
}
