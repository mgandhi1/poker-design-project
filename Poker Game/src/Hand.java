import java.util.*;
import java.util.stream.Collectors;

class Hand{
	//private Player player;
	private int handSize = 0; 
	private ArrayList<Card> cardList = new ArrayList<Card>(); //Using ArrayList because of fast sorting times
	static String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};

	
	public Hand(/*Player p*/) {
		//this.player = p 
	}

	public Hand(/*Player p*/ Card c1, Card c2) {
		//this.player = p 
		this.cardList.add(c1);
		this.cardList.add(c2);
	}
	
	public ArrayList<Card> getCards(){
		cardList.sort(new sortRank());
		return this.cardList;
	}
	
	public void addCard(Card card ) {  // card other // add 1 card //queue implementation???
		this.cardList.add(card);
	} 
	
	void clearHand(Deck deck){// removing all cards from hands of players and adding to END of deck
		
		for(int i=0; i<cardList.size(); i++) {
			Card removedCard = this.cardList.remove(i);
			deck.addCard(removedCard);
		}
		this.cardList.clear();
	} 
	
	
	
	int compareHand(Hand other){ // Neg val if first hand is worse, 0 if they're the same, and positive if first one is better
		if(this.checkHand()==0 && other.checkHand()==0) {
			this.cardList.sort(new sortRank());
			other.cardList.sort(new sortRank());
			return this.cardList.get(this.cardList.size()-1).getRank() - other.cardList.get(other.cardList.size()-1).getRank();
		}
		else {
			return this.checkHand() - other.checkHand();
		}
		
	} // hand  other // implement all - checkhand i boolean if one hand isbetter thatn the the other 
	
	public int checkHand(){
		int playerPotDeterminant = 0;

		if (royalFlush() == true){
			playerPotDeterminant = 9;
			return 9;
			}
		else if (straightFlush() == true){
			playerPotDeterminant = 8;
			return 8;
			}
		else if (fourKind()== true){
			playerPotDeterminant = 7;
			return 7;
			}
		else if (fullHouse() == true){
			playerPotDeterminant = 6;
			return 6;
			}
		else if (pokerFlush() == true){
			playerPotDeterminant = 5;
			return 5;
			}
		else if (straight() == true){
			playerPotDeterminant = 4;
			return 4;
			}
		else if (threeKind() == true){
			playerPotDeterminant = 3;
			return 3;
			}
		else if (twoPair() == true){
			playerPotDeterminant = 2;
			return 2;
			}
		else if (pair() == true){
			playerPotDeterminant = 1;
			return 1;
			}
		else {
			playerPotDeterminant = 0;
			return 0;
			}
	}
	

	
	//ArrayList<Card> cards = this.sort(); // queue implementation with name cards
	
	//Deque<Card> = this.sort();
	
	public boolean sameSuit(List<Card> arr) {  //Takes in List of 5 cards. Tells if they are all the same suit
		
		String checkSuit = arr.get(0).getSuit();
		return arr.get(1).getSuit() == checkSuit && arr.get(2).getSuit() == checkSuit &&
				arr.get(3).getSuit() == checkSuit && arr.get(4).getSuit() == checkSuit;
	}
	
	//Kinda Useless, shouldn't have bothered
	
//	public boolean allSuits(List<Card> arr) {  //Takes in List of 4 cards. Tells if they are all DIFFERENT suits
//		
//		arr.sort(new sortSuit());
//		for(int i=0; i<4; i++) {
//			if(arr.get(i).getSuit() != suits[i]) {return false;}
//		}
//		return true;
//	}
	
	public ArrayList<Card> fullSort(ArrayList<Card> arr){
		return (ArrayList)cardList.stream().sorted(Comparator.comparing(Card::getSuit).thenComparing(Card::getRank))
					.collect(Collectors.toList());
		//sorts by suit and within each sorted suit sorts by rank  - thx Bradley D on stackoverflow
	}
	
	
	//ACTUAL HAND TYPES START HERE
	
	public boolean royalFlush(){ // 1
		cardList = fullSort(cardList);

		
		boolean correctRankings = false;
		boolean correctSuit = false;
		String checkSuit;
		for(int i=0; i<cardList.size()-4; i++) {
			correctRankings = cardList.get(i).getRank() == 10 && cardList.get(i+1).getRank() == 11 && cardList.get(i+2).getRank() == 12 && 
					cardList.get(i+3).getRank() == 13 && cardList.get(i+4).getRank() == 14;
			if(correctRankings) {break;}
		}
		
		this.cardList.sort(new sortSuit());
		for(int i=0; i<=cardList.size()-5; i++) {
			List<Card> subArray = cardList.subList(i, i+5);
			correctSuit = sameSuit(subArray);
			if(correctSuit) {break;}
		}
		if(correctSuit && correctRankings) {return true;}
		return false;
		
		
	}
	
	public void addCard(ArrayList<Card> assignCard) {
	}
	
	public boolean straightFlush(){// 2
		boolean correctRankings = false;
		boolean correctSuit = false;
		int checkRank;
		
		this.cardList = fullSort(cardList);
		
		//this.cardList.sort(new sortSuit());
		for(int i=0; i<=cardList.size()-5; i++) {
			List<Card> subArray = cardList.subList(i, i+5);
			correctSuit = sameSuit(subArray);
			if(correctSuit) {break;}
		}
		
		
		for(int i=0; i<cardList.size()-4; i++) {
			checkRank = cardList.get(i).getRank();
			correctRankings = cardList.get(i+1).getRank() == checkRank+1 && cardList.get(i+2).getRank() == checkRank+2 && 
								cardList.get(i+3).getRank() == checkRank+3 && cardList.get(i+4).getRank() == checkRank+4;
			if(correctRankings) {break;}
		}
		

		if(correctSuit && correctRankings) {return true;}
		return false;
		
//		for (int counter = 1; counter < 5; counter++){
//			if (cardList.get(counter - 1).getRank() != (cardList.get(counter).getRank() - 1)){
//				return false;
//			}
//				
//		}
//		for (int counter = 1; counter < 5; counter++){
//			if (cardList.get(0).getSuit() != cardList.get(counter).getSuit()){
//				return false;
//			}
//		}
//		return true;
	}
	
	
	public boolean fourKind(){//3
		boolean matchTracker = true;
		cardList.sort(new sortRank());
		for(int i=0; i<=cardList.size()-4; i++) {
			matchTracker = true;
			for(int j=0; j<3; j++) {
				if(cardList.get(i+j).getRank() != cardList.get(i+j+1).getRank()) {
					matchTracker = false;
				}
			}
			if(matchTracker) {break;}
		}
		if(matchTracker) {return true;}
		return false;
	}
	
	//NOT DONE YET
	public boolean fullHouse(){//4
		cardList.sort(new sortRank());
		int pairCount = 0;
		int tripleCount = 0;
		int blackList = 0;
		List<Card> subset;
		
		for (int i=0; i<=cardList.size()-3; i++) {
			subset = cardList.subList(i, i+3);
			if(findTriple(subset)) {
				tripleCount+=1; 
				blackList = subset.get(0).getRank();
			}
		}
		
		for (int i=0; i<=cardList.size()-2; i++) {
			subset = cardList.subList(i, i+2);
			if(findPair(subset) && subset.get(0).getRank() != blackList) {
				pairCount+=1; 
			}
		}
		
		if(tripleCount==1 & pairCount==1) {return true;}
		return false;
	}
	
	
	public boolean pokerFlush() {//5
		boolean matchTracker = false;
		cardList.sort(new sortSuit());
		for (int i=0; i<=cardList.size()-5; i++) {
			matchTracker = sameSuit(cardList.subList(i, i+5));
			if(matchTracker) {return true;}
		}
		
		return false;
	}
	
	//Working for MOST cases but failed an important one
	public boolean straight(){//6
		boolean matchTracker = false;
		boolean subsetCorrect = true;
		cardList.sort(new sortRank());
		
		for(int i=0; i<=cardList.size()-5; i++) {
			subsetCorrect = true;
			List<Card> subset = cardList.subList(i, i+5);
			for (int counter = 1; counter < 5; counter++) {
				if (subset.get(counter - 1).getRank() != (subset.get(counter).getRank() - 1)) {
					matchTracker = true;
					if(matchTracker) {subsetCorrect = false;}
				}	
			}
			if(subsetCorrect) {return true;}
		}
		return false;
	}
	
	
	public boolean threeKind(){//7
		cardList.sort(new sortRank());
		boolean subsetMatch = false;
		boolean overallMatch = false;
		int rank;
		
		for(int i=0; i<=cardList.size()-3; i++) {
			List<Card> subset = cardList.subList(i, i+3);
			rank = subset.get(0).getRank();
			if(subset.get(1).getRank()==rank && subset.get(2).getRank()==rank) {subsetMatch=true;}
			if(subsetMatch) {overallMatch = true; break;}
		}
		if(overallMatch) {return true;}
		return false;
		
	}
	
	
	public boolean twoPair(){//8
		cardList.sort(new sortRank());
		int counter = 0;
		for(int i=0; i<=cardList.size()-2; i++) {
			List<Card> subset = cardList.subList(i, i+2);
			if(findPair(subset)) {counter += 1;}
		}
		return (counter==2 ? true:false);
	}
	
	public boolean pair(){//9
		cardList.sort(new sortRank());
		int counter = 0;
		for(int i=0; i<=cardList.size()-2; i++) {
			List<Card> subset = cardList.subList(i, i+2);
			if(findPair(subset)) {counter += 1;}
		}
		return (counter==1 ? true:false);
	}
		
	private boolean findPair(List<Card> arr) {
		if (arr.get(0).getRank() == arr.get(1).getRank()) {
			return true;
		}
		return false;
	}
	private boolean findTriple(List<Card> arr) {
		if (arr.get(0).getRank() == arr.get(1).getRank() && arr.get(1).getRank() == arr.get(2).getRank()) {
			return true;
		}
		return false;
	}
	
	public void evaluateHand(){
		if (this.royalFlush()){
			System.out.println("You have a royal flush");
			}
		else if (this.straightFlush()){
			System.out.println("You have a straight flush");
			}
		else if (this.fourKind()){
			System.out.println("You have four of a kind");
			}
		else if (this.fullHouse()){
			System.out.println("You have a full house");
			}
		else if (this.pokerFlush()){
			System.out.println("You have a flush");
			}
		else if (this.straight()){
			System.out.println("You have a straight");
			}
		else if (this.threeKind()){
			System.out.println("You have a triple");
			}
		else if (this.twoPair()){
			System.out.println("You have a two pair");
			}
		else if (this.pair()){
			System.out.println("You have a one pair");
			}
		else{
			System.out.println("You have a high card"); 
			System.out.println("Your high card is : " + this.cardList.get(cardList.size()-1).getRank());
			}
		}

		public String toString() {
			return this.cardList.toString();
		}
}
