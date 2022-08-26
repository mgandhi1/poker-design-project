import java.util.*;

class Game {
	public Player[] players; // same length as money
	public int pot;
	public int round = 1;
	public int gameNum;
	public ArrayList<Card> communityCards;
	public int[] money; // for next round => keeps track of how much money each player has
	public int callAmount;
	public boolean canCheck = true;

	public Game(Player[] p, int[] endMoney, int game) { // for any subsequent games after the first one
		this.players = p;
		this.money = endMoney;
		for (int i = 0; i < players.length; i++) {
			players[i].money = this.money[i];
		}
		this.gameNum = game;
		this.pot = 0;
		this.communityCards = new ArrayList<Card>();
	}

	public Game(Player[] p) { // for the first game
		this.players = p;
//		for (int i=0; i<players.length; i++) {
//			this.money[i] = 1000; 		
//		}
		this.money = new int[players.length];
		this.gameNum = 1;
		this.pot = 0;
		this.communityCards = new ArrayList<Card>();
	}

	private Player[] reorderPlayers(Player[] arr) { // shifts players one to the left (in terms of index) so that small
																	// and
		Player temp = arr[0]; // big blind will always be the first and second index in the players array
		for (int i = 1; i < arr.length; i++) {
			arr[i - 1] = arr[i];
		}
		arr[arr.length - 1] = temp;
		return arr;
	}

	public int checkPot() {
		return this.pot;
	}

	public int playerPotDeterminant() {
		return this.playerPotDeterminant();
	}

	public void moveToPot() {
		for (Player p : players) {
			pot += p.resetBets();
		}
	}

	public Player removeFromPot() {
		List<Player> maxScore = new ArrayList<>();
		List<Player> eligiblePlayers = new ArrayList<Player>();
		for (Player p : players) {
			if (p.inGame) {
				eligiblePlayers.add(p);
			}
		}
		Player winner = eligiblePlayers.get(0);

		for (Player p : eligiblePlayers) {
			if (maxScore.size() == 0 || p.checkHand() > maxScore.get(0).checkHand()) {
				maxScore.clear();
				maxScore.add(p);
				winner = p;
			} else if (p.checkHand() == maxScore.get(0).checkHand()) {
				maxScore.add(p);
			}
		}

		int finalScore = 0;
		int maxCard;
		if (maxScore.size() > 1) {
			for (Player p : maxScore) {
				ArrayList<Card> currCards = p.getHand().getCards();
				maxCard = currCards.get(currCards.size() - 1).getRank();
				if (maxCard >= finalScore) {
					finalScore = maxCard;
					winner = p;
				}
			}
		}

		return winner;
	}

	public void play(Player p, Deck deck) {
		p.callAmount = this.callAmount;
		p.canCheck = this.canCheck;
		for (int i = 0; i < players.length; i++) {
			if (!(players[i].playerName.equals(p.playerName)) && players[i].inGame == true)
				System.out.printf("\n\nPlayer %s has %d money and %d bet", players[i].playerName, players[i].money,
						players[i].getbet());
		}
		System.out.println("\n" + p.toString() + "\n");

		// if(this.canCheck == false) {System.out.println("You cannot check");}
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter your chosen action below : ");
		String inputString = input.nextLine();
		inputString = inputString.substring(0, 1).toUpperCase() + inputString.substring(1);

		if (inputString.equals("Raise")) {
			System.out.println("Please enter the Raise amount below : ");
			int raiseAmount = input.nextInt();
			p.raiseTurn(raiseAmount);
			this.callAmount += raiseAmount;
			this.canCheck = false;
			for (Player player : this.players) {
				player.canCheck = false;
			}
		} else if (inputString.equals("Fold")) {
			p.foldTurn(deck);
			;
			System.out.println("You have folded and are out of the current game");
		} 
		else if(inputString.equals("Check") && this.canCheck == false) {
			System.out.println("\nYou cannot check. Instead, would you like to FOLD or Call (enter below) : ");
			String choice = input.nextLine();
			choice  = choice.substring(0, 1).toUpperCase() + choice.substring(1);
			if(choice.equals("Fold")) {
				p.foldTurn(deck);
			}
			else {
				p.callTurn();
			}
		}
		else if (inputString.equals("Check")) {
			p.checkTurn();
		} else if (inputString.equals("Call")) {
			p.callTurn();
		}

		System.out.println("\nPress Enter to end your turn");
		String temp = input.nextLine();
		for (int j = 0; j < 30; j++) {
			System.out.println("\n");
		}

		// input.close();
	}

	public void initializeGame(Deck deck) {
		if (this.gameNum == 1) {
			System.out.println("Good Day everyone! Today we will be playing Poker. Here are today's players\n");
			for (int i = 0; i < this.players.length; i++) {
				System.out.print("" + this.players[i].getName() + ", ");
				players[i].giveMoney(1000);
				players[i].resetBets();

				players[i].getCard(deck);
				players[i].getCard(deck);

			}
			System.out.println("You will all start with $1000");

		} else {
			System.out.println("\n\n\n");
			System.out.printf("\t\t\tGAME %d of poker\n", this.gameNum);
			System.out.println("The players are : ");
			for (int i = 0; i < this.players.length; i++) {
				players[i].giveMoney(this.money[i]);
				System.out.println(String.format("%s starting with $%d", players[i].playerName, players[i].money));
				players[i].resetBets();

				players[i].getCard(deck);
				players[i].getCard(deck);
			}
		}
	}

	/*
	 * public void addPlayer() {
	 * 
	 * }
	 */

	public void addCard(Deck deck) {
		Card card = deck.removeCard();
		this.communityCards.add(card);
		for (Player i : this.players) {
			i.addCard(card);

		}
	}

	public void removeBankruptPlayers() {
		ArrayList<Player> newPlayers = new ArrayList<Player>();
		ArrayList<Integer> newMoney = new ArrayList<Integer>();

		for (int i = 0; i < players.length; i++) {
			if (players[i].money > 0) {
				newPlayers.add(players[i]);
				newMoney.add(money[i]);
			}
		}

		this.players = new Player[newPlayers.size()];
		this.money = new int[newMoney.size()];
		this.players = newPlayers.toArray(this.players);
		this.money = newMoney.stream().mapToInt(i -> i).toArray();

	}

	public void onePlayerLeft() {
		int notFolded = 0;
		for (Player p : this.players) {
			if (p.inGame && p.money>0) {
				notFolded += 1;
			}
		}
		if (notFolded == 1) {
			this.round = 5;
		}
	}

	public void clearCommunityCards(Deck deck) {
		Card removedCard;
		for (int i = 0; i < communityCards.size(); i++) {
			removedCard = this.communityCards.remove(i);
			// communityCards.addCard(removedCard);
			deck.addCard(removedCard);
		}
		this.communityCards.clear();
	}

	public Game endGame(Deck deck) {

		for (Player p : players) {
			p.resetVars();
		}
		this.canCheck = true;
		this.reorderPlayers(this.players);

		for (int i = 0; i < players.length; i++) {
			this.money[i] = this.players[i].money;
		}

		for (Player p : players) {
			p.clearHand(deck);
		}

		this.removeBankruptPlayers();

		//System.out.println("\n\nGAME OVER\n\n");
		return new Game(this.players, this.money, this.gameNum + 1);
	}

}