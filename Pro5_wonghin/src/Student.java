import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class Student extends Participant {
	//For Pro5
	private double GPA; // GPA
	private int ES; // extracurricular score

	// constructors
	public Student() {
		super();
		this.GPA = 0;
		this.ES = 0;
	}

	public Student(String name, double GPA, int ES, int nSchools) {
		
		//This is where Student's MaxMatches are initialized to 1!!!
		super(name, 1, nSchools);
	//	this.setMaxMatches(1);
	//	this.setName(name);
		this.setGPA(GPA);
		this.setES(ES);
	//	this.setNParticipants(nSchools);
		
	}

	// getters and setters
	public double getGPA() {
		return this.GPA;
	}

	public int getES() {
		return this.ES;
	}

	public void setGPA(double GPA) {
		this.GPA = GPA;
	}
	
	public void setES(int ES) {
		this.ES = ES;
	}

	public void editInfo(ArrayList<School> H, boolean canEditRankings) {
		// user info
	}

	public void print(ArrayList<? extends Participant> H) {
		// print student row
	}

	public boolean isValid(ArrayList<School> H) {
		// check if this student has valid info
		int[] rankingArray = new int[H.size()];
		for (int index = 0; index < H.size(); index++) {
			rankingArray[index] = -1;
		}


		if (this.getGPA() < 0.00 || this.getGPA() > 4.00) {
			return false;
		}

		if (this.getES() < 0 || this.getES() > 5) {
			return false;
		}

		for (int i = 0; i < H.size(); i++) {
			//Checks for correct number of rankings and whether they are [1,n]
			//NEW: Checks for correct number of rankings and whether they are [0,n -1]
			if (this.getRankingInverse(i) < 0 || this.getRankingInverse(i) > H.size() - 1) {
				return false;
			}

			//Checks for repeated ranks
			for (int j = 0; j < H.size(); j++) {
				if (this.getRankingInverse(i) == rankingArray[j])
					return false;

			}

			rankingArray[i] = this.getRankingInverse(i);
			//Set the rankingArray to the current rank.
		}

		return true;
	}
}
