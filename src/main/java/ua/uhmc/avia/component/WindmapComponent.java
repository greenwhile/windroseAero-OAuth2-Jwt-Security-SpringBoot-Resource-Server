package ua.uhmc.avia.component;

import ua.uhmc.avia.handler.FileHandler;
import ua.uhmc.avia.model.Windmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WindmapComponent {

    private List<String> areas = new ArrayList<>(Arrays.asList("Area H (NorthAtl QWA*)", "Area G (EurAs QWC*)", "Area EUR (Eur QWD*)", "Area C (EurAfr QWR*)", ""));
    private List<String> grib2_dates = new ArrayList<>(Arrays.asList("00 UTC", "06 UTC", "12 UTC", "18 UTC", ""));
    private List<String> levels = new ArrayList<>(Arrays.asList("100hPa FL530", "125hPa FL480", "150hPa FL450", "175hPa FL410", "200hPa FL390", "225hPa FL360", "250hPa FL340", "275hPa FL320", "300hPa FL300", "350hPa FL270", "400hPa FL240", "450hPa FL210", "500hPa FL180", "600hPa FL140", "700hPa FL100", "750hPa FL080", "850hPa FL050", ""));
    private List<String> validities = new ArrayList<>(Arrays.asList("C (+6H) timeUTC", "D (+9H) timeUTC", "E (+12H) timeUTC", "F (+15H) timeUTC", "G (+18H) timeUTC", "H (+21H) timeUTC", "I (+24H) timeUTC", ""));

    private String windmapImageFilename;
    private Integer validityIndex;
    private Integer levelIndex;
    private byte [] currentWindmapImage;

    public WindmapComponent(){}

    public WindmapComponent(String windmapImageFilename, Integer validityIndex, Integer levelIndex, byte[] currentWindmapImage) {
        this.windmapImageFilename = windmapImageFilename;
//        if(validityIndex == null){
//            this.validityIndex = 0;
//        }
//        else{
            this.validityIndex = validityIndex;
//        }
//        if(levelIndex == null){
//            this.levelIndex = 0;
//        }
//        else{
            this.levelIndex = levelIndex;
//        }
        this.currentWindmapImage = currentWindmapImage;
        System.out.println("Contructor FROM windmapComponent Class: " + this.validityIndex + " " + this.levelIndex);
    }

    public String getWindmapImageFilename() {
        return windmapImageFilename;
    }

    public void setWindmapImageFilename(String windmapImageFilename) {
        this.windmapImageFilename = windmapImageFilename;
        System.out.println("setWindmapImageFilename FROM windmapComponent Class: " + this.windmapImageFilename);
    }

    public Integer getValidityIndex() {
        return validityIndex;
    }

    public void setValidityIndex(Integer validityIndex) {
//        if(validityIndex == null){
//            this.validityIndex = 0;
//        }
//        else{
            this.validityIndex = validityIndex;
//        }
        System.out.println("setValidityIndex FROM windmapComponent Class: " + this.validityIndex);
    }

    public Integer getLevelIndex() {
        return levelIndex;
    }

    public void setLevelIndex(Integer levelIndex) {
//        if(levelIndex == null){
//            this.levelIndex = 0;
//        }
//        else{
            this.levelIndex = levelIndex;
//        }
        System.out.println("setLevelIndex FROM windmapComponent Class: " + this.levelIndex);
    }

    public byte[] getCurrentWindmapImage() {
        return currentWindmapImage;
    }

    public void setCurrentWindmapImage(byte[] currentWindmapImage) {
        this.currentWindmapImage = currentWindmapImage;
    }

    @Override
    public String toString() {
        return "WindmapComponent{" +
                "windmapImageFilename='" + windmapImageFilename + '\'' +
                ", validityIndex=" + validityIndex +
                ", levelIndex=" + levelIndex +
                ", currentWindmapImage=" + Arrays.toString(currentWindmapImage) +
                '}';
    }
}
