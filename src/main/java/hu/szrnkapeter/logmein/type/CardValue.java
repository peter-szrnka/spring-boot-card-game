package hu.szrnkapeter.logmein.type;

import java.util.Map;

import com.google.common.collect.Maps;

public enum CardValue {

	HA(1, "H", "A"),
	H2(2, "H", "2"),
	H3(3, "H", "3"),
	H4(4, "H", "4"),
	H5(5, "H", "5"),
	H6(6, "H", "6"),
	H7(7, "H", "7"),
	H8(8, "H", "8"),
	H9(9, "H", "9"),
	H10(10, "H", "10"),
	HJ(11, "H", "J"),
	HQ(12, "H", "Q"),
	HK(13, "H", "K"),
	SA(1, "S", "A"),
	S2(2, "S", "2"),
	S3(3, "S", "3"),
	S4(4, "S", "4"),
	S5(5, "S", "5"),
	S6(6, "S", "6"),
	S7(7, "S", "7"),
	S8(8, "S", "8"),
	S9(9, "S", "9"),
	S10(10, "S", "10"),
	SJ(11, "S", "J"),
	SQ(12, "S", "Q"),
	SK(13, "S", "K"),
	CA(1, "C", "A"),
	C2(2, "C", "2"),
	C3(3, "C", "3"),
	C4(4, "C", "4"),
	C5(5, "C", "5"),
	C6(6, "C", "6"),
	C7(7, "C", "7"),
	C8(8, "C", "8"),
	C9(9, "C", "9"),
	C10(10, "C", "10"),
	CJ(11, "C", "J"),
	CQ(12, "C", "Q"),
	CK(13, "C", "K"),
	DA(1, "D", "A"),
	D2(2, "D", "2"),
	D3(3, "D", "3"),
	D4(4, "D", "4"),
	D5(5, "D", "5"),
	D6(6, "D", "6"),
	D7(7, "D", "7"),
	D8(8, "D", "8"),
	D9(9, "D", "9"),
	D10(10, "D", "10"),
	DJ(11, "D", "J"),
	DQ(12, "D", "Q"),
	DK(13, "D", "K");

	private static Map<String, CardValue> valueMap = Maps.newHashMap();
	private static Map<String, CardValue> labelMap = Maps.newHashMap();
	
	static {
		for(CardValue value : values()) {
			valueMap.put(value.name(), value);
			labelMap.put(value.getLabel(), value);
		}
	}

	CardValue(int v, String c, String l) {
		value = v;
		color = c;
		label = l;
	}

	private String color;
	private int value;
	private String label;

	public int getValue() {
		return value;
	}
	
	public static CardValue getValueByName(String name) {
		return valueMap.get(name);
	}
	
	public static CardValue getValueByLabel(String label) {
		return labelMap.get(label);
	}

	public String getColor() {
		return color;
	}
	
	public String getLabel() {
		return label;
	}
}