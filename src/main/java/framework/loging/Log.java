package framework.loging;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    private Log() {
    }

    private static final Logger LOGGER = LogManager.getLogger("binary_data_logger");

    public static void logInfoMessage(String message) {
        LOGGER.info(message);
    }

    public static void logInfoMessage(String msg, Object... params) {
        LOGGER.info(String.format(msg, params));
    }

    public static void logInfoMessage(File file, String message) {
        LOGGER.info("RP_MESSAGE#FILE#{}#{}", file.getAbsolutePath(), message);
    }

    public static void logDebug(String message) {
        LOGGER.debug(message);
    }
}