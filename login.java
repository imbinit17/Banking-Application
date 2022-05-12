import java.awt.event.ActionListener ;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.awt.event.ActionEvent ;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class login implements ActionListener{
        private static JFrame loginFrame ;
        private static JButton signupBtn ;
        private static JButton loginBtn ;
        private static JTextField lUsernameFied ;
        private static JPasswordField lPasswordField ;

        private static JFrame loggedIn ;
        private static JButton profileBtn ;
        private static JButton passwordChangeBtn ;
        private static JButton transactionBtn ;
        private static JButton viewBalanceBtn ;
        private static JButton logOutBtn ;

        private static String tempString ;

        private static JFrame transactionFrame ;
        private static JTextField tAcField ;
        private static JTextField tAmountField ;
        private static JTextField tRemarksField ;
        private static JButton tButton ;

        private static JFrame passChangeFrame ;
        private static JButton PCFsubmitBtn ;
        private static JPasswordField oldPassField ;
        private static JPasswordField newPassField ;
        private static JPasswordField confirmPassField ;
        
    public static void loginForm(){
        loginFrame = new JFrame("Net Banking Login") ;
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel() ;
        loginFrame.setSize(250,150);
        loginFrame.add(panel) ;
        panel.setLayout(null);

        JLabel lbl1 = new JLabel("Username") ;
        lbl1.setBounds(10,5,100,25);
        panel.add(lbl1) ;

        lUsernameFied = new JTextField() ;
        lUsernameFied.setBounds(120,5,100,25);
        panel.add(lUsernameFied) ;

        JLabel lbl2 = new JLabel("Password") ;
        lbl2.setBounds(10,40,100,25);
        panel.add(lbl2) ;

        lPasswordField = new JPasswordField() ;
        lPasswordField.setBounds(120,40,100,25);
        panel.add(lPasswordField) ;

        signupBtn = new JButton("Register") ;
        signupBtn.setBounds(25,75,90,25);
        signupBtn.addActionListener(new login());
        panel.add(signupBtn) ;

        loginBtn = new JButton("Login") ;
        loginBtn.setBounds(120,75,90,25);
        loginBtn.addActionListener(new login());
        panel.add(loginBtn) ;

        loginFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object obj = e.getSource() ;
        if(obj==signupBtn){
            signup.signupForm();
            loginFrame.dispose();
        }
        else if(obj==loginBtn){
            checkLogin() ;
        }
        else if(obj==profileBtn){
            displayProfile(tempString);
        }
        else if(obj==passwordChangeBtn){
            passChangeForm() ;
        }
        else if(obj==transactionBtn){
            fundsTransferForm(tempString);
        }
        else if(obj==viewBalanceBtn){
            showBalance(tempString) ;
        }
        else if(obj==logOutBtn){
            logOutUser() ;
            tempString = "" ;
        }
        else if(obj==tButton){
            initiateTransaction(tempString) ;
        }
        else if(obj==PCFsubmitBtn){
            changePass(tempString) ;
        }
    }

    public static void checkLogin()
    {
        String lUsername = lUsernameFied.getText().toString() ;
        String lPass = lPasswordField.getText().toString() ;

        Scanner kb = new Scanner(System.in) ;
        File dir = new File("users.txt") ;
        try {
            kb = new Scanner(dir) ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        dataManager dm = new dataManager() ;

        String lineData = "" ;
        String dbUsername = "" ;
        String dbPass = "" ;
        boolean response = false ;
        while(kb.hasNextLine()){
           lineData = kb.nextLine() ;
            dbUsername = dm.getUsername(lineData) ;
            if(lUsername.equals(dbUsername)){
                dbPass = dm.getPassword(lineData) ;
                if(dbPass.equals(lPass)){
                    loginGranted(lineData) ;
                    response = true ;
                }
            }
        }

        if(response==false)
        invalidDetails() ;
    }

    public static void loginGranted(String lineData)
    {
        dataManager dm = new dataManager() ;
        String dbName = dm.getName(lineData) ;
        dbName = dbName.substring(0,dbName.indexOf(' ')) ;
         
        loggedIn = new JFrame("Net Banking Dashboard") ;
        JPanel panel = new JPanel() ;
        loggedIn.setSize(350,320);
        loggedIn.add(panel) ;
        panel.setLayout(null);

        String welcome = "Welcome " + dbName ;
        tempString = lineData ;

        JLabel lbl1 = new JLabel(welcome) ;
        lbl1.setBounds(115,5,150,35);
        panel.add(lbl1) ;

        profileBtn = new JButton("View Profile") ;
        profileBtn.addActionListener(new login());
        profileBtn.setBounds(80,60,175,25);
        panel.add(profileBtn) ;

        transactionBtn = new JButton("Online Funds Transfer") ;
        transactionBtn.addActionListener(new login());
        transactionBtn.setBounds(80,100,175,25);
        panel.add(transactionBtn) ;

        passwordChangeBtn = new JButton("Change Password") ;
        passwordChangeBtn.addActionListener(new login());
        passwordChangeBtn.setBounds(80,140,175,25);
        panel.add(passwordChangeBtn) ;

        viewBalanceBtn = new JButton("View Balance") ;
        viewBalanceBtn.addActionListener(new login());
        viewBalanceBtn.setBounds(80,180,175,25);
        panel.add(viewBalanceBtn) ;

        logOutBtn = new JButton("Log Out") ;
        logOutBtn.addActionListener(new login());
        logOutBtn.setBounds(80,220,175,25);
        panel.add(logOutBtn) ;

        loggedIn.setVisible(true);
        loginFrame.dispose();
    }

    public static void invalidDetails(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(350,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Invalid Details !") ;
        displayLabel.setBounds(15,30,275,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);
    }

    public static void displayProfile(String lineData){
        dataManager dm = new dataManager() ;
        String[] arr = dm.getData(lineData) ;
        String name = arr[0] ;
        String ac_no = arr[1] ;
        String age = arr[2] ;
        String adress = arr[3] ;
        String mobile = arr[4] ;
        String email = arr[5] ;

        JFrame profileDashboard = new JFrame("User Profile") ;
        profileDashboard.setSize(350,270) ;
        JPanel tempPanel = new JPanel() ;
        profileDashboard.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel lbl1 = new JLabel("NAME : "+name) ;
        lbl1.setBounds(10,10,300,25);
        tempPanel.add(lbl1) ;

        JLabel lbl2 = new JLabel("ACCOUNT NUMBER : "+ ac_no) ;
        lbl2.setBounds(10,40,300,25) ;
        tempPanel.add(lbl2) ;

        JLabel lbl3 = new JLabel("AGE : "+ age) ;
        lbl3.setBounds(10,70,300,25) ;
        tempPanel.add(lbl3) ;

        JLabel lbl4 = new JLabel("ADRESS : "+ adress) ;
        lbl4.setBounds(10,110,300,25) ;
        tempPanel.add(lbl4) ;

        JLabel lbl5 = new JLabel("MOBILE NUMBER : "+ mobile) ;
        lbl5.setBounds(10,150,300,25) ;
        tempPanel.add(lbl5) ;

        JLabel lbl6 = new JLabel("EMAIL ID : "+ email) ;
        lbl6.setBounds(10,190,300,25) ;
        tempPanel.add(lbl6) ;

        profileDashboard.setVisible(true);
    }

    public static void logOutUser(){
        loginForm();
        loggedIn.dispose();
    }

    public static void fundsTransferForm(String lineData){
        transactionFrame = new JFrame("Online Funds Transfer") ;
        JPanel panel = new JPanel() ;
        transactionFrame.setSize(300,250);
        transactionFrame.add(panel) ;
        panel.setLayout(null);

        JLabel lbl1 = new JLabel("Enter the details of the beneficiary") ;
        lbl1.setBounds(40,5,200,35) ;
        panel.add(lbl1) ;

        JLabel lbl2 = new JLabel("A/c No : ") ;
        lbl2.setBounds(10,50,100,25) ;
        panel.add(lbl2) ;

        JLabel lbl3 = new JLabel("Amount : ") ;
        lbl3.setBounds(10,80,100,25);
        panel.add(lbl3) ;

        JLabel lbl4 = new JLabel("Remarks : ") ;
        lbl4.setBounds(10,110,100,25) ;
        panel.add(lbl4) ;

        tAcField = new JTextField() ;
        tAcField.setBounds(120,50,100,25) ;
        panel.add(tAcField) ;

        tAmountField = new JTextField() ;
        tAmountField.setBounds(120,80,100,25) ;
        panel.add(tAmountField) ;

        tRemarksField = new JTextField() ;
        tRemarksField.setBounds(120,110,100,25) ;
        panel.add(tRemarksField) ;

        tButton = new JButton("Initiate Transaction") ;
        tButton.addActionListener(new login());
        tButton.setBounds(50,140,160,25) ;
        panel.add(tButton) ;

        transactionFrame.setVisible(true);
    }

    public static void showBalance(String lineData){
        dataManager dm = new dataManager() ;
        String balance = dm.fetchBalance(lineData) ;
        
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(350,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Available Balance : "+balance) ;
        displayLabel.setBounds(15,30,275,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);

    }

    public static void initiateTransaction(String lineData)
    {
        String tAccountNo = tAcField.getText().toString() ;
        String tAmount = tAmountField.getText().toString() ;
        String tRemarks = tRemarksField.getText().toString() ;

        int amt ;
        try{
            amt = Integer.parseInt(tAmount) ;
            boolean acExistence = doesAccountExist(tAccountNo) ;
            if(acExistence==true)
            transactPhase2(tAccountNo,amt,tRemarks) ;
            else
            acDoesntExist() ;
        }
        catch(NumberFormatException f){
            numericValueOnly();
        }
    }

    public static void numericValueOnly(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(350,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Amount accepted in numeric only !") ;
        displayLabel.setBounds(15,30,275,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);
    }

    public static boolean doesAccountExist(String acNo)
    {
        Scanner kb = new Scanner(System.in) ;
        File dir = new File("users.txt") ;
        try {
            kb = new Scanner(dir) ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        dataManager dm = new dataManager() ;

        String lineStr = "" ;
        String dbAcNo ="" ;
        boolean res = false ;
        while(kb.hasNextLine()){
            lineStr = kb.nextLine() ;
            dbAcNo = dm.fetchAccountNo(lineStr) ;
            if(dbAcNo.equals(acNo))
            res = true ;
        }

        return res ;
    }

    public static void acDoesntExist(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(350,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Account Does Not Exist !") ;
        displayLabel.setBounds(15,30,275,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);
    }

    public static void transactPhase2(String ac_no,int amount,String remarks){
        dataManager dm = new dataManager() ;
        String balance = dm.fetchBalance(tempString) ;
        int bal = Integer.parseInt(balance) ;

        if(bal>=amount){
            String id = server.getTransactionID() ;
            String senderAC = dm.fetchAccountNo(tempString) ;
            String receiverAC = ac_no ;
            String transactionAmt = Integer.toString(amount) ;
            int remainingBal = bal - amount ;
            String closingBal = Integer.toString(remainingBal) ;
            //remarks in parameter
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss") ;
            Date dt = new Date() ;
            String dateTime = sdf.format(dt) ;

            String[] transaction = {id,senderAC,receiverAC,transactionAmt,closingBal,remarks,dateTime} ;

            server.addTransaction(transaction);
            dm.transactForSender(transaction);
            dm.transactForReceiver(transaction);
            transactionSuccessful() ;
        }
        else
        insufficientBalance() ;
        
    }

    public static void insufficientBalance(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(350,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Insufficient Balance!") ;
        displayLabel.setBounds(15,30,275,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);
    }

    public static void transactionSuccessful(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(350,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Transaction Successful !") ;
        displayLabel.setBounds(15,30,275,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);

        transactionFrame.dispose() ;
    }

    public static void passChangeForm()
    {
        passChangeFrame = new JFrame("Change Password") ;
        JPanel panel = new JPanel() ;
        passChangeFrame.setSize(250,300);
        passChangeFrame.add(panel) ;
        panel.setLayout(null);

        JLabel lbl1 = new JLabel("Old Password") ;
        lbl1.setBounds(10,5,150,25);
        panel.add(lbl1) ;

        oldPassField = new JPasswordField() ;
        oldPassField.setBounds(10,35,150,25) ;
        panel.add(oldPassField) ;

        JLabel lbl2 = new JLabel("New Password") ;
        lbl2.setBounds(10,70,100,25);
        panel.add(lbl2) ;

        newPassField = new JPasswordField() ;
        newPassField.setBounds(10,100,150,25) ;
        panel.add(newPassField) ;

        JLabel lbl3 = new JLabel("Confirm New Password") ;
        lbl3.setBounds(10,135,150,25);
        panel.add(lbl3) ;

        confirmPassField = new JPasswordField() ;
        confirmPassField.setBounds(10,170,150,25) ;
        panel.add(confirmPassField) ;

        PCFsubmitBtn = new JButton("Change Password") ;
        PCFsubmitBtn.addActionListener(new login());
        PCFsubmitBtn.setBounds(50,210,150,25);
        panel.add(PCFsubmitBtn) ;

        passChangeFrame.setVisible(true);
    }

    public static void changePass(String lineData)
    {
        dataManager dm = new dataManager() ;
        String dbPass = dm.getPassword(lineData) ;

        String oldPass = oldPassField.getText().toString() ;
        String p1 = newPassField.getText().toString() ;
        String p2 = confirmPassField.getText().toString() ;

        if(p1.equals(p2)){
            if(oldPass.equals(dbPass)){
                dm.changePassInstant(lineData,p1) ;
                passChanged() ;
            }
        }
        else
        invalidDetails();
    }

    public static void passChanged(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(350,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Password Changed!") ;
        displayLabel.setBounds(15,30,275,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);

        passChangeFrame.dispose();
        loginFrame.dispose() ;
        loginForm();
    }
}
