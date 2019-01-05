# Telegram bot for Raspberry Pi humidity and temperature sensor `SHT3x-DIS`

Control your apartment's humidity and temperature via Telegram bot, Raspberry Pi and sensor `SHT3x-DIS`.

Demo (my own apartment's bot): http://telegram.me/i_am_at_home_bot

## Preparing bot
- Create [Telegram bot](https://core.telegram.org/bots#creating-a-new-bot) and retrieve the token
- Set Telegram bot token by implementing `co.axelrod.rpi.meteo.bot.config.token.TelegramTokenStorage.java`

## Build and run
- Connect `SHT3x-DIS` sensor to your Raspberry Pi
- [Install Pi4J](http://pi4j.com/install.html#Installation) on your Raspberry Pi
- Build binary, copy it to Raspberry Pi and start
    ``
    mvn clean install
    scp target/raspberry-pi-meteo-bot-0.1.0.jar pi@address:~
    ssh pi@address 'chmod +x raspberry-pi-meteo-bot-0.1.0.jar'
    ssh pi@address 'nohup java -jar raspberry-pi-meteo-bot-0.1.0.jar &> meteo-bot.out&'
    ``

## Using
- Open your Telegram bot and call for sensor
    
## Notes
- Tested on this sensor implementation: http://amperka.ru/product/troyka-meteo-sensor?utm_source=man&utm_campaign=troyka-meteo-sensor&utm_medium=wiki
