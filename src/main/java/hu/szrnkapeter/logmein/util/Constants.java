package hu.szrnkapeter.logmein.util;

public interface Constants {

	public static final String ID = "/{id}";
	public static final String NAME = "/{name}";

	public static final String CARDLIST_HEARTS = "HA,H2,H3,H4,H5,H6,H7,H8,H9,H10,HJ,HQ,HK";
	public static final String CARDLIST_SPADES = "SA,S2,S3,S4,S5,S6,S7,S8,S9,S10,SJ,SQ,SK";
	public static final String CARDLIST_CLUBS = "CA,C2,C3,C4,C5,C6,C7,C8,C9,C10,CJ,CQ,CK";
	public static final String CARDLIST_DIAMONDS = "DA,D2,D3,D4,D5,D6,D7,D8,D9,D10,DJ,DQ,DK";
	
	public static final String COMMA = ",";
	public static final String CONFIG_SHUFFLE_STRATEGY = "shuffle.strategy";
	
	/**
	 * Yes, thats not a pretty solution, but the easiest :)
	 */
	public static final String LIST_ALL_CARD = CARDLIST_HEARTS + "," + CARDLIST_SPADES + "," + CARDLIST_CLUBS + "," + CARDLIST_DIAMONDS;
}