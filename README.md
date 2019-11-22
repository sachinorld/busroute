# busroute
bus routes, stops, departure details base on metro transit api

One REST API is exposed to get time until next bus departs from provided bus stop, in a direction on a bus route.
Access as: "http://localhost:18080/v1/nexttrip/METRO Blue Line/North/Target Field Station Platform 2"
here 
    'METRO Blue Line' is Bus Route
    'North' is direction
    'Target Field Station Platform 2' is bus stop

# Build
mvn clean install
or 
mvn clean install -DskipTests - to skip tests ans build

# Run
mvn spring-boot:run

Application runs at 127.0.0.1:18080, port specified in application.properties.
We can use hostname as 'metrotransit'. The word needs to be mapped to 127.0.0.1 in /etc/hosts file OR 
