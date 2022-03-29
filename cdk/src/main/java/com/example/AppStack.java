package com.example;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.lambda.Architecture;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.IFunction;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.Tracing;
import software.constructs.Construct;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AppStack extends Stack {

    private static final String OPTIMIZED = "optimized";
    public static final String ENVIRONMENT_TEST_SUITE = "TEST_SUITE";
    private static final String JAR = "jar";
    private static final String ALL = "all";
    private static final String DASH = "-";
    private static final String BUILD = "build";
    private static final String LIBS = "libs";
    private static final String DOTDOT = "..";
    private static final String SLASH = "/";
    private static final String LAMBDA = "lambda";
    private static final String ZIP = "zip";
    private static final String DOT = ".";

    private static final String PROXY_HANDLER = "io.micronaut.function.aws.proxy.MicronautLambdaHandler";
    private static final String FUNCTION_HANDLER = "com.example.core.FunctionHandler";
    private static final String VERSION_ZERO_ONE = "0.1";
    private static final String COMMA = ",";
    private static final String TARGET = "target";
    private static final String FUNCTION = "function";


    public AppStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public AppStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        LambdaRestApi lambdaRestApi = createLambdaRestApi(this, "Endpoint", createFunction(this));
        CfnOutput apiUrl = CfnOutput.Builder.create(this, "ApiUrl")
                .exportName("ApiUrl")
                .value(lambdaRestApi.getUrl())
                .build();

    }

    private static LambdaRestApi createLambdaRestApi(Construct scope, String id, IFunction handler) {
        return LambdaRestApi.Builder.create(scope, id)
                .handler(handler)
                .build();
    }

    private static Function createFunction(Construct scope) throws IllegalArgumentException {
        Supplier<IllegalArgumentException> exceptionSupplier = () -> new IllegalArgumentException("Please, specify " + ENVIRONMENT_TEST_SUITE + " environment variable with any of the following values: " + candidates());
        Scenario scenario = Scenario.of(System.getenv(ENVIRONMENT_TEST_SUITE))
                .orElseThrow(exceptionSupplier);
        switch (scenario) {
            case MAVEN_APP:
                return createFunctionJava(scope, Scenario.MAVEN_APP.name(), jarPath(BuildTool.MAVEN,"mvnapp", "demo"), PROXY_HANDLER);
            case MAVEN_APP_NATIVE:
                return createFunctionProvided(scope, Scenario.MAVEN_APP_NATIVE.name(), providedZipPath(BuildTool.MAVEN,"mvnapp"), PROXY_HANDLER);
            case MAVEN_FUNCTION:
                return createFunctionJava(scope, Scenario.MAVEN_FUNCTION.name(), jarPath(BuildTool.MAVEN,"mvnfunction", "demo"), FUNCTION_HANDLER);
            case MAVEN_FUNCTION_NATIVE:
                return createFunctionProvided(scope, Scenario.MAVEN_FUNCTION_NATIVE.name(), providedZipPath(BuildTool.MAVEN,"mvnfunction-native"), FUNCTION_HANDLER);
            case GRADLE_APP:
                return createFunctionJava(scope, Scenario.GRADLE_APP.name(), jarPath(BuildTool.GRADLE, "app"), PROXY_HANDLER);
            case GRADLE_APP_AOT:
                return createFunctionJava(scope, Scenario.GRADLE_APP_AOT.name(), jarPath(BuildTool.GRADLE, "app", true), PROXY_HANDLER);
            case GRADLE_APP_NATIVE:
                return createFunctionProvided(scope, Scenario.GRADLE_APP_NATIVE.name(), providedZipPath(BuildTool.GRADLE, "app"), PROXY_HANDLER);
            case GRADLE_APP_NATIVE_AOT:
                return createFunctionProvided(scope, Scenario.GRADLE_APP_NATIVE_AOT.name(), providedZipPath(BuildTool.GRADLE, "app", true), PROXY_HANDLER);
            case GRADLE_FUNCTION:
                return createFunctionJava(scope, Scenario.GRADLE_FUNCTION.name(), jarPath(BuildTool.GRADLE, "function"), FUNCTION_HANDLER);
            case GRADLE_FUNCTION_SERDE:
                return createFunctionJava(scope, Scenario.GRADLE_FUNCTION_SERDE.name(), jarPath(BuildTool.GRADLE, "function-serde"), "io.micronaut.aws.lambda.events.serde.FunctionHandler");
            case GRADLE_FUNCTION_AOT:
                return createFunctionJava(scope, Scenario.GRADLE_FUNCTION_AOT.name(), jarPath(BuildTool.GRADLE, "function", true), FUNCTION_HANDLER);
            case GRADLE_FUNCTION_NATIVE:
                return  createFunctionProvided(scope, Scenario.GRADLE_FUNCTION_NATIVE.name(), providedZipPath(BuildTool.GRADLE, "function-native"), FUNCTION_HANDLER);
            case GRADLE_FUNCTION_NATIVE_AOT:
                return createFunctionProvided(scope, Scenario.GRADLE_FUNCTION_NATIVE_AOT.name(), providedZipPath(BuildTool.GRADLE, "function-native", true), FUNCTION_HANDLER);
            default:
                throw exceptionSupplier.get();
        }
    }

    private static String jarPath(BuildTool buildTool, String moduleName, String artifactId, boolean optimized, String version) {
        switch (buildTool) {
            case MAVEN:
                String filename = String.join(DOT, String.join(DASH, artifactId, version), JAR);
                return mavenArtifact(moduleName, filename);
            case GRADLE:
            default:
                return gradleArtifact(moduleName, gradleJarFilename(optimized, artifactId, version));
        }
    }

    private static String gradleJarFilename(boolean optimized, String artifactId, String version) {
        return optimized ?
                String.join(DOT, String.join(DASH, artifactId, version, ALL, OPTIMIZED), JAR) :
                String.join(DOT, String.join(DASH, artifactId, version, ALL), JAR);
    }

    private static String jarPath(BuildTool buildTool, String moduleName, String artifactId, boolean optimized) {
        return jarPath(buildTool, moduleName, artifactId, optimized, VERSION_ZERO_ONE);
    }

    private static String jarPath(BuildTool buildTool, String moduleName, boolean optimized) {
        return jarPath(buildTool, moduleName, moduleName, optimized, VERSION_ZERO_ONE);
    }

    private static String jarPath(BuildTool buildTool, String moduleName, String artifactId) {
        return jarPath(buildTool, moduleName, artifactId, false, VERSION_ZERO_ONE);
    }

    private static String jarPath(BuildTool buildTool, String moduleName) {
        return jarPath(buildTool, moduleName, moduleName, false, VERSION_ZERO_ONE);
    }

    private static String mavenArtifact(String moduleName, String filename) {
        return String.join(SLASH, DOTDOT, moduleName, TARGET, filename);
    }

    private static String gradleArtifact(String moduleName, String filename) {
        return String.join(SLASH, DOTDOT, moduleName, BUILD, LIBS, filename);
    }

    private static String providedZipPath(BuildTool buildTool, String moduleName, boolean optimized, String version) {
        switch (buildTool) {
            case MAVEN:
                String filename = String.join(DOT, FUNCTION, ZIP);
                return mavenArtifact(moduleName, filename);

            case GRADLE:
            default:
                return gradleArtifact(moduleName, gradleZipFilename(optimized, moduleName, version));
        }
    }

    private static String gradleZipFilename(boolean optimized, String moduleName, String version) {
        return optimized ?
                String.join(DOT, String.join(DASH, moduleName, version, OPTIMIZED, LAMBDA), ZIP) :
                String.join(DOT, String.join(DASH, moduleName, version, LAMBDA), ZIP);
    }

    private static String providedZipPath(BuildTool buildTool, String moduleName, boolean optimized) {
        return providedZipPath(buildTool, moduleName, optimized, VERSION_ZERO_ONE);
    }

    private static String providedZipPath(BuildTool buildTool, String moduleName) {
        return providedZipPath(buildTool, moduleName, false, VERSION_ZERO_ONE);
    }

    private static Function.Builder createFunctionBuilder(Construct scope,
                                                       String id,
                                                       String codePath,
                                                       String handler) {
        return Function.Builder.create(scope, id)
                .code(Code.fromAsset(codePath))
                .handler(handler)
                .timeout(Duration.seconds(20))
                .memorySize(1024)
                .tracing(Tracing.ACTIVE);
    }

    private static Function createFunctionProvided(Construct scope,
                                               String id,
                                               String codePath,
                                               String handler) {
        return createFunctionBuilder(scope, id, codePath, handler)
                .runtime(Runtime.PROVIDED_AL2)
                .build();
    }

    private static Function createFunctionJava(Construct scope,
                                               String id,
                                               String codePath,
                                               String handler) {
        return createFunctionBuilder(scope, id, codePath, handler)
                .runtime(Runtime.JAVA_11)
                .architecture(Architecture.X86_64)
                .build();
    }

    private static String candidates() {
        List<String> candidates = new ArrayList<>();
        for (Scenario it : Scenario.values()) {
            candidates.add(it.name());
        }
        return String.join(COMMA, candidates);
    }
}
