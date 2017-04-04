/**
 * Created by kunalkrishna on 4/4/17.
 */
import java.util.Scanner;
/**
 * Conversion of String to Integer
 * @author kunal Krishna
 * praneeth
 *
 */

public class ConvertToInt {
    /**
     * Function to convert String to Integer.
     * @param str String that is needed to be converted.
     * @return Converted number.
     */
    public Integer convert(String str) {
        int num = 0;
        int factor = 1;
        int j = 0;
        //converting string to integer
        if(str.charAt(0) == '-') {//checking for negative numbers.
            factor = -1;
            j = 1;
        }
        for (int i = str.length()-1; i >= j; i--) {
            if(!(str.charAt(i) <= '9' && str.charAt(i) >= '0')) {
                // If character can not be converted to number, exit.
                return null;
            }
            //character is converted to int and added to the current number.
            num += (str.charAt(i) - '0') * factor;
            factor *= 10;
        }
        return num;
    }
    //Test cases.
    public static void test(){
        ConvertToInt converter = new ConvertToInt();
        assert(converter.convert("1234") == 1234);
        assert(converter.convert("abcd") == null);
        assert(converter.convert("-1234") == -1234);
    }

    public static void main (String args[]) {
        Scanner scan = new Scanner(System.in);
        String str = scan.next();
        scan.close();
        ConvertToInt converter = new ConvertToInt();
        Integer number = converter.convert(str);
        if(number == null) {
            // If character can not be converted to number, exit.
            System.out.println("String cannot be converted to Integer.");
            return;
        }
        System.out.println("Integer Output = "+number);
        test();
    }
}
