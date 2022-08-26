public class Card 
{
  String[] string_names = {"Jack", "Queen", "King", "Ace"};
  String[] suits = {"Hearts", "Spades", "Diamonds", "Clubs"};

  private int rank;
  private String suit;
  
  public Card(int r, String s){
    rank = r;
    suit = s;
  }
  public Card(String r, String s){
    int intRank = -10;
    for(int i=0; i<string_names.length; i++){
      if(string_names[i].equals(r)){intRank = 11+i;}
    }
    if(intRank==-10){
      throw new IllegalArgumentException("Invalid Rank Argument");
    }
    rank = intRank;
    suit = s;
  }
  
  
  public String getSuit() {return this.suit;}
  
  public int getRank() {return this.rank;}
  
  //returns boolean value
  public int compare(Card other) {
    Integer temp1 = this.rank;
    Integer temp2 = other.rank;
    return temp1.compareTo(temp2);
  }
  
  public boolean sameRank(Card other) {return this.suit == other.suit;}
  
  public String toString(){
	boolean convertedToString = false;
    String stringRank = "";
    if(this.rank>10){
      stringRank = string_names[this.rank-11];
      convertedToString = true;
    }
    if(convertedToString==true) {return (stringRank + " of " + this.suit);}
    else {return ("" + this.rank + " of " + this.suit);}
    
  }
}

//  static public void main(String[] passedArgs) {
//    String[] appletArgs = new String[] { "Poker_Game" };
//    if (passedArgs != null) {
//      PApplet.main(concat(appletArgs, passedArgs));
//    } else {
//      PApplet.main(appletArgs);
//    }
//  }
//}
