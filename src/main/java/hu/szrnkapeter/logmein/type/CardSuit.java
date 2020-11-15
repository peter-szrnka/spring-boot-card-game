package hu.szrnkapeter.logmein.type;

import java.util.HashMap;
import java.util.Map;

public enum CardSuit {

	H(1),
	S(2),
	C(3),
	D(4);
	
	private int order;
	private static Map<String, CardSuit> nameMap = new HashMap<>();
	
	static {
		for(CardSuit suit : values()) {
			nameMap.put(suit.name(), suit);
		}
	}
	
	CardSuit(int o) {
		order = o;
	}

	public int getOrder() {
		return order;
	}
	
	public static CardSuit getByName(String name) {
		return nameMap.get(name);
	}
}