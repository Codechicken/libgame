package codechicken.libgame.io;

import java.io.*;

import codechicken.libgame.*;
import java.util.logging.*;

public class ConfigLoader {
	String appid;
	Logger syslog = Logger.getLogger("libgame");

	ConfigLoader(String id) {
		appid = id;
		syslog.finest("Init:ConfigLoader");
	}

	Config load() {
		File target;
		target = new File(appid + ".conf");
		int input;
		Config config = new Config();
		boolean keyflag = false;
		try (FileReader file = new FileReader(target)) {

			top: do {
				try {
					input = file.read();
					if (!(input == -1)) {
						input = (char) input;
						if (!keyflag) {
							switch (input) {
							case 0:
								// software version
								config = config.putKeyProp(KEYPROP.VERSION, file.read());
								break;
							case 1:
								// file size, 4 bytes
								config = config.putKeyProp(KEYPROP.FIlE_SIZE_L, file.read());
								config = config.putKeyProp(KEYPROP.FILE_SIZE_H, file.read());
								break;
							case 2:
								// offset to true config; should be 0 or 1
								config = config.putKeyProp(KEYPROP.PROP_OFFSET, file.read());
								break;
							case 3:
								// end of key properties
								keyflag = true;
							default:
								// Error, undefined op
								syslog.severe("ConfigLoader: Undefined operation encountered" + input);
								throw new codechicken.libgame.UndefinedOp();
							}

						}
					}
				}

				catch (IOException e) {
					if (e instanceof EOFException)
						syslog.severe("ConfigLoader:Config:" + e.getMessage());
					break top;
				}
			} while (!(input == -1));
		} catch (FileNotFoundException e) {
			AbortExecption ex = new AbortExecption();
			ex.initCause(e);
			throw ex;
		} catch (IOException e) {
			// failed to close, don't care
		}

		return null;
	}
}
