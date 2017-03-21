package dk.sdu.mmmi.cbse.laserweapon;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class LaserSystem implements IEntityProcessingService, BulletSPI {

    private float CD;
    private boolean canShoot = true;

    @Override
    public void process(GameData gameData, World world) {


        for (Entity bullet : world.getEntities(Bullet.class)) {
            moveBullet(gameData, bullet);
            setShape(bullet);

            if (bullet.getExpiration() > 0) {
                bullet.reduceExpiration(gameData.getDelta());
            } else {
                world.removeEntity(bullet.getID());
            }
        }
    }

    private void moveBullet(GameData gameData, Entity entity) {
        float x = entity.getX();
        float y = entity.getY();
        float dt = gameData.getDelta();
        float dx = entity.getDx();
        float dy = entity.getDy();
        float radians = entity.getRadians();
        float speed = 350;

        dx = (float) cos(radians) * speed;
        dy = (float) sin(radians) * speed;

        //Wrap
        if (entity.getX() > gameData.getDisplayWidth()) {
            x = 0;
        } else if (entity.getX() < 0) {
            x = gameData.getDisplayWidth();
        }

        if (entity.getY() > gameData.getDisplayHeight()) {
            y = 0;
        } else if (entity.getY() < 0) {
            y = gameData.getDisplayHeight();
        }

        // Update entity
        entity.setDx(dx);
        entity.setDy(dy);
        entity.setPosition(x + dx * dt, y + dy * dt);
    }

    @Override
    public Entity createBullet(Entity e, GameData gameData) {
        float dx = e.getDx();
        float dy = e.getDy();
        float x = e.getX();
        float y = e.getY();
        float radians = e.getRadians();
        float dt = gameData.getDelta();

        Entity bullet = new Bullet();
        bullet.setRadius(2);

        float by = (float) sin(radians) * e.getRadius() * bullet.getRadius();
        float bx = (float) cos(radians) * e.getRadius() * bullet.getRadius();
        bullet.setPosition(bx + x, by + y);

        bullet.setShapeX(new float[2]);
        bullet.setShapeY(new float[2]);
        bullet.setRadians(radians);
        bullet.setLife(1);

        bullet.setExpiration(5);

        return bullet;
    }

    private void setShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        float x = entity.getX();
        float y = entity.getY();
        float radians = entity.getRadians();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5));
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5));

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
