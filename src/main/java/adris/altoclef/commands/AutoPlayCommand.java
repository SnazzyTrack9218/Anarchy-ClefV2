package adris.altoclef.commands;

import adris.altoclef.AltoClef;
import adris.altoclef.commandsystem.Arg;
import adris.altoclef.commandsystem.ArgParser;
import adris.altoclef.commandsystem.Command;
import adris.altoclef.commandsystem.CommandException;

public class AutoPlayCommand extends Command {

    public AutoPlayCommand() throws CommandException {
        super("autoplay", "Toggle persistent autoplay survival", new Arg<>(String.class, "mode", null, 0));
    }

    @Override
    protected void call(AltoClef mod, ArgParser parser) throws CommandException {
        String mode = parser.get(String.class);

        if (mode == null) {
            mod.toggleAutoPlay();
            mod.log("Autoplay " + (mod.isAutoPlayEnabled() ? "enabled" : "disabled"));
            finish();
            return;
        }

        if (mode.equalsIgnoreCase("on")) {
            mod.enableAutoPlay();
            mod.log("Autoplay enabled");
        } else if (mode.equalsIgnoreCase("off") || mode.equalsIgnoreCase("stop")) {
            mod.disableAutoPlay();
            mod.log("Autoplay disabled");
        } else {
            throw new CommandException("Unknown mode '" + mode + "'. Use on/off or no argument to toggle.");
        }

        finish();
    }
}
