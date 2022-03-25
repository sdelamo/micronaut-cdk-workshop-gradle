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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AppStack extends Stack {

    private static final String OPTIMIZED = "optimized";
    public static final String ENVIRONMENT_TEST_SUITE = "TEST_SUITE";


    public static final String FUNCTION_HANDLER = "com.example.core.FunctionHandler";
    public static final String PROXY_HANDLER = "io.micronaut.function.aws.proxy.MicronautLambdaHandler";
    public static final String LAMBDA_ZIP = "-lambda.zip";
    public static final String VERSION_ZERO_ONE = "0.1";

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

    private static String candidates() {
        List<String> candidates = new ArrayList<>();
        for (Scenario it : Scenario.values()) {
            candidates.add(it.name());
        }
        return String.join(",", candidates);
    }

    private static Function createFunction(Construct scope) throws IllegalArgumentException {
        Supplier<IllegalArgumentException> exceptionSupplier = () -> new IllegalArgumentException("Please, specify " + ENVIRONMENT_TEST_SUITE + " environment variable with any of the following values: " + candidates());
        Scenario scenario = Scenario.of(System.getenv(ENVIRONMENT_TEST_SUITE))
                .orElseThrow(exceptionSupplier);
        switch (scenario) {
            case GRADLE_APP:
                return createFunctionJava(scope, Scenario.GRADLE_APP.name(), jarPath("app"), PROXY_HANDLER);
            case GRADLE_APP_AOT:
                return createFunctionJava(scope, Scenario.GRADLE_APP_AOT.name(), jarPath("app", true), PROXY_HANDLER);
            case GRADLE_APP_NATIVE:
                return createFunctionProvided(scope, Scenario.GRADLE_APP_NATIVE.name(), providedZipPath("app"), PROXY_HANDLER);
            case GRADLE_APP_NATIVE_AOT:
                return createFunctionProvided(scope, Scenario.GRADLE_APP_NATIVE_AOT.name(), providedZipPath("app", true), PROXY_HANDLER);
            case GRADLE_FUNCTION:
                return createFunctionJava(scope, Scenario.GRADLE_FUNCTION.name(), jarPath("function"), FUNCTION_HANDLER);
            case GRADLE_FUNCTION_AOT:
                return createFunctionJava(scope, Scenario.GRADLE_FUNCTION_AOT.name(), jarPath("function", true), FUNCTION_HANDLER);
            case GRADLE_FUNCTION_NATIVE:
                return  createFunctionJava(scope, Scenario.GRADLE_FUNCTION_NATIVE.name(), providedZipPath("function-native"), FUNCTION_HANDLER);
            case GRADLE_FUNCTION_NATIVE_AOT:
                return createFunctionJava(scope, Scenario.GRADLE_FUNCTION_NATIVE_AOT.name(), providedZipPath("function-native", true), FUNCTION_HANDLER);
            default:
                throw exceptionSupplier.get();
        }
    }

    static String jarPath(String moduleName, boolean optimized, String version) {
        String path = "../" + moduleName + "/build/libs/" + moduleName + "-" + version +"-all.jar";
        if (optimized) {
            path = path.replaceAll("-all.jar", "-all-"+ OPTIMIZED + ".jar");
        }
        return path;
    }

    static String jarPath(String moduleName, boolean optimized) {
        return jarPath(moduleName, optimized, VERSION_ZERO_ONE);
    }

    static String jarPath(String moduleName) {
        return jarPath(moduleName, false, VERSION_ZERO_ONE);
    }

    static String providedZipPath(String moduleName, boolean optimized, String version) {
        String path = "../" + moduleName + "/build/libs/" + moduleName + "-" + version + LAMBDA_ZIP;
        if (optimized) {
            path = path.replace(LAMBDA_ZIP, "-" + OPTIMIZED + LAMBDA_ZIP);
        }
        return path;
    }

    static String providedZipPath(String moduleName, boolean optimized) {
        return providedZipPath(moduleName, optimized, VERSION_ZERO_ONE);
    }

    static String providedZipPath(String moduleName) {
        return providedZipPath(moduleName, false, VERSION_ZERO_ONE);
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
