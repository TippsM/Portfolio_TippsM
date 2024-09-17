import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class SignUpPage extends LoginPage {


    //--------------------------------------------------------------------

    protected static String FILENAME = "loginInfo.txt";

    private final String defaultPasswordField = "Reenter password";

    protected JTextField reenterPasswordField = new JTextField(defaultPasswordField);

    protected JButton cancelButton = new JButton("Cancel");

    //--------------------------------------------------------------------

    protected SignUpPage(HashMap<String, String> loginInfoO) {
        super(loginInfoO);

        Scanner loginInfoScanner = null;

        try {

            loginInfoScanner = new Scanner(new FileInputStream(FILENAME));

            while (loginInfoScanner.hasNextLine()){
                String accountInfoLine = loginInfoScanner.nextLine();
                String [] accountInfoSplit = accountInfoLine.split(",");

                if (accountInfoSplit.length >= 2) {
                    String userName = accountInfoSplit[0].trim();
                    String passWord = accountInfoSplit[1].trim();

                    loginInfo.put(userName, passWord);

                }


            }

            //--------------------------------------------------------------------


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {
            assert loginInfoScanner != null;
            loginInfoScanner.close();
        }

        //--------------------------------------------------------------------

        reenterPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (reenterPasswordField.getText().equals(defaultPasswordField)){
                    reenterPasswordField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (reenterPasswordField.getText().isEmpty()){
                    reenterPasswordField.setText(defaultPasswordField);
                }

            }
        });

        welcomeLabel.setText("CREATE ACCOUNT");

        reenterPasswordField.setBounds(125, 250, 200, 25);

        resetButton.setBounds(227, 300, 100, 25);

        signUpButton.setBounds(125, 300, 100, 25);

        cancelButton.setBounds(176, 330, 100, 25);
        cancelButton.addActionListener(this);
        cancelButton.setFocusable(false);

        //--------------------------------------------------------------------

        frame.add(reenterPasswordField);
        frame.add(signUpButton);
        frame.add(cancelButton);
        loginButton.setVisible(false);

        //--------------------------------------------------------------------
    }

    //--------------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == resetButton){
            userIDField.setText("");
            userPasswordField.setText("");
            reenterPasswordField.setText("");
        }

        if (e.getSource() == signUpButton){

            String userID = userIDField.getText();
            String userPassword = String.valueOf(userPasswordField.getText());
            String reenterUserPassword = String.valueOf(reenterPasswordField.getText());

            if (userPassword.length() != 7){
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Password must be at least 7 characters long!");
            }


            if (!userPassword.equals(reenterUserPassword)){

                messageLabel.setForeground(Color.red);
                messageLabel.setText("Passwords do not match!");

            }else if (userID.isEmpty()){
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Please enter a userID.");

            } else if (userPassword.length() < 7){
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Password must be at least 7 characters long!");

            } else {

                if (loginInfo.containsKey(userID)){
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("User already exists.");
                }else {

                    PrintWriter loginInfoWriter = null;
                    Scanner loginInfoScanner = null;

                    try {

                        loginInfoScanner = new Scanner(new FileInputStream("loginInfo.txt"));

                        loginInfoWriter = new PrintWriter(new FileOutputStream("loginInfo.txt", true));
                        loginInfoWriter.println(userID + "," + userPassword);

                        while (loginInfoScanner.hasNextLine()){
                            String accountInfoLine = loginInfoScanner.nextLine();
                            String [] accountInfoSplit = accountInfoLine.split(",");

                            if (accountInfoSplit.length >= 2) {
                                String userName = accountInfoSplit[0].trim();
                                String passWord = accountInfoSplit[1].trim();

                                loginInfo.put(userName, passWord);


                            }

                        }

                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }finally {
                        assert loginInfoWriter != null;
                        loginInfoWriter.close();
                        loginInfoScanner.close();
                    }


                    loginInfo.put(userID, userPassword);
                    userIDField.setText("");
                    userPasswordField.setText("");
                    reenterPasswordField.setText("");
                    frame.dispose();
                }
            }
        }

        if (e.getSource() == cancelButton){
            frame.dispose();
        }

    }
}