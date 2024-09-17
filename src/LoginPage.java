import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class LoginPage implements ActionListener {

    //--------------------------------------------------------------------
    protected final String defaultUserID = "Enter UserID";
    protected final String defaultPassword = "Enter Password";

    //--------------------------------------------------------------------
    protected JFrame frame = new JFrame();
    protected JLabel welcomeLabel = new JLabel("PASSWORD MANAGER");
    protected JLabel messageLabel = new JLabel();
    protected JLabel userIDLabel = new JLabel("UserID");
    protected JLabel userPasswordLabel = new JLabel("Password");

    //--------------------------------------------------------------------
    protected JButton loginButton = new JButton("Log In");
    protected JButton resetButton = new JButton("Reset");
    protected JButton signUpButton =  new JButton("Sign Up");

    //--------------------------------------------------------------------
    protected JTextField userIDField = new JTextField(defaultUserID);
    protected JTextField userPasswordField = new JTextField(defaultPassword);

    //--------------------------------------------------------------------

    protected HashMap<String, String> loginInfo;

    //--------------------------------------------------------------------

    protected LoginPage(HashMap<String, String> loginInfoOriginal){

        //--------------------------------------------------------------------

        userPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userPasswordField.getText().equals(defaultPassword)){
                    userPasswordField.setText("");
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userPasswordField.getText().isEmpty()){
                    userPasswordField.setText(defaultPassword);
                }

            }
        });

        userIDField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userIDField.getText().equals(defaultUserID)){
                    userIDField.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (userIDField.getText().isEmpty()){
                    userIDField.setText(defaultUserID);
                }
            }
        });

        //--------------------------------------------------------------------


        loginInfo = loginInfoOriginal;

        welcomeLabel.setBounds(126, 75, 200, 35);
        welcomeLabel.setFont(new Font(null, Font.BOLD, 17));

        userIDLabel.setBounds(50, 150, 75, 25);
        userPasswordLabel.setBounds(50, 200, 75, 25);

        messageLabel.setBounds(120,105,300,35);
        messageLabel.setFont(new Font(null, Font.PLAIN, 12));


        userIDField.setBounds(125,150, 200, 25);
        userPasswordField.setBounds(125,200, 200, 25);

        loginButton.setBounds(123, 250, 100, 25);
        loginButton.addActionListener(this);

        resetButton.setBounds(226, 250, 100, 25);
        resetButton.addActionListener(this);

        signUpButton.setBounds(175, 280, 100, 25);
        signUpButton.addActionListener(this);

        //--------------------------------------------------------------------

        signUpButton.setFocusable(false);
        loginButton.setFocusable(false);
        resetButton.setFocusable(false);

        //--------------------------------------------------------------------


        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(signUpButton);
        frame.add(messageLabel);
        frame.add(welcomeLabel);

        //--------------------------------------------------------------------


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(425, 425);
        frame.setLayout(null);
        frame.setVisible(true);

        //--------------------------------------------------------------------

    }

    //--------------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e) {

        loadLoginInfo();

        if (e.getSource() == signUpButton){
            SignUpPage signUpPage = new SignUpPage(loginInfo);
        }

        if (e.getSource() == resetButton){
            userIDField.setText("");
            userPasswordField.setText("");
        }

        if (e.getSource() == loginButton){


            String userID = userIDField.getText();
            String userPassword = String.valueOf(userPasswordField.getText());

            if (loginInfo.containsKey(userID)){
                if (loginInfo.get(userID).equals(userPassword)){
                    userIDField.setText("");
                    userPasswordField.setText("");
                    messageLabel.setText("");
                    PassWordManagerPage welcomePage = new PassWordManagerPage(userID);
                }else {
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("That username or password didn't work.");
                }

            } else if (userID.isEmpty() || userPassword.isEmpty()) {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("Please enter a username or password!");
            } else {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("That username or password didn't work.");
            }
        }

    }

    //-------------------------------------------------------------------

    protected void loadLoginInfo() {

        Scanner loginInfoScanner;

        try {

            loginInfoScanner = new Scanner(new FileInputStream("loginInfo.txt"));

            while (loginInfoScanner.hasNextLine()) {
                String accountInfoLine = loginInfoScanner.nextLine();
                String[] accountInfoSplit = accountInfoLine.split(",");

                if (accountInfoSplit.length >= 2) {
                    String userName = accountInfoSplit[0].trim();
                    String passWord = accountInfoSplit[1].trim();

                    loginInfo.put(userName, passWord);
                }


            }

            loginInfoScanner.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //--------------------------------------------------------------------


    }

    //--------------------------------------------------------------------

}
