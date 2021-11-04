// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(AppClasspath.gradle)
        classpath(AppClasspath.kotlinGradlePlugin)
        classpath(AppClasspath.serialization)
        classpath(AppClasspath.hilt)
        classpath(AppClasspath.safeArgs)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url ="https://jitpack.io")
    }
}
tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}