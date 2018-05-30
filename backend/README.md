# kickvinwl
## Backend

*gradle.properties* im *Backend*-Verzeichnis hinterlegen und die Parameter *username* und *password* anpassen.
```
systemProp.http.proxyHost=172.28.2.4
systemProp.http.proxyPort=9090
systemProp.http.proxyUser=username
systemProp.http.proxyPassword=password
systemProp.http.nonProxyHosts=localhost|127.0.0.1|10.10.1.*
systemProp.https.proxyHost=172.28.2.4
systemProp.https.proxyPort=9090
systemProp.https.proxyUser=username
systemProp.https.proxyPassword=password
systemProp.https.nonProxyHosts=localhost|127.0.0.1|10.10.1.*

```


## Lokale Testdatenbank
Um beim Applikationsstart eine Datenbank anzulegen sind folgende Schritte notwendig:
```
1. Starte einen lokalen MySQL-Server
2. Füge in der ersten Zeile von *KickVinWlApplication.run()* den Aufruf *DBInitilizer.init()* ein.
   Standard-Attribute
      User="root"
      Passwort=""
3. Beim nächsten Start der Anwendung sollte die Datenbank mit der Usertabelle angeleft werden
4. Für weitere Tabellen müssen diese in der persistence.xml ergänzt werden
```
