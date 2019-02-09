package co.axelrod.rpi.meteo.bot.pi;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MeteoSensor {
    private I2CBus bus;
    private I2CDevice device;

    public MeteoSensor() throws IOException, I2CFactory.UnsupportedBusNumberException {
        // Create I2CBus
        bus = I2CFactory.getInstance(I2CBus.BUS_1);

        // Get I2C device, SHT31 I2C address is 0x44(68)
        device = bus.getDevice(0x44);
    }

    public String getTemperatureInCelsius() throws IOException, InterruptedException {
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
        //double fTemp = ((((data[0] & 0xFF) * 256) + (data[1] & 0xFF)) * 315.0) / 65535.0 - 49.0;
        double humidity = ((((data[3] & 0xFF) * 256) + (data[4] & 0xFF)) * 100.0) / 65535.0;

        return String.format("Температура: %.2f °C %n\nВлажность: %.2f%%RH %n\n", cTemp, humidity);
    }
}
