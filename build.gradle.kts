import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.openapi.generator") version "5.1.1"
	kotlin("jvm") version "1.5.10"
	kotlin("plugin.spring") version "1.5.10"
	kotlin("kapt") version "1.3.61"
}

group = "me.chalkboard"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

buildscript {
	repositories {
		mavenLocal()
		maven { url = uri("https://repo1.maven.org/maven2") }
	}
	dependencies {
		classpath("org.openapitools:openapi-generator-gradle-plugin:5.1.1")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-cassandra-reactive")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	implementation("io.swagger:swagger-annotations:1.6.2")
	implementation("javax.validation:validation-api:2.0.1.Final")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

val generatedSourcesDir = "$buildDir/generated/openapi"

openApiGenerate {
	generatorName.set("kotlin-spring")
	inputSpec.set("$rootDir/openapi/reference/forum.yaml")
	outputDir.set(generatedSourcesDir)

	apiPackage.set("me.chalkboard.forum.api")
	modelPackage.set("me.chalkboard.forum.model")

	configOptions.set(mapOf(
		"dateLibrary" to "java8",
		"library" to "spring-boot",
		"useBeanValidation" to "true",
		"interfaceOnly" to "true",
		"serializableModel" to "true",
		"reactive" to "true",
		"configPackage" to "me.chalkboard.forum.appconfig",
		"enumPropertyNaming" to "UPPERCASE"
	))
}

sourceSets {
	getByName("main") {
		java {
			srcDir("$generatedSourcesDir/src/main/kotlin")
		}
	}
}

tasks.compileJava {
	dependsOn(tasks.openApiGenerate)
}

tasks.compileKotlin {
	dependsOn(tasks.openApiGenerate)
}

tasks.withType<KotlinCompile> {
	dependsOn ("openApiGenerate")
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
