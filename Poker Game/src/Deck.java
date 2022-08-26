import java.util.*;

public class Deck 
{
	static String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
	private Deque<Card> cardStack = new ArrayDeque<>();
	private int size;
	
	public Deck() {
		for(String j: suits) {
			for(int i=2; i<=14; i++) {
				this.cardStack.addLast(new Card(i, j));
			}
		}
	}
	
	void shuffleCards() {
		Card[] cardArr = new Card[52];
		Card tempCard;
		for(int i=0; i<52; i++) {
			cardArr[i] = this.cardStack.pollFirst();
		}
		
		Random ran = new Random();
		for(int i=0; i<52; i++) {
			int randomInt = ran.nextInt(52);
			tempCard = cardArr[i];
			cardArr[i] = cardArr[randomInt];
			cardArr[randomInt] = tempCard;
		}
		
		//Testing
		int counter = 0;
		for(Card c: cardArr) {
			if(c != null) {counter +=1;}
		}
		System.out.println(counter);
		
		for(int i=0; i<52; i++) {
			this.cardStack.addLast(cardArr[i]);
		}
	}
	
	
		
	void addCard(Card card) {this.cardStack.addLast(card);}
	
	//STILL NEED TO FINISH THESE TWO AFTER CREATING HAND AND PLAYER CLASSES
//	 deal(Player p) {p.getHand().addCard(this.cardStack.pollFirst());}
	
	
	public Card removeCard() {  
		return this.cardStack.poll();
	}
	
	
	//TOSTRING
	public String toString() {
		Iterator<Card> iter = this.cardStack.iterator();
		String returnStr = "";
		System.out.println(returnStr);
		while(iter.hasNext()) {
			//System.out.println("Returning Object "+ iter.next().toString());
			returnStr += "\n" + iter.next().toString();
		}
		return returnStr;
	}
}
