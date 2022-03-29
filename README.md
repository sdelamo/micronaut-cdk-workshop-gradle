![](measurements.png)

## Scenarios

| Done | Build Tool  | Application Type |Runtime | AOT | Command  | 
|:-----|:------------|:-----------------|:---      |:---    | :--- |
| [ ]  | Gradle      | Application      | Java 11                       | FALSE  | `export TEST_SUITE=GRADLE_APP;./release.sh`  |
| [ ]  | Gradle      | Application      | Java 11          | TRUE  | `export TEST_SUITE=GRADLE_APP_AOT;./release.sh`  |
| [ ]  | Gradle      | Application      | NATIVE           | FALSE  | `export TEST_SUITE=GRADLE_APP_NATIVE;./release.sh`  |
| [ ]  | Gradle      | Application      | NATIVE           | TRUE  | `export TEST_SUITE=GRADLE_APP_NATIVE_AOT;./release.sh`  |
| [ ]  | Gradle      | Function         | Java 11          | FALSE  | `export TEST_SUITE=GRADLE_FUNCTION;./release.sh`  |
| [ ]  | Gradle      | Function         | Java 11          | TRUE  | `export TEST_SUITE=GRADLE_FUNCTION_AOT;./release.sh`  |
| [ ]  | Gradle      | Function         | NATIVE           | FALSE  | `export TEST_SUITE=GRADLE_FUNCTION_NATIVE;./release.sh`  |
| [ ]  | Gradle      | Function         | NATIVE           | TRUE  | `export TEST_SUITE=GRADLE_FUNCTION_NATIVE_AOT;./release.sh`  |
| [ ]  | Maven       | Application      | NATIVE           | FALSE  | `export TEST_SUITE=MAVEN_APP_NATIVE;./release.sh`  |

With serde

`export TEST_SUITE=GRADLE_FUNCTION_SERDE;./release.sh`
