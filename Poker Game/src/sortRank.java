import java.util.*;

//sort method uses this to sort by ascending rank
class sortRank implements Comparator<Card>{
	public int compare(Card c1, Card c2) {
		return c1.getRank() - c2.getRank();
	}
}