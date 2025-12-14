package adris.altoclef.experimental.AI.Util.Agent;

import adris.altoclef.util.Dimension;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil {
    public static double lastHealth = -0.1;
    public static boolean healthChanged;
    public static boolean isDead;

    public static void trackAgentHealth() {
        PlayerEntity player = player();
        if (player == null) {
            healthChanged = false;
            return;
        }

        float health = player.getHealth();
        if (lastHealth == -0.1) lastHealth = health;
        else if (health != lastHealth) {
            healthChanged = true;
            lastHealth = health;
        } else healthChanged = false;
    }

    public static void isDead() {
        PlayerEntity player = player();
        if (player == null) {
            isDead = false;
            return;
        }
        isDead = player.getHealth() == 0;
    }

    public static boolean isMoving() {
        PlayerEntity player = player();
        return player != null && (player.forwardSpeed != 0 || player.sidewaysSpeed != 0);
    }

    public static boolean isSprinting() {
        PlayerEntity player = player();
        return player != null && player.isSprinting() && (player.forwardSpeed != 0 || player.sidewaysSpeed != 0);
    }

    public static Dimension getDimension() {
        ClientWorld world = world();
        if (world == null) return Dimension.OVERWORLD;

        return switch (world.getRegistryKey().getValue().getPath()) {
            case "the_nether" -> Dimension.NETHER;
            case "the_end" -> Dimension.END;
            default -> Dimension.OVERWORLD;
        };
    }

    ///////////////////////////// // *Crystal PvP Related* // ///////////////////////////////////////////////
    public static boolean isInHole(PlayerEntity p) {
        BlockPos pos = p.getBlockPos();
        ClientWorld world = world();
        return world != null && !world.getBlockState(pos.add(1, 0, 0)).isAir() && !world.getBlockState(pos.add(-1, 0, 0)).isAir() && !world.getBlockState(pos.add(0, 0, 1)).isAir() && !world.getBlockState(pos.add(0, 0, -1)).isAir() && !world.getBlockState(pos.add(0, -1, 0)).isAir();
    }

    public static boolean isSurrounded(PlayerEntity target) {
        ClientWorld world = world();
        return world != null && !world.getBlockState(target.getBlockPos().add(1, 0, 0)).isAir() && !world.getBlockState(target.getBlockPos().add(-1, 0, 0)).isAir() && !world.getBlockState(target.getBlockPos().add(0, 0, 1)).isAir() && !world.getBlockState(target.getBlockPos().add(0, 0, -1)).isAir();
    }

    public static boolean isBurrowed(PlayerEntity target) {
        ClientWorld world = world();
        return world != null && !world.getBlockState(target.getBlockPos()).isAir();
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    private static PlayerEntity player() {
        return MinecraftClient.getInstance().player;
    }

    private static ClientWorld world() {
        return MinecraftClient.getInstance().world;
    }
}
