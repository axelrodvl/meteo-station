package co.axelrod.rpi.weather.station.pi.sensor;

import co.axelrod.rpi.weather.station.metrics.Prometheus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractSensor<T extends AbstractSensorData> {
    protected Prometheus prometheus;

    protected T latestResult;

    public AbstractSensor(Prometheus prometheus) throws Exception {
        this.prometheus = prometheus;
        registerSensor();
        log.info(this.getClass().getSimpleName() + " registered successfully");
    }

    protected abstract void registerSensor() throws Exception;

    protected abstract T getData() throws Exception;

    public String getResult() {
        try {
            log.debug("Observing data for " + this.getClass().getSimpleName());
            return latestResult.toString();
        } catch (Exception ex) {
            log.debug("Unable to get sensor data for " + this.getClass().getSimpleName());
            return "Unable to get sensor data";
        }
    }

    protected abstract void sendResultToPrometheus() throws Exception;
}
