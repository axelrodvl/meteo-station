package co.axelrod.rpi.weather.station.pi.sensor.weather;

import co.axelrod.rpi.weather.station.metrics.Prometheus;
import co.axelrod.rpi.weather.station.pi.sensor.AbstractSensor;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class WeatherSensor extends AbstractSensor<WeatherData> {
    private I2CDevice device;

    public WeatherSensor(Prometheus prometheus) throws Exception {
        super(prometheus);
    }

    @Override
    protected void registerSensor() throws Exception {
        // Create I2CBus
        I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);

        // Get I2C device, SHT31 I2C address is 0x44(68)
        device = bus.getDevice(0x44);
    }

    @Override
    protected WeatherData getData() throws IOException, InterruptedException {
        // Send high repeatability measurement command
        // Command msb, command lsb
        byte[] config = new byte[2];
        config[0] = (byte) 0x2C;
        config[1] = (byte) 0x06;
        device.write(config, 0, 2);

        Thread.sleep(500);

        // Read 6 bytes of data
        // temp msb, temp lsb, temp CRC, humidity msb, humidity lsb, humidity CRC
        byte[] data = new byte[6];
        device.read(data, 0, 6);

        //Convert the data
        double cTemp = ((((data[0] & 0xFF) * 256) + (data[1] & 0xFF)) * 175.0) / 65535.0 - 45.0;
        double humidity = ((((data[3] & 0xFF) * 256) + (data[4] & 0xFF)) * 100.0) / 65535.0;

        latestResult = new WeatherData(cTemp, humidity);

        log.debug("Got data for WeatherSensor: " + latestResult);

        return latestResult;
    }

    @Override
    @Scheduled(fixedRate = 5000, initialDelay = 8000)
    protected void sendResultToPrometheus() throws IOException, InterruptedException {
        WeatherData weatherData = getData();
        prometheus.temperature.set(weatherData.getTemperature());
        prometheus.humidity.set(weatherData.getHumidity());
    }
}
