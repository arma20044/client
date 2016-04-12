package src.main.java.admin.candidato;
import java.util.ArrayList;
import java.util.List;

public class Pattern {
	

	
		public static void main(String[] args) {
			List<String> input = new ArrayList<String>();
			input.add("9999");
			input.add("2015/abcd");
			input.add("987-65-4321 (attack)");
			input.add("987-65-4321 ");
			input.add("5");


			for (String ssn : input) {
				if (ssn.matches("\\d{4}/[a-zA-Z]{3}")) {
					System.out.println("Found good SSN: " + ssn);
				}
			}
		}
	
}
