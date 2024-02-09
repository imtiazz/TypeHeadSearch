package HLD.TypeHeadSearch;
import com.activebase.content.mask.ContentProcessor;
import com.activebase.contentHandlers.statement.StatementContentHandler;
import com.activebase.logging.TraceFacility;
import com.activebase.rule.RuleContext;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetMicroStrategyApplicationUserName {
    private static final String regexforApplicationUserName = "(.*)
(MICROSTRATEGY_USERNAME\\s*=\\s*(.*?),)(.*)";
    private static final String MicroStrategy_USER = "MICROSTRATEGY_USERNAME";
    private static final int flags = Pattern.DOTALL | Pattern.MULTILINE |
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE;
    private static final Pattern patternforApplicationUserName =
            Pattern.compile(regexforApplicationUserName, flags);
    public static String execute(RuleContext ctx) {
        StatementContentHandler stmtContenthandler = null;
        if (ctx == null) {
            return null;
        } else {
            ContentProcessor contentProcessor = (ContentProcessor) ctx;
            stmtContenthandler = contentProcessor.getStatementContentHandler();
            if (stmtContenthandler == null) {
                return null;
            }
        }
        String orginalStatement = stmtContenthandler.getStatement();
        Matcher applicationUserNameMatcher =
                patternforApplicationUserName.matcher(orginalStatement);
        if (applicationUserNameMatcher.find()) {
            String microStrategyApplicationUserName =
                    applicationUserNameMatcher.group(3);
            Map symbolTable = stmtContenthandler.getSymbolTable();
            symbolTable.put(MicroStrategy_USER, microStrategyApplicationUserName.trim());
            String newStatement = applicationUserNameMatcher.group(1) + " " +
                    applicationUserNameMatcher.group(4);
            return newStatement;
        }
        return null;
    }
}
