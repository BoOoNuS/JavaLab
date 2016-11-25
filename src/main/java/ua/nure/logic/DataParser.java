package ua.nure.logic;

import java.util.regex.Pattern;

/**
 * Created by Stanislav on 30.10.2016.
 */
public class DataParser {

    private static final String REGEX_FOR_LOGIN = ".{5,}";
    private static final String REGEX_FOR_PASSWORD = ".{3,}";
    private static final String REGEX_FOR_NAME = ".+";

    private static DataParser parser;

    private DataParser() {
    }

    public static DataParser initParser() {
        if (parser == null) {
            synchronized (DataParser.class) {
                if (parser == null) {
                    parser = new DataParser();
                }
            }
        }
        return parser;
    }

    public boolean checkAll(String login, String password, String fullName) {
        return check(login, Pattern.compile(REGEX_FOR_LOGIN)) &
                check(password, Pattern.compile(REGEX_FOR_PASSWORD)) &&
                check(fullName, Pattern.compile(REGEX_FOR_NAME));
    }

    private boolean check(String param, Pattern pattern) {
        return pattern.matcher(param).matches();
    }

}
