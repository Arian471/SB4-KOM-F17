package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import java.util.Map;

public interface IGamePluginService {

    void start(GameData gameData, Map<String, Entity> world);

    void stop(GameData gameData);
}
