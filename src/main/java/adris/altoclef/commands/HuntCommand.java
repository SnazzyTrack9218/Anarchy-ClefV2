package adris.altoclef.commands;

import adris.altoclef.AltoClef;
import adris.altoclef.commandsystem.Arg;
import adris.altoclef.commandsystem.ArgParser;
import adris.altoclef.commandsystem.Command;
import adris.altoclef.commandsystem.CommandException;
import adris.altoclef.tasks.entity.HuntPlayerTask;

public class HuntCommand extends Command {

    public HuntCommand() throws CommandException {
        super("hunt", "Hunt down a player by name", new Arg<>(String.class, "username", null, 0, false));
    }

    @Override
    protected void call(AltoClef mod, ArgParser parser) throws CommandException {
        String username = parser.get(String.class);
        if (username == null || username.equalsIgnoreCase("stop")) {
            mod.getUserTaskChain().cancel(mod);
            mod.log("Hunt stopped.");
            finish();
            return;
        }

        mod.runUserTask(new HuntPlayerTask(username), this::finish);
    }
}
