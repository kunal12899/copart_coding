/**
 * Created by kunalkrishna on 4/4/17.
 */

import java.util.Scanner;

public class ConvertStringToInteger {

    public static int convert_To_Number(String numStr){

        char ch[] = numStr.toCharArray();
        int sum = 0;
        //get ascii value for zero
        int zeroAscii = (int)'0';
        for(char c:ch){
            int tmpAscii = (int)c;
            sum = (sum*10)+(tmpAscii-zeroAscii);
        }
        return sum;
    }

    public static void main(String args[]){

        Scanner in = new Scanner(System.in);
        System.out.println("Enter your string");
            String s= in.nextLine();
            System.out.println("After Converting to Number    "+convert_To_Number(s));

    }
}
