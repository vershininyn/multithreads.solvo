package com.solvo.mthreads.domain.command;

import com.solvo.mthreads.domain.command.consumable.FileLoggablePushQueryCommand;
import com.solvo.mthreads.domain.command.consumable.QueuesStructureCommand;

public class CommandFactory {

    public static ICommand parseAndCreateCommand(String pLine) {
        if ((pLine == null) || (pLine.isEmpty())) throw new IllegalArgumentException("Line is null or empty");

        String str = pLine.toLowerCase().trim();

        if (str.matches("\\s*push\\s+type(a|b)\\s*,\\s*-?[0-9]+")) {
            return new FileLoggablePushQueryCommand(pLine.replaceAll("push","").trim());
        }

        ICommand result = null;

        switch(str) {
            case "qstruct" : {
                result = new QueuesStructureCommand();
                break;
            }
            case "exit" : {
                result = new ExitCommand();
                break;
            }
            case "help" : {
                result = new HelpCommand();
                break;
            }
            default: {
                result = new UnknownCommand();
                break;
            }
        }

        return result;
    }

}
