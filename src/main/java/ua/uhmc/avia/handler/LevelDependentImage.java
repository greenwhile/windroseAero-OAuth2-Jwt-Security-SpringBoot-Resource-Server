package ua.uhmc.avia.handler;


import java.util.Objects;

public class LevelDependentImage implements Comparable<LevelDependentImage> {

    private String territory;
    private String observation;
    private Integer level;
    private Integer validity;

    public LevelDependentImage(String territory, String observation, Integer level, Integer validity) {
        this.territory = territory;
        this.observation = observation;
        this.level = level;
        this.validity = validity;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Integer getLevel() { return level; }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelDependentImage that = (LevelDependentImage) o;
        return Objects.equals(territory, that.territory) &&
                Objects.equals(observation, that.observation) &&
                Objects.equals(level, that.level) &&
                Objects.equals(validity, that.validity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(territory, observation, level, validity);
    }


    @Override
    public int compareTo(LevelDependentImage other) {
        if(this.getTerritory().equals(other.getTerritory()) && this.getObservation().equals(other.getObservation())
                && this.getValidity() == other.getValidity()){
            if (this.getLevel() == other.getLevel()) {
                return 0;
            }
            if (this.getLevel() > other.getLevel()) {
                return 1;
            }
            if (this.getLevel() < other.getLevel()) {
                return -1;
            }
        }
        return -2;
    }

    @Override
    public String toString() {
        return "LevelDependentImage{" +
                "territory='" + territory + '\'' +
                ", observation='" + observation + '\'' +
                ", level=" + level +
                ", validity=" + validity +
                '}';
    }
}
