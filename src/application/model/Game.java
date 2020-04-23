package application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Game {
	private List<Stack<Card>> foundations;
	private Stack<Card> draw;

	public Game() {
		this.foundations = new ArrayList<Stack<Card>>();
		for(int i = 0; i < 4; i++) {
			this.foundations.add(new Stack<Card>());
		}
		this.draw = new Stack<Card>();
	}

}
