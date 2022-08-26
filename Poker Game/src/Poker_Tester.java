import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class Poker_Tester {
	public static void main(String[] args) {

		
		
		String[] Player_Names = { 
		
		//Enter Names of all players below in quotation marks separated my commas. 
		//Then make your console window big and press run.
		// Example ==> "Arya", "Milan", "Shaggy"
		
		};
		
		
		runGame(Player_Names);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void runGame(String[] Player_Names) {
		Player[] playerArr = new Player[Player_Names.length];
		Deck deck = new Deck();
		ArrayList<Game> games = new ArrayList<Game>();

		for (int i = 0; i < playerArr.length; i++) {
			playerArr[i] = new Player(Player_Names[i], new Hand());
		}

		games.add(new Game(playerArr));
		Game g = games.get(0);
		// Initial setup finished

		while (g.players.length != 1) {
			deck.shuffleCards();
			g = games.get(0);
			g.initializeGame(deck);

			// Have to add bets to the pot after each round
			// Small blind and big blind after first round
			// give pot to player who wins at the end.
			// at the end of each round, copy each players money to this.money array

			System.out.println("\n\n");
			// round 1
			if (g.round == 1) {
				System.out.printf("Small Blind: %s. Big Blind: %s\n", g.players[0].playerName, g.players[1].playerName);
				g.players[0].raiseTurn(5);
				g.players[1].raiseTurn(10);
				g.callAmount = 10;
				g.canCheck = false;
				for(Player p : g.players) {
					p.canCheck = false;
					//g.callAmount = 10;
				}
				System.out.println("\n\n\n");
				System.out.println("\t\t\tWelcome to round 1");

				for (int i = 2; i < g.players.length; i++) {
					if (g.players[i].inGame == true) {
						g.play(g.players[i], deck);
					}
				}

				for (int i = 0; i < 2; i++) {
					if (g.players[i].inGame == true) {
						g.play(g.players[i], deck);
					}
				}
				g.moveToPot();

				for (int i = 0; i < g.players.length; i++) {
					g.money[i] = g.players[i].money;
					g.players[i].callAmount = 0;
					g.players[i].canCheck = true;
				}
				g.callAmount = 0;
				g.canCheck = true;
				
				g.onePlayerLeft();
				g.round += 1;
				
			}

			// round 2
			if (g.round == 2) {
				System.out.println("\t\t\tRound 2 has begun !!");
				g.addCard(deck);
				g.addCard(deck);
				g.addCard(deck);

				for (Player p : g.players) {
					if (p.inGame == true) {
						g.play(p, deck);
					}
				}

				g.moveToPot();

				for (int i = 0; i < g.players.length; i++) {
					g.money[i] = g.players[i].money;
					g.players[i].callAmount = 0;
					g.players[i].canCheck = true;
				}
				g.callAmount = 0;
				g.canCheck = true;
				
				g.onePlayerLeft();
				g.round += 1;
			}

			// round 3
			if (g.round == 3) {
				System.out.println("\t\t\tRound 3 has begun");
				g.addCard(deck);
				for (Player p : g.players) {
					if (p.inGame == true) {
						g.play(p, deck);
					}
				}
				g.moveToPot();

				for (int i = 0; i < g.players.length; i++) {
					g.money[i] = g.players[i].money;
					g.players[i].callAmount = 0;
					g.players[i].canCheck = true;
				}
				g.callAmount = 0;
				g.canCheck = true;
				
				g.onePlayerLeft();
				g.round += 1;
			}

			// round 4
			if (g.round == 4) {
				System.out.println("\t\t\tRound 4 has begun. This is the last round");
				g.addCard(deck);
				for (Player p : g.players) {
					if (p.inGame == true) {
						g.play(p, deck);
					}
				}
				g.moveToPot();

				for (int i = 0; i < g.players.length; i++) {
					g.money[i] = g.players[i].money;
					g.players[i].callAmount = 0;
					g.players[i].canCheck = true;
				}
				g.callAmount = 0;
				g.canCheck = true;
				
				g.onePlayerLeft();
				g.round += 1;
			}

			for (Player p : g.players) {
				p.checkHand();
			}

			Player winner = g.removeFromPot();
			System.out.printf("\nPlayer %s has won this game\n", winner.playerName);
			System.out.println("Winner's hand : " + winner.getHand());
			winner.getHand().evaluateHand();
			winner.money += g.pot;
			System.out.println("\n");
			g.pot = 0;
			g.clearCommunityCards(deck);

			
						
			games.add(g.endGame(deck));
			games.remove(0);

		}

		System.out.printf("\nPlayer %s has Won. Restart the code to play again", g.players[0].playerName);

	}
	
	
//	public static Player[] generatePlayers(Card[] cardArr) { // Generates two Players
//		Random rand = new Random();
//		for (int i = 0; i < cardArr.length; i++) {
//			int suit = rand.nextInt(4);
//			int rank = ThreadLocalRandom.current().nextInt(2, 15);
//			cardArr[i] = new Card(rank, suits[suit]);
//		}
//
//		Hand h1 = new Hand(cardArr[0], cardArr[1]);
//		Hand h2 = new Hand(cardArr[2], cardArr[3]);
//		Player p1 = new Player("P1", h1);
//		Player p2 = new Player("P2", h2);
//		return new Player[] { p1, p2 };
//	}

}