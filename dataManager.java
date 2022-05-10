import java.io.File ;
import java.util.Arrays;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class dataManager{

    public String[] getData(String str){
        str = str.substring(1,str.length()-1) ;
       
        
        int i = str.indexOf(',') ;
        String name = str.substring(0,i) ;
        str = str.substring(i+2) ;

        i = str.indexOf(',') ;
        String ac = str.substring(0,i) ;
        str = str.substring(i+2) ;

        i = str.indexOf(',') ;
        String age = str.substring(0,i) ;
        str = str.substring(i+2) ;

        i = str.indexOf('$') ;
        String adress = str.substring(0,i) ;
        str = str.substring(i+3) ;

        i = str.indexOf(',') ;
        String mobile = str.substring(0,i) ;
        str = str.substring(i+2) ;

        i = str.indexOf(',') ;
        String email = str.substring(0,i) ;
        str = str.substring(i+2) ;

        i = str.indexOf(',') ;
        String balance = str.substring(0,i) ;
        str = str.substring(i+2) ;
        
        i = str.indexOf(',') ;
        String username = str.substring(0,i) ;
        str = str.substring(i+2) ;

        i = str.indexOf('$') ;
        String pass = str.substring(0,i) ;
        str = str.substring(i) ;

        String arr[] = {name,ac,age,adress,mobile,email,balance,username,pass,str} ;
        return arr ;
    }

    public String getUsername(String str){
        String[] arr = getData(str) ;
        String uname = arr[7] ;
        return uname ;
    }

    public String getPassword(String str){
        String arr[] = getData(str) ;
        String pass = arr[8] ;
        return pass ;
    }

    public String getName(String str){
        String arr[] = getData(str) ;
        String name = arr[0] ;
        return name ;
    }

    public int getAccountNo(){
        Scanner sc3 = new Scanner(System.in) ;
        File dir = new File("users.txt") ;
        try {
            sc3 = new Scanner(dir) ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        String str = "" ;
        int max = 0 ;
        int ac_no = 0 ;
        while(sc3.hasNextLine()){
            str = sc3.nextLine() ;
            String arr[] = getData(str) ;
            ac_no = Integer.parseInt(arr[1]) ;
            if(ac_no>max){
                max = ac_no ;
            }
        }

        return max ;
    }

    public String fetchBalance(String str){
        String arr[] = getData(str) ;
        String balance = arr[6] ;
        return balance ;
    }

    public String fetchAccountNo(String str){
        String arr[] = getData(str) ;
        String ac_no = arr[1] ;
        return ac_no ;
    }

    public void transactForSender(String arr[])
    {
        File file = new File("users.txt") ;
        Scanner kb = new Scanner(System.in) ;
        try{
            kb = new Scanner(file) ;
        }catch(FileNotFoundException l){
            l.printStackTrace();
        }

        String[] transaction = {arr[0],"SENT",arr[2],arr[3],arr[4],arr[5],arr[6]} ;
        String newData ="" ;
        String lineData = "" ;
        String dbAcNo = "" ;
        String content = "" ;
        while(kb.hasNextLine()){
            lineData = kb.nextLine() ;
            dbAcNo = fetchAccountNo(lineData) ;
            if(dbAcNo.equals(arr[1]))
            {
                newData = sMutate(lineData,transaction) ;
                content = content + newData + "\n" ;
            }
            else{
                content = content + lineData +"\n";
            }
        }

        try (FileWriter wr = new FileWriter(file)) {
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
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static String balChange(String lineData,String amt)
    {        
        String s1 = modules.reverseString(lineData) ;
        int i1 = s1.indexOf('$') ;
        String rest = s1.substring(0,i1) ;
        String s2 = s1.substring(i1) ;
        
        int i2 = s2.indexOf(',') ;
        rest = rest +  s2.substring(0,i2+1) ;
        s2 = s2.substring(i2+1) ;

        i2 = s2.indexOf(',') ;
        rest = rest +  s2.substring(0,i2+1) ;
        s2 = s2.substring(i2+1) ;

        i2 = s2.indexOf(',') ;
        String previousAmt = s2.substring(0,i2) ;
        amt = modules.reverseString(amt) ;
        rest = rest + amt+" ," ;
        s2 = s2.substring(i2+1) ;

        String newLineData = rest + s2 ;
     
        newLineData = modules.reverseString(newLineData) ;
        newLineData = newLineData  ;
        
        return newLineData ;
    }

    public static String sMutate(String lineData,String[] transaction)
    {
        String amt = transaction[4] ;
        lineData = balChange(lineData, amt) ;
        int index = lineData.lastIndexOf(']') ;
        String newLineData = lineData.substring(0,index) ;

        String newTransaction = Arrays.toString(transaction) ;
        newLineData =newLineData + ","+newTransaction+"]" ;

        return newLineData ;
    }

    public static String rMutate(String lineData,String[] transaction)
    {
        dataManager dm = new dataManager() ;
        String availableBal = dm.fetchBalance(lineData) ;
        int temp = Integer.parseInt(availableBal) ;
        String amt = transaction[3] ;
        temp = temp + Integer.parseInt(amt) ;
        amt = Integer.toString(temp) ;
        lineData = balChange(lineData, amt) ;
        int index = lineData.lastIndexOf(']') ;
        String newLineData = lineData.substring(0,index) ;
        String newTransaction = Arrays.toString(transaction) ;
        newLineData =newLineData + ","+newTransaction+"]" ;

        return newLineData ;
    }

    public void transactForReceiver(String[] arr)
    {
        File file = new File("users.txt") ;
        Scanner kb = new Scanner(System.in) ;
        try{
            kb = new Scanner(file) ;
        }catch(FileNotFoundException l){
            l.printStackTrace();
        }

        String[] transaction = {arr[0],"RECEIVED",arr[1],arr[3],arr[4],arr[5],arr[6]} ;
        String newData ="" ;
        String lineData = "" ;
        String dbAcNo = "" ;
        String content = "" ;
        while(kb.hasNextLine()){
            lineData = kb.nextLine() ;
            dbAcNo = fetchAccountNo(lineData) ;
            if(dbAcNo.equals(arr[2]))
            {
                newData = rMutate(lineData,transaction) ;
                content = content + newData + "\n" ;
            }
            else{
                content = content + lineData +"\n";
            }
        }

        try (FileWriter wr = new FileWriter(file)) {
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
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void changePassInstant(String lineData,String newPass)
    {
        newPass = " " +newPass + "$" ;
        String username = getUsername(lineData) ;
        newPass = modules.reverseString(newPass) ;
        lineData = modules.reverseString(lineData) ;

        int i = lineData.indexOf('$') ;
        String rest = lineData.substring(0,i) ;

        lineData = lineData.substring(i) ;

        int j = lineData.indexOf(',') ;
        String other = lineData.substring(j) ;

        String newString = rest + newPass + other ;
        newString = modules.reverseString(newString) ;

        File file = new File("users.txt") ;
        Scanner kb = new Scanner(System.in) ;
        try{
            kb = new Scanner(file) ;
        }catch(FileNotFoundException l){
            l.printStackTrace();
        }

        
        String content = "" ;
        String tempData = "" ;
        String uname = "" ;
        while(kb.hasNextLine()){
            tempData = kb.nextLine() ;
            uname = getUsername(tempData) ;
            if(uname.equals(username)){
                content = content + newString + "\n" ;
            }
            else
            content = content + tempData + "\n" ;
        }

        try (FileWriter wr = new FileWriter(file)) {
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
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}