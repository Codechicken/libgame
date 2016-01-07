package codechicken.libgame;

import java.util.EnumMap;
import java.io.EOFException;

public class Config {
	
	private EnumMap<KEYPROP, Character> keyprop = new EnumMap<>(KEYPROP.class);
	private EnumMap<PROPERTY, String[]> props = new EnumMap<>(PROPERTY.class);
public Config(){
	keyprop.clear();
	props.clear();
	}
	public Config putKeyProp(KEYPROP id, int data) throws EOFException {
		if (data == -1) {
			throw new EOFException("Unexpected EOF while processing property" + id.toString());
		}
		keyprop.put(id, (char) data);
		return this;

	}
	public Config putProp(PROPERTY id, String[] vals){
		props.put(id, vals);
		return this;
	}
}
