package co.axelrod.rpi.meteo.bot.pi.sensor;

import co.axelrod.rpi.meteo.bot.metrics.Prometheus;

public abstract class AbstractSensor<T extends AbstractSensorData> {
    protected Prometheus prometheus;

    protected T latestResult;

    public AbstractSensor(Prometheus prometheus) throws Exception {
        this.prometheus = prometheus;
        registerSensor();
    }

    protected abstract void registerSensor() throws Exception;

    protected abstract T getData() throws Exception;

    public String getResult() {
        try {
            return latestResult.toString();
        } catch (Exception ex) {
            return "Unable to get sensor data";
        }
    }

    protected abstract void sendResultToPrometheus() throws Exception;
}
