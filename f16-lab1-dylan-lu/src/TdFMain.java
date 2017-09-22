import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * This is the main class for the Tour de France program. If has the main( )
 * method that reads biker information from a file and then performs some
 * computations with the data.
 * 
 * @author Sathish Gopalakrishnan
 * 
 */
public class TdFMain {

    /**
     * This is the main( ) method.
     * 
     * @param args
     *            We do not use this parameter at this point.
     */
	
    public static void main(String[] args) {
    	int numberOfEntries = 0;
        // The FileInputStream to open and read from the file that
        // has the Tour de France data.
        FileInputStream tdfStream;

        // This map maintains the relationship between the
        // name of the biker and the object that holds the
        // biker's data.
        Map<String, Biker> allBikers = new TreeMap<String, Biker>();

        // Let us try to open the data file.
        // The file name is hardcoded, which is not elegant.
        // Suffices for now.
        try {
            tdfStream = new FileInputStream("tdf.txt");
        } catch (FileNotFoundException e) {
            // If, for some reason, the file was not found,
            // then throw an exception.
            // The file is however included in the git repo
            // so this should not happen.
            throw new RuntimeException(e);
        }

        // We have opened the file.
        // Let us try to read data.
        try {
            // We will use a BufferedReader to read the data from the file.
            BufferedReader tdfReader = new BufferedReader(
                    new InputStreamReader(tdfStream));

            // We will read one line at a time and then split it.
            // The format for tdf.txt is as follows:
            // - Column 1: Year
            // - Column 2: Average Speed
            // - Column 3: Biker's last name
            // - Column 4: Biker's first name
            // tdf.txt contains real data. It is also noisy like real data.
            // Some of the names have formatting issues but we have left
            // things as is.
            String line;

            // Read each line of the file until there is nothing left to read.
            while ((line = tdfReader.readLine()) != null) {

                // Split the line into columns using the split( )
                // method for Strings.
                String[] columns = line.split(",");

                // After the split, we should have the following (as Strings):
                // - columns[0] contains the year,
                // - columns[1] contains the average speed,
                // - columns[2] contains the last name,
                // - columns[3] contains the first name.

                // Is this biker already in our list?
                // If so then we do not have to create a new biker.
                // We only have to add an entry to the existing biker.
                // We will use the full name to search allBikers.
                String key = columns[3] + columns[2]; // this is the full name

                // If search is successful then add stats
                if (allBikers.containsKey(key)) {
                    allBikers.get(key).addPerformanceStats(
                            Integer.parseInt(columns[0]),
                            Double.parseDouble(columns[1]));
                    		numberOfEntries++;

                    // System.out.println("Added data to biker "+allBikers.get(
                    // key ).getName( ));
                } else {
                    // Let us now create a new Biker
                    Biker newBiker = new Biker(columns[2], columns[3],
                            Integer.parseInt(columns[0]),
                            Double.parseDouble(columns[1]));
                    		numberOfEntries++;

                    // Now we add this biker to allBikers.
                    allBikers.put(key, newBiker);

                    // System.out.println("Created new biker "+newBiker.getName()
                    // );
                }

            }
            tdfReader.close();
            tdfStream.close();
        } catch (Exception e) {
            // If, for any reason, we had some problems reading data...
            throw new RuntimeException(e);
        }

        // for each entry in allBikers:
        // print the best gain for the biker.
        // The best gain is defined as the maximum improvement in
        // speed between successive entries at the Tour de France.
        // This does not have to be between consecutive years;
        // entries with a gap (no racing) between the years is okay.
        for (Map.Entry<String, Biker> currentEntry : allBikers.entrySet()) {
            Biker currentBiker = currentEntry.getValue();

            // How to print formatted strings
            // Note the use of String.format( )
            System.out.println(String.format("%-30s: %s",
                    currentBiker.getName(), currentBiker.getBestGain()));
        }

        // TODO: Compute the median speed across all the entries.
        double[] medianArrayOfAll = new double[numberOfEntries] ;
        int index = 0;
        double medianOfAll;
        for(Map.Entry<String, Biker> currentEntry : allBikers.entrySet()){
        	SortedMap<Integer, Double> averageSpeeds = currentEntry.getValue().getAverageSpeeds();
        	for(Entry<Integer, Double> entry : averageSpeeds.entrySet()){
        		medianArrayOfAll[index] += entry.getValue();
        		index++;
        	}
        }
        
        Arrays.sort(medianArrayOfAll);
        
        if(index % 2 == 0){
        	medianOfAll = (double) (medianArrayOfAll[index/2-1] + medianArrayOfAll[index/2])/2;
        }
        else{
        	medianOfAll = medianArrayOfAll[index/2];
        }
    	System.out.println("\nThe median speed at the Tour de France is "
                + medianOfAll);

    	
    	
        // TODO: Compute the median of medians.
    	int sizeOfBikers = allBikers.size();
        double medianOfMedians;
        double[] allMedianSpeeds = new double[sizeOfBikers];
        int i = 0;
        for(Map.Entry<String, Biker> currentEntry : allBikers.entrySet()){
        	allMedianSpeeds[i] += currentEntry.getValue().getMedianSpeed();
        	i++;
        }
        Arrays.sort(allMedianSpeeds);
        if(allBikers.size() % 2 == 0){
    		medianOfMedians = (double)(allMedianSpeeds[sizeOfBikers/2 - 1] + allMedianSpeeds[sizeOfBikers/2])/2;    		
    	}
    	else{
    		medianOfMedians = allMedianSpeeds[sizeOfBikers/2];
    	}
        // For each biker, compute the median speed. This will result in a list
        // of
        // median speeds. Now determine the median of this list.
        // Store the result in medianOfMedians.
        // Your code should go here.
        System.out.println("\nThe median of medians at the Tour de France is "
                + medianOfMedians);
    }
}