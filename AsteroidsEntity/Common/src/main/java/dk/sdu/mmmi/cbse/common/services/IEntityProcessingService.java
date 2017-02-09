package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import java.util.Map;

public interface IEntityProcessingService {

    /**
     * TODO: Describe the contract using pre- and post-conditions.
     *
     * @param gameData
     * @param world
     * @param entity
     */
    void process(GameData gameData, Map<String, Entity> world, Entity entity);
}
