#!/bin/bash
./gradlew clean
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
if [ "$TEST_SUITE" == "GRADLE_APP_NATIVE_AOT" ]
then
./gradlew :app:optimizedBuildNativeLambda --console=plain
fi
cd cdk
cdk synth --quiet true
cdk deploy
cd ..