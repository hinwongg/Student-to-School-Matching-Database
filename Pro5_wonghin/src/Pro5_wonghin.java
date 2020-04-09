import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class Pro5_wonghin {

	public static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	public static String dashesStudent = "---------------------------------------------------------------------------------------------------------------------------";
	public static String dashesSchool = "------------------------------------------------------------------------------------------------------------------------------";
	public static String dashesComparison = "----------------------------------------------------------------------------------------------------------------";

	public static void main(String[] args) throws NumberFormatException, IOException, FileNotFoundException {
		// TODO Auto-generated method stub

		
		int numOfStudents = 0;
		int numOfSchools = 0;
		int numOfStudentsAdded;
		int numOfSchoolsAdded;
		boolean matchingHappened = false;

		Student student = new Student();
		School school = new School();
		 
		// Initializing the S and H arrays?
		ArrayList<Student> S = new ArrayList<Student>();
		ArrayList<School> H = new ArrayList<School>();
		
		ArrayList<Student> S2 = new ArrayList<Student>();
		ArrayList<School> H2 = new ArrayList<School>();
		
		//These 4 copied arrays are for oldDisplayMatches
		ArrayList<Student> oldStudents = new ArrayList<Student>();
		ArrayList<School> oldSchools = new ArrayList<School>();
		
		ArrayList<Student> oldStudents2 = new ArrayList<Student>();
		ArrayList<School> oldSchools2 = new ArrayList<School>();
		
		
		//Two SMPSolver Objects
		SMPSolver GSS = new SMPSolver ();
		SMPSolver GSH = new SMPSolver ();

		
		String enterChoice;

		do {

			displayMenu();
			
			enterChoice = cin.readLine();

			System.out.print("\n");

			if (enterChoice.equals("l") || enterChoice.equals("L")) {
				
				
				numOfSchoolsAdded = loadSchools(S, H);
				numOfSchools = numOfSchoolsAdded + numOfSchools;
				numOfStudents = 0;
				//CAUTION. This SHOULD help with printing, but it may not work.
				
				numOfStudentsAdded = loadStudents(S, H);
				numOfStudents = numOfStudentsAdded + numOfStudents;

				// You need to reset the matches if a new student/school
				// is SUCCESSFULLY loaded.
				if (numOfSchoolsAdded > 0 || numOfStudentsAdded > 0) {
					matchingHappened = false;
				}

				// in the main function after getting new Schools (H) and Students (S)
				H2.clear();
				S2.clear();
				H2 = copySchools(H, numOfStudents);
				S2 = copyStudents(S, numOfSchools);
			//	GSH.reset(H2, S2); // SMPSolver with high schools as suitors
				
				//Copy the 4 duplicate arrays
				oldStudents.clear();
				oldSchools.clear();
				oldStudents = copyStudents(S, numOfSchools);
				oldSchools = copySchools(H, numOfStudents);
				
				oldStudents2.clear();
				oldSchools2.clear();
				oldStudents2 = copyStudents(S2, numOfSchools);
				oldSchools2 = copySchools(H2, numOfStudents);
				
				
			} else if (enterChoice.equals("e") || enterChoice.equals("E")) {

				editData(S, H, oldStudents, oldSchools, oldStudents2, oldSchools2, numOfStudents, matchingHappened);
				
				//After editing students/schools you should CLEAR 
				//them and then COPY them again.
				//NOTE: Not sure if correct though...
				H2.clear();
				S2.clear();
				H2 = copySchools(H, numOfStudents);
				S2 = copyStudents(S, numOfSchools);
				//If it really does not work, the just EXCLUDE the above ^
			}

			else if (enterChoice.equals("q") || enterChoice.equals("Q")) {
				break;
			}

			else if (enterChoice.equals("p") || enterChoice.equals("P")) {

				if (numOfStudents == 0) {
					System.out.println("ERROR: No students are loaded!");
					System.out.print("\n");
				}
				if (numOfStudents != 0) {
					System.out.print("STUDENTS:");
					System.out.print("\n");
					printStudents(S, H, matchingHappened);
					System.out.print("\n");
				}
				if (numOfSchools == 0) {
					System.out.println("ERROR: No schools are loaded!");
					System.out.print("\n");
				}
				if (numOfSchools != 0) {

					System.out.print("SCHOOLS:");
					System.out.print("\n");
					printSchools(S, H, numOfStudents, matchingHappened);
					System.out.print("\n");
				}
			}

			else if (enterChoice.equals("m") || enterChoice.equals("M")) {
				
				
				int totalNumberOfOpenings = 0;
				int currentNumberOfOpenings = 0;
				long start;
				long elapsedTime;
				int numOfSuitors = numOfStudents;
				int numOfReceivers = numOfSchools;

				System.out.print("STUDENT-OPTIMAL MATCHING");
				System.out.print("\n");

				for (int i = 0; i < H.size(); i++) {
					currentNumberOfOpenings = H.get(i).getMaxMatches();
					totalNumberOfOpenings = totalNumberOfOpenings + currentNumberOfOpenings;
				}

				if (numOfSuitors == 0) {
					System.out.print("\n");
					System.out.print("ERROR: No suitors are loaded!");
					System.out.print("\n");
					System.out.print("\n");
				}

				else if (numOfReceivers == 0) {
					System.out.print("\n");
					System.out.print("ERROR: No receivers are loaded!");
					System.out.print("\n");
					System.out.print("\n");
				}

				// Total num of openings at all schools must be
				// equal to the number of students
				else if (totalNumberOfOpenings != S.size()) {
					System.out.print("ERROR: The number of suitor and receiver openings must be equal!");
					System.out.print("\n");
				}

				//^Everything above is just ERROR statements...
				else {

					//Clear and then copy the oldArrays. This makes "d" (displayMatches) work
					//NOTE: Not sure if it will work correctly....
					oldStudents.clear();
					oldSchools.clear();
					oldStudents = copyStudents(S, numOfSchools);
					oldSchools = copySchools(H, numOfStudents);
					GSS.setParticipants(oldStudents, oldSchools);
					matchingHappened = GSS.match();
					
					GSS.setParticipants(S, H);
					start = System.currentTimeMillis();
					// Get current time
					matchingHappened = GSS.match();
					elapsedTime = System.currentTimeMillis() - start;
					// Get elapsed time in ms
					
					displayMatchesForSMPSolver(GSS, GSH, S, H, true);
					GSS.setTime(elapsedTime);
					System.out.format("%d matches made in %dms!", numOfSchools, elapsedTime);
					System.out.print("\n");
					System.out.print("\n");
					
					
				}

				System.out.print("SCHOOL-OPTIMAL MATCHING");
				System.out.print("\n");

				totalNumberOfOpenings = 0;
				currentNumberOfOpenings = 0;

				//Use H2 since it is for the copied schools
				for (int i = 0; i < H2.size(); i++) {
					currentNumberOfOpenings = H2.get(i).getMaxMatches();
					totalNumberOfOpenings = totalNumberOfOpenings + currentNumberOfOpenings;
				}

				//NOTE: I swapped with numOfReceivers since everything is reversed when
				//schools are suitors
				if (numOfReceivers == 0) {
					System.out.print("\n");
					System.out.print("ERROR: No suitors are loaded!");
					System.out.print("\n");
					System.out.print("\n");
				}

				else if (numOfSuitors == 0) {
					System.out.print("\n");
					System.out.print("ERROR: No receivers are loaded!");
					System.out.print("\n");
					System.out.print("\n");
				}

				// Total num of openings at all schools must be
				// equal to the number of students
				else if (totalNumberOfOpenings != S.size()) {
					System.out.print("ERROR: The number of suitor and receiver openings must be equal!");
					System.out.print("\n");
				}

				else {

					//Clear and then copy the oldArrays. This makes "d" (displayMatches) work
					//NOTE: Not sure if it will work correctly....
					oldStudents2.clear();
					oldSchools2.clear();
					oldStudents2 = copyStudents(S2, numOfSchools);
					oldSchools2 = copySchools(H2, numOfStudents);
					//Remember for GSH you have to swap suitors and receivers!!
					GSH.setParticipants(oldSchools2, oldStudents2);
					matchingHappened = GSH.matchForSchoolsAsSuitors();
					
					GSH.setParticipants(H2, S2);
					start = System.currentTimeMillis();
					// Get current time
					matchingHappened = GSH.matchForSchoolsAsSuitors();
					elapsedTime = System.currentTimeMillis() - start;
					// Get elapsed time in ms

					displayMatchesForSMPSolver(GSS, GSH, S2, H2, false);
					GSH.setTime(elapsedTime);
					System.out.format("%d matches made in %dms!", numOfSchools, elapsedTime);
					System.out.print("\n");
					System.out.print("\n");
					
					
				}

			}

			else if (enterChoice.equals("d") || enterChoice.equals("D")) {

				int totalNumberOfOpenings = 0;
				int currentNumberOfOpenings = 0;
				long start;
				long elapsedTime;
				int numOfSuitors = numOfStudents;
				int numOfReceivers = numOfSchools;
			
				
				System.out.print("STUDENT-OPTIMAL MATCHING");
				System.out.print("\n");
				
				if (matchingHappened == false) {
					System.out.print("\n");
					System.out.println("ERROR: No matches exist!");
					System.out.print("\n");
				}

				else {
					//Added this here
					//LMFAO
				//	oldStudents.clear();
				//	oldSchools.clear();
				//	oldStudents = copyStudents(S, numOfSchools);
				//	oldSchools = copySchools(H, numOfStudents);
					//LMFAO
				//	oldStudents2.clear();
				//	oldSchools2.clear();
				//	oldStudents2 = copyStudents(S2, numOfSchools);
				//	oldSchools2 = copySchools(H2, numOfStudents);
					
					GSS.setParticipants(oldStudents, oldSchools);
					start = System.currentTimeMillis();
					// Get current time
					matchingHappened = GSS.match();
					elapsedTime = System.currentTimeMillis() - start;
					GSS.setTime(elapsedTime);
					
					
					GSH.setParticipants(oldSchools2, oldStudents2);
					start = System.currentTimeMillis();
					// Get current time
					matchingHappened = GSH.matchForSchoolsAsSuitors();
					elapsedTime = System.currentTimeMillis() - start;
					GSH.setTime(elapsedTime);
					
					System.out.print("\n");
					displayMatches(GSS, GSH, oldStudents, oldSchools, true);
				}

				System.out.print("SCHOOL-OPTIMAL SOLUTION");
				System.out.print("\n");

				if (matchingHappened == false) {
					System.out.print("\n");
					System.out.println("ERROR: No matches exist!");
					System.out.print("\n");
				}

				else {
					
					System.out.print("\n");
					displayMatches(GSS, GSH, oldStudents2, oldSchools2, false);
				}

			}

			else if (enterChoice.equals("x") || enterChoice.equals("X")) {

				if (matchingHappened == false) {
					System.out.println("ERROR: No matches exist!");
					System.out.print("\n");
				}

				else {
					printComparison(GSS, GSH);
					System.out.print("\n");
				}
			}
			else if (enterChoice.equals("r") || enterChoice.equals("R")) {
				// CLEAR EVERYTHING

				numOfStudents = 0;
				numOfSchools = 0;
				S.clear();
				H.clear();
				
				S2.clear();
				H2.clear();
				
				matchingHappened = false;

				System.out.println("Database cleared!");
				System.out.print("\n");
				
			}
			
			
			else {
				System.out.print("ERROR: Invalid menu choice!");
				System.out.print("\n");
				System.out.print("\n");
				
			}

		} while (!enterChoice.equals("q") || !enterChoice.equals("Q"));

		System.out.print("Hasta luego!");
		
	}

	public static void displayMenu() {

		
		System.out.print("JAVA STABLE MARRIAGE PROBLEM v3");
		System.out.print("\n");
		System.out.println("L - Load students and schools from file");
		System.out.println("E - Edit students and schools");
		System.out.println("P - Print students and schools");
		System.out.println("M - Match students and schools using Gale-Shapley algorithm");
		System.out.println("D - Display matches");
		System.out.println("X - Compare student-optimal and school-optimal matches");
		System.out.println("R - Reset database");
		System.out.println("Q - Quit");
		System.out.print("\n");
		System.out.print("Enter choice: ");

	}
	

	// create independent copy of School ArrayList
	public static ArrayList<School> copySchools(ArrayList<School> P, int nStudents) {
		ArrayList<School> newList = new ArrayList<School>();
		for (int i = 0; i < P.size(); i++) {
			String name = P.get(i).getName();
			double alpha = P.get(i).getAlpha();
			int maxMatches = P.get(i).getMaxMatches();
			nStudents = P.get(i).getNParticipants();
			School temp = new School(name, alpha, maxMatches, nStudents);
			for (int j = 0; j < nStudents; j++) {
				
				temp.setRanking(j, P.get(i).getRanking(j));
				temp.setRankingInverse(j, P.get(i).getRankingInverse(j));
			}
			newList.add(temp);
		}
		return newList;
	}
	
	// create independent copy of Student ArrayList
		public static ArrayList<Student> copyStudents(ArrayList<Student> P, int nSchools) {
			ArrayList<Student> newList = new ArrayList<Student>();
			for (int i = 0; i < P.size(); i++) {
				String name = P.get(i).getName();
				double GPA = P.get(i).getGPA();
				int extraCurricular = P.get(i).getES();
				//int maxMatches = P.get(i).getMaxMatches();
				nSchools = P.get(i).getNParticipants();
				Student temp = new Student(name, GPA, extraCurricular, nSchools);
				for (int j = 0; j < nSchools; j++) {
					temp.setRanking(j, P.get(i).getRanking(j));
					temp.setRankingInverse(j, P.get(i).getRankingInverse(j));
				}
				newList.add(temp);
			}
			return newList;
		}
		
		public static ArrayList<School> copySchoolsExceptOpenings(ArrayList<School> P, int nStudents, int schoolToEdit, int oldSchoolsNumOfOpenings) {
			ArrayList<School> newList = new ArrayList<School>();
		
				//Not sure if this is correct ".remove()"
				//newList.remove(schoolToEdit);
				
				String name = P.get(schoolToEdit).getName();
				double alpha = P.get(schoolToEdit).getAlpha();

				nStudents = P.get(schoolToEdit).getNParticipants();
				School temp = new School(name, alpha, oldSchoolsNumOfOpenings, nStudents);
				for (int j = 0; j < nStudents; j++) {

					temp.setRanking(j, P.get(schoolToEdit).getRanking(j));
					temp.setRankingInverse(j, P.get(schoolToEdit).getRankingInverse(j));
				}
				newList.add(temp);
			
			
		
		return newList;
	}

	public static int loadStudents(ArrayList<Student> S, ArrayList<School> H)
			throws NumberFormatException, IOException, FileNotFoundException {

		School school = new School();
		String filename = "";
		String line = "\0";

		int studentCount = 0;
		int lineCount = 0;

		boolean fileExists;
		boolean isValid = false;

		do {
			System.out.format("Enter student file name (0 to cancel): ");
			filename = cin.readLine();

			if (filename.equals("0")) {
				System.out.format("\n");
				System.out.format("File loading process canceled.");
				System.out.format("\n");
				System.out.format("\n");
				return studentCount;
			}

			fileExists = fileExists(filename);
		
		} while (fileExists == false);

		BufferedReader fin = new BufferedReader(new FileReader(filename));

		do {
			Student student = new Student();
			line = fin.readLine();	
			boolean canRank = true;

			if (line != null && !line.isEmpty()) {
				//System.out.format(line);
				//System.out.print("\n");
				String[] input = line.split(",");

				String nameInput = (input[0]);
				student.setName(nameInput);

				double GPA = Double.parseDouble(input[1]);
				student.setGPA(GPA);

				int extraCurricular = Integer.parseInt(input[2]);
				student.setES(extraCurricular);

				// Check that there is correct number of rankings
				if (input.length != (3 + H.size())) {
					canRank = false;
				}

				if (canRank == true) {
					for (int k = 0; k < H.size(); k++) {

						int studentsRankingOfSchool = Integer.parseInt(input[k + 3]);
						//Something is wrong over here
						student.setRanking(studentsRankingOfSchool-1, k);
						student.setRankingInverse(k, studentsRankingOfSchool-1);
					

					}

					isValid = student.isValid(H);

					if (isValid == true) {
						S.add(student);
						studentCount++;
						// The studentCount is the number of valid students
					}
				}
				lineCount++;
				// The lineCount is the number of lines you read. You need this
				// to print 3 of 4 students added...etc.
			}
		} while (line != null);

		// Assign or update the school's rankings of students
		school.calcRankings(S, H);
		
		
		
		
		System.out.print("\n");
		System.out.format("%d of %d students loaded!", studentCount, lineCount);
		System.out.print("\n");
		System.out.print("\n");
	
		
		//This just prints the rankings. Make sure no repeats.
		
		
		
		 fin.close();
		return studentCount;

	}

	public static int loadSchools(ArrayList<Student> S, ArrayList<School> H)
			throws NumberFormatException, IOException, FileNotFoundException {

		//Clears the students since rankings won't be valid
		S.clear();
		String filename = "";

		int schoolCount = 0;
		int lineCount = 0;

		String line;
		boolean fileExists;
		boolean isValid = false;

		do {

			System.out.format("Enter school file name (0 to cancel): ");
			filename = cin.readLine();

			if (filename.equals("0")) {
				System.out.format("\n");
				System.out.format("File loading process canceled.");
				System.out.format("\n");
				System.out.format("\n");
				return schoolCount;
			}

			fileExists = fileExists(filename);

		} while (fileExists == false);

		BufferedReader fin = new BufferedReader(new FileReader(filename));

		do {
			line = fin.readLine();
			School school = new School();

			if (line != null && !line.isEmpty()) {
				//System.out.format(line);
				String[] input = line.split(",");
				//System.out.format("\n");

				String nameInput = (input[0]);
				school.setName(nameInput);

				double GPAWeight = Double.parseDouble(input[1]);
				school.setAlpha(GPAWeight);

				int maxMatches = Integer.parseInt(input[2]);
				school.setMaxMatches(maxMatches);
				
				
				isValid = school.isValid();

				if (isValid == true) {

					H.add(school);
					schoolCount++;

				}

				lineCount++;
			}
		} while (line != null);
		
		System.out.print("\n");
		System.out.format("%d of %d schools loaded!", schoolCount, lineCount);
		System.out.print("\n");
		System.out.print("\n");

		fin.close();
		return schoolCount;
	}

	public static void editData(ArrayList<Student> S, ArrayList<School> H, ArrayList<Student> oldStudents, ArrayList<School> oldSchools, ArrayList<Student> oldStudents2, ArrayList<School> oldSchools2,  int numOfStudents, boolean matchingHappened) throws NumberFormatException, IOException {

		String enterChoice;

		do {

			System.out.println("Edit data");
			System.out.println("---------");
			System.out.println("S - Edit students");
			System.out.println("H - Edit high schools");
			System.out.println("Q - Quit");
			System.out.print("\n");
			System.out.print("Enter choice: ");

			enterChoice = cin.readLine();
			System.out.print("\n");

			if (enterChoice.equals("s") || enterChoice.equals("S")) {

				if (S.size() == 0) {

					System.out.println("ERROR: No students are loaded!");

				}

				else {
					editStudents(S, H, oldStudents, oldStudents2, matchingHappened);

				}
			} else if (enterChoice.equals("h") || enterChoice.equals("H")) {
				if (H.size() == 0) {

					System.out.println("ERROR: No schools are loaded!");

				} else {
					editSchools(S, H, oldSchools, oldSchools2, numOfStudents, matchingHappened);
				}

			} else if (enterChoice.equals("q") || enterChoice.equals("Q")) {
				break;

			} else {
				System.out.print("ERROR: Invalid menu choice!");
				System.out.print("\n");
			}

			System.out.print("\n");
		} while (!enterChoice.equals("q") || !enterChoice.equals("Q"));
	}

	public static void editStudents(ArrayList<Student> S, ArrayList<School> H, ArrayList<Student> oldStudents, ArrayList<Student> oldStudents2, boolean matchingHappened)
			throws NumberFormatException, IOException {

		int studentToEdit;
		Student student = new Student();
		School school = new School();
		

		do {

			printStudents(S, H, matchingHappened);
			studentToEdit = getInteger("Enter student (0 to quit): ", 0, S.size());
			System.out.print("\n");

			if (studentToEdit == 0) {
				break;
			} else {

				System.out.print("Name: ");
				String nameInput = cin.readLine();

				S.get(studentToEdit - 1).setName(nameInput);
				//Help456
				oldStudents.get(studentToEdit - 1).setName(nameInput);
				oldStudents2.get(studentToEdit - 1).setName(nameInput);
				

				double GPA = getDouble("GPA: ", 0.00, 4.00);
				S.get(studentToEdit - 1).setGPA(GPA);
				//Help456
				oldStudents.get(studentToEdit - 1).setGPA(GPA);
				oldStudents2.get(studentToEdit - 1).setGPA(GPA);

				int extraCurricular = getInteger("Extracurricular score: ", 0, 5);
				S.get(studentToEdit - 1).setES(extraCurricular);
				//Help456
				oldStudents.get(studentToEdit - 1).setES(extraCurricular);
				oldStudents2.get(studentToEdit - 1).setES(extraCurricular);

				int maxMatches = getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE);
				S.get(studentToEdit - 1).setMaxMatches(maxMatches);
				// Alex said to immediately change ".setMaxMatches" back to 1!
				// S.get(studentToEdit - 1).setMaxMatches(1);

				String userInput;
				int studentsRankingOfSchool;

				// If yes then edit him
				do {
					System.out.print("Edit rankings (y/n): ");
					userInput = cin.readLine();

					if (userInput.equals("y") || userInput.equals("Y")) {

						// Clear previous rankings
						for (int j = 0; j < H.size(); j++) {
							S.get(studentToEdit - 1).setRanking(j, -5);
							S.get(studentToEdit - 1).setRankingInverse(j, -5);
						}

						System.out.format("Participant " + S.get(studentToEdit - 1).getName() + "'s rankings:");
						System.out.print("\n");

						for (int schoolsOrder = 0; schoolsOrder < H.size(); schoolsOrder++) {

							boolean rankingIsValid;

							do {

								rankingIsValid = true;
								studentsRankingOfSchool = getInteger(("School " + H.get(schoolsOrder).getName() + ": "),
										1, H.size());

								// Check if ranking has been used.
								for (int k = 0; k < H.size(); k++) {

									// All rankings that have NOT been used
									// are equal to -5.
									if (studentsRankingOfSchool == (S.get(studentToEdit - 1).getRankingInverse(k))
											+ 1) {

										System.out.format("ERROR: Rank %d already used!\n", studentsRankingOfSchool);
										System.out.print("\n");
										rankingIsValid = false;
										break;

									}

								}

							} while (rankingIsValid == false);

							S.get(studentToEdit - 1).setRanking(studentsRankingOfSchool - 1, schoolsOrder);
							S.get(studentToEdit - 1).setRankingInverse(schoolsOrder, studentsRankingOfSchool - 1);
							//Help456
							oldStudents.get(studentToEdit - 1).setRanking(studentsRankingOfSchool - 1, schoolsOrder);
							oldStudents.get(studentToEdit - 1).setRankingInverse(schoolsOrder, studentsRankingOfSchool - 1);
							oldStudents2.get(studentToEdit - 1).setRanking(studentsRankingOfSchool - 1, schoolsOrder);
							oldStudents2.get(studentToEdit - 1).setRankingInverse(schoolsOrder, studentsRankingOfSchool - 1);

						}
						break;

					} else if (userInput.equals("n") || userInput.equals("N")) {
						break;
					} else {

						System.out.println("ERROR: Choice must be 'y' or 'n'!");
						System.out.print("\n");

					}
				} while (!userInput.equals("y") || !userInput.equals("n") || !userInput.equals("Y") || !userInput.equals("N"));
			}

		} while (studentToEdit != 0);

		// Update the school's rankings of students
		school.calcRankings(S, H);
	}

	public static void editSchools(ArrayList<Student> S, ArrayList<School> H, ArrayList<School> oldSchools, ArrayList<School> oldSchools2, int numOfStudents,
			boolean matchingHappened) throws NumberFormatException, IOException {

	
		School school = new School();
		
		int schoolToEdit;

		do {

			printSchools(S, H, numOfStudents, matchingHappened);
			schoolToEdit = getInteger("Enter school (0 to quit): ", 0, H.size());
			System.out.print("\n");
			
			if (schoolToEdit == 0) {
				break;
			} else {
				
				//int oldSchoolsNumOfOpenings = H.get(schoolToEdit - 1).getMaxMatches();
				System.out.print("Name: ");
				String nameInput = cin.readLine();

				System.out.print("\n");

				H.get(schoolToEdit - 1).setName(nameInput);
				//Help456
				oldSchools.get(schoolToEdit - 1).setName(nameInput);
				oldSchools2.get(schoolToEdit - 1).setName(nameInput);
				
				double GPAWeight = getDouble("GPA weight: ", 0.00, 1.00);
				H.get(schoolToEdit - 1).setAlpha(GPAWeight);
				//Help456
				oldSchools.get(schoolToEdit - 1).setAlpha(GPAWeight);
				oldSchools2.get(schoolToEdit - 1).setAlpha(GPAWeight);
				
				//Get the maxMatches from before
			//	int previousMaxMatches = H.get(schoolToEdit - 1).getMaxMatches();
				
				//school.calcRankings(S, H);
				//oldSchools = copySchoolsExceptOpenings(H, numOfStudents, schoolToEdit - 1, oldSchoolsNumOfOpenings);
				
				//oldSchools2.clear();
				//oldSchools2 = copySchoolsExceptOpenings(H, numOfStudents, schoolToEdit - 1, oldSchoolsNumOfOpenings);
				
				int maxMatches = getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE);
				//This is messed up because schools cannot be printed if the number of maxMatches changes
				H.get(schoolToEdit - 1).setMaxMatches(maxMatches);
				//Immediately change maxMatches back to before.
			//	H.get(schoolToEdit - 1).setMaxMatches(previousMaxMatches);

			}

		} while (schoolToEdit != 0);

		
		
		school.calcRankings(S, H);
		//Once you recalculate the school rankings, copy them into the "oldSchools" arrays
		for (int i = 0; i < H.size(); i++) {
			for (int j = 0; j < numOfStudents; j++) {
				// Help456
				oldSchools.get(i).setRanking(j, H.get(i).getRanking(j));
				oldSchools.get(i).setRankingInverse(j, H.get(i).getRankingInverse(j));
				oldSchools2.get(i).setRanking(j, H.get(i).getRanking(j));
				oldSchools2.get(i).setRankingInverse(j, H.get(i).getRankingInverse(j));
			}
		}

	}
	
	
	
	public static void printStudents(ArrayList<Student> S, ArrayList<School> H, boolean matchingHappened)
			throws NumberFormatException, IOException {

		System.out.print("\n");
		System.out.println(" #   Name                                         GPA  ES  Assigned school                         Preferred school order");
		System.out.println(dashesStudent);

		for (int i = 0; i < S.size(); i++) {

			String matchedSchool;
			 if (matchingHappened == true) {
				//This is just hard coded to print the first name
			 matchedSchool = H.get(S.get(i).getMatch(0)).getName(); 

			 } else {
			matchedSchool = "-";
			 }

			System.out.format("%3d. %-31s%17.2f%4d  %-40s", i + 1, S.get(i).getName(), S.get(i).getGPA(),
					S.get(i).getES(), matchedSchool);
			//("  %d. %-43s %.2f %3d  %-40s")
			// Following helper 1. Fix this shit up later

			for (int placement = 0; placement < H.size(); placement++) {
				//Why do you use getRankingInverse here??
				System.out.print(H.get(S.get(i).getRankingInverse(placement)).getName());

				if (placement == H.size() - 1) {
					System.out.print("");
				} else {
					System.out.print(", ");
				}

			}

			System.out.print("\n");

		}
		System.out.println(dashesStudent);

	}

	public static void printSchools(ArrayList<Student> S, ArrayList<School> H, int numOfStudents,
			boolean matchingHappened) throws NumberFormatException, IOException {

		School school = new School();
		
		
		System.out.print("\n");
		System.out.println(
				" #   Name                                     # spots  Weight  Assigned students                       Preferred student order");
		System.out.println(dashesSchool);
		for (int i = 0; i < H.size(); i++) {

			String matchedStudent ="";
			
			if (matchingHappened == true) {

				for (int j = 0; j < H.get(i).getMaxMatches(); j++) {
					//This prevents crashing if ".getMaxMatches" changes
					//in editSchool
					if (j < H.get(i).getMatchesSize()) {
						matchedStudent = matchedStudent + S.get(H.get(i).getMatch(j)).getName();

						if (j == H.get(i).getMaxMatches() - 1) {
							matchedStudent = matchedStudent + "";
						} else {
							matchedStudent = matchedStudent + ", ";
						}
					}
				}
			} else {

				matchedStudent = "-";
			}
			
			
			System.out.format("  %d. %-46s%2d%8.2f  %-38s  ", i + 1, H.get(i).getName(), H.get(i).getMaxMatches(),
					H.get(i).getAlpha(), matchedStudent);

			//My old one("  %d. %-30s %15d      %4.2f  ")
			
			// This print stuff is tacked on at the end of the row
			
			// System.out.format(" %-27s");
			//System.out.format("        ");

			if (numOfStudents != 0) {
				for (int placement = 0; placement < S.size(); placement++) {
					// Why do you use getRanking here??
					System.out.print(S.get(H.get(i).getRanking(placement)).getName());

					if (placement == S.size() - 1) {
						System.out.print("");
					} else {
						System.out.print(", ");
					}

				}

			}
			if (numOfStudents == 0) {
				System.out.print("-");
			}

			System.out.print("\n");
		}
		System.out.println(dashesSchool);

	}

	public static void displayMatches(SMPSolver GSS, SMPSolver GSH, ArrayList<Student> S, ArrayList<School> H,  boolean studentOptimal)
			throws NumberFormatException, IOException {

		System.out.println("Matches");
		System.out.println("--------");

		String stableMatching = "Yes";

		for (int i = 0; i < H.size(); i++) { // For each school.

			System.out.format("%s: ", H.get(i).getName());

			for (int j = 0; j < H.get(i).getMaxMatches(); j++) {

				System.out.format("%s", S.get(H.get(i).getMatch(j)).getName());

				if (j == H.get(i).getMaxMatches() - 1) {
					System.out.print("");
				} else {
					System.out.print(", ");
				}
			}
			System.out.format("\n");
		}

		// Stability

		for (int i = 0; i < S.size(); i++) {

			for (int schoolBeingChecked = 0; schoolBeingChecked < H.size(); schoolBeingChecked++) {

				// Check every school that the student ranked higher than
				// the one they're at.
				// If the ranking of the school being checked is LOWER (the
				// smaller the better) than
				// the ranking of the school that the student is matched with
				if (S.get(i).getRanking(schoolBeingChecked) < S.get(i).getRanking(S.get(i).getMatch(0))) {
					// if you enter this loop, this mean the school is being
					// checked.

					// If the ranking of the schoolBeingChecked is even lower
					// than the ranking
					// of the least preferred school, then matching is NOT
					// stable

					for (int k = 0; k < H.get(schoolBeingChecked).getMaxMatches(); k++) {

						if (H.get(schoolBeingChecked).getRankingInverse(i) < H.get(schoolBeingChecked)
								.getRankingInverse(H.get(schoolBeingChecked).getMatch(k))) {

							stableMatching = "No";

						}
					}

				}

			}
		}

		double studentRegret;
		double totalStudentRegret;
		double averageStudentRegret;
		double schoolRegret;
		double totalSchoolRegret;
		double averageSchoolRegret;
		
		double averageTotalRegret;

		double averageSuitorRegret = 0;
		double averageReceiverRegret = 0;

		if (studentOptimal == true) {
			// Student Regret
			studentRegret = 0;
			totalStudentRegret = 0;
			averageStudentRegret = 0;
			averageTotalRegret = 0;
			for (int i = 0; i < S.size(); i++) {

				// Changed to ".getRanking" (from .getRankingInverse)
				studentRegret = ((S.get(i).getRanking(((S.get(i).getMatch(0))))) + 1) - 1;
				totalStudentRegret = totalStudentRegret + studentRegret;
			}

			averageStudentRegret = (totalStudentRegret / S.size());
			averageSuitorRegret = averageStudentRegret;

			// School Regret
			schoolRegret = 0;
			totalSchoolRegret = 0;
			averageSchoolRegret = 0;
			for (int i = 0; i < H.size(); i++) {

				for (int k = 0; k < H.get(i).getMaxMatches(); k++) {

					schoolRegret = ((H.get(i).getRankingInverse(((H.get(i).getMatch(k))))) + 1) - 1;
					totalSchoolRegret = totalSchoolRegret + schoolRegret;

				}

			}
			// I do NOT know why H.size() works...
			averageSchoolRegret = (totalSchoolRegret / H.size());
			averageReceiverRegret = averageSchoolRegret;

			averageTotalRegret = (totalStudentRegret + totalSchoolRegret) / (S.size()+H.size());
			
			//Help456
			//setters
			GSS.setStableMatching(stableMatching);
			GSS.setAvgSuitorRegret(averageSuitorRegret);
			GSS.setAvgReceiverRegret(averageReceiverRegret);
			GSS.setAvgTotalRegret(averageTotalRegret);
			
			
		} else {

			// Student Regret. Student is receiver
			studentRegret = 0;
			totalStudentRegret = 0;
			averageStudentRegret = 0;
			averageTotalRegret = 0;
			for (int i = 0; i < S.size(); i++) {

				// Changed to ".getRanking" (from .getRankingInverse)
				studentRegret = ((S.get(i).getRanking(((S.get(i).getMatch(0))))) + 1) - 1;
				totalStudentRegret = totalStudentRegret + studentRegret;
			}

			averageStudentRegret = (totalStudentRegret / S.size());
			averageReceiverRegret = averageStudentRegret;

			// School Regret. School is suitor
			schoolRegret = 0;
			totalSchoolRegret = 0;
			averageSchoolRegret = 0;
			for (int i = 0; i < H.size(); i++) {

				for (int k = 0; k < H.get(i).getMaxMatches(); k++) {

					schoolRegret = ((H.get(i).getRankingInverse(((H.get(i).getMatch(k))))) + 1) - 1;
					totalSchoolRegret = totalSchoolRegret + schoolRegret;

				}

			}
			// I do NOT know why H.size() works...
			averageSchoolRegret = (totalSchoolRegret / H.size());
			averageSuitorRegret = averageSchoolRegret;

			averageTotalRegret = (totalStudentRegret + totalSchoolRegret) / (S.size()+H.size());
			
			//Help456
			//setters
			GSH.setStableMatching(stableMatching);
			GSH.setAvgSuitorRegret(averageSuitorRegret);
			GSH.setAvgReceiverRegret(averageReceiverRegret);
			GSH.setAvgTotalRegret(averageTotalRegret);
		}

		System.out.print("\n");
		System.out.format("Stable matching? %s\n", stableMatching);
		System.out.format("Average suitor regret: %.2f\n", averageSuitorRegret);
		System.out.format("Average receiver regret: %.2f\n", averageReceiverRegret);
		// Total number of openings == S.size()
		System.out.format("Average total regret: %.2f\n", averageTotalRegret);
		System.out.print("\n");

	}

	public static void displayMatchesForSMPSolver(SMPSolver GSS, SMPSolver GSH, ArrayList<Student> S, ArrayList<School> H, boolean studentOptimal)
			throws NumberFormatException, IOException {
		
		// Stability
		String stableMatching = "Yes";

		for (int i = 0; i < S.size(); i++) {

			for (int schoolBeingChecked = 0; schoolBeingChecked < H.size(); schoolBeingChecked++) {

				// Check every school that the student ranked higher than
				// the one they're at.
				// If the ranking of the school being checked is LOWER (the
				// smaller the better) than
				// the ranking of the school that the student is matched with
				if (S.get(i).getRanking(schoolBeingChecked) < S.get(i).getRanking(S.get(i).getMatch(0))) {
					// if you enter this loop, this mean the school is being
					// checked.

					// If the ranking of the schoolBeingChecked is even lower
					// than the ranking
					// of the least preferred school, then matching is NOT
					// stable

					for (int k = 0; k < H.get(schoolBeingChecked).getMaxMatches(); k++) {

						if (H.get(schoolBeingChecked).getRankingInverse(i) < H.get(schoolBeingChecked)
								.getRankingInverse(H.get(schoolBeingChecked).getMatch(k))) {

							stableMatching = "No";

						}
					}

				}

			}
		}

		double studentRegret;
		double totalStudentRegret;
		double averageStudentRegret;
		double schoolRegret;
		double totalSchoolRegret;
		double averageSchoolRegret;
		
		double averageTotalRegret;

		double averageSuitorRegret = 0;
		double averageReceiverRegret = 0;

		if (studentOptimal == true) {
			// Student Regret
			studentRegret = 0;
			totalStudentRegret = 0;
			averageStudentRegret = 0;
			averageTotalRegret = 0;
			for (int i = 0; i < S.size(); i++) {

				// Changed to ".getRanking" (from .getRankingInverse)
				studentRegret = ((S.get(i).getRanking(((S.get(i).getMatch(0))))) + 1) - 1;
				totalStudentRegret = totalStudentRegret + studentRegret;
			}

			averageStudentRegret = (totalStudentRegret / S.size());
			averageSuitorRegret = averageStudentRegret;

			// School Regret
			schoolRegret = 0;
			totalSchoolRegret = 0;
			averageSchoolRegret = 0;
			for (int i = 0; i < H.size(); i++) {

				for (int k = 0; k < H.get(i).getMaxMatches(); k++) {

					schoolRegret = ((H.get(i).getRankingInverse(((H.get(i).getMatch(k))))) + 1) - 1;
					totalSchoolRegret = totalSchoolRegret + schoolRegret;

				}

			}
			// I do NOT know why H.size() works...
			averageSchoolRegret = (totalSchoolRegret / H.size());
			averageReceiverRegret = averageSchoolRegret;
			
			averageTotalRegret = (totalStudentRegret + totalSchoolRegret) / (S.size() + H.size());
			//setters
			GSS.setStableMatching(stableMatching);
			GSS.setAvgSuitorRegret(averageSuitorRegret);
			GSS.setAvgReceiverRegret(averageReceiverRegret);
			GSS.setAvgTotalRegret(averageTotalRegret);

		} else {

			// Student Regret. Student is receiver
			studentRegret = 0;
			totalStudentRegret = 0;
			averageStudentRegret = 0;
			averageTotalRegret = 0;
			for (int i = 0; i < S.size(); i++) {

				// Changed to ".getRanking" (from .getRankingInverse)
				studentRegret = ((S.get(i).getRanking(((S.get(i).getMatch(0))))) + 1) - 1;
				totalStudentRegret = totalStudentRegret + studentRegret;
			}

			averageStudentRegret = (totalStudentRegret / S.size());
			averageReceiverRegret = averageStudentRegret;

			// School Regret. School is suitor
			schoolRegret = 0;
			totalSchoolRegret = 0;
			averageSchoolRegret = 0;
			for (int i = 0; i < H.size(); i++) {

				for (int k = 0; k < H.get(i).getMaxMatches(); k++) {

					schoolRegret = ((H.get(i).getRankingInverse(((H.get(i).getMatch(k))))) + 1) - 1;
					totalSchoolRegret = totalSchoolRegret + schoolRegret;

				}

			}
			// I do NOT know why H.size() works...
			averageSchoolRegret = (totalSchoolRegret / H.size());
			averageSuitorRegret = averageSchoolRegret;
			
			averageTotalRegret = (totalStudentRegret + totalSchoolRegret) / (S.size() + H.size());
			
			//setters
			GSH.setStableMatching(stableMatching);
			GSH.setAvgSuitorRegret(averageSuitorRegret);
			GSH.setAvgReceiverRegret(averageReceiverRegret);
			GSH.setAvgTotalRegret(averageTotalRegret);
			
		}

		System.out.print("\n");
		System.out.format("Stable matching? %s\n", stableMatching);
		System.out.format("Average suitor regret: %.2f\n", averageSuitorRegret);
		System.out.format("Average receiver regret: %.2f\n", averageReceiverRegret);
		// Total number of openings == S.size()
		System.out.format("Average total regret: %.2f\n", averageTotalRegret);
		System.out.print("\n");

		
		
		
	}
	
	public static void printComparison(SMPSolver GSS, SMPSolver GSH) {

		String stabilityWinner;
		String avgSchoolRegretWinner;
		String avgStudentRegretWinner;
		String avgTotalRegretWinner;
		String compTimeWinner;
		//REMEMBER: The LOWER number for this wins!
		
		//To find Stability winner
		if (GSS.getStableMatching() == "Yes" && GSH.getStableMatching() == "No") {
			stabilityWinner = "Student-opt";
		}
		
		else if (GSS.getStableMatching() == "No" && GSH.getStableMatching() == "Yes") {
			stabilityWinner = "School-opt";
			
		}
		
		else {
			stabilityWinner = "Tie";
		}
		
		//To find Avg school regret winner
		if (GSS.getAvgReceiverRegret() > GSH.getAvgSuitorRegret()) {
			avgSchoolRegretWinner = "School-opt";
		}
		
		else if (GSS.getAvgReceiverRegret() < GSH.getAvgSuitorRegret()) {
			avgSchoolRegretWinner = "Student-opt";
		}
		
		else {
			avgSchoolRegretWinner = "Tie";
		}

		// To find Avg student regret winner
		if (GSS.getAvgSuitorRegret() > GSH.getAvgReceiverRegret()) {
			avgStudentRegretWinner = "School-opt";
		}

		else if (GSS.getAvgSuitorRegret() < GSH.getAvgReceiverRegret()) {
			avgStudentRegretWinner = "Student-opt";
		}

		else {
			avgStudentRegretWinner = "Tie";
		}

		// To find Avg total regret winner
		if (GSS.getAvgTotalRegret() > GSH.getAvgTotalRegret()) {
			avgTotalRegretWinner = "School-opt";
		}

		else if (GSS.getAvgTotalRegret() < GSH.getAvgTotalRegret()) {
			avgTotalRegretWinner = "Student-opt";
		}

		else {
			avgTotalRegretWinner = "Tie";
		}

		// To find comp time winner
		if (GSS.getTime() > GSH.getTime()) {
			compTimeWinner = "School-opt";
		}

		else if (GSS.getTime() < GSH.getTime()) {
			compTimeWinner = "Student-opt";
		}

		else {
			compTimeWinner = "Tie";
		}

		System.out.print("\n");
		System.out.format(
				"Solution              Stable    Avg school regret   Avg student regret     Avg total regret       Comp time (ms)\n");
		System.out.format(dashesComparison);
		System.out.print("\n");
		
		System.out.format(
				"Student optimal          %s %20.2f %20.2f %20.2f %20d\n",
				GSS.getStableMatching(), GSS.getAvgReceiverRegret(), GSS.getAvgSuitorRegret(), GSS.getAvgTotalRegret(), GSS.getTime());
				//"Student optimal          %s                %.2f                 %.2f                 %.2f                  %d\n"
		
		
		System.out.format(
				"School optimal           %s %20.2f %20.2f %20.2f %20d\n",
				GSH.getStableMatching(), GSH.getAvgSuitorRegret(), GSH.getAvgReceiverRegret(), GSH.getAvgTotalRegret(), GSH.getTime());

		System.out.format(dashesComparison);
		System.out.print("\n");
		
		System.out.format("WINNER                   %s %20s %20s %20s %20s\n",
				stabilityWinner, avgSchoolRegretWinner, avgStudentRegretWinner, avgTotalRegretWinner, compTimeWinner);
		
		System.out.format(dashesComparison);
		System.out.print("\n");

	}
	
	
	public static int getInteger(String prompt, int LB, int UB) throws NumberFormatException, IOException {


		int value = 0;
		int valid;

		do {
			valid = 1;
			System.out.format(prompt);

			try {

				value = Integer.parseInt(cin.readLine());
			}

			catch (NumberFormatException e) {

				// Experiment
				if (UB == Integer.MAX_VALUE) {

					System.out.print("\n");
					System.out.format("ERROR: Input must be an integer in [1, infinity]!");
					valid = 0;
					System.out.println("\n");

				}

				else {

					System.out.print("\n");
					System.out.format("ERROR: Input must be an integer in [%d, %d]!", LB, UB);
					valid = 0;
					System.out.println("\n");
				}

			} catch (IOException e) {

				// Experiment
				if (UB == Integer.MAX_VALUE) {

					System.out.print("\n");
					System.out.format("ERROR: Input must be an integer in [1, infinity]!");
					valid = 0;
					System.out.println("\n");

				} else {

					System.out.print("\n");
					System.out.format("ERROR: Input must be an integer in [%d, %d]!", LB, UB);
					valid = 0;
					System.out.println("\n");
				}

			}

			if (valid == 1 && (value < LB || value > UB)) {

				if (UB == Integer.MAX_VALUE) {

					System.out.print("\n");
					System.out.format("ERROR: Input must be an integer in [1, infinity]!");
					valid = 0;
					System.out.println("\n");

				} else {
					System.out.print("\n");
					System.out.format("ERROR: Input must be an integer in [%d, %d]!", LB, UB);
					valid = 0;
					System.out.println("\n");
				}

			}
		} while (valid == 0);

		return value;
	}

	public static double getDouble(String prompt, double LB, double UB) throws NumberFormatException, IOException {

		double value = 0;
		int valid;

		do {
			valid = 1;
			System.out.format(prompt);
			try {
				value = Double.parseDouble(cin.readLine());
			}

			catch (NumberFormatException e) {
				
				//Experiment
				if (UB == Double.MAX_VALUE) {

					System.out.print("\n");
					System.out.format("ERROR: Input must be an integer real number in [1, infinity]!");
					valid = 0;
					System.out.println("\n");

				}

				else {

					System.out.print("\n");
					System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!", LB, UB);
					valid = 0;
					System.out.println("\n");
				}

			} catch (IOException e) {

				// Experiment
				if (UB == Double.MAX_VALUE) {

					System.out.print("\n");
					System.out.format("ERROR: Input must be an integer real number in [1, infinity]!");
					valid = 0;
					System.out.println("\n");

				}

				else {
					System.out.print("\n");
					System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!", LB, UB);
					valid = 0;
					System.out.println("\n");
				}
			}

			if (valid == 1 && (value < LB || value > UB)) {
				
				System.out.print("\n");
				System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!", LB, UB);
				valid = 0;
				System.out.println("\n");
			}
		} while (valid == 0);

		return value;
	}

	public static boolean fileExists(String fileName) throws NumberFormatException, IOException, FileNotFoundException {

		File file = new File(fileName);

		boolean exists = file.exists();
		if (!exists) {
			System.out.format("\n");
			System.out.format("ERROR: File not found!\n");
			System.out.format("\n");
			exists = false;

		} else {
			exists = true;
		}
		return exists;

	}
	
	
}
	