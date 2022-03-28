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
if [ "$TEST_SUITE" == "GRADLE_FUNCTION_NATIVE" ]
then
./gradlew :function-native:buildNativeLambda -Pmicronaut.runtime=lambda
fi
if [ "$TEST_SUITE" == "GRADLE_FUNCTION_NATIVE_AOT" ]
then
./gradlew :function-native:optimizedBuildNativeLambda --console=plain
fi
if [ "$TEST_SUITE" == "GRADLE_FUNCTION" ]
then
./gradlew :function:shadowJar --console=plain
fi
if [ "$TEST_SUITE" == "GRADLE_FUNCTION_AOT" ]
then
./gradlew :function:optimizedJitJarAll --console=plain
fi
if [ "$TEST_SUITE" == "GRADLE_APP" ]
then
./gradlew :app:shadowJar --console=plain
fi
if [ "$TEST_SUITE" == "GRADLE_APP_AOT" ]
then
./gradlew :app:optimizedJitJarAll --console=plain
fi
if [ "$TEST_SUITE" == "GRADLE_APP_NATIVE" ]
then
./gradlew :app:buildNativeLambda --console=plain
fi
if [ "$TEST_SUITE" == "MAVEN_APP" ]
then
cd mvnapp
./mvnw package
cd ..
fi
if [ "$TEST_SUITE" == "MAVEN_APP_NATIVE" ]
then
cd mvnapp
./mvnw package -Dpackaging=docker-native -Dmicronaut.runtime=lambda
cd ..
fi
if [ "$TEST_SUITE" == "MAVEN_FUNCTION" ]
then
cd mvnfunction
./mvnw package
cd ..
fi
if [ "$TEST_SUITE" == "MAVEN_FUNCTION_NATIVE" ]
then
cd mvnfunction-native
./mvnw package -Dpackaging=docker-native -Dmicronaut.runtime=lambda
cd ..
fi
if [ "$TEST_SUITE" == "GRADLE_APP_NATIVE_AOT" ]
then
./gradlew :app:optimizedBuildNativeLambda --console=plain
fi
cd cdk
cdk synth --quiet true
cdk deploy --require-approval never
cd ..
./run-load-test.sh
