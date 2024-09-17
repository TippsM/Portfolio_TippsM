public class Main {

    public static void main(String[] args) {

        //----------------------------------------------------------------------

        IDAndPasswords iDandPasswords = new IDAndPasswords();

        //PassWordManagerPage p = new PassWordManagerPage("Name");  //Tester for PasswordManagerPage class

        LoginPage loginPage = new LoginPage(iDandPasswords.getLoginInfo());

        //----------------------------------------------------------------------

    }
}