import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;


public class PassWordManagerPage implements ActionListener {

    //-----------------------------------------------------------
    protected int passwordCounter = 1;
    protected String sourceFileName;
    protected File sourceDataFile;

    //-----------------------------------------------------------
    protected JFrame frame = new JFrame();
    protected String username;
    protected String getUsername(){
        return username;
    }

    //-----------------------------------------------------------
    protected JLabel welcomeLabel;
    protected JLabel jLabel = new JLabel("PASSWORD MANAGER");
    protected JLabel newUsernameLabel = new JLabel("Username");
    protected JLabel newPasswordLabel = new JLabel("Password");
    protected JLabel errorMessageLabel = new JLabel();
    protected JLabel newPasswordMsgLabel = new JLabel("Add details of a new password to store");

    //-----------------------------------------------------------
    protected JButton logOutButton = new JButton("Log out");
    protected JButton addInfoButton = new JButton("Add");
    protected JTextField newUsernameField = new JTextField();
    protected JTextField newPasswordField = new JTextField();

    //-----------------------------------------------------------
    protected DefaultTableModel dataTableModel = new DefaultTableModel();
    protected JTable userDataTable = new JTable(dataTableModel);
    protected JScrollPane dataScrollTable = new JScrollPane(userDataTable);

    //---------------------------------------------------------------

    protected PassWordManagerPage(String usernameOriginal) {

        username = usernameOriginal;


        sourceFileName = getUsername() + "Data.txt";
        sourceDataFile = new File(sourceFileName);

        try {
            if (!sourceDataFile.exists()){
                if (sourceDataFile.createNewFile()){
                    System.out.println("File created: " + sourceFileName);
                }else {
                    System.out.println("File failed to create!");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        welcomeLabel = new JLabel("Welcome back, " + usernameOriginal + "!");

        jLabel.setBounds(265, 40, 200, 35);
        jLabel.setFont(new Font(null, Font.BOLD, 17));

        welcomeLabel.setBounds(285, 65, 200, 35);
        welcomeLabel.setFont(new Font(null, Font.PLAIN, 15));

        newPasswordMsgLabel.setBounds(235, 90, 300, 35);
        newPasswordMsgLabel.setFont(new Font(null, Font.PLAIN, 15));

        logOutButton.setBounds(505, 97, 80, 20);
        logOutButton.addActionListener(this);
        logOutButton.setFocusable(false);

        newUsernameField.setBounds(230, 170, 300, 35);
        newPasswordField.setBounds(230, 240, 300, 35);

        newUsernameLabel.setBounds(230, 142, 300, 35);
        newPasswordLabel.setBounds(230, 213, 300, 35);

        addInfoButton.setBounds(230, 420, 300, 25);
        addInfoButton.addActionListener(this);
        addInfoButton.setFocusable(false);

        errorMessageLabel.setBounds(275, 115, 300, 35);
        errorMessageLabel.setFont(new Font(null, Font.PLAIN, 12));

        dataScrollTable.setBounds(165, 290, 450, 110);

        dataTableModel.addColumn("No.");
        dataTableModel.addColumn("Username");
        dataTableModel.addColumn("Password");

        userDataTable.setDefaultEditor(Object.class, null);

        frame.add(welcomeLabel);
        frame.add(jLabel);
        frame.add(newPasswordMsgLabel);
        frame.add(logOutButton);
        frame.add(newUsernameField);
        frame.add(newPasswordField);
        frame.add(newUsernameLabel);
        frame.add(newPasswordLabel);
        frame.add(dataScrollTable);
        frame.add(addInfoButton);
        frame.add(errorMessageLabel);

        frame.setSize(755, 565);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);

        if (dataTableModel != null){
            addDataToTable(sourceDataFile, dataTableModel);
        }

        userDataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = userDataTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int ans = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?",
                                "Delete selected row?", JOptionPane.OK_CANCEL_OPTION);

                        if (ans == 0){
                            dataTableModel.removeRow(selectedRow);
                            PrintWriter userDataWriter = null;
                            try {



                                userDataWriter = new PrintWriter(new FileOutputStream(sourceDataFile, true));

                                for (int row = 0; row < dataTableModel.getRowCount(); row++){
                                    for (int col = 0; col < dataTableModel.getColumnCount(); col++){
                                        userDataWriter.print(dataTableModel.getValueAt(row, col) + ",");

                                    }

                                    userDataWriter.println();
                                }
                            }
                            catch (FileNotFoundException e2){
                                System.out.println("File not found!???");
                            }
                            finally {
                                assert userDataWriter != null;
                                userDataWriter.close();
                            }
                        }


                    }
                }
            }
        });


    }

    //---------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == dataTableModel){
            JPopupMenu menu = new JPopupMenu();
        }


        if (e.getSource() == logOutButton){
            frame.dispose();
        }

        if (e.getSource() == addInfoButton){
            if (newUsernameField.getText().isEmpty() || newPasswordField.getText().isEmpty()){
                errorMessageLabel.setForeground(Color.red);
                errorMessageLabel.setText("Enter a username or password to store!");

            }else {

                String strCounter = String.valueOf(passwordCounter++);
                errorMessageLabel.setForeground(Color.BLUE);
                errorMessageLabel.setText("Passwords added successfully!");



                dataTableModel.addRow(new String[]{strCounter, newUsernameField.getText(), newPasswordField.getText()});

                addDataToFile(dataTableModel, sourceDataFile);

                newUsernameField.setText("");
                newPasswordField.setText("");

            }



        }

    }

    //---------------------------------------------------------------

    protected void addDataToFile(DefaultTableModel table, File sourceFile){

        sourceFile = new File(sourceFile.getName());

        try {

            PrintWriter dataWriter = new PrintWriter(sourceFile);

            for (int row = 0; row < table.getRowCount(); row++){
                for (int col = 0; col < table.getColumnCount(); col++){
                    dataWriter.print(table.getValueAt(row, col) + ",");
                }

                dataWriter.println();
            }

            dataWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found! Help!");
        }

    }

    //---------------------------------------------------------------

    protected void addDataToTable(File sourceFile, DefaultTableModel dataTableModel){

        Scanner fileReader = null;

        try {

            fileReader = new Scanner(new FileInputStream(sourceFile));
            Object [] line;

            while (fileReader.hasNextLine()){
                line = fileReader.nextLine().split(",");
                dataTableModel.addRow(line);
            }


        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
        finally {
            if (fileReader != null){
                fileReader.close();
            }
        }


    }

    //---------------------------------------------------------------

}