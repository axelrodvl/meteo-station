package co.axelrod.rpi.meteo.bot.pi.sensor.co2;

import co.axelrod.rpi.meteo.bot.metrics.Prometheus;
import co.axelrod.rpi.meteo.bot.pi.sensor.AbstractSensor;
import com.pi4j.io.serial.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CO2Sensor extends AbstractSensor<CO2Data> {
    private Serial serial;

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

    public CO2Sensor(Prometheus prometheus) throws Exception {
        super(prometheus);
    }

    @Override
    protected void registerSensor() throws IOException, InterruptedException {
        serial = SerialFactory.createInstance();

        serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                try {
                    if (event.length() >= 4 && (0xff & event.getBytes()[1]) == 0x86) {
                        int high = Integer.parseInt(event.getHexByteString().split(",")[2], 16);
                        int low = Integer.parseInt(event.getHexByteString().split(",")[3], 16);
                        latestResult = new CO2Data(high * 256 + low);
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

    @Override
    protected CO2Data getData() throws IOException, InterruptedException {
        serial.write(sensorCall);
        Thread.sleep(500);
        return latestResult;
    }

    @Override
    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    protected void sendResultToPrometheus() throws IOException, InterruptedException {
        prometheus.co2.set(getData().getLevel());
    }


}
