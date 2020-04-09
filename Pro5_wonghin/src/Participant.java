import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class Participant {
	//For Pro5
	private String name; // name
	
	private int[] rankings = new int[2000]; // rankings of participants
	private int[] rankingsInverse = new int[2000]; //inverse array which takes in (schoolOrder, placement)
	
	private ArrayList<Integer> matches = new ArrayList<Integer>(); // match indices
	
	private int regret; // total regret
	private int maxMatches; // max # of allowed matches/openings 
	private boolean isMatched; //Hin added this on his own
	
	
	// constructors
	
	public Participant() {
		
		this.name = "";
		//this.rankings = null;
		this.regret = 0;
		this.maxMatches = 1;
		this.isMatched = false;
		
	}
	public Participant(String name, int maxMatches, int nParticipants) {
		//NOTE: Use "this." if you want to talk about the variable at the top
		
		this.setName(name);
		this.setNParticipants(nParticipants);
		this.setMaxMatches(maxMatches);
		
		// TA said that you don't need to intialize the matches array list.
		// for (int i = 0; i < maxMatches; i++) {
		// this.matches.add(3000);
		// }
		
		this.rankings = new int [nParticipants];
		for (int i = 0; i < nParticipants; i++) {
			this.rankings[i] = 0;
		}
		
		this.rankingsInverse = new int [nParticipants];
		for (int i = 0; i < nParticipants; i++){
			this.rankingsInverse[i] = 0;
		}
	}

	// getters
	public String getName() {
		return this.name;
	}
	
	public int getRanking(int i) {
		return this.rankings[i];
	}
	
	public int getRankingInverse(int i) {
		return this.rankingsInverse[i];
	}

	public int getMatch(int i) {
		return this.matches.get(i);
	//	return this.matches[i];
	}

	public int getRegret() {
		return this.regret;

	}

	public int getMaxMatches() {
		return this.maxMatches;

	}

	public int getNMatches() {
		return this.getNMatches();
		//Not sure  about this.
	}

	public int getNParticipants() {
		// return length of rankings array
		return this.rankings.length;
	}

	public int getMatchesSize() {
		
		return this.matches.size();
	//	return this.matches.length;
		
	}
	
	public boolean isFull() {
		return true;
	}
	
	public boolean getIsMatched() {
		return this.isMatched;
	}
	
	
	// setters
	public void setName(String name) {
		this.name = name;
	}

	public void setRanking(int i, int r) {
		this.rankings[i] = r;
	}
	
	public void setRankingInverse(int i, int r) {
		this.rankingsInverse[i] = r;
	}
	//TA said that if you initialize matches array list to -1's
	//and you use ".add(m)", this can be dangerous because it will
	//just add on the m at the end of the -1's.
	public void setMatch(int m) {
		
		this.matches.add(m);
		
		//This is the problem with converting matches into
		//an array
	//	this.matches[i] = r;
		
	}

	public void removeMatchIndex(int m) {
		
		//This removes the element "m" in matches arrayList
		this.matches.remove(m);
	
	}

	public void removeMatchObject(Object m) {

		// This removes the object "m" in matches arrayList
		this.matches.remove(m);

	}
	
	public void setRegret(int r) {
		this.regret = r;
	}

	public void setNParticipants(int n) {
		// set rankings array size
		this.rankings = new int [n];
	}

	public void setMaxMatches(int n) {
		this.maxMatches = n;
	}

	public void setIsMatched(boolean i) {
		this.isMatched = i;
	}
	
	
	// methods to handle matches
	public void clearMatches() {
		// clear all matches
		this.matches.clear();
	}

	public int findRankingByID(int k) {
		// find rank of participant k
		return 0;
	}

	public int getWorstMatch() {
		// find the worst-matched participant
		return 0;
	}

	public void unmatch(int k) {
		// remove the match with participant k
	}

	public boolean matchExists(int k) {
		// check if match to participant k exists
		return true;
	}

	public int getSingleMatchedRegret(int k) {
		// get regret from match with k
		return 0;
	}

	public void calcRegret() {
		// calculate total regret over all matches
	}

	// methods to edit data from the user
	public void editInfo(ArrayList<? extends Participant> P) {
		//TA said that ? means we don't if it is school or student.
		//With ?, we can pass in both school or student and either will work.
	}

	public void editRankings(ArrayList<? extends Participant> P) {

	}

	// print methods
	
	public void print(ArrayList<? extends Participant> P) {
		
	}
	
	//public void printRankings(ArrayList <? Participant> P)extends Participant > P) {
		
	//}
	
	
	public String getMatchNames(ArrayList<? extends Participant> P) {
	
		return ""; 

	}

	// check if this participant has valid info
	
	public boolean isValid() {
		return true;
	}
}