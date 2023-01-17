// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package micronaut.serverless.graalvm.demo;

import io.micronaut.aws.cdk.function.MicronautFunction;
import io.micronaut.aws.cdk.function.MicronautFunctionFile;
import io.micronaut.starter.application.ApplicationType;
import io.micronaut.starter.options.BuildTool;
import software.amazon.awscdk.BundlingOptions;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.DockerImage;
import software.amazon.awscdk.DockerVolume;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigatewayv2.alpha.AddRoutesOptions;
import software.amazon.awscdk.services.apigatewayv2.alpha.HttpApi;
import software.amazon.awscdk.services.apigatewayv2.alpha.HttpMethod;
import software.amazon.awscdk.services.apigatewayv2.alpha.PayloadFormatVersion;
import software.amazon.awscdk.services.apigatewayv2.integrations.alpha.LambdaProxyIntegration;
import software.amazon.awscdk.services.apigatewayv2.integrations.alpha.LambdaProxyIntegrationProps;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.BillingMode;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.Tracing;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.s3.assets.AssetOptions;
import software.constructs.Construct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static software.amazon.awscdk.BundlingOutput.ARCHIVED;

public class InfrastructureStack extends Stack {

    List<Function> functions = new ArrayList<>();

    public InfrastructureStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public InfrastructureStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        Table productsTable = Table.Builder.create(this, "Products")
                .tableName("Products")
                .partitionKey(Attribute.builder()
                        .type(AttributeType.STRING)
                        .name("PK")
                        .build())
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .build();
        Map<String, String> environmentVariables = new HashMap<>();
        environmentVariables.put("PRODUCT_TABLE_NAME", productsTable.getTableName());
        boolean graalVMNative = false;
        Function getProductFunction = MicronautFunction.create(ApplicationType.FUNCTION,
                        graalVMNative,
                        this,
                        "GetProductFunction")
                .environment(environmentVariables)
                .code(Code.fromAsset(functionPath("get-product")))
                .handler("software.amazonaws.example.product.entrypoints.ApiGatewayGetProductRequestHandler")
                .timeout(Duration.seconds(10))
                .memorySize(512)
                .logRetention(RetentionDays.ONE_WEEK)
                .tracing(Tracing.ACTIVE)
                .tracing(Tracing.ACTIVE)
                .build();

        Function getAllProductFunction = MicronautFunction.create(ApplicationType.FUNCTION,
                        graalVMNative,
                        this,
                        "GetAllProductFunction")
                .environment(environmentVariables)
                .code(Code.fromAsset(functionPath("get-all-product")))
                .handler("software.amazonaws.example.product.entrypoints.ApiGatewayGetAllProductRequestHandler")
                .timeout(Duration.seconds(10))
                .memorySize(512)
                .logRetention(RetentionDays.ONE_WEEK)
                .tracing(Tracing.ACTIVE)
                .tracing(Tracing.ACTIVE)
                .build();


        Function putProductFunction = MicronautFunction.create(ApplicationType.FUNCTION,
                        graalVMNative,
                        this,
                        "PutProductFunction")
                .environment(environmentVariables)
                .code(Code.fromAsset(functionPath("put-product")))
                .handler("software.amazonaws.example.product.entrypoints.ApiGatewayPutProductRequestHandler")
                .timeout(Duration.seconds(10))
                .memorySize(512)
                .logRetention(RetentionDays.ONE_WEEK)
                .tracing(Tracing.ACTIVE)
                .tracing(Tracing.ACTIVE)
                .build();

        Function deleteProductFunction = MicronautFunction.create(ApplicationType.FUNCTION,
                        graalVMNative,
                        this,
                        "DeleteProductFunction")
                .environment(environmentVariables)
                .code(Code.fromAsset(functionPath("delete-product")))
                .handler("software.amazonaws.example.product.entrypoints.ApiGatewayDeleteProductRequestHandler")
                .timeout(Duration.seconds(10))
                .memorySize(512)
                .logRetention(RetentionDays.ONE_WEEK)
                .tracing(Tracing.ACTIVE)
                .tracing(Tracing.ACTIVE)
                .build();

        productsTable.grantReadData(getProductFunction);
        productsTable.grantReadData(getAllProductFunction);
        productsTable.grantWriteData(putProductFunction);
        productsTable.grantWriteData(deleteProductFunction);

        HttpApi httpApi = HttpApi.Builder.create(this, "ProductsApi")
                .apiName("ProductsApi")
                .build();

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/{id}")
                .methods(singletonList(HttpMethod.GET))
                .integration(new LambdaProxyIntegration(LambdaProxyIntegrationProps.builder()
                        .handler(getProductFunction)
                        .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                        .build()))
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/")
                .methods(singletonList(HttpMethod.GET))
                .integration(new LambdaProxyIntegration(LambdaProxyIntegrationProps.builder()
                        .handler(getAllProductFunction)
                        .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                        .build()))
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/{id}")
                .methods(singletonList(HttpMethod.PUT))
                .integration(new LambdaProxyIntegration(LambdaProxyIntegrationProps.builder()
                        .handler(putProductFunction)
                        .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                        .build()))
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/{id}")
                .methods(singletonList(HttpMethod.DELETE))
                .integration(new LambdaProxyIntegration(LambdaProxyIntegrationProps.builder()
                        .handler(deleteProductFunction)
                        .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                        .build()))
                .build());

        functions.add(getAllProductFunction);
        functions.add(getProductFunction);
        functions.add(putProductFunction);
        functions.add(deleteProductFunction);

        CfnOutput apiUrl = CfnOutput.Builder.create(this, "ApiUrl")
                .exportName("ApiUrl")
                .value(httpApi.getApiEndpoint())
                .build();
    }

    public List<Function> getFunctions() {
        return Collections.unmodifiableList(functions);
    }

    public static String functionPath(String moduleName) {
        return "../" + moduleName + "/build/libs/" + functionFilename(moduleName);
    }

    public static String functionFilename(String moduleName) {
        return MicronautFunctionFile.builder()
                .graalVMNative(false)
                .version("0.1")
                .archiveBaseName(moduleName)
                .buildTool(BuildTool.GRADLE)
                .build();
    }
}
