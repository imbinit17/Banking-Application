public class modules {
    

    public static String reverseString(String str){
        String newString = "" ;

        for(int i=str.length()-1;i>-1;i--)
        newString += str.charAt(i) ;

        return newString ;
    }
    public static void main(String[] args) {
        System.out.println(reverseString("[Virat Kohli, 1006, 34, Antilia ,Navi Mumbai$, 948754976, virat@bcci.co, 0, vk, 123$]"));
    }
}
