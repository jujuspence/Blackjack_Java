package blackjack;
import java.util.*;

public class Blackjack implements BlackjackEngine {

	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> playersHand= new ArrayList<Card>();
	private ArrayList<Card> dealersHand= new ArrayList<Card>();
	private int account;
	private int bet;
	private int deckNum;
	private Random random;
	private int gameStatus;
	private int playerStatus;

	/**
	 * Constructor you must provide.  Initializes the player's account 
	 * to 200 and the initial bet to 5.  Feel free to initialize any other
	 * fields. Keep in mind that the constructor does not define the 
	 * deck(s) of cards.
	 * @param randomGenerator
	 * @param numberOfDecks
	 */
	
	


	public Blackjack(Random randomGenerator, int numberOfDecks) {

		this.account= 200;
		this.bet = 5;
		deckNum = numberOfDecks;
		random = randomGenerator;
	}

	public int getNumberOfDecks() {
		return deckNum;
	}

	public void createAndShuffleGameDeck() {

		for(CardSuit suit : CardSuit.values()) {
			for(CardValue value : CardValue.values()) {
				deck.add(new Card(value,suit));	
			}
		}

		Collections.shuffle(deck,random);
		return;
	}

	public Card[] getGameDeck() {
		Card[] array = new Card[deck.size()*deckNum];


		for(int countRound=0;countRound<deckNum;countRound++) {
			for(int i=0;i<deck.size();i++) {
				array[i+(52*countRound)]= deck.get(i);
			}
		}
		return array;
	}

	public void deal() {
		account = account-bet; //places bet from account
		deck = new ArrayList<Card>();
		playersHand = new ArrayList<Card>();
		dealersHand = new ArrayList<Card>();
		createAndShuffleGameDeck();

		//card1
		playersHand.add(deck.get(0));
		deck.remove(0);
		//card2
		dealersHand.add(deck.get(0));
		dealersHand.get(0).setFaceDown();
		deck.remove(0);
		//card3
		playersHand.add(deck.get(0));
		deck.remove(0);
		//card4
		dealersHand.add(deck.get(0));
		deck.remove(0);


		gameStatus = GAME_IN_PROGRESS;

	}

	public Card[] getDealerCards() {
		return getCards(dealersHand, dealersHand.size());
	}

	public int[] getDealerCardsTotal() {
		return getCardsTotal(dealersHand);
	}

	public int getDealerCardsEvaluation() {
		return getCardsEvaluation(dealersHand);
	}

	public Card[] getPlayerCards() {
		return getCards(playersHand,playersHand.size());
	}

	private static Card[] getCards(ArrayList<Card> hand, int handSize) {
		Card[] array = new Card[handSize];

		for(int i=0;i<handSize;i++) {
			array[i]=hand.get(i);
		}

		return array;
	}

	private static int[] getCardsTotal(ArrayList<Card> hand) {
		int[] cardsTotal = new int[2];
		int total = 0;
		ArrayList<Card> tempHand = new ArrayList<Card>();

		for(int i=0;i<hand.size();i++) {
			tempHand.add(hand.get(i));
			total += hand.get(i).getValue().getIntValue();
		} //creates deep copy of ArrayList hand for editing purposes, also gets total IntCount for values list

		if(total>21) {
			total=0;
		}

		if(hand.size()>2) {
			if(total!=0) {
				forLoop:
					for(int i=0;i<hand.size();i++) {
						if(hand.get(i).getValue()==CardValue.Ace) {
							if(tempHand.size()<hand.size()) {
								break forLoop;	
							}  //breaks loop if more than one Ace is found 
							tempHand.remove(i);
						}
					}//finds any Aces in hand and removes the first found from TempHand

			if(hand.size()==tempHand.size()) { //no Aces in hand
				cardsTotal = new int[1];
				cardsTotal[0]= total;
			}if(hand.size()>tempHand.size()) { //one Ace with double value
				if(total<21 && total>10) { //only considers second value of Ace(11) if total isnt over 10
					cardsTotal = new int[1];
					cardsTotal[0]= total; 
				}else {
					cardsTotal[0]= total;	
					cardsTotal[1]= total +10; 
				}
			}
			}}
		if(hand.size()==2) {
			int aceCount=0;

			for(int i=0;i<2;i++) {
				if(hand.get(i).getValue()==CardValue.Ace) {
					aceCount++;
				}
			} // for loop to count Aces in hand
			if(aceCount==0) {
				cardsTotal = new int[1];
				cardsTotal[0] = total;
			}if(aceCount==1) {
				cardsTotal[0]= total;
				cardsTotal[1]=total+ 10;
			}if(aceCount==2) {
				cardsTotal[0]= 2;
				cardsTotal[1]=12;
			}
		}

		if(cardsTotal[0]==0) {
			cardsTotal= null;
		}
		return cardsTotal;
	}

	public int[] getPlayerCardsTotal() {
		return getCardsTotal(playersHand);
	}

	private static int getCardsEvaluation(ArrayList<Card> hand) {
		int Status=0;
		int valueTotal=0;
		boolean bustCondition= false;

		if(getCardsTotal(hand)==null){
			bustCondition=true;
		} //takes into account the possibility of returning an empty array of values under 21(null)


		if(bustCondition==false) {
			valueTotal += getCardsTotal(hand)[getCardsTotal(hand).length-1];
		}

		if(hand.size()==2) {
			forLoop:
				for(int i=0;i<hand.size();i++) {
					if(hand.get(i).getValue()==CardValue.Ace) {
						if(valueTotal-hand.get(i).getValue().getIntValue()==20) {
							Status= BLACKJACK;
							break forLoop;
						}
					}
				}
		}

		if(Status==0 || Status!= BLACKJACK) {
			if(valueTotal<21) {
				Status = LESS_THAN_21;
			}if(bustCondition==true) {
				Status = BUST;
			}if(valueTotal==21){
				Status= HAS_21;
			}
		}

		return Status;
	}

	public int getPlayerCardsEvaluation() {
		return getCardsEvaluation(playersHand);
	}

	public void playerHit() {

		playersHand.add(deck.get(0));
		deck.remove(0);

		playerStatus= getCardsEvaluation(playersHand);

		if(playerStatus!=BUST) {
			gameStatus=GAME_IN_PROGRESS;
		}if(playerStatus==BUST) {
			gameStatus=DEALER_WON;
		}


	}

	public void playerStand() {
		int[]playerCardsTotal= new int[playersHand.size()];
		int[] dealerCardsTotal= new int[dealersHand.size()];
		playerCardsTotal= getPlayerCardsTotal();
		int playerTotal= playerCardsTotal[playerCardsTotal.length-1]; //gets players total closest to 21
		dealersHand.get(0).setFaceUp();

		dealerCardsTotal=  getDealerCardsTotal();
		int dealerTotal= dealerCardsTotal[dealerCardsTotal.length-1]; //get dealers highest total without going over 21

		forLoop: //add necessary cards to dealers highest total
			for(int i=0;i<deck.size();i++) {
				if(dealerTotal<16) {
					dealersHand.add(deck.get(0));
					int newValue = deck.get(0).getValue().getIntValue();
					deck.remove(0);
					dealerTotal+= newValue;
				}if(dealerTotal>21) {
					gameStatus= PLAYER_WON; // dealer busts after adding new card
					break forLoop;
				}if(dealerTotal>=16 && dealerTotal<=21) {
					break forLoop; 
				}
			}
		int playerDiff = 21-playerTotal;
		int dealerDiff = 21-dealerTotal;

		if(gameStatus!= PLAYER_WON) {
			if(dealerDiff>playerDiff) {
				gameStatus= PLAYER_WON;
			}if(dealerDiff<playerDiff) {
				gameStatus= DEALER_WON;
			}if(dealerDiff==playerDiff){
				gameStatus=DRAW;
			}
		}


		if(gameStatus== PLAYER_WON) { 
			account+= bet*2; 
		}if(gameStatus==DRAW) {
			account+= bet; 
		}


	}

	public int getGameStatus() {
		return gameStatus;
	}

	public void setBetAmount(int amount) {
		bet = amount;
	}

	public int getBetAmount() {
		return bet;
	}

	public void setAccountAmount(int amount) {	
		account = amount;
	}

	public int getAccountAmount() {
		return account;
	}

	/* Feel Free to add any private methods you might need */
}