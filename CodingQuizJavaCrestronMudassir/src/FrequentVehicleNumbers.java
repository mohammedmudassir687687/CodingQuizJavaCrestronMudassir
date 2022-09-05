// Crestron Co-op Java Coding Quiz 
// Title: Frequent Vehicle Numebers Top 100 
// Author name: Mudassir Mohammed 
// mailto: mohammedmudassir687687@gmail.com
// Date: September 4, 2022
// IDE used: Eclipse

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FrequentVehicleNumbers {
	
	public static void main (String args[]) throws IOException {
		Map<String, Integer> vehicleCount = new HashMap<>();
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		System.out.println("Welcome...");
		System.out.println();
		System.out.println("Instructions: \nKindly place the input excel file in the src folder of this project and name it 'numberPlates'.");
		
		do {
			System.out.println("Please select one of the two options telling the program the format of the input excel file.");
			System.out.println("******************");
			System.out.println("1. (numberPlates.xls) format \n2. (numberPlates.xlsx) format");
			try {
				choice = sc.nextInt();
			} catch (Exception e) {
				e.getMessage();
				break;
			}
			
		} while (choice != 1 && choice != 2);
		sc.close();
			
		switch(choice) {

			case 1:
				xlsFormatProcess(vehicleCount);
				outputTopHundredVehicles(vehicleCount);
				break;
			
			case 2:
				xlsxFormatProcess(vehicleCount);
				outputTopHundredVehicles(vehicleCount);
				break;
			
			default:
				System.out.println("Invalid option.");
				System.out.println("Program terminated. Run again.");
				break;
		}
		
		
	}
	
	
	// Method for formatting the .xls extension excel file
	public static void xlsFormatProcess(Map<String, Integer> vehicleCount) {
		try {
			File file = new File("src/numberPlates.xls");
			
			// creating an input stream on the file
			FileInputStream fis=new FileInputStream(file);
			
			//creating workbook instance that refers to .xls file  
			HSSFWorkbook wb=new HSSFWorkbook(fis);   
			
			//creating a Sheet object to retrieve the object  
			HSSFSheet sheet=wb.getSheetAt(0);  
			
			for(Row row: sheet)     //iteration over row using for each loop  
			{  
				for(Cell cell: row)    //iteration over cell using for each loop  
				{  
					String vehicleNumber = cell.getStringCellValue();
					vehicleNumber.toLowerCase(); 	// converting vehicle number to lowercase
					vehicleNumber.replaceAll("\\s", ""); 	// removing all whitespaces from the string	
					
					// Check if the key exists in the map. If key exists, increment the count by 1
					// else create a new key and add 0 as the value;
					if (vehicleCount.containsKey(vehicleNumber)) {
						vehicleCount.put(vehicleNumber, vehicleCount.get(vehicleNumber) + 1);
					} else {
						vehicleCount.put(vehicleNumber, 1);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Kindly select the matching option for the file extension and name the file 'numberPlates'.");
			System.out.println("Program Terminated. Run again.");
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	// Method for formatting the .xlsx extension excel file
	public static void xlsxFormatProcess(Map<String, Integer> vehicleCount) {
		try {
			File file = new File("src/numberPlates.xlsx");
			
			// creating an input stream on the file
			FileInputStream fis=new FileInputStream(file);
			
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			
			//creating a Sheet object to retrieve object 
			XSSFSheet sheet = wb.getSheetAt(0);   
			
			Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
			while (itr.hasNext()) {
				
				Row row = itr.next();  
				Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
				
				while (cellIterator.hasNext()) {  
					Cell cell = cellIterator.next();
					
					String vehicleNumber = cell.getStringCellValue();
					vehicleNumber.toLowerCase(); 	// converting vehicle number to lowercase
					vehicleNumber.replaceAll("\\s", ""); 	// removing all whitespaces from the string	
					
					// Check if the key exists in the map. If key exists, increment the count by 1
					// else create a new key and add 0 as the value;
					if (vehicleCount.containsKey(vehicleNumber)) {
						vehicleCount.put(vehicleNumber, vehicleCount.get(vehicleNumber) + 1);
					} else {
						vehicleCount.put(vehicleNumber, 1);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Kindly select the correct option for the file extension and name the file 'numberPlates'.");
			System.out.println("Program Terminated. Run again.");
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void outputTopHundredVehicles(Map<String, Integer> vehicleCount) {
		Set<Entry<String, Integer>> entrySet = vehicleCount.entrySet();
		
		List<Entry<String, Integer>> list = new ArrayList<>(entrySet);
		
		Collections.sort(list, (o1, o2) -> -1*(o1.getValue().compareTo(o2.getValue())));
		
		// printing top 100 most frequent vehicle numbers
		System.out.println();
		System.out.println("************************************************************");
		System.out.println("Output: The 100 most frequent vehicle license plate numbers");
		
		if (list.size() <= 100) {
			if (list.size() < 100)
				System.out.println("Number of different vehicle numbers provided in the excel file are less than 100");
			else if (list.size() == 100) 
				System.out.println("Number of different vehicle numbers provided in the excel file are equal to 100");
			else 
				System.out.println("Number of different vehicle numbers provided in the excel file are more than 100. The top 100 frequent vehicle numbers are:");
			
			for (int i = 0; i < list.size(); i++) {
				System.out.println(i + 1 + ".\t" + list.get(i).getKey() + "\tFrequency: " + list.get(i).getValue());
			}
		} else {
			for (int i = 0; i < 100; i++) {
				System.out.println(i + 1 + ".\t" + list.get(i).getKey() + "\tFrequency: " + list.get(i).getValue());
			}
		}
	}
	
}




















