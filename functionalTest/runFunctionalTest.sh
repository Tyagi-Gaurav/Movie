#!/bin/bash

cd /data
/opt/gradle-7.0.2/bin/gradle wrapper
./gradlew remoteFunctional --info 2> /dev/null
RETURN_CODE=`cat target/cucumber-report.json | grep -i "\"status\": \"failed\"" | wc -l`

echo "Number of Test Failures: $RETURN_CODE"
exit $RETURN_CODE