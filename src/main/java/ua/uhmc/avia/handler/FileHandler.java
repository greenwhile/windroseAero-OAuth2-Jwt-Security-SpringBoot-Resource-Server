package ua.uhmc.avia.handler;

import org.springframework.core.io.Resource;
import ua.uhmc.avia.component.WindmapComponent;
import ua.uhmc.avia.error.*;
import ua.uhmc.avia.model.Windmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler extends AbstractHandler implements AbstractHandlerI, AbstractServiceI {

    private String windmapImageFilename;
    private ResourceHandler resourceHandler;
    private List<String> levelDependentList;
    private List<String> validityDependentList;

    public FileHandler(String windmapImageFilename) {
        this.windmapImageFilename = windmapImageFilename;
        this.resourceHandler = new ResourceHandler();
    }

    public Windmap getWindmap(){
        return new Windmap(this.getTerritory(), this.getObservation(), this.getLevel(), this.getValidity());
    }

    public void setWindmapImageFilename(String windmapImageFilename) {
        this.windmapImageFilename = windmapImageFilename;
    }

    @Override
    public String getWindmapImageFilename() {
        return this.windmapImageFilename;
    }

    @Override
    public String getTerritory() {
        String territoryFileName = this.windmapImageFilename.substring(9, this.windmapImageFilename.length()-1);
        return territoryFileName.substring(0, territoryFileName.indexOf("_"));
    }

    @Override
    public String getTerritoryCode() {
        return this.windmapImageFilename.substring(4,5);
    }

    @Override
    public String getT1() {
        return this.windmapImageFilename.substring(4, 5);
    }

    @Override
    public String getObservation() {
        return this.windmapImageFilename.substring(this.windmapImageFilename.length()-6, this.windmapImageFilename.length()-4);
    }

    @Override
    public String getLevel() {
        return this.windmapImageFilename.substring(6, 8);
    }

    @Override
    public String getValidity() {
        return this.windmapImageFilename.substring(this.windmapImageFilename.length()-13, this.windmapImageFilename.length()-11);
    }

    @Override
    public String getValidityCode() {
        return this.windmapImageFilename.substring(5,6);
    }

    @Override
    public LevelDependentImage getSelectedLevelDependentWindmap(){
        if(this.getWindmapImageFilename() == null || this.getWindmapImageFilename().isEmpty()){
            throw new WindmapImageFilenameFormatException("Wrong windmap image filename format");
        }
        else if(this.getWindmap() == null){
            throw new WindmapDataFormatException("Wrong windmap data format");
        }
        else{
            return new LevelDependentImage(
                    this.getTerritory(),
                    this.getObservation(),
                    Integer.valueOf(this.getLevel()),
                    Integer.valueOf(this.getValidity()));
        }
    }

    @Override
    public LevelDependentImage getLevelDependentResource(Resource resource){
        this.resourceHandler.setResource(resource);
        return new LevelDependentImage(
                this.resourceHandler.getTerritory(),
                this.resourceHandler.getObservation(),
                Integer.valueOf(this.resourceHandler.getLevel()),
                Integer.valueOf(this.resourceHandler.getValidity()));
    }

    @Override
    public ValidityDependentImage getSelectedValidityDependentWindmap(){
        if(this.getWindmapImageFilename() == null || this.getWindmapImageFilename().isEmpty()){
            throw new WindmapImageFilenameFormatException("Wrong windmap image filename format");
        }
        else if(this.getWindmap() == null){
            throw new WindmapDataFormatException("Wrong windmap data format");
        }
        else{
            return new ValidityDependentImage(
                    this.getTerritory(),
                    this.getObservation(),
                    this.getValidityCode(),
                    Integer.valueOf(this.getValidity()),
                    Integer.valueOf(this.getLevel()));
        }
    }

    @Override
    public ValidityDependentImage getValidityDependentResource(Resource resource){
        this.resourceHandler.setResource(resource);
        return new ValidityDependentImage(
                this.resourceHandler.getTerritory(),
                this.resourceHandler.getObservation(),
                this.resourceHandler.getValidityCode(),
                Integer.valueOf(this.resourceHandler.getValidity()),
                Integer.valueOf(this.resourceHandler.getLevel()));
    }

    @Override
    public Integer getLevelIndex(){
        Integer index = null;
        if(this.getWindmapImageFilename() == null){
            throw new WindmapImageFilenameNullException("windmapImageFilename is not set");
        }
        else if(!isWindmapPresent(this.getWindmapImageFilename(), this.getLevelDependentList())){
            throw new WindmapImageFilenameNullException("windmapImageFile is not present in the levelDependentList");
        }
        else if(this.getLevelDependentList().isEmpty()){
            throw new ValidityDependentListIsEmpty("levelDependentList is empty");
        }
        else {
            for (int i = 0; i < this.getLevelDependentList().size(); i++) {
                if(this.getWindmapImageFilename().equals(this.getLevelDependentList().get(i))){
                    index = i;
                }
            }
        }
        System.out.println("getLevelIndex FROM fileHandler Class: " + index);
        return index;
    }

    @Override
    public Integer getValidityIndex(){
        Integer index = null;
        if(this.getWindmapImageFilename() == null){
            throw new WindmapImageFilenameNullException("windmapImageFilename is not set");
        }
        else if(!isWindmapPresent(this.getWindmapImageFilename(), this.getValidityDependentList())){
            throw new WindmapImageFilenameNullException("windmapImageFile is not present in the validityDependentList");
        }
        else if(this.getValidityDependentList().isEmpty()){
            throw new ValidityDependentListIsEmpty("validityDependentList is empty");
        }
        else {
            for (int i = 0; i < this.getValidityDependentList().size(); i++) {
                if(this.getWindmapImageFilename().equals(this.getValidityDependentList().get(i))){
                    index = i;
                }
            }
        }
        System.out.println("getValidityIndex FROM fileHandler Class: " + index);
        return index;
    }

    @Override
    public List<String> getValidityDependentList() {
        this.validityDependentList = new ArrayList<String>();
        try{
            for(var i = 0; i < this.getImagesList().size(); i++){
                resourceHandler.setResource(this.getImagesList().get(i));
                if(this.equalsValidityDependentImg(this, this.resourceHandler)){
                    validityDependentList.add(this.getImagesList().get(i).getFile().getName());
                }
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return validityDependentList;
    }

    @Override
    public List<String> getLevelDependentList() {
        this.levelDependentList = new ArrayList<String>();
        try{
            for(var i = 0; i < this.getImagesList().size(); i++){
                this.resourceHandler.setResource(this.getImagesList().get(i));
                if(this.equalsLevelDependentImg(this, this.resourceHandler)){
                    levelDependentList.add(this.getImagesList().get(i).getFile().getName());
                }
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return levelDependentList;
    }

    private boolean equalsValidityDependentImg(FileHandler currentImg, ResourceHandler arrayImg) {
        return currentImg.getTerritory().equals(arrayImg.getTerritory()) &&
                currentImg.getTerritoryCode().equals(arrayImg.getTerritoryCode()) &&
                currentImg.getT1().equals(arrayImg.getT1()) &&
                currentImg.getLevel().equals(arrayImg.getLevel()) &&
                currentImg.getObservation().equals(arrayImg.getObservation());
    }

    private boolean equalsLevelDependentImg(FileHandler currentImg, ResourceHandler arrayImg) {
        return currentImg.getTerritory().equals(arrayImg.getTerritory()) &&
                currentImg.getTerritoryCode().equals(arrayImg.getTerritoryCode()) &&
                currentImg.getT1().equals(arrayImg.getT1()) &&
                currentImg.getObservation().equals(arrayImg.getObservation()) &&
                currentImg.getValidity().equals(arrayImg.getValidity()) &&
                currentImg.getValidityCode().equals(arrayImg.getValidityCode());
    }

    @Override
    public byte[] getCurrentWindmapImageInBytes() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            for(Resource resource : ResourceHandler.resources){
                if(resource.getFile().getName().equals(this.getWindmapImageFilename())){
                    byteArrayOutputStream = getImageBytes(resource.getFile());
                }
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public Integer getValidityDependentItem(String imageName, List<String> validityDependentList) {
        if(imageName.isEmpty() || imageName == null){
            throw new WindmapImageFilenameNullException("windmap image filename is null");
        }
//        else if(!isWindmapPresent(imageName, validityDependentList)){
//            throw new WindmapImageFilenameIsNotPresent("windmap image file is not present in the validityDependentList");
//        }
        else if(levelDependentList.isEmpty()){
            throw new ValidityDependentListIsEmpty("validityDependentList is empty");
        }
        else{
            for (int i = 0; i < validityDependentList.size(); i++) {
                if (validityDependentList.get(i).equals(imageName)) {
                    return i;
                }
            }
        }
        return null;
    }

    @Override
    public Integer getLevelDependentItem(String imageName, List<String> levelDependentList) {
        if(imageName.isEmpty() || imageName == null){
            throw new WindmapImageFilenameNullException("windmap image filename is null");
        }
//        else if(!isWindmapPresent(imageName, levelDependentList)){
//            throw new WindmapImageFilenameIsNotPresent("windmap image file is not present in the levelDependentList");
//        }
        else if(levelDependentList.isEmpty()){
            throw new LevelDependentListIsEmpty("levelDependentList is empty");
        }
        else{
            for (int i = 0; i < levelDependentList.size(); i++) {
                if (levelDependentList.get(i).equals(imageName)) {
                    return i;
                }
            }
        }
        return null;
    }

    @Override
    public WindmapComponent createWindmapComponent(){
        if(this.getValidityIndex() == null){
            throw new ValidityIndexException("FROM WindmapHandler Class: validity index is null");
        }
        else if(this.getLevelIndex() == null){
            throw new LevelIndexException("FROM WindmapHandler Class: level index is null");
        }
        else{
            return new WindmapComponent(this.getWindmapImageFilename(), this.getValidityIndex(), this.getLevelIndex(), this.getCurrentWindmapImageInBytes());
        }
    }

    @Override
    public String toString() {
        return "FileHandler{" +
                "windmapImageFilename='" + windmapImageFilename + '\'' +
                '}';
    }
}
