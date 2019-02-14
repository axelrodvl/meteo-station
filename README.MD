# Telegram bot and Grafana dashboard for Raspberry Pi's sensors

Observe your apartment's humidity, temperature and CO2 level with Raspberry Pi via Telegram bot and Grafana.

Supported sensors:
- Humidity and temperature: `SHT3x-DIS`
- CO2 level: `MH-Z19B NDIR`

Demo (my own apartment's bot): http://telegram.me/i_am_at_home_bot

## Preparing bot
- Create [Telegram bot](https://core.telegram.org/bots#creating-a-new-bot) and retrieve the token
- Remove unused sensor component (if you need)
- Set Telegram bot token
    - at application.properties
    - by passing as argument: 'java -jar build.jar --telegram.bot.token=YOUR_TOKEN'

## Preparing Grafana and Prometheus
- Download and unpack ARM version of [Grafana](https://grafana.com/grafana/download?platform=arm)
- Download and unpack ARM version of [Prometheus](https://prometheus.io/download)
- Set Prometheus scrape config at `prometheus.yml`:
```
- targets: ['localhost:8779']
```
- Start Prometheus and Grafana

## Build and run
- [Install Pi4J](http://pi4j.com/install.html#Installation) on your Raspberry Pi
- Connect `SHT3x-DIS` sensor to your Raspberry Pi
- Connect `MH-Z19B NDIR` sensor to your Raspberry Pi
- Build binary, copy it to Raspberry Pi and start
```
mvn clean install
scp target/raspberry-pi-meteo-bot-0.3.0.jar pi@address:~
ssh pi@piaddress 'nohup java -jar raspberry-pi-meteo-bot-0.3.0.jar &> meteo-bot.out&'
or
ssh pi@piaddress 'nohup java -jar raspberry-pi-meteo-bot-0.3.0.jar --telegram.bot.token=YOUR_TOKEN &> meteo-bot.out&'
```

## Using
- Open your Telegram bot and call for sensor
- Open Grafana at piaddress:3000 
    
## Notes
- Tested on this `SHT3x-DIS` sensor implementation: http://amperka.ru/product/troyka-meteo-sensor?utm_source=man&utm_campaign=troyka-meteo-sensor&utm_medium=wiki
