package adris.altoclef.tasks.entity;

import adris.altoclef.AltoClef;
import adris.altoclef.tasks.movement.GetToBlockTask;
import adris.altoclef.tasks.movement.GetToEntityTask;
import adris.altoclef.tasksystem.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class HuntPlayerTask extends Task {

    private final String _targetName;

    public HuntPlayerTask(String targetName) {
        _targetName = targetName;
    }

    @Override
    protected void onStart(AltoClef mod) {

    }

    @Override
    protected Task onTick(AltoClef mod) {
        Optional<Vec3d> lastPosition = mod.getEntityTracker().getPlayerMostRecentPosition(_targetName);
        if (lastPosition.isEmpty()) {
            setDebugState("Awaiting sighting of target " + _targetName);
            return null;
        }

        Optional<PlayerEntity> player = mod.getEntityTracker().getPlayerEntity(_targetName);
        Vec3d target = lastPosition.get();

        if (player.isPresent()) {
            PlayerEntity targetPlayer = player.get();
            if (targetPlayer.isInRange(mod.getPlayer(), 4)) {
                setDebugState("Engaging target " + _targetName);
                return new KillPlayerTask(_targetName);
            }
            setDebugState("Pathing toward target " + _targetName);
            return new GetToEntityTask(targetPlayer, 2);
        }

        if (target.isInRange(mod.getPlayer().getPos(), 1) && !mod.getEntityTracker().isPlayerLoaded(_targetName)) {
            setDebugState("Lost target, holding position for re-acquire");
            return null;
        }

        setDebugState("Moving to last known position of " + _targetName);
        return new GetToBlockTask(new BlockPos((int) target.x, (int) target.y, (int) target.z), false);
    }

    @Override
    protected void onStop(AltoClef mod, Task interruptTask) {

    }

    @Override
    protected boolean isEqual(Task other) {
        if (other instanceof HuntPlayerTask task) {
            return _targetName.equals(task._targetName);
        }
        return false;
    }

    @Override
    protected String toDebugString() {
        return "Hunt player " + _targetName;
    }
}
