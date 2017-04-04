import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by kunalkrishna on 4/4/17.
 * Praneeth
 */
public class FindNearestYard {


    /*
    *   Calculate the distance between two points used fixed radius
    *
    * */

    public static float PythagorasEquirectangular(float long1,float lat1, float long2, float lat2){
        float dist=0;
        lat1 = DegToRadian(lat1);
        lat2 = DegToRadian(lat2);
        long1 = DegToRadian(long1);
        long2 = DegToRadian(long2);
        float radius = (float)3434.34;

        float x = (float) ((long2 - long1) * Math.cos((lat1 + lat2) / 2)) *radius;
        float y =(lat2 - lat1);
        dist = (float) Math.sqrt(x * x + y * y);

        return dist;

    }

     /*
    *   Calculate the distance between two points used fixed radius
    *
    * */

    public static float DegToRadian(float deg){

            return (float) ((float)deg * Math.PI / 180);
    }


    public static void main(String args[]){
        String line = " ";
        HashMap<String,ArrayList> yard = new HashMap<>();
        Scanner in = new Scanner(System.in);

        try (BufferedReader br = new BufferedReader(new FileReader("zip_codes_states.csv"))) {
           while((line = br.readLine()) !=null){
             String[] records= line.split(",");
             ArrayList<Float> value = new ArrayList<>();
             String yardname= records[2];
             value.add(Float.valueOf(records[0]));
             value.add(Float.valueOf(records[1]));
             yard.put(yardname,value);
           }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Enter your Geo Location");
        float long1= (float) 23.23;
        float lat1= (float)234.34;

        HashMap<String, Float> finalresult= new HashMap<>();

        float min=Integer.MAX_VALUE;
        Iterator it = yard.entrySet().iterator();

        String yardlocname= " ";
        while(it.hasNext()){
            Map.Entry pair=  (Map.Entry)it.next();
            ArrayList val= (ArrayList) pair.getValue();
            float diff = PythagorasEquirectangular(long1,lat1,(float)val.get(0),(float)val.get(1));
            if(diff<min) {
                min = diff;
                yardlocname = (String) pair.getKey();

            }
        }

        System.out.println("Nearest Yard Location is ::"  +yardlocname);



    }
}
