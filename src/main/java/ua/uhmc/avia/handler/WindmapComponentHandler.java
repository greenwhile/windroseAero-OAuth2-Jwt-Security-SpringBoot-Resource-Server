package ua.uhmc.avia.handler;

import ua.uhmc.avia.component.WindmapComponent;
import ua.uhmc.avia.model.Windmap;

import java.io.FileOutputStream;
import java.io.IOException;

public class WindmapComponentHandler {

    private WindmapComponent windmapComponent;
    private AbstractServiceI service;

    public WindmapComponentHandler(Windmap windmap) {
        this.service = new WindmapHandler(windmap);
        this.windmapComponent = this.service.createWindmapComponent();
    }

    public WindmapComponentHandler(String windmapImageFilename) {
        this.service = new FileHandler(windmapImageFilename);
        this.windmapComponent = this.service.createWindmapComponent();
    }

    public WindmapComponent getWindmapComponent() {
        return windmapComponent;
    }

    public void setWindmapComponent(WindmapComponent windmapComponent) {
        this.windmapComponent = windmapComponent;
    }

    public String getUpImage() {
        Integer validityIndex = this.windmapComponent.getValidityIndex();
        System.out.println("\nSTART getUpImage WMComponent Handler");
        System.out.println("start: " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());

        if(this.windmapComponent.getLevelIndex() == 0){
            System.out.println("last img: " + this.windmapComponent.getWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
        }
        if(this.windmapComponent.getLevelIndex() > 0){
            this.windmapComponent.setLevelIndex(this.windmapComponent.getLevelIndex()-1);
            this.windmapComponent.setWindmapImageFilename(this.service.getLevelDependentList().get(this.windmapComponent.getLevelIndex()));
            this.windmapComponent.setValidityIndex(validityIndex/*this.service.getValidityDependentItem(this.windmapComponent.getWindmapImageFilename(), this.service.getValidityDependentList())*/);
            System.out.println("curr: " + this.windmapComponent.getWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
        }
        System.out.println("end: " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
        System.out.println("END getUpImage WMComponent Handler");
        return this.windmapComponent.getWindmapImageFilename();
    }

    public String getDownImage() {
        Integer validityIndex = this.windmapComponent.getValidityIndex();
        System.out.println("\nSTART getDownImage WMComponent Handler");
        System.out.println("start: " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());

        if(this.windmapComponent.getLevelIndex() == this.service.getLevelDependentList().size()-1){
            System.out.println("last img: " + this.windmapComponent.getWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
        }
        if(this.windmapComponent.getLevelIndex() < this.service.getLevelDependentList().size()-1){
            this.windmapComponent.setLevelIndex(this.windmapComponent.getLevelIndex()+1);
            this.windmapComponent.setWindmapImageFilename(this.service.getLevelDependentList().get(this.windmapComponent.getLevelIndex()));
            this.windmapComponent.setValidityIndex(validityIndex/*this.service.getValidityDependentItem(this.windmapComponent.getWindmapImageFilename(), this.service.getValidityDependentList())*/);
            System.out.println("curr img: " + this.windmapComponent.getWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
        }
        System.out.println("end: " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
        System.out.println("END getDownImage WMComponent Handler");

        return this.windmapComponent.getWindmapImageFilename();
    }

    public String getLeftImage() {
        Integer levelIndex = this.windmapComponent.getLevelIndex();
        System.out.println("\nSTART getLeftImage WMComponent Handler");
        System.out.println("start: " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());

        if(this.windmapComponent.getValidityIndex() == 0){
            System.out.println("last img: " + this.windmapComponent.getWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex());
        }
        if(this.windmapComponent.getValidityIndex() > 0){
            this.windmapComponent.setValidityIndex(this.windmapComponent.getValidityIndex()-1);
            this.windmapComponent.setWindmapImageFilename(this.service.getValidityDependentList().get(this.windmapComponent.getValidityIndex()));
            this.windmapComponent.setLevelIndex(levelIndex/*this.service.getLevelDependentItem(this.windmapComponent.getWindmapImageFilename(), this.service.getLevelDependentList())*/);
            System.out.println("curr img: " + this.windmapComponent.getWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
        }
        System.out.println("end: " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
        System.out.println("END getLeftImage WMComponent Handler");

        return this.windmapComponent.getWindmapImageFilename();
    }

    public String getRightImage() {
        Integer levelIndex = this.windmapComponent.getLevelIndex();
        System.out.println("\nSTART getRightImage WMComponent Handler");
        System.out.println("start: " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());

        if(this.windmapComponent.getValidityIndex() == this.service.getValidityDependentList().size()-1){
            System.out.println("last img: " + this.windmapComponent.getWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex());
        }
        if(this.windmapComponent.getValidityIndex() < this.service.getValidityDependentList().size()-1){
            this.windmapComponent.setValidityIndex(this.windmapComponent.getValidityIndex()+1);
            this.windmapComponent.setWindmapImageFilename(this.service.getValidityDependentList().get(this.windmapComponent.getValidityIndex()));
            this.windmapComponent.setLevelIndex(levelIndex/*this.service.getLevelDependentItem(this.windmapComponent.getWindmapImageFilename(), this.service.getLevelDependentList())*/);
            System.out.println("curr img: " + this.windmapComponent.getWindmapImageFilename() + " " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
        }
        System.out.println("end: " + this.windmapComponent.getValidityIndex() + " " + this.windmapComponent.getLevelIndex());
        System.out.println("END getRightImage WMComponent Handler");

        return this.windmapComponent.getWindmapImageFilename();
    }

    public boolean writeToFile(/*byte[] array, String windmapImageFilename*/) {
        try {
            String path = "C:/Users/tatka_000/Desktop/curr_data/" + this.windmapComponent.getWindmapImageFilename();
            FileOutputStream stream = new FileOutputStream(path);
            stream.write(this.windmapComponent.getCurrentWindmapImage());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
