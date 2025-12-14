package adris.altoclef.tasks.Anarchy;

import adris.altoclef.AltoClef;
import adris.altoclef.tasks.entity.KillPlayerTask;
import adris.altoclef.tasks.entity.SelfCareTask;
import adris.altoclef.tasks.movement.RunAwayFromEntitiesTask;
import adris.altoclef.tasks.movement.TimeoutWanderTask;
import adris.altoclef.tasksystem.Task;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class AutoPlayTask extends Task {

    private static final double ATTACK_RANGE = 6;
    private static final double FLEE_RANGE = 18;

    private final SelfCareTask _selfCare = new SelfCareTask();

    @Override
    protected void onStart(AltoClef mod) {

    }

    @Override
    protected Task onTick(AltoClef mod) {
        Optional<Entity> closestPlayer = mod.getEntityTracker().getClosestEntity(
                entity -> entity instanceof PlayerEntity && entity != mod.getPlayer(),
                PlayerEntity.class);

        if (closestPlayer.isPresent()) {
            PlayerEntity threat = (PlayerEntity) closestPlayer.get();
            if (threat.isInRange(mod.getPlayer(), ATTACK_RANGE)) {
                setDebugState("Attacking nearby threat");
                return new KillPlayerTask(threat.getName().getString());
            }
            if (threat.isInRange(mod.getPlayer(), FLEE_RANGE)) {
                setDebugState("Running from player");
                return new RunAwayFromPlayersTask(() -> List.of(threat), FLEE_RANGE * 2);
            }
        }

        if (!_selfCare.isFinished(mod)) {
            setDebugState("Gathering gear and supplies");
            return _selfCare;
        }

        setDebugState("Wandering while on autoplay");
        return new TimeoutWanderTask();
    }

    @Override
    protected void onStop(AltoClef mod, Task interruptTask) {

    }

    @Override
    protected boolean isEqual(Task other) {
        return other instanceof AutoPlayTask;
    }

    @Override
    protected String toDebugString() {
        return "Autoplay survival";
    }

    private static class RunAwayFromPlayersTask extends RunAwayFromEntitiesTask {

        public RunAwayFromPlayersTask(Supplier<List<Entity>> toRunAwayFrom, double distanceToRun) {
            super(toRunAwayFrom, distanceToRun, true, 1.5);
        }

        @Override
        protected boolean isEqual(Task other) {
            return other instanceof RunAwayFromPlayersTask;
        }

        @Override
        protected String toDebugString() {
            return "Fleeing from players";
        }
    }
}
