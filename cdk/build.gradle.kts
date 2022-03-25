plugins {
    id("application")
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.17.0")
    implementation("software.constructs:constructs:10.0.94")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
}
application {
    mainClass.set("com.example.Main")
}
