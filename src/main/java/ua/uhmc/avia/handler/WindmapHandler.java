package ua.uhmc.avia.handler;

import org.springframework.core.io.Resource;
import ua.uhmc.avia.component.WindmapComponent;
import ua.uhmc.avia.error.*;
import ua.uhmc.avia.model.Windmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WindmapHandler extends AbstractHandler implements AbstractHandlerI, AbstractServiceI {

    private Windmap windmap;
    private ResourceHandler resourceHandler;
    private List<String> levelDependentList;
    private List<String> validityDependentList;

    public WindmapHandler(Windmap windmap) {
        this.windmap = windmap;
        this.resourceHandler = new ResourceHandler();
    }

    public Windmap getWindmap() {
        return windmap;
    }

    public void setWindmap(Windmap windmap) {
        this.windmap = windmap;
    }

    @Override
    public String getWindmapImageFilename() {
        return "u_QW" + this.windmap.getT1() + this.windmap.getValidityCode() + this.windmap.getLevel() + "_" +
                this.windmap.getTerritory() + "_" + "H+" + this.windmap.getValidity() + "_" + "K=" +
                /*Calendar.getInstance().get(Calendar.DAY_OF_MONTH)*/ "09" + this.windmap.getObservation() + ".png";
    }

    @Override
    public String getTerritory() {
        return this.windmap.getTerritory();
    }

    @Override
    public String getTerritoryCode() {
        return this.windmap.getTerritoryCode();
    }

    @Override
    public String getT1() {
        return this.windmap.getT1();
    }

    @Override
    public String getObservation() {
        return this.windmap.getObservation();
    }

    @Override
    public String getLevel() {
        return this.windmap.getLevel();
    }

    @Override
    public String getValidity() {
        return this.windmap.getValidity();
    }

    @Override
    public String getValidityCode() {
        return this.windmap.getValidityCode();
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
        System.out.println("getLevelIndex FROM windmapHandler Class: " + index);
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
        System.out.println("getValidityIndex FROM windmapHandler Class: " + index);
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

    private boolean equalsValidityDependentImg(WindmapHandler currentImg, ResourceHandler arrayImg) {
        return currentImg.getTerritory().equals(arrayImg.getTerritory()) &&
                currentImg.getTerritoryCode().equals(arrayImg.getTerritoryCode()) &&
                currentImg.getT1().equals(arrayImg.getT1()) &&
                currentImg.getLevel().equals(arrayImg.getLevel()) &&
                currentImg.getObservation().equals(arrayImg.getObservation());
    }

    private boolean equalsLevelDependentImg(WindmapHandler currentImg, ResourceHandler arrayImg) {
        return currentImg.getTerritory().equals(arrayImg.getTerritory()) &&
                currentImg.getTerritoryCode().equals(arrayImg.getTerritoryCode()) &&
                currentImg.getT1().equals(arrayImg.getT1()) &&
                currentImg.getObservation().equals(arrayImg.getObservation()) &&
                currentImg.getValidity().equals(arrayImg.getValidity()) &&
                currentImg.getValidityCode().equals(arrayImg.getValidityCode());
    }

    @Override
    public Integer getValidityDependentItem(String imageName, List<String>validityDependentList) {
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
    public Integer getLevelDependentItem(String imageName, List<String>levelDependentList) {
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
        return "WindmapHandler{" +
                "windmap=" + windmap +
                '}';
    }

    //    private WindmapService service;
//    private WindmapComponent windmapComponent;
//
//    private String currentImageName;
//    private Integer validityIndex;
//    private Integer levelIndex;
//    private byte [] currentWindmapImage;
////    private List<Resource> imagesList;
//    private List<String> levelDependentList;
////    private List<String> levelDependentImagesList;
//    private List<String> validityDependentList;
////    private List<String> validityDependentImagesList;
//
//    public WindmapHandler(Windmap windmap) {
//        this.service = new WindmapService(windmap);
//        this.currentImageName = this.service.getSelectedWindmapImageName();
//        this.levelIndex = this.service.getLevelIndex();
//        this.validityIndex = this.service.getValidityIndex();
//        this.currentWindmapImage = this.service.getCurrentWindmapImageInBytes();
////        this.imagesList = this.service.getImagesList();
//        this.levelDependentList = new ArrayList<String>();
//        this.levelDependentList = this.service.getLevelDependentList();
////        this.levelDependentImagesList = new ArrayList<String>();
////        this.levelDependentImagesList = this.service.getLevelDependentImagesList();
//        this.validityDependentList = new ArrayList<String>();
//        this.validityDependentList = this.service.getValidityDependentList();
////        this.validityDependentImagesList = new ArrayList<String>();
////        this.validityDependentImagesList = this.service.getValidityDependentImagesList();
//        this.windmapComponent = new WindmapComponent(this.currentImageName, this.validityIndex, this.levelIndex, this.currentWindmapImage);
//    }
//
//    public WindmapHandler(String windmapImageFilename) {
//        this.service = new WindmapService(windmapImageFilename);
//        this.currentImageName = this.service.getSelectedWindmapImageName();
//        this.levelIndex = this.service.getLevelIndex();
//        this.validityIndex = this.service.getValidityIndex();
//        this.currentWindmapImage = this.service.getCurrentWindmapImageInBytes();
////        this.imagesList = this.service.getImagesList();
//        this.levelDependentList = new ArrayList<String>();
//        this.levelDependentList = this.service.getLevelDependentList();
////        this.levelDependentImagesList = new ArrayList<String>();
////        this.levelDependentImagesList = this.service.getLevelDependentImagesList();
//        this.validityDependentList = new ArrayList<String>();
//        this.validityDependentList = this.service.getValidityDependentList();
////        this.validityDependentImagesList = new ArrayList<String>();
////        this.validityDependentImagesList = this.service.getValidityDependentImagesList();
//        this.windmapComponent = new WindmapComponent(this.currentImageName, this.validityIndex, this.levelIndex, this.currentWindmapImage);
//    }
//
//    public List<String> getLevelDependentList() {
//        return levelDependentList;
//    }
//
//    public void setLevelDependentList(List<String> levelDependentList) {
//        this.levelDependentList = levelDependentList;
//    }
//
//    public List<String> getValidityDependentList() {
//        return validityDependentList;
//    }
//
//    public void setValidityDependentList(List<String> validityDependentList) {
//        this.validityDependentList = validityDependentList;
//    }
//
//    public WindmapService getService() {
//        return service;
//    }
//
//    public void setService(WindmapService service) {
//        this.service = service;
//    }
//
//    public String getCurrentImageName() {
//        return currentImageName;
//    }
//
//    public void setCurrentImageName(String currentImageName) {
//        this.currentImageName = currentImageName;
//    }
//
//    public Integer getValidityIndex() {
//        return validityIndex;
//    }
//
//    public void setValidityIndex(Integer validityIndex) {
//        this.validityIndex = validityIndex;
//    }
//
//    public Integer getLevelIndex() {
//        return levelIndex;
//    }
//
//    public void setLevelIndex(Integer levelIndex) {
//        this.levelIndex = levelIndex;
//    }
//
////    public List<Resource> getImagesList() {
////        return imagesList;
////    }
////
////    public void setImagesList(List<Resource> imagesList) {
////        this.imagesList = imagesList;
////    }
//
////    public List<String> getLevelDependentImagesList() {
////        return levelDependentImagesList;
////    }
////
////    public void setLevelDependentImagesList(List<String> levelDependentImagesList) {
////        this.levelDependentImagesList = levelDependentImagesList;
////    }
////
////    public List<String> getValidityDependentImagesList() {
////        return validityDependentImagesList;
////    }
////
////    public void setValidityDependentImagesList(List<String> validityDependentImagesList) {
////        this.validityDependentImagesList = validityDependentImagesList;
////    }
//
//    public WindmapComponent getWindmapComponent() {
//        return windmapComponent;
//    }
//
//    public void setWindmapComponent(WindmapComponent windmapComponent) {
//        this.windmapComponent = windmapComponent;
//    }
//
//    private Integer getValidityDependentItem(String imageName, List<String>validityDependentList) {
//        for(var i = 0; i < validityDependentList.size(); i++){
//            if(validityDependentList.get(i).equals(imageName)){
//                return i;
//            }
//        }
//        return null;
//    }
//
//    private Integer getLevelDependentItem(String imageName, List<String>levelDependentList) {
//        for(var i = 0; i < levelDependentList.size(); i++){
//            if(levelDependentList.get(i).equals(imageName)){
//                return i;
//            }
//        }
//        return null;
//    }
//
//    public String getUpImage() {
////        let levelDependentList: Array<string> = this.getLevelDependentList();
//        if(this.windmapComponent.getLevelIndex() == 0){
//            System.out.println("last img: " + this.windmapComponent.getCurrentWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
//        }
//        if(this.windmapComponent.getLevelIndex() > 0){
//            this.windmapComponent.setLevelIndex(this.windmapComponent.getLevelIndex()-1);
//            this.windmapComponent.setCurrentWindmapImageFilename(this.levelDependentList.get(this.windmapComponent.getLevelIndex()));
////            let validityDependentList: Array<string> = this.getValidityDependentList();
//            this.windmapComponent.setValidityIndex(this.getValidityDependentItem(this.currentImageName, this.validityDependentList));
//            System.out.println("curr: " + this.windmapComponent.getCurrentWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
//        }
//        return this.windmapComponent.getCurrentWindmapImageFilename();
////        this.getImage(this.currentImageName);
//    }
//
//    public String getDownImage() {
////        let levelDependentList: Array<string> = this.getLevelDependentList();
//        if(this.windmapComponent.getLevelIndex() == this.levelDependentList.size()-1){
//            System.out.println("last img: " + this.windmapComponent.getCurrentWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
//        }
//        if(this.windmapComponent.getLevelIndex() < this.levelDependentList.size()-1){
//            this.windmapComponent.setLevelIndex(this.windmapComponent.getLevelIndex()+1);
//            this.windmapComponent.setCurrentWindmapImageFilename(this.levelDependentList.get(this.windmapComponent.getLevelIndex()));
////            let validityDependentList: Array<string> = this.getValidityDependentList();
//            this.windmapComponent.setValidityIndex(this.getValidityDependentItem(this.windmapComponent.getCurrentWindmapImageFilename(), this.validityDependentList));
//            System.out.println("last img: " + this.windmapComponent.getCurrentWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
//        }
//        return this.windmapComponent.getCurrentWindmapImageFilename();
////        this.getImage(this.obj.currentImageName);
//    }
//
//    public String getLeftImage() {
////        let validityDependentList: Array<string> = this.getValidityDependentList();
//        if(this.windmapComponent.getValidityIndex() == 0){
//            System.out.println("last img: " + this.windmapComponent.getCurrentWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex());
//        }
//        if(this.windmapComponent.getValidityIndex() > 0){
//            this.windmapComponent.setValidityIndex(this.windmapComponent.getValidityIndex()-1);
//            this.windmapComponent.setCurrentWindmapImageFilename(this.validityDependentList.get(this.windmapComponent.getValidityIndex()));
////            let levelDependentList: Array<string> = this.getLevelDependentList();
//            this.windmapComponent.setLevelIndex(this.getLevelDependentItem(this.windmapComponent.getCurrentWindmapImageFilename(), this.levelDependentList));
//            System.out.println("last img: " + this.windmapComponent.getCurrentWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
//        }
//        return this.windmapComponent.getCurrentWindmapImageFilename();
////        this.getImage(this.obj.currentImageName);
//    }
//
//    public String getRightImage() {
////        let validityDependentList: Array<string> = this.getValidityDependentList();
//        if(this.windmapComponent.getValidityIndex() == this.validityDependentList.size()-1){
//            System.out.println("last img: " + this.windmapComponent.getCurrentWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex());
//        }
//        if(this.windmapComponent.getValidityIndex() < this.validityDependentList.size()-1){
//            this.windmapComponent.setValidityIndex(this.windmapComponent.getValidityIndex()+1);
//            this.windmapComponent.setCurrentWindmapImageFilename(this.validityDependentList.get(this.windmapComponent.getValidityIndex()));
////            let levelDependentList: Array<string> = this.getLevelDependentList();
//            this.windmapComponent.setLevelIndex(this.getLevelDependentItem(this.windmapComponent.getCurrentWindmapImageFilename(), this.levelDependentList));
//            System.out.println("last img: " + this.windmapComponent.getCurrentWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
//        }
//        return this.windmapComponent.getCurrentWindmapImageFilename();
////        this.getImage(this.obj.currentImageName);
//    }
//
//    public byte[] getCurrentWindmapImage() {
//        return currentWindmapImage;
//    }
//
//    public void setCurrentWindmapImage(byte[] currentWindmapImage) {
//        this.currentWindmapImage = currentWindmapImage;
//    }
}
