package co.axelrod.rpi.weather.station.pi.sensor.weather;

import co.axelrod.rpi.weather.station.pi.sensor.AbstractSensorData;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WeatherData extends AbstractSensorData {
    private double temperature;
    private double humidity;

    @Override
    public String toString() {
        return String.format("Температура: %.2f °C %n\nВлажность: %.2f%%RH %n\n", temperature, humidity);
    }
}
