package tests;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import blackjack.Blackjack;
import blackjack.Card;


/**
 * Please put your own test cases into this file, so they can be tested
 * on the server.
*/

public class StudentTests {
	Random random = new Random(13l);
	Random random2 = new Random(12L);
	@Test
	public void test1(){
	
	Blackjack test = new Blackjack(random,2);
	test.setBetAmount(30);
	test.deal();
	test.playerStand();
	int bet= test.getBetAmount();
	Card[] player = test.getPlayerCards();
	Card[] dealer = test.getDealerCards();
	System.out.println("{Test:\n");
	for(int i=0;i<player.length;i++) {
		System.out.println(player[i] + " : Student");
		System.out.println(dealer[i] + " : Dealer");
	}
	test.playerStand();
	assertEquals(6,test.getGameStatus());
	assertEquals(30,bet);
	assertEquals(4,test.getDealerCardsEvaluation());
	assertEquals(2,test.getPlayerCardsEvaluation());
	
	}
	
	
	
	@Test
	public void test2(){
		Blackjack test2 = new Blackjack(random,1);
		test2.deal();
		test2.playerHit();
		System.out.println("Test 2:\n");
		Card[] player = test2.getPlayerCards();
		Card[] dealer = test2.getDealerCards();
		
		for(int i=0;i<player.length;i++) {
			System.out.println(player[i] + " : Student");
			
		}
		for(int i=0;i<dealer.length;i++) {
			
			System.out.println(dealer[i] + " : Dealer");
		}
		assertEquals(6,test2.getGameStatus());
		assertEquals(3,test2.getPlayerCardsEvaluation());
		
	}
	
	@Test
	public void test3() {
		Blackjack test3 = new Blackjack(random2, 1);
		test3.deal();
		System.out.println("Test3:\n");
		test3.playerStand();
		Card[] player = test3.getPlayerCards();
		Card[] dealer = test3.getDealerCards();
		
		for(int i=0;i<player.length;i++) {
			System.out.println(player[i] + " : Student");
		}
		for(int i=0;i<dealer.length;i++) {
			System.out.println(dealer[i] + " : Dealer");
		}
		test3.playerStand();
		assertEquals(6,test3.getGameStatus());
		assertEquals(2,test3.getPlayerCardsEvaluation());
		assertEquals(2,test3.getDealerCardsEvaluation());
	}
	
	@Test
	public void test4() {
		Blackjack test4 = new Blackjack(random2, 1);
		test4.deal();
		System.out.println("Test4:\n");
		test4.playerHit();
		Card[] player = test4.getPlayerCards();
		Card[] dealer = test4.getDealerCards();
		
		for(int i=0;i<player.length;i++) {
			System.out.println(player[i] + " : Student");
		}
		for(int i=0;i<dealer.length;i++) {
			System.out.println(dealer[i] + " : Dealer");
		}
		assertEquals(6,test4.getGameStatus());
		assertEquals(47,test4.getGameDeck().length);
		assertEquals(3,test4.getPlayerCardsEvaluation());
	}
	
	
}