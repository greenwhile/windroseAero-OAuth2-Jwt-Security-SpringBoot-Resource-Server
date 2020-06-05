package ua.uhmc.avia.model;

import ua.uhmc.avia.error.WindmapDataFormatException;

import javax.validation.constraints.NotNull;

public class Windmap {

    @NotNull
    private String territory;
    private String territoryCode;
    private String t1;
    @NotNull
    private String observation;
    @NotNull
    private String level;
    @NotNull
    private String validity;
    private String validityCode;

    public Windmap() {
    }

    public Windmap(@NotNull String territory, @NotNull String observation, @NotNull String level, @NotNull String validity) {
        if(!territory.isEmpty() && territory!=null && !territory.contains(" ") &&
                !observation.isEmpty() && observation!=null && !observation.contains(" ") &&
                !level.isEmpty() && level!=null && !level.contains(" ") &&
                !validity.isEmpty() && validity!=null && !validity.contains(" ")){
            this.territory = territory;
            this.observation = observation;
            this.level = level;
            this.validity = validity;
        }
        else
        if(!territory.isEmpty() && territory!=null && territory.contains(" ") &&
                !observation.isEmpty() && observation!=null && observation.contains(" ") &&
                !level.isEmpty() && level!=null && level.contains(" ") &&
                !validity.isEmpty() && validity!=null && validity.contains(" ")){

            String [] area_elements = territory.split(" ");
            String territoryCode = area_elements[1];
            String parsed_territory = getTerritoryByCode(territoryCode);
            String t1 = getT1ByTerritoryCode(territoryCode);
            String [] grib2date_elements = observation.split(" ");
            String parsed_observation = grib2date_elements[0];
            String [] level_elements = level.split(" ");
            String parsed_level = level_elements[0].substring(0, 2);
            String [] validity_elements = validity.replace("( ", "(+").split(" "); // getValidityByCode(windmapViewModelModel.getValidity());
            String validityCode = validity_elements[0];
            String parsed_validity = getValidityByCode(validityCode);

            this.territory = parsed_territory;
            this.territoryCode = territoryCode;
            this.t1 = t1;
            this.observation = parsed_observation;
            this.level = parsed_level;
            this.validity = parsed_validity;
            this.validityCode = validityCode;
        }
        else{
            throw new WindmapDataFormatException("Something wrong with windmap data");
        }
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getTerritoryCode() {
        return territoryCode;
    }

    public void setTerritoryCode(String territoryCode) {
        this.territoryCode = territoryCode;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getValidityCode() {
        return validityCode;
    }

    public void setValidityCode(String validityCode) {
        this.validityCode = validityCode;
    }

    private String getT1ByTerritoryCode(String territory_code){
        String territory = null;
        switch(territory_code){
            case "H" :{
                territory = "A";
                break;
            }
            case "G" :{
                territory = "C";
                break;
            }
            case "EUR" :{
                territory = "D";
                break;
            }
            case "C" :{
                territory = "R";
                break;
            }
            default:{
                System.out.println("There is no such territory code");
            }
        }
        return territory;
    }

    private String getTerritoryByCode(String area_code){
        String territory = null;
        switch(area_code){
            case "H" :{
                territory = "NorthAtl";
                break;
            }
            case "G" :{
                territory = "EurAs";
                break;
            }
            case "EUR" :{
                territory = "Eur";
                break;
            }
            case "C" :{
                territory = "EurAfr";
                break;
            }
            default:{
                System.out.println("There is no such territory");
            }
        }
        return territory;
    }

    private String getValidityByCode(String validity_code){
        String result = null;
        switch(validity_code){
            case "C": {
                result = "06";
                break;
            }
            case "D": {
                result = "09";
                break;
            }
            case "E": {
                result = "12";
                break;
            }
            case "F": {
                result = "15";
                break;
            }
            case "G": {
                result = "18";
                break;
            }
            case "H": {
                result = "21";
                break;
            }
            case "I": {
                result = "24";
                break;
            }
            default: {
                System.out.println("There is no such validity time");
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Windmap{" +
                "territory='" + territory + '\'' +
                ", territoryCode='" + territoryCode + '\'' +
                ", t1='" + t1 + '\'' +
                ", observation='" + observation + '\'' +
                ", level='" + level + '\'' +
                ", validity='" + validity + '\'' +
                ", validityCode='" + validityCode + '\'' +
                '}';
    }
}
