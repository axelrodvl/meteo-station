package co.axelrod.rpi.weather.station.pi.sensor.co2;

import co.axelrod.rpi.weather.station.pi.sensor.AbstractSensorData;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CO2Data extends AbstractSensorData {
    private double level;

    @Override
    public String toString() {
        return "CO₂: " + level + " (" + interpretCO2Result() + ")";
    }

    private String interpretCO2Result() {
        if (level < 800) {
            return "нормально";
        }

        if(level < 1000) {
            return "приемлемо";
        }

        if(level < 1400) {
            return "необходимо проветрить";
        }

        return "срочно проветрить";
    }

}
