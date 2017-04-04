package copart;

import java.util.Scanner;

public class LiscenseKeys {

	private static String formatLiscenseKey(String s, int k) {
		// TODO Auto-generated method stub
		s = s.toUpperCase().replaceAll("-", "");
		String temp = "";
		int i = 0, first = s.length() % k;

		/*
		 * first variable is used to check if the given String can be split
		 * exactly into a integer multiple of the given length.
		 */

		// If its a multiple then we start splitting from first index.
		if (first == 0)
			i = 0;
		/*
		 * If its not a multiple then we assign the difference of the multiple
		 * count as first part of the liscence plate.
		 */
		else {
			temp = temp.concat(s.substring(0, first)).concat("-");
			i = first;
		}
		for (int count = s.length() / k; count >= 1; count--) {
			temp = temp.concat(s.substring(i, i + k));
			if (count != 1)
				temp = temp.concat("-");
			i = i + k;
		}
		return temp;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// Input the Liscence plate Number.
		System.out.println("Enter the Liscense Plate Number:");
		String no = sc.nextLine();

		// Enter the number in which we want to group the Characters.
		System.out.println("Enter the group count:");
		int k = sc.nextInt();

		// FromatLiscenseKey function formats and returns the modified String.
		String output = formatLiscenseKey(no, k);
		System.out.println(output);
		sc.close();
	}

}
