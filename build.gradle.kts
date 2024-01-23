// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }


    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")

        classpath ("com.android.tools.build:gradle:7.4.1")
        val navVersion = "2.3.0"
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }
}