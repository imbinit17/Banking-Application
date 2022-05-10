import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class server {
    

    public static String[] getData(String str){
        str = str.substring(1,str.length()-1) ;

        int i = str.indexOf(',') ;
        String id = str.substring(0,i) ;
        str = str.substring(i+2) ;

        i = str.indexOf(',') ;
        String senderAc = str.substring(0,i) ;
        str = str.substring(i+2) ;

        i = str.indexOf(',') ;
        String receiverAC = str.substring(0,i) ;
        str = str.substring(i+2) ;

        i = str.indexOf(',') ;
        String amount = str.substring(0,i) ;
        str = str.substring(i+2) ;

        i = str.indexOf(',') ;
        String closingBal = str.substring(0,i) ;
        str = str.substring(i+2) ;

        i = str.indexOf(',') ;
        String remarks = str.substring(0,i) ;
        str = str.substring(i+2) ;

        String dateTime = str ;

        String[] arr = {id,senderAc,receiverAC,amount,closingBal,remarks,dateTime} ;

        return arr ;
    }

    public static String getTransactionID(){
        File dir = new File("transactionData.txt") ;
        Scanner sc1 = new Scanner(System.in) ;
        try {
            sc1 = new Scanner(dir) ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String currentTransactionID = "" ;
        String[] arr ;
        String lineData = "" ;
        while(sc1.hasNextLine()){
            lineData = sc1.nextLine() ;
            arr = getData(lineData) ;
            currentTransactionID = arr[0] ;
        }

        int temp = Integer.parseInt(currentTransactionID) ;
        temp += 1 ;
        String newID = Integer.toString(temp) ;
        return newID ;
    }

    public static void addTransaction(String[] arr){

        File dir = new File("transactionData.txt") ;
        Scanner sc2 = new Scanner(System.in) ;

        try{
            sc2 = new Scanner(dir) ;
        }catch(FileNotFoundException f){
            f.printStackTrace();
        }

        String content = "" ;
        while(sc2.hasNextLine()){
            content = content + sc2.nextLine() + "\n" ;
        }

        String nextTransaction = Arrays.toString(arr) ;
        content = content + nextTransaction + "\n" ;

        try (FileWriter wr = new FileWriter(dir)) {
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
