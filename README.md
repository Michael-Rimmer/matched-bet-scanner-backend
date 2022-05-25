# HelloWorld

How to start the HelloWorld application
---

1. Run `mvn clean install` to build your application
2. java -jar target/HelloWorld-1.0-SNAPSHOT.jar server config/dev.yml
3. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`


Production
---
The backend currently runs as a systemd service file on an AWS Lightsail server (18.134.46.14).
The systemd service file can be found /etc/systemd/service/scannerbackend.service

Troubleshooting
---

To check that the backend is running - curl http://localhost:8080/hello-world

The matched bet scanning endpoint is http://localhost:8080/matched-bets

To view logs from the systemd service - journalctl -u scannerbackend.service | tail -100

Note, the java jar file (HelloWorld-1.0-SNAPSHOT.jar) must be copied from ~/matched-bet-scanner-backend/target to /opt/prod , to be run by the service - sudo cp HelloWorld-1.0-SNAPSHOT.jar /opt/prod/. 

