# Telegram bot for using Raspberry Pi's sensors

Control your apartment's humidity and temperature via Telegram bot and Raspberry Pi.

Supported sensors:
- Humidity and temperature: `SHT3x-DIS`

Demo (my own apartment's bot): http://telegram.me/i_am_at_home_bot

## Preparing bot
- Create [Telegram bot](https://core.telegram.org/bots#creating-a-new-bot) and retrieve the token
- Set Telegram bot token
    - at application.properties
    - by passing as argument: 'java -jar build.jar --telegram.bot.token=YOUR_TOKEN'

## Build and run
- [Install Pi4J](http://pi4j.com/install.html#Installation) on your Raspberry Pi
- Connect `SHT3x-DIS` sensor to your Raspberry Pi
- Build binary, copy it to Raspberry Pi and start
```
mvn clean install
scp target/raspberry-pi-meteo-bot-0.2.0.jar pi@address:~
ssh pi@address 'nohup java -jar raspberry-pi-meteo-bot-0.2.0.jar &> meteo-bot.out&'
or
ssh pi@address 'nohup java -jar raspberry-pi-meteo-bot-0.2.0.jar --telegram.bot.token=YOUR_TOKEN &> meteo-bot.out&'
```

## Using
- Open your Telegram bot and call for sensor
    
## Notes
- Tested on this `SHT3x-DIS` sensor implementation: http://amperka.ru/product/troyka-meteo-sensor?utm_source=man&utm_campaign=troyka-meteo-sensor&utm_medium=wiki
