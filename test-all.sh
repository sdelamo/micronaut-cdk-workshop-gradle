#!/bin/bash
EXIT_STATUS=0
./gradlew clean
cd mvnapp
./mvnw clean
cd ..
cd mvnfunction
./mvnw clean
cd ..
cd mvnfunction-native
./mvnw clean
cd ..
export TEST_SUITE=GRADLE_FUNCTION_NATIVE
./release.sh || EXIT_STATUS=$?
export TEST_SUITE=GRADLE_FUNCTION_NATIVE_AOT
./release.sh || EXIT_STATUS=$?
export TEST_SUITE=GRADLE_FUNCTION
./release.sh || EXIT_STATUS=$?
export TEST_SUITE=GRADLE_FUNCTION_AOT
./release.sh || EXIT_STATUS=$?
export TEST_SUITE=GRADLE_APP
./release.sh || EXIT_STATUS=$?
export TEST_SUITE=GRADLE_APP_AOT
./release.sh || EXIT_STATUS=$?
export TEST_SUITE=GRADLE_APP_NATIVE
./release.sh || EXIT_STATUS=$?
export TEST_SUITE=GRADLE_APP_NATIVE_AOT
./release.sh || EXIT_STATUS=$?
export TEST_SUITE=MAVEN_APP
./release.sh || EXIT_STATUS=$?
export TEST_SUITE=MAVEN_APP_NATIVE
./release.sh || EXIT_STATUS=$?
export TEST_SUITE=MAVEN_FUNCTION
./release.sh || EXIT_STATUS=$?
export TEST_SUITE=MAVEN_FUNCTION_NATIVE
./release.sh || EXIT_STATUS=$?
exit $EXIT_STATUS