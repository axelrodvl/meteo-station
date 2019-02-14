package co.axelrod.rpi.meteo.bot.pi.sensor.meteo;

import co.axelrod.rpi.meteo.bot.pi.sensor.AbstractSensorData;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MeteoData extends AbstractSensorData {
    private double temperature;
    private double humidity;

    @Override
    public String toString() {
        return String.format("Температура: %.2f °C %n\nВлажность: %.2f%%RH %n\n", temperature, humidity);
    }
}
