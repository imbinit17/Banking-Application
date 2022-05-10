import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter ;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.event.ActionListener ;
import java.awt.event.ActionEvent ;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;


public class signup implements ActionListener{

    private static JFrame frame1 ;
    private static JTextField rNameField ;
    private static JTextField rMobileField ;
    private static JTextField rEmailField;
    private static JTextField rAgeField ;
    private static JTextField rAdressField ;
    private static JTextField rUsernameField ;
    private static JPasswordField rPasswordField ;
    private static JPasswordField rConfirmPasswordField ;
    private static JButton submitButton ;
    private static JButton loginButton ;

    public static void registerUser(String name,String age ,String adress,String mobile,String email,String username,String password)
    {
        dataManager dm2 = new dataManager() ;
        int ac = dm2.getAccountNo() ;
        ac += 1 ;

        Scanner sc1 = new Scanner(System.in) ;
        File dir = new File("users.txt") ;
        try {
            sc1 = new Scanner(dir) ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String content = "" ;
        while(sc1.hasNextLine()){
            content = content + sc1.nextLine()+"\n" ;
        }
        adress+= "$" ;
        password += "$" ;
        int balance = 10 ;
        name += " " ;
        String data[] = {name,Integer.toString(ac),age,adress,mobile,email,Integer.toString(balance),username,password} ;

        String dataStr = Arrays.toString(data) ;
        content = content+dataStr + "\n" ;
        
        try (FileWriter wr = new FileWriter("users.txt")) {
            try {
                wr.write(content) ;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                wr.close() ;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            userCreated() ;
            login.loginForm();
            frame1.dispose();


        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }

    public static void signupForm(){
        frame1 = new JFrame("Net Banking Sign Up") ;
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(350,440);
        JPanel panel1 = new JPanel() ;
        frame1.add(panel1) ;
        panel1.setLayout(null);

        JLabel lbl1 = new JLabel("Full Name ") ;
        lbl1.setBounds(10,10,100,25) ;
        panel1.add(lbl1) ;

        rNameField = new JTextField() ;
        rNameField.setBounds(120,10,175,25) ;
        panel1.add(rNameField) ;

        JLabel lbl2 = new JLabel("Mobile") ;
        lbl2.setBounds(10,40,100,25);
        panel1.add(lbl2) ;

        rMobileField = new JTextField() ;
        rMobileField.setBounds(120,40,175,25) ;
        panel1.add(rMobileField) ;

        JLabel lbl3 = new JLabel("Email") ;
        lbl3.setBounds(10,70,100,25) ;
        panel1.add(lbl3) ;

        rEmailField = new JTextField() ;
        rEmailField.setBounds(120,70,175,25) ;
        panel1.add(rEmailField) ;

        JLabel lbl4 = new JLabel("Age") ;
        lbl4.setBounds(10,100,100,25) ;
        panel1.add(lbl4) ;

        rAgeField = new JTextField() ;
        rAgeField.setBounds(120,100,175,25) ;
        panel1.add(rAgeField) ;

        JLabel lbl5 = new JLabel("Adress") ;
        lbl5.setBounds(10,130,100,25) ;
        panel1.add(lbl5) ;

        rAdressField = new JTextField() ;
        rAdressField.setBounds(120,130,175,25) ;
        panel1.add(rAdressField) ;

        JLabel lbl6 = new JLabel("Username") ;
        lbl6.setBounds(20,200,100,25) ;
        panel1.add(lbl6) ;

        rUsernameField = new JTextField() ;
        rUsernameField.setBounds(160,200,100,25) ;
        panel1.add(rUsernameField) ;

        JLabel lbl7 = new JLabel("Password") ;
        lbl7.setBounds(20,230,100,25) ;
        panel1.add(lbl7) ;

        rPasswordField = new JPasswordField() ;
        rPasswordField.setBounds(160,230,100,25) ;
        panel1.add(rPasswordField) ;

        JLabel lbl8 = new JLabel("Confirm Password") ;
        lbl8.setBounds(20,260,150,25) ;
        panel1.add(lbl8) ;

        rConfirmPasswordField = new JPasswordField() ;
        rConfirmPasswordField.setBounds(160,260,100,25) ;
        panel1.add(rConfirmPasswordField) ;

        submitButton = new JButton("Submit") ;
        submitButton.setBounds(140,300,100,25);
        submitButton.addActionListener(new signup());
        panel1.add(submitButton);

        JLabel forLoginLbl = new JLabel("Already a user ?") ;
        forLoginLbl.setBounds(35,340,100,25);
        panel1.add(forLoginLbl) ;

        loginButton = new JButton("Login Then") ;
        loginButton.addActionListener(new signup());
        loginButton.setBounds(135,340,100,25);
        panel1.add(loginButton) ;

        frame1.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    { 
        Object btnObj = e.getSource() ;
        if(btnObj==loginButton){
            login.loginForm();
            frame1.dispose() ;
        }

        else{
            String name = rNameField.getText().toString() ;
            String mobile = rMobileField.getText().toString() ;
            String email = rEmailField.getText().toString() ;
            int age = Integer.parseInt(rAgeField.getText()) ;
            String adress = rAdressField.getText().toString() ;
            String username = rUsernameField.getText().toString() ;
            String password = rPasswordField.getText().toString() ;
            String cPassword = rConfirmPasswordField.getText().toString() ;

            boolean res = false ;
            if(age<18)
            ageIsLess() ;

            else{
                res = existAlready(username) ;
                if(res==true){
                    tryDifferentUsername() ;
                }
                else{
                    int res2 = 0 ;
                    res2 = checkFaultyInput(name,mobile,email,adress,password) ;
                    if(res2>0){
                        displayInstructionNotFollowed() ;
                    }
                    else {
                        if(password.equals(cPassword)){
                            boolean authentication_res = false ;
                            // authentication_res = authenticate(email) ; 
                            authentication_res = true ; //temporarily as long the email issue is resolved
                            if(authentication_res==true){
                                registerUser(name, Integer.toString(age), adress, mobile, email, username, password);
                            }
                            else
                            invalidOTP() ;
                        }
                        else
                        passwordsDontMatch() ;
                }
                }
            }
        }

    }

    public static void ageIsLess(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(300,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Net Banking not supported for minors") ;
        displayLabel.setBounds(30,30,250,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);
    }

    public static boolean existAlready(String username){
        boolean res = false ;
        Scanner sc2 = new Scanner(System.in) ;
        File dir = new File("users.txt") ;
        try {
            sc2 = new Scanner(dir) ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        dataManager dm1 = new dataManager() ;
        

        String lineData = "" ;
        String dbUsername = "" ;
        // String arr[] = {"hello","hello","hello","hello","hello","hello","hello","hello"} ;
        // int k= 0 ;
        while(sc2.hasNextLine()){
            lineData = sc2.nextLine() ;
            dbUsername = dm1.getUsername(lineData) ;
            if(dbUsername.equals(username)){
                res = true ;
                break ;
            }
        }

        // while(sc2.hasNextLine()){
        //     arr[k] = sc2.nextLine() ;
        //     k++ ;
        // }

        return res ;
    }

    public static void tryDifferentUsername(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(350,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Username already exists ! Try with a different one !") ;
        displayLabel.setBounds(15,30,275,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);
    }

    public static int checkFaultyInput(String name,String mobile,String email,String adress,String password){
        int count = 0 ;
        int i ;
        for(i=0;i<name.length();i++){
            if(name.charAt(i)==',')
            count++ ;
        }

        for(i=0;i<mobile.length();i++){
            if(mobile.charAt(i)==',')               
            count++ ;
        }

        for(i=0;i<email.length();i++){
            if(email.charAt(i)==',')
            count++ ;
        }

        for(i=0;i<adress.length();i++){
            if(adress.charAt(i)=='$')
            count++ ;
        }

        for(i=0;i<password.length();i++){
            if(password.charAt(i)=='$')
            count++ ;
        }

        return count ;
    }

    public static void displayInstructionNotFollowed(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(300,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Instructions not followed while giving Input") ;
        displayLabel.setBounds(30,30,250,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);
    }

    public static boolean authenticate(String email){
        boolean response = false ;
        double a = Math.random() ;
        a = a * 1000000 ;
        String otp = Double.toString(a) ;

        String message = "Your OTP for registration of Net Banking is : "+otp +" .\n Thank You .";
        // smtpEmail mailObj = new smtpEmail() ;
        // mailObj.sendEmail(email, message);

        // otpVerification obj = new otpVerification() ;
        // String userEnteredOTP = obj.otpVerificationWindow() ;

        // if(otp.equals(userEnteredOTP))
        response = true ;
        return response ;
    }

    public static void invalidOTP(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(350,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Invalid OTP entered !") ;
        displayLabel.setBounds(15,30,275,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);
    }

    public static void userCreated(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(350,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("User Registered ! Now login with your credentials") ;
        displayLabel.setBounds(15,30,320,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);
    }

    public static void passwordsDontMatch(){
        JFrame msgFrame = new JFrame("Dialogue Window") ;
        msgFrame.setSize(350,150) ;
        JPanel tempPanel = new JPanel() ;
        msgFrame.add(tempPanel) ;
        tempPanel.setLayout(null) ;

        JLabel displayLabel = new JLabel("Passwords do not match !") ;
        displayLabel.setBounds(15,30,275,30) ;
        tempPanel.add(displayLabel) ;

        msgFrame.setVisible(true);
    }

}