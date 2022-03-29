#!/bin/bash
set -e
EXIT_STATUS=0
export TEST_SUITE=GRADLE_FUNCTION_NATIVE
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=GRADLE_FUNCTION_NATIVE_AOT
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=GRADLE_FUNCTION
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=GRADLE_FUNCTION_SERDE
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=GRADLE_FUNCTION_AOT
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=GRADLE_APP
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=GRADLE_APP_AOT
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=GRADLE_APP_NATIVE
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=GRADLE_APP_NATIVE_AOT
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=MAVEN_APP
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=MAVEN_APP_NATIVE
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=MAVEN_FUNCTION
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
export TEST_SUITE=MAVEN_FUNCTION_NATIVE
./release.sh || EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi
exit $EXIT_STATUS
