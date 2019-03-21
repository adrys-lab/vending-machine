package com.dexma.adrian.rebollo.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Class loading and holding the contents (currently) of com.dexma.adrian.rebollo.ResponseMessages.properties
 */
public class PropertyFactory {

    private static final Map<String, String> MESSAGES = new HashMap<>();
    private static final String MESSAGES_PATH = "com/dexma/adrian/rebollo/ResponseMessages.properties";


    // load properties files.
    static {
        loadProperties();
    }

    /**
     * Load MESSAGES from a properties file.
     */
    private static void loadProperties() {
        try {
            load(MESSAGES_PATH, MESSAGES);
        } catch (IOException e) {
            throw new IllegalArgumentException("Properties file not found!");
        }
    }

    private static void load(final String filePath, final Map<String, String> propertiesMap) throws IOException {
        propertiesMap.clear();
        final Properties properties = new Properties();
        final InputStream streamReader = getClassLoader().getResourceAsStream(filePath);
        properties.load(streamReader);
        for (final Map.Entry entry : properties.entrySet()) {
            propertiesMap.put((String) entry.getKey(), (String) entry.getValue());
        }
    }

    /**
     * Disable factory instantiation.
     */
    private PropertyFactory() {
    }

    /**
     * {@code Map<String, String>} holding the contents of 'MESSAGES'.
     *
     * @return map holding the contents of 'MESSAGES'.
     */
    public static String getMessage(final String key) {
        return MESSAGES.get(key);
    }


    public static String formatMessage(final String property, final Object... arguments) {
        return format(getMessage(property), arguments);
    }

    private static String format(final String message, final Object... arguments) {
        return new MessageFormat(message, Locale.getDefault()).format(arguments);
    }

    private static ClassLoader getClassLoader() {
        return PropertyFactory.class.getClassLoader();
    }
}
