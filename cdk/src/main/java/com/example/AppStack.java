package com.example;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.lambda.Architecture;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.Tracing;
import software.constructs.Construct;

import java.util.Arrays;
import java.util.List;

public class AppStack extends Stack {

    public static final String ENVIRONMENT_TEST_SUITE = "TEST_SUITE";
    public static final String TEST_SUITE_FUNCTION = "GRADLE_FUNCTION";
    public static final String TEST_SUITE_FUNCTION_AOT = "GRADLE_FUNCTION_AOT";
    public static final String TEST_SUITE_FUNCTION_NATIVE = "GRADLE_FUNCTION_NATIVE";
    public static final String TEST_SUITE_APP_NATIVE = "GRADLE_APP_NATIVE";
    public static final String TEST_SUITE_APP = "GRADLE_APP";
    public static final String TEST_SUITE_APP_AOT = "GRADLE_APP_AOT";

    public static final List<String> CANDIDATES = Arrays.asList(TEST_SUITE_FUNCTION,
            TEST_SUITE_FUNCTION_NATIVE,
            TEST_SUITE_APP,
            TEST_SUITE_APP_NATIVE,
            TEST_SUITE_FUNCTION_AOT,
            TEST_SUITE_APP_AOT
            );
    public static final String FUNCTION_HANDLER = "com.example.core.FunctionHandler";
    public static final String PROXY_HANDLER = "io.micronaut.function.aws.proxy.MicronautLambdaHandler";

    public AppStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public AppStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        Function selectedFunction = createFunction(this);
        LambdaRestApi.Builder.create(this, "Endpoint")
                .handler(selectedFunction)
                .build();
    }

    private static Function createFunction(Construct scope) throws IllegalArgumentException {
        if (System.getenv(ENVIRONMENT_TEST_SUITE) == null) {
            throw new IllegalArgumentException("Please, specify " + ENVIRONMENT_TEST_SUITE + " environment variable with any of the following values: " + String.join(",", CANDIDATES));
        }
        if (System.getenv(ENVIRONMENT_TEST_SUITE).equals(TEST_SUITE_FUNCTION)) {
            return createFunctionServerlessFunction(scope);

        } else if (System.getenv(ENVIRONMENT_TEST_SUITE).equals(TEST_SUITE_FUNCTION_AOT)) {
            return createFunctionServerlessFunctionAot(scope);

        } else if (System.getenv(ENVIRONMENT_TEST_SUITE).equals(TEST_SUITE_APP_AOT)) {
            return createFunctionApplicationAot(scope);

        } else if (System.getenv(ENVIRONMENT_TEST_SUITE).equals(TEST_SUITE_FUNCTION_NATIVE)) {
            return createFunctionNative(scope);

        } else if (System.getenv(ENVIRONMENT_TEST_SUITE).equals(TEST_SUITE_APP_NATIVE)) {
            return createFunctionApplicationNative(scope);

        } else if (System.getenv(ENVIRONMENT_TEST_SUITE).equals(TEST_SUITE_APP)) {
            return createFunctionApplication(scope);
        }
        throw new IllegalArgumentException("Please, specify " + ENVIRONMENT_TEST_SUITE + " environment variable with any of the following values: " + String.join(",", CANDIDATES));
    }

    private static Function createFunctionNative(Construct scope) {
        return createFunctionProvided(scope, "MicronautFunctionGraalVMNativeImage", "../function-native/build/libs/function-native-0.1-lambda.zip",FUNCTION_HANDLER);
    }

    private static Function createFunctionApplication(Construct scope) {
        return createFunctionJava(scope, "MicronautApp", "../app/build/libs/app-0.1-all.jar", PROXY_HANDLER);
    }

    private static Function createFunctionApplicationAot(Construct scope) {
        return createFunctionJava(scope, "MicronautApp", "",PROXY_HANDLER);
    }

    private static Function createFunctionApplicationNative(Construct scope) {
        return createFunctionProvided(scope, "MicronautAppGraalVMNativeImage","../app/build/libs/app-0.1-lambda.zip", PROXY_HANDLER);
    }

    private static Function createFunctionServerlessFunction(Construct scope) {
        return createFunctionJava(scope, "MicronautFunction", "../function/build/libs/function-0.1-all.jar", FUNCTION_HANDLER);
    }

    private static Function createFunctionServerlessFunctionAot(Construct scope) {
        //TODO Fill code path
        return createFunctionJava(scope, "MicronautFunctionAot", "", "com.example.core.FunctionHandler");
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
}
