/**
 * @author Mengyuan Huang, Ruijia Wang
 * This program takes user's input as a query and take data 
 * from population file. It will calculate the population in 
 * the query rectangle, print out the population in that 
 * query rectangle and the percent to the entire population.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class PopulationQuery {
	// next four constants are relevant to parsing
	public static final int TOKENS_PER_LINE  = 7;
	public static final int POPULATION_INDEX = 4; // zero-based indices
	public static final int LATITUDE_INDEX   = 5;
	public static final int LONGITUDE_INDEX  = 6;
	public static final int SEQUENTIAL_CUTOFF = 10000;
	
	// parse the input file into a large array held in a CensusData object
	@SuppressWarnings("resource")
	public static CensusData parse(String filename) {
		CensusData result = new CensusData();
		
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(filename));
            
            // Skip the first line of the file
            // After that each line has 7 comma-separated numbers (see constants above)
            // We want to skip the first 4, the 5th is the population (an int)
            // and the 6th and 7th are latitude and longitude (floats)
            // If the population is 0, then the line has latitude and longitude of +.,-.
            // which cannot be parsed as floats, so that's a special case
            //   (we could fix this, but noisy data is a fact of life, more fun
            //    to process the real data as provided by the government)
            
            String oneLine = fileIn.readLine(); // skip the first line

            // read each subsequent line and add relevant data to a big array
            while ((oneLine = fileIn.readLine()) != null) {
                String[] tokens = oneLine.split(",");
                if(tokens.length != TOKENS_PER_LINE)
                	throw new NumberFormatException();
                int population = Integer.parseInt(tokens[POPULATION_INDEX]);
                if(population != 0)
                	result.add(population,
                			   Float.parseFloat(tokens[LATITUDE_INDEX]),
                		       Float.parseFloat(tokens[LONGITUDE_INDEX]));
            }

            fileIn.close();
        } catch(IOException ioe) {
            System.err.println("Error opening/reading/writing input or output file.");
            System.exit(1);
        } catch(NumberFormatException nfe) {
            System.err.println(nfe.toString());
            System.err.println("Error in file format");
            System.exit(1);
        }
        return result;
	}

	/**
	 * pre: the arugment should be in the correct format.
	 * 		(quit otherwise)
	 *  	the user input should be in the format of four
	 *  	integer in the same line.
	 *  	(quit otherwise)
	 * post: Take user input as a query, find the population 
	 * 		 in the query rectangle and print it out.
	 * @param args takes argument in the format of 
	 * 			[filenName, x, y, version]
	 * argument 1: file name for input data: pass this to parse
	// argument 2: number of x-dimension buckets
	// argument 3: number of y-dimension buckets
	// argument 4: -v1, -v2, -v3, -v4, or -v5
	 */
	public static void main(String[] args) {
		if(args.length != 4){
			System.err.println("arguments wrong");
			System.exit(1);
		}
		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		Calculator cal = null;
		if(args[3].equals("-v1")){
			cal = new Version1(parse(args[0]));
		} else if(args[3].equals("-v2")){
			cal = new Version2(parse(args[0]), SEQUENTIAL_CUTOFF);
		} else if(args[3].equals("-v3")){
			cal = new Version3(parse(args[0]), x, y);
		} else if(args[3].equals("-v4")){
			cal = new Version4(parse(args[0]), SEQUENTIAL_CUTOFF, x, y);
		} else if(args[3].equals("-v5")){
			cal = new Version5(parse(args[0]), SEQUENTIAL_CUTOFF, x, y);
		} else {
			System.exit(1);
		}
		Rectangle rect = cal.findCorners();
		
		Scanner console = new Scanner(System.in);
		printResult(x, y, rect, cal, console);
		console.close();
	}
	
	/**
	 * Help method to calculate and print query rectangle population.
	 * It will repeat the prompt for another query if user input is 
	 * valid and in the form of four integers a line. (exit otherwise).
	 * @param x columns
	 * @param y rows
	 * @param rect the US rectangle
	 * @param cal version to calculate the population
	 * @param console for user input
	 */
	private static void printResult(int x, int y, Rectangle rect, Calculator cal, Scanner console){
		System.out.println("Please give west, south, east, north coordinates of your query rectangle:");

		int west = 0;
		int south = 0;
		int north = 0;
		int east = 0;
		
		String line = console.nextLine(); //get user input from a line.
		String[] input = line.split(" ");
		if(input.length != 4){
			System.exit(1);
		}
		for (String value : input){
			if(!isValidNumber(value)){
				System.exit(1);
			}
		}
		west = Integer.parseInt(input[0]); 
		south = Integer.parseInt(input[1]);
		east = Integer.parseInt(input[2]);
		north = Integer.parseInt(input[3]);
		
		checkValid(x, y, west, south, east, north);
		
		
		int population = cal.calculateQuery(rect, x, y, west, south, east, north);
		System.out.println("population of rectangle:" + population);
		int totalPopulation = cal.calculateQuery(rect, x, y, 1, 1, x, y);
		float percent = ((float)population * 100 / (float) totalPopulation);
		System.out.println("percent of total population:" + String.format("%.2f", percent));
		
		printResult(x, y, rect, cal, console); //repeat prompt for new query.
	}
	
	/**
	 * help method of printResult. 
	 * It will check whether user input is a valid number.
	 * exit otherwise.
	 * @param value user input
	 * @return true if user input can be transform to an integer.
	 */
	private static boolean isValidNumber(String value){
		for(int i = 0; i < value.length(); i++){
			if(!Character.isDigit(value.charAt(i))){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * help method of printResult
	 * it will check whether user inputs are inside 
	 * the range of x*y rectangle.
	 * exit otherwise.
	 * @param x columns
	 * @param y rows
	 * @param west user input
	 * @param south user input
	 * @param east user input
	 * @param north user input
	 */
	private static void checkValid(int x, int y, int west, int south, int east, int north){
		if(west < 1 || west > x || south < 1 || south > y || east < west || east > x || north < south || north > y){
			System.exit(1);
		}
	}
	
}
