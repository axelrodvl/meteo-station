package co.axelrod.rpi.meteo.bot.pi;

import com.pi4j.io.serial.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CO2Sensor {
    private final Serial serial;

    private static final byte[] sensorCall = new byte[]{
            (byte) 0xff,
            (byte) 0x01,
            (byte) 0x86,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x79
    };

    private int latestResult;

    public CO2Sensor() throws IOException, InterruptedException {
        serial = SerialFactory.createInstance();

        serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                try {
                    if (event.length() >= 4 && (0xff & event.getBytes()[1]) == 0x86) {
                        int high = Integer.parseInt(event.getHexByteString().split(",")[2], 16);
                        int low = Integer.parseInt(event.getHexByteString().split(",")[3], 16);
                        latestResult = high * 256 + low;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        SerialConfig config = new SerialConfig();

        config.device(SerialPort.getDefaultPort())
                .baud(Baud._9600)
                .dataBits(DataBits._8)
                .parity(Parity.NONE)
                .stopBits(StopBits._1)
                .flowControl(FlowControl.NONE);

        serial.open(config);
    }

    public String getCO2() throws IOException, InterruptedException {
        latestResult = 0;
        serial.write(sensorCall);
        Thread.sleep(100);

        return "CO₂: " + latestResult + " (" + interpretCO2Result(latestResult) + ")";
    }

    private static String interpretCO2Result(int result) {
        if (result < 800) {
            return "нормально";
        }

        if(result < 1000) {
            return "приемлемо";
        }

        if(result < 1400) {
            return "необходимо проветрить";
        }

        return "срочно проветрить";
    }
}
