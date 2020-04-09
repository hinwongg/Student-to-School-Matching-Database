import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class School extends Participant {
	//For Pro5
	private double alpha; // GPA weight

	// constructors
	public School() {
	//	super();
		this.alpha = 0;
	}

	public School(String name, double alpha, int maxMatches, int nStudents) {
		super(name, maxMatches, nStudents);
		this.setAlpha(alpha);
		
		
	}

	// getters and setters
	public double getAlpha() {
		return this.alpha;
	}
	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	// get new info from the user; cannot be inherited or overridden from parent
	
	public void editSchoolInfo(ArrayList<Student> S, boolean canEditRankings) {

	}

	public void calcRankings(ArrayList<Student> S, ArrayList<School> H) {
		// calc rankings from alpha
		// School's ranking of students.
		double compositeScore = 0;
		double schoolGPAWeight;
		double studentsGPA = 0;
		int studentsES = 0;

		double[] unsortedArray = new double[S.size()];// May enter NULL....
		// for (int i = 0; i < nStudents + 1; i++){
		// unsortedArray [i] = -1;
		// }
		double[] sortedArray = new double[S.size()];
		// for (int i = 0; i < nStudents + 1; i++){
		// sortedArray [i] = -1;
		// }

		// This is for each school. I.e School 1, then school 2.
		for (int i = 0; i < H.size(); i++) {

			// This is where you get alpha of School 1.
			schoolGPAWeight = H.get(i).getAlpha();

			// This is for each student. I.e Student 1, Student 2
			for (int studentsOrder = 0; studentsOrder < S.size(); studentsOrder++) {

				studentsGPA = S.get(studentsOrder).getGPA();
				// This is where you get GPA of Student 1.
				studentsES = S.get(studentsOrder).getES();
			
				compositeScore = (schoolGPAWeight * studentsGPA) + ((1 - schoolGPAWeight) * studentsES);
				unsortedArray[studentsOrder] = compositeScore;
				sortedArray[studentsOrder] = compositeScore;
				
				
				
				// Right now, this is also unsorted.

				// Enter this once you have finished indexing through all the
				// students
				if (studentsOrder == S.size()-1) {
					
					// Sort array in ascending order
					Arrays.sort(sortedArray);
					double temp;
					// Flip array
					for (int n = 0; n < S.size() / 2; n++) {
						temp = sortedArray[n];
						sortedArray[n] = sortedArray[S.size() - (n + 1)];
						sortedArray[S.size() - (n + 1)] = temp;

					}
					for (int unsortedIndex = 0; unsortedIndex < S.size(); unsortedIndex++) {

						int schoolsRankingOfStudent = 0;

						for (int sortedIndex = 0; sortedIndex < S.size(); sortedIndex++) {

							if (unsortedArray[unsortedIndex] == sortedArray[sortedIndex]) {

								H.get(i).setRanking(schoolsRankingOfStudent, unsortedIndex);
								H.get(i).setRankingInverse(unsortedIndex, schoolsRankingOfStudent);
								sortedArray[sortedIndex] = -1;
								// i represents which school.
								break;

							} else {
								schoolsRankingOfStudent++;
							}
						}

					}

				}

			}

		}
	}

	public void print(ArrayList<? extends Participant> S) {
		// print school row
	}

	public boolean isValid() { // check if this school has valid info

		if (this.getAlpha() < 0.00 || this.getAlpha() > 1.00) {
			return false;
		}

		if (this.getMaxMatches() < 1) {
			return false;
		}

		return true;

	}

}