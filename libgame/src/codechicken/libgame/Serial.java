package codechicken.libgame;

import java.lang.reflect.InvocationTargetException;

public interface Serial<T> {
	T load(String in);

	String put(T ob);

	/*
	 * R:Reference Y:Byte C:Char S:Short {Y:Begin array }Y:End array A:Boolean
	 * false T:Boolean true I:Int L:Long F:Float D:Double
	 */
	static <U extends Serial<U>> String putObject(U ob) {
		return "R" + ob.getClass().getName() + ob.put(ob);
	}

	static <U extends Serial<U>> String beginNarray(byte dim, Class<U> ob) {

		return "R" + ob.getName().replaceAll("\\[\\]", "") + "{Y" + Integer.toHexString(dim);
	}

	static String endNarray(byte dim) {
		return "}Y" + Integer.toHexString(dim);
	}

	static String writeNArray(byte dim) {
		return "{Y" + Integer.toHexString(dim);
	}

	static String putByte(byte x) {
		return "Y" + Integer.toHexString(x);
	}

	static String putChar(char x) {
		return "C" + Integer.toHexString(x);
	}

	static String putShort(short x) {
		return "S" + Integer.toHexString(x);
	}

	static String putBoolean(boolean x) {
		if (x)
			return "T";
		return "A";
	}

	static String putInt(int x) {
		return "I" + Integer.toHexString(x);
	}

	static String putLong(long x) {
		return "L" + Long.toHexString(x);
	}

	static String putFloat(float x) {
		return "F" + Integer.toHexString(Float.floatToRawIntBits(x));
	}

	static String putDouble(double x) {
		return "D" + Long.toHexString(Double.doubleToRawLongBits(x));
	}

	static <U extends Serial<U>> U loadObject(String in, Class<U> cls) {
		if (in.charAt(0) != 'R')
			throw new SerialException();
		in = in.substring(1);
		StringBuilder tmp = new StringBuilder();
		for (int i = 0;; i++) {
			if (in.charAt(i) == '@')
				break;
			tmp.append(in.charAt(i));
		}
		if (cls.getName().equals(tmp.toString())) {
			try {
				return cls.getConstructor().newInstance().load(in);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw (SerialException) new SerialException().initCause(e);

			}
		}
		throw new SerialException();
	}

	static byte readNarray(String in) {
		if (in.startsWith("{Y", 0))
			return (byte) Integer.parseInt(in.substring(2), 16);
		throw new SerialException();
	}
	static byte readBeginNarray(String in ,Class<Serial<?>> cls){
		
	}
}
