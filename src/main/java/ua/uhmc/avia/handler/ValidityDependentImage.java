package ua.uhmc.avia.handler;

import java.util.Objects;

public class ValidityDependentImage implements Comparable<ValidityDependentImage> {

    private String territory;
    private String observation;
    private String validityCode;
    private Integer validity;
    private Integer level;

    public ValidityDependentImage(String territory, String observation, String validityCode, Integer validity, Integer level) {
        this.territory = territory;
        this.observation = observation;
        this.validityCode = validityCode;
        this.validity = validity;
        this.level = level;
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

    public String getValidityCode() {
        return validityCode;
    }

    public void setValidityCode(String validityCode) {
        this.validityCode = validityCode;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidityDependentImage that = (ValidityDependentImage) o;
        return Objects.equals(territory, that.territory) &&
                Objects.equals(observation, that.observation) &&
                Objects.equals(validityCode, that.validityCode) &&
                Objects.equals(validity, that.validity) &&
                Objects.equals(level, that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(territory, observation, validityCode, validity, level);
    }

    @Override
    public int compareTo(ValidityDependentImage other) {
        if(this.getTerritory().equals(other.getTerritory()) && this.getObservation().equals(other.getObservation())
                && this.getLevel().equals(other.getLevel())) {
            if (this.getValidity() == other.getValidity()) {
                return 0;
            }
            if (this.getValidity() > other.getValidity()) {
                return 1;
            }
            if (this.getValidity() < other.getValidity()) {
                return -1;
            }
        }
        return -2;
    }

    @Override
    public String toString() {
        return "ValidityDependentImage{" +
                "territory='" + territory + '\'' +
                ", observation='" + observation + '\'' +
                ", validityCode='" + validityCode + '\'' +
                ", validity=" + validity +
                ", level=" + level +
                '}';
    }

}
