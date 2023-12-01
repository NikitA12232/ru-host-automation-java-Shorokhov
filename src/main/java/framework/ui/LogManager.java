package framework.ui;

import framework.loging.Log;
import java.util.Arrays;

public class LogManager {

    private LogManager() {
    }

    static void logGetTextAction(String text) {
        String[] contextKeyWords = parseActionContext();
        contextKeyWords = Arrays.copyOf(contextKeyWords, contextKeyWords.length - 1);
        String actionObject = parseActionObject(contextKeyWords);
        String actionObjectType = contextKeyWords[contextKeyWords.length - 1].toLowerCase();
        Log.logInfoMessage("Text from '%s' %s is: '%s'", actionObject, actionObjectType, text);
    }

    static void logClickOnAction() {
        Log.logInfoMessage(parseAction("%s '%s' %s"));
    }

    static void logScrollOnAction() {
        Log.logInfoMessage(parseAction("%s '%s' %s"));
    }

    static void logStateElementOnAction() {
        Log.logInfoMessage("Check " + parseActionFromStateElement("'%s' %s %s"));
    }

    static void logFillInAction(String value) {
        Log.logInfoMessage(parseAction("%s in '%s' %s") + " with a value: '%s'", value);
    }

    static void logSearchElement(String locatorType, String locatorValue) {
        Log.logInfoMessage("Search element by '%s'. Locator is '%s'", locatorType, locatorValue);
    }

    private static String parseAction(String pattern) {
        String[] contextKeyWords = parseActionContext();
        String actionType = upperCaseFirstLetter(contextKeyWords[0]);
        String actionObject = parseActionObject(contextKeyWords);
        String actionObjectType = contextKeyWords[contextKeyWords.length - 1].toLowerCase();
        return String.format(pattern, actionType, actionObject, actionObjectType);
    }

    private static String parseActionFromStateElement(String pattern) {
        String[] contextKeyWords = parseActionContext();
        String actionType = contextKeyWords[0].toLowerCase();
        String actionObject = parseActionObject(contextKeyWords);
        String actionObjectType = contextKeyWords[contextKeyWords.length - 1].toLowerCase();
        return String.format(pattern, actionObject, actionType, actionObjectType);
    }

    private static String upperCaseFirstLetter(String contextKeyWord) {
        return contextKeyWord.substring(0, 1).toUpperCase() + contextKeyWord.substring(1);
    }

    private static String[] parseActionContext() {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            String className = stackTraceElement.getClassName();
            if (className.endsWith("Screen") || className.endsWith("Page")) {
                return stackTraceElement.getMethodName()
                        .split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
            }
        }
//        throw new MethodNameParseException(
//                "Unable to parse method's name cause the method doesn't belong to a screen class!");
        return new String[0];
    }

    private static String parseActionObject(String[] actions) {
        StringBuilder actionObject = new StringBuilder();
        for (int i = 1; i < actions.length - 1; i++) {
            actionObject.append(actions[i]).append(" ");
        }
        return actionObject.toString().trim();
    }
}
