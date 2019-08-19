# Discord-bot

## Prerequisites

- Java JRE 8 or later

        $ java -version
        openjdk version "1.8.0_201"
        
- JAVA_HOME is set to your JDK directory
## Building

Unix

    $ gradlew clean build


Windows:
    
    > gradlew.bat clean build

## Running
1. Create a [Discord Application](https://discordapp.com/developers/applications/)
2. Add a bot to your app 
3. Invite your bot to your guild https://discordapp.com/api/oauth2/authorize?client_id=YOUR_CLIENT_ID&permissions=0&scope=bot
4. Add your bot token to local.properties

<a/>

    $ echo "token=YOUR TOKEN" > local.properties
    $ java -jar build/libs/discord-bot-1.0-SNAPSHOT.jar

