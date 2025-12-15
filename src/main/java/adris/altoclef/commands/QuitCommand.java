package adris.altoclef.commands;

import adris.altoclef.AltoClef;
import adris.altoclef.commandsystem.ArgParser;
import adris.altoclef.commandsystem.Command;
import adris.altoclef.commandsystem.CommandException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.text.Text;

public class QuitCommand extends Command {
    public QuitCommand() {
        super("quit", "log off");
    }

    @Override
    protected void call(AltoClef mod, ArgParser parser) throws CommandException {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.player.networkHandler != null) {
            client.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("Altoclef disconnected")));
        }
    }
}
