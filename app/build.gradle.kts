val pixabayApiKey: String by project
val pixabayBaseUrl: String by project
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(AppConfig.appCompileSdk)

    defaultConfig {
        applicationId = "com.nezspencer.pixaimagesearch"
        minSdkVersion(AppConfig.appMinSdk)
        targetSdkVersion(AppConfig.appTargetSdk)
        versionCode = AppConfig.appVersionCode
        versionName = AppConfig.appVersionName
        testInstrumentationRunner = AppConfig.androidTestInstrumentation

        buildConfigField("String", "API_TOKEN", pixabayApiKey)
        buildConfigField("String", "BASE_URL", pixabayBaseUrl)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = AppConfig.appJavaVersion
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-beta02"
    }
}

dependencies {
    configureMainDeps(MainDependency.mainLibs)
    configureKaptDeps(MainDependency.kaptLibs)
    configureTestDeps(TestDependency.testLibs)
    configureAndroidTestDeps(TestDependency.androidTestLibs)
}