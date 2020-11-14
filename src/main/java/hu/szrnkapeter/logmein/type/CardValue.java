package hu.szrnkapeter.logmein.type;

import java.util.Map;

import com.google.common.collect.Maps;

public enum CardValue {

	HA(1),
	H2(2),
	H3(3),
	H4(4),
	H5(5),
	H6(6),
	H7(7),
	H8(8),
	H9(9),
	H10(10),
	HJ(11),
	HQ(12),
	HK(13),
	SA(1),
	S2(2),
	S3(3),
	S4(4),
	S5(5),
	S6(6),
	S7(7),
	S8(8),
	S9(9),
	S10(10),
	SJ(11),
	SQ(12),
	SK(13),
	CA(1),
	C2(2),
	C3(3),
	C4(4),
	C5(5),
	C6(6),
	C7(7),
	C8(8),
	C9(9),
	C10(10),
	CJ(11),
	CQ(12),
	CK(13),
	DA(1),
	D2(2),
	D3(3),
	D4(4),
	D5(5),
	D6(6),
	D7(7),
	D8(8),
	D9(9),
	D10(10),
	DJ(11),
	DQ(12),
	DK(13);
	
	private static Map<String, Integer> valueMap = Maps.newHashMap();
	
	static {
		for(CardValue value : values()) {
			valueMap.put(value.name(), value.getValue());
		}
	}

	CardValue(int v) {
		value = v;
	}

	private int value;

	public int getValue() {
		return value;
	}
	
	public static int getValueByName(String name) {
		return valueMap.get(name).intValue();
	}
}