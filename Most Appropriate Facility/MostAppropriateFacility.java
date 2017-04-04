package copart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

/*
 * COPART location details are there in the file 'Locations.csv'
 * Customer and Nearest Copart Location deatils are there in the file 'CustomerLocation.csv' 
 * Case 1: Member Customer searches. Fetch from Customer Details and display
 * Case 2: New Customer searches. Get the zipCode and get minimum Distance zipCode(from API) and fetch details and display  
 * Case 3: New Customer searches for already mapped minimum zip code, fetch from Map and display.  -- Only if same session 
 */

//Structure for the nearest copart location which contains an Address Map, Customer Map and Nearest Location Map.
public class MostAppropriateFacility {

	Map<String, String> nearLocation;
	Map<String, String> custMap;
	Map<String, CopartLocation> addMap;

	MostAppropriateFacility() {
		nearLocation = new HashMap<String, String>();
	}

	// The Location Class contains StateCode, State Name, Phone Number, Zipcode.
	class CopartLocation {
		String stateCode;
		String state;
		String phoneNumber;
		String zipCode;

		public CopartLocation(String stateCode, String state, String phoneNumber, String zipCode) {
			this.stateCode = stateCode;
			this.state = state;
			this.phoneNumber = phoneNumber;
			this.zipCode = zipCode;
		}

		public String toString() {
			return this.stateCode + " " + this.state + " " + this.phoneNumber;
		}
	}

	public static void main(String args[]) throws Exception {
		String customerID;
		String zipCode;
		Scanner s = new Scanner(System.in);
		MostAppropriateFacility nCopart = new MostAppropriateFacility();
		nCopart.addMap = nCopart.getLocationInput(new File("Locations.csv"));
		nCopart.custMap = nCopart.getCustomerInput(new File("CustomerLocation.csv"));
		System.out.println("Enter Customer ID and Zip code");
		while (s.hasNext()) {
			customerID = s.nextLine();
			zipCode = s.nextLine();
			if (customerID.length() > 1 && zipCode.length() > 1)
				System.out.println(nCopart.getNearestLocation(customerID, zipCode));
			System.out.println("Enter CustomerID, Zip code");
		}
		s.close();
	}

	/*
	 * In this function we are parsing the Locations.csv file. We are taking the
	 * State code, State name, Zipcode and the address. All this data is being
	 * added into a Hashmap with the Zipcode as the key.
	 */
	public Map<String, CopartLocation> getLocationInput(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);
		HashMap<String, CopartLocation> hm = new HashMap<>();
		while (s.hasNextLine()) {
			try {
				String now = s.nextLine();
				String str[] = now.split(",");
				CopartLocation copartLoc = new CopartLocation(str[0], str[1], str[str.length - 2], str[str.length - 4]);
				hm.put(str[str.length - 4], copartLoc);
			} catch (Exception e) {
				// just skip mismatched locations
			}
		}
		s.close();
		return hm;
	}

	/*
	 * In this function we are parsing the customerLocations.csv file. We are
	 * taking the customerId and the Zipcode. All this data is being added into
	 * a Hashmap with the Zipcode as the key.
	 */
	public Map<String, String> getCustomerInput(File file) throws Exception {
		Scanner s = new Scanner(file);
		HashMap<String, String> hm = new HashMap<>();
		while (s.hasNextLine()) {
			try {
				String now = s.nextLine();
				String str[] = now.split(",");
				hm.put(str[0], str[1]);
			} catch (Exception e) {
			}
		}

		s.close();
		return hm;
	}

	/*
	 * In this function, we use the zipcode given from the User as input. We
	 * then check the given input with all the zipcodes that are available in
	 * the Locations.csv file against all thier Zipcodes. Each and every time
	 * the Distance is calculated. The zipcode which has the minimum distance is
	 * stored and the address corresponding to that Zipcode is returned.
	 */
	public CopartLocation getNearestLocation(String customerID, String zipCode) {
		if (custMap.containsKey(customerID))
			return addMap.get(custMap.get(customerID));
		if (nearLocation.containsKey(zipCode)) {
			return addMap.get(nearLocation.get(zipCode));
		}

		double distanceMin = Double.MAX_VALUE;
		String minZipCode = null;
		System.out.println("Fetching from API............");
		for (Object zip : addMap.keySet()) {
			double value = getDistance(zipCode, (String) zip);
			if (value < distanceMin) {
				distanceMin = value;
				minZipCode = (String) zip;
			}
		}
		nearLocation.put(zipCode, minZipCode);
		return addMap.get(minZipCode);
	}

	// This function uses the Zipcode API and returns the distance between the
	// given 2 zipcodes.
	public double getDistance(String zip1, String zip2) {
		String API_Key = "SH8Be5Im18StbtqknXgm9a9aoJZsukum2CXhHGDJOvd0ZDknBAJKLSV9CVC6g6si";
		String url = "https://www.zipcodeapi.com/rest/" + API_Key + "/distance.json/" + zip1 + "/" + zip2 + "/km";
		String inputLine;
		try {
			URL ur = new URL(url);
			HttpURLConnection con = (HttpURLConnection) ur.openConnection();
			con.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			inputLine = reader.readLine();
			JSONObject json = new JSONObject(inputLine);
			String distance = json.getString("distance");
			return Double.parseDouble(distance);
		} catch (Exception e) {
			return Integer.MAX_VALUE;
		}
	}

}