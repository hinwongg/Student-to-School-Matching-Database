import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class SMPSolver {
	// For Pro5
	
	private ArrayList<Participant> S = new ArrayList<Participant>(); // suitors
	private ArrayList<Participant> R = new ArrayList<Participant>(); // receivers
	private String stableMatching; //Hin added this himself
	private double avgSuitorRegret; // average suitor regret
	private double avgReceiverRegret; // average receiver regret
	private double avgTotalRegret; // average total regret
	private boolean matchesExist; // whether or not matches exist
	private boolean stable; // whether or not matching is stable
	private long compTime; // computation time
	private boolean suitorFirst; // whether to print suitor stats first

	// constructor
	public SMPSolver() {

		this.stableMatching = "Yes";
		this.avgSuitorRegret = 0;
		this.avgReceiverRegret = 0;
		this.avgTotalRegret = 0;
		this.compTime = 0;
		this.matchesExist = false;
		
		
	}

	// getters
	public String getStableMatching() {
		//I added this getter myself
		return this.stableMatching;
	}
	
	public double getAvgSuitorRegret() {
		return this.avgSuitorRegret;
	}

	public double getAvgReceiverRegret() {
		return this.avgReceiverRegret;
	}

	public double getAvgTotalRegret() {
		return this.avgTotalRegret;
	}

	public boolean matchesExist() {
		return true;
	}

	public boolean isStable() {
		return true;
	}

	public long getTime() {
		return this.compTime;
	}

	public int getNSuitorOpenings() {
		//return this.get(i).getMaxMatches();
		return 0;
	}

	public int getNReceiverOpenings() {
		//return this.get(i).getMaxMatches():
		return 0;
	}

	// setters
	public void setMatchesExist(boolean b) {

	}

	public void setSuitorFirst(boolean b) {
		//NOT needed yet....
	}

	public void setParticipants(ArrayList<? extends Participant> S, ArrayList<? extends Participant> R) {

		this.S = (ArrayList<Participant>) S;
		this.R = (ArrayList<Participant>) R;
		
	}
	//Note: I added this myself
	public void setStableMatching(String stableMatching) {
		this.stableMatching = stableMatching;
	}
	
	public void setAvgSuitorRegret(double avgSuitorRegret) {
		this.avgSuitorRegret = avgSuitorRegret;
	}

	public void setAvgReceiverRegret(double avgReceiverRegret) {
		this.avgReceiverRegret = avgReceiverRegret;
	}

	public void setAvgTotalRegret(double avgTotalRegret) {
		this.avgTotalRegret = avgTotalRegret;
	}
	public void setTime(long compTime) {
		this.compTime = compTime;
	}
	
	// methods for matching
	public void clearMatches() {
		// clear out existing matches
		
		//DO NOT do ".setMatch" because it
		//simply ".adds" on element to end of the list
		for (int i = 0; i < S.size(); i++) {
			
			S.get(i).setIsMatched(false);
			S.get(i).clearMatches();
		}

		for (int i = 0; i < R.size(); i++) {
			
			R.get(i).setIsMatched(false);
			R.get(i).clearMatches();

		}
	

	}

	public boolean matchingCanProceed() {
		// check that matching rules are satisfied
		return true;
	}
	
	// Gale-Shapley algorithm to match; students are suitors
	public boolean match() {
	
		clearMatches();
		
		// Assign one "nextHighestRank" ARRAY to EACH mean.
		int[]nextHighestRank = new int[S.size()];

		for (int index = 0; index < S.size(); index++) {
				 
			nextHighestRank[index] = 0;
			// 0 is highest rank

		}
		
		
	

		boolean proposingFinished = false;
		
		while (proposingFinished == false) {

			// This is for each man (student). This does one whole iteration.
			for (int i = 0; i < S.size(); i++) {
				
				//If ".getIsMatched()" is false, this means the man has not yet been matched
				//OR still has spots available for proposing.
				
				 if (S.get(i).getIsMatched() == false) {
				

					 //I do NOT know why ".getRankingInverse" works.....
					int receiver = S.get(i).getRankingInverse(nextHighestRank[i]);

					boolean success = makeProposal(i, receiver);

					if (success == true) {
						// If success is true, then the woman still
						// has spots available

						// This makes the engagement
						S.get(i).setMatch(receiver);
						R.get(receiver).setMatch(i);
						nextHighestRank[i]++;
						
						//Check if suitor still as spots to propose
						//(i.e. when schools are suitors)
						//This is fine because "S.get(i).setMatch(receiver)" already increase
						//match's size to 1.
					//	if (S.get(i).getMatchesSize() == S.get(i).getMaxMatches()) {

							S.get(i).setIsMatched(true);

					//	} else {
					//		S.get(i).setIsMatched(false);

					//	}
						
						// Check if woman (school) still has spots available anymore
						// I.e. Does matches.size() (which gets bigger) equal to
						// ".getMaxMatches"
						if (R.get(receiver).getMatchesSize() == R.get(receiver).getMaxMatches()) {

							R.get(receiver).setIsMatched(true);

						} else {
							R.get(receiver).setIsMatched(false);

						}

					} else {
						// If you enter this, then the woman has no spots available or
						//the man has no more spots to propose
						
						//Find the LEAST PREFERED suitor (of the suitors that already exist). 
						//You're NOT using the newSuitor's ranking!
						
						int maxLeastPrefSuitor = R.get(receiver).getMatch(0);
			
						//This is when schools is receiver
						for (int j = 0; j < R.get(receiver).getMaxMatches(); j++) {

							int currentSuitor = R.get(receiver).getMatch(j);

							// If the ranking of the currentSuitor is greater than
							// the ranking of the least pref suitor, then currentSuitor
							// becomes the NEW least pref suitor.
							if (R.get(receiver).getRankingInverse(currentSuitor) > R.get(receiver)
									.getRankingInverse(maxLeastPrefSuitor)) {

								maxLeastPrefSuitor = currentSuitor;

								}

							
						}
						int leastPreferedSuitor = maxLeastPrefSuitor;
						
						
						
						//Might need to fix this since the schools and students will get swapped...
						int leastPreferedSuitorRanking = R.get(receiver).getRankingInverse(leastPreferedSuitor);
						int newSuitorRanking = R.get(receiver).getRankingInverse(i);
						
						//i is the person we are exploring
						
						// Enter this once engagement can happen.
						if (newSuitorRanking < leastPreferedSuitorRanking) {
							makeEngagement(i, receiver, leastPreferedSuitor);
							nextHighestRank[i]++;
							
							//S.get(i).setIsMatched(true) is always true b/c man
							//has no spots available.
							S.get(i).setIsMatched(true);	
							
							//R.get(receiver).setIsMatched(true) is always true b/c
							//woman has no spots available
							R.get(receiver).setIsMatched(true);
							S.get(leastPreferedSuitor).setIsMatched(false);
							
							//Increment +1 for the leastPreferedSuitor since he got dumped.
							

						} else {
							// Increment +1 if the man gets rejected.
							nextHighestRank[i]++;
						}

					}

					
				}
			
			}

			// Check to see if any of the schools or students have not
			// been matched yet (technically only need to do for STUDENTS)
			
			boolean checkMatchesForReceiver = true;
			// Check to see if any of the schools or students have not
			// been matched yet (technically only need to do for STUDENTS)

			for (int a = 0; a < S.size(); a++) {

				// NOTE: In Pro5, you have to index through the match array
				// for (int j = 0; j < S.get(i).getMaxMatches(); j++) {

				proposingFinished = true;
				if (S.get(a).getIsMatched() == false) {
					proposingFinished = false;
					checkMatchesForReceiver = false;
					break;
				}
			}

			if (checkMatchesForReceiver == true) {
				for (int b = 0; b < R.size(); b++) {

					if (R.get(b).getIsMatched() == false) {
						proposingFinished = false;
						break;
					}
				}
			}

		}
		return true;
	}

	private boolean makeProposal(int suitor, int receiver) {
		// suitor proposes
		// The engagement is made only if the woman has spots available

		//If ".getIsMatched" is false, this means woman still 
		//has spots available
		if (R.get(receiver).getIsMatched() == false) {
			
			return true;

		}

		else {

			return false;
		}

	}

	private void makeEngagement(int suitor, int receiver, int leastPreferedSuitor) {
		// make suitor -receiver engagement , break receiver -leastPreferedSuitor
		// engagement
		//This matches the school to student and student to school (just like matching)

		
		//This removes the element at index 0. Since it is a student,
		//the array only has one index
		
		//help 123: You may need to change this up when you switch students with schools
		
		S.get(leastPreferedSuitor).removeMatchIndex(0);
		R.get(receiver).removeMatchObject(leastPreferedSuitor);
		
		S.get(suitor).setMatch(receiver);
		R.get(receiver).setMatch(suitor);

	}

	
	
	
	//Duplicate!!!! Swap all rankings and rankingInverse
	public boolean matchForSchoolsAsSuitors() {
		
		clearMatches();
		
		// Assign one "nextHighestRank" ARRAY to EACH mean.
		int[]nextHighestRank = new int[S.size()];

		for (int index = 0; index < S.size(); index++) {
				 
			nextHighestRank[index] = 0;
			// 0 is highest rank
			 

		}

		boolean proposingFinished = false;
		
		while (proposingFinished == false) {

			// This is for each school. This does one whole iteration.
			for (int i = 0; i < S.size(); i++) {
				
				//If ".getIsMatched()" is false, this means the man has not yet been matched
				//OR still has spots available for proposing.
				
				//You CANNOT use while loop because if a school gets rejected,
				//it enters an infinite loop.
				//REMEMBER, a school can only propose ONCE per iteration.
				 if (S.get(i).getIsMatched() == false) {
				

					 //Changed back to getRanking (since it's school now)
					 //This entered in "2000" because you are in an infinite loop!!
					int receiver = S.get(i).getRanking(nextHighestRank[i]);

					boolean success = makeProposal(i, receiver);

					if (success == true) {
						// If success is true, then the woman(student) still
						// has spots available

						// This makes the engagement
						S.get(i).setMatch(receiver);
						R.get(receiver).setMatch(i);
						nextHighestRank[i]++;
						
						//Check if suitor still as spots to propose
						//(i.e. when schools are suitors)
						if (S.get(i).getMatchesSize() == S.get(i).getMaxMatches()) {

							S.get(i).setIsMatched(true);

						} else {
							S.get(i).setIsMatched(false);
							

						}
						
							R.get(receiver).setIsMatched(true);

					} else {
						// If you enter this, then the woman has no spots available.
						//(Has NOTHING to do with the suitor)
						
						//Find the LEAST PREFERED suitor (of the suitors that already exist).
						int leastPreferedSuitor =  R.get(receiver).getMatch(0);
						
						
						
						//Might need to fix this since the schools and students will get swapped...
						int leastPreferedSuitorRanking = R.get(receiver).getRanking(leastPreferedSuitor);
						int newSuitorRanking = R.get(receiver).getRanking(i);
						
						//i is the person we are exploring
						
						// Enter this once engagement can happen.
						if (newSuitorRanking < leastPreferedSuitorRanking) {
							makeEngagementForSchoolsAsSuitors(i, receiver, leastPreferedSuitor);
							nextHighestRank[i]++;
							
							//Check if suitor still as spots to propose
							if (S.get(i).getMatchesSize() == S.get(i).getMaxMatches()) {
								//If ".setIsMatched" is true, then the suitor has no more spots.
								S.get(i).setIsMatched(true);
								
							} else {
								S.get(i).setIsMatched(false);
								

							}	
							
							//R.get(receiver).setIsMatched(true) is always true b/c
							//woman has no spots available
							R.get(receiver).setIsMatched(true);
							S.get(leastPreferedSuitor).setIsMatched(false);
							
							//Increment +1 for the leastPreferedSuitor since he got dumped.
							

						} else {
							// Increment +1 if the man gets rejected.
							nextHighestRank[i]++;
						}

					}

					
				}
			
			}

			// Check to see if any of the schools or students have not
			// been matched yet (technically only need to do for STUDENTS)
			
			boolean checkMatchesForReceiver = true;
			// Check to see if any of the schools or students have not
			// been matched yet (technically only need to do for STUDENTS)

			for (int a = 0; a < S.size(); a++) {

				// NOTE: In Pro5, you have to index through the match array
				// for (int j = 0; j < S.get(i).getMaxMatches(); j++) {

				proposingFinished = true;
				if (S.get(a).getIsMatched() == false) {
					proposingFinished = false;
					checkMatchesForReceiver = false;
					break;
				}
			}

			if (checkMatchesForReceiver == true) {
				for (int b = 0; b < R.size(); b++) {

					if (R.get(b).getIsMatched() == false) {
						proposingFinished = false;
						break;
					}
				}
			}


		}
		return true;
	}


	private void makeEngagementForSchoolsAsSuitors(int suitor, int receiver, int leastPreferedSuitor) {
		// make suitor -receiver engagement , break receiver -leastPreferedSuitor
		// engagement
		//This matches the school to student and student to school (just like matching)

		
		//This removes the element at index 0. Since it is a student,
		//the array only has one index
		S.get(leastPreferedSuitor).removeMatchObject(receiver);
		R.get(receiver).removeMatchIndex(0);
		
		S.get(suitor).setMatch(receiver);
		R.get(receiver).setMatch(suitor);

	}
	
	public void calcRegrets() {
		// calculate regrets
	}

	public boolean determineStability() {
		// calculate if a matching is stable
		return true;
	}

	// print methods
	public void print() {
		// print the matching results and statistics
	}

	public void printMatches() {
		// print matches
	}

	public void printStats() {
		// print matching statistics
	}

	public void printStatsRow(String rowHeading) {
		// print stats as row
	}

}