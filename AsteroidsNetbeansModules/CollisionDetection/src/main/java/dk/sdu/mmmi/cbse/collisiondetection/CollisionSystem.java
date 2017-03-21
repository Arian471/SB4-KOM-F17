package dk.sdu.mmmi.cbse.collisiondetection;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IPostEntityProcessingService.class)
public class CollisionSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities()) {
            for (Entity otherEntity : world.getEntities()) {
                if (otherEntity != entity && !entity.isHit() && testCollision(entity, otherEntity)) {
                    entity.setIsHit(true);
                }
            }
        }
    }

    private boolean testCollision(Entity source, Entity target) {
        float srcRadius = source.getRadius();
        float targetRadius = target.getRadius();
        float dx = (source.getX() + srcRadius / 2) - (target.getX() + targetRadius / 2);
        float dy = (source.getY() + srcRadius / 2) - (target.getY() + targetRadius / 2);

        double dist = Math.sqrt((dx * dx) + (dy * dy));
        boolean isCollision = dist <= srcRadius + targetRadius;

        return isCollision;
    }
}
