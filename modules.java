public class modules {
    public static String reverseString(String str){
        String newString = "" ;

        for(int i=str.length()-1;i>-1;i--)
        newString += str.charAt(i) ;

        return newString ;
    }
}
