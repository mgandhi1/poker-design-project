import java.util.*;


//     ARYA - I edited this and added more booleans. Read the comments
//            Also, watch the video I sent you on email if you didn't it explained all the poker actions and rules clearly
//            READ comments for variable explanations

import java.util.*;

public class Player {
	

	//money that player has 
	//money that the player bets 
	
	
//	Boolean inGame
//	isTurn (??? maybe game will handle this?????)
//	Money

	public String playerName; 
	public int money = 0;  //amount of money
	private Hand hand;    //Player's hand
	private int bet; // bet int amount to subtract from money
	
	boolean isSmallBlind = false;
	boolean isBigBlind = false; 
	
	public boolean isTurn = false;   //true if it is the player's turn. Useful for game class
	public boolean inGame = true;    //If player has folded, this will be false
	public boolean hasBet = false;   //Useful for the NEXT player's canCheck boolean
	public boolean canCheck = true;   // if the previous player has bet, cannot check
	public boolean isBankrupt = false;
	public int callAmount;          // Is based on how much the previous player has bet 
	
	
	public Player(String playerName, int money, Hand hand){
		this.playerName = playerName;
		this.money = money; 
		this.hand = hand;
	}
	public Player(String playerName, Hand hand){
		this.playerName = playerName;
		this.hand = hand;
	}
	
	public Hand getHand() {
		return this.hand;
	}
  
	
	public String getName() { 
		return playerName;
	}
	
	public int getbet() {
		return this.bet;
	}

	
	public void getCard(Deck deck){ // player gets 1 card 
		this.hand.addCard(deck.removeCard()); // assignCard method NEW in Deck class
		//System.out.println(""+this.getName() + " has taken a card");
	}		
	// card other // add 1 card //queue implementation???

	
	public void resetVars(){    //Resets the booleans of the player like isSmallBlind and hasBet, etc. will be useful
									//for game class
		this.isSmallBlind = false;
		this.isBigBlind = false;
		this.hasBet = false;
		this.callAmount = 0;
		this.canCheck = true;
		this.inGame = true;
		
	}
	
		public void clearHand(Deck deck) {
			this.hand.clearHand(deck);
			
		}
	
	public int resetBets() {  //Returns amount player has bet. This is useful for when the next round begins and all bets need
								//to be collected in the pot
		int storedBet = this.bet;
		this.bet = 0;
		return storedBet;
	}
	
	public void giveMoney(int amount) {   
		this.money = amount;
	}
	
	public void getMoney(int amount) {
		this.money += amount;
	}
	
	
	public void addCard(Card card) {
		this.hand.addCard(card);
	}
	
	public int checkHand() {
		return this.hand.checkHand();
	}
	
	public int compareHand(Player other) {
		Hand thisHand = this.hand;
		Hand otherHand = other.hand;
		return thisHand.compareHand(otherHand);
	}
	
	
	//ACTIONS START HERE
	
	
	
	
	public void makeTurn() {
		this.isTurn = true;
	}
	
	public void checkTurn() {
		//implementation of check with small and big blinds: player "smallBlind" = player"original" -/+ 1;   player "bigBlind" = player"original" -/+ 2
		this.isTurn = false;
		System.out.printf("\nPlayer %s has checked", this.playerName);
	}
	
	
	public int raiseTurn(int amount) {        // Bet more than the previous player	
		// if bet.player(current) > bet.player(other), then raiseTurn == true 
		this.money -= (callAmount + amount);
		this.bet = callAmount + amount;
		this.isTurn = false;
		System.out.printf("\nPlayer %s has raised by %d", this.playerName, amount);
		
		return amount;
		
		
	} // amount
	
	
	public int callTurn() {   //returns amount player called for
		// if bet.player(current) == bet.player(other), then callTurn == true 
		if(this.money - callAmount <0) {
			System.out.println("You do not have enough money. Please try again.");
			this.inGame = false;
			this.money = 0;
		}
		else {
			int betDifference = this.callAmount - this.bet;
			this.bet = callAmount;
			this.money -= betDifference;
			System.out.printf("\nPlayer %s has called to %d", this.playerName, this.callAmount);
		}
		
		return this.callAmount;
	}
	
	
//	public void removeCard() {
//		
//	}
	
	
	public void foldTurn(Deck deck) {
		this.hand.clearHand(deck);
		this.inGame = false;
	}

	
	
	//Bet sBlind or bBlind amount
	public void smallBlind(int amount) {
		this.money -= amount;
		this.bet += money;
	} 
	public void bigBlind(int amount) {
		this.money -= amount;
		this.bet += money;
	} 

	public String toString() {
		String str = "";
		str += "\n";
		str += "Name : " + this.playerName + "\n";
		str += String.format("Your hand is : %s\n", this.hand.getCards().toString());
		str += String.format("Money: %d -- Bet on Table: %d\n", this.money, this.bet);
		str += String.format("Can check: %s -- Call Amount: %s ", this.canCheck, this.callAmount);
		return str;
	}
	
	public String publicToString() {
		String str = "";
		str += "\n";
		str += "Name : " + this.playerName + "\n";
		str += String.format("Money: %d -- Bet on Table: %d\n", this.money, this.bet);
		str += String.format("Can check: %s -- Call Amount: %s ", this.canCheck, this.callAmount);
		return str;
	}
	
	
	
}

