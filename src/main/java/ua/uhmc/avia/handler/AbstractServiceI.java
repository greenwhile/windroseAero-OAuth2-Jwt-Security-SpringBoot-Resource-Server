package ua.uhmc.avia.handler;

import org.springframework.core.io.Resource;
import ua.uhmc.avia.component.WindmapComponent;

import java.util.List;

public interface AbstractServiceI {
    byte[] getCurrentWindmapImageInBytes();
    LevelDependentImage getSelectedLevelDependentWindmap();
    LevelDependentImage getLevelDependentResource(Resource resource);
    ValidityDependentImage getSelectedValidityDependentWindmap();
    ValidityDependentImage getValidityDependentResource(Resource resource);
    Integer getLevelIndex();
    Integer getValidityIndex();
    List<String> getValidityDependentList();
    List<String> getLevelDependentList();
    Integer getValidityDependentItem(String imageName, List<String> validityDependentList);
    Integer getLevelDependentItem(String imageName, List<String>levelDependentList);
    WindmapComponent createWindmapComponent();
}
