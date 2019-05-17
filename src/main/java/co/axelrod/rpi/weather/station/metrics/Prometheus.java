package co.axelrod.rpi.weather.station.metrics;

import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Prometheus {
    public Prometheus() throws IOException {}

    private HTTPServer server = new HTTPServer(8779);

    public final Gauge temperature = Gauge.build()
            .name("temperature").help("Current temperature").register();

    public final Gauge humidity = Gauge.build()
            .name("humidity").help("Current humidity").register();

    public final Gauge co2 = Gauge.build()
            .name("co2").help("Current CO₂ level").register();
}
