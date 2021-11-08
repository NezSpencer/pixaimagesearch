import org.gradle.api.artifacts.dsl.DependencyHandler

object AppConfig {
    const val appCompileSdk = 31
    const val appMinSdk = 23
    const val appTargetSdk = 31
    const val appVersionCode = 1
    const val appVersionName = "1.0.0"
    const val buildToolsVersion = "29.0.3"
    const val appJavaVersion = "11"
    const val kotlinVersion = "1.5.31"

    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"
}
object DependencyVersion {
    const val coreKtx = "1.6.0"
    const val androidXAppCompat = "1.3.1"
    const val googleMaterial = "1.4.0"
    const val constraintLayout = "2.1.1"
    const val glide = "4.12.0"

    //tests
    const val jUnit = "4.13.2"
    const val androidXJunit = "1.1.3"
    const val espresso = "3.4.0"
    const val moshi = "1.12.0"
    const val retrofit = "2.9.0"
    const val lifecycleKtx = "2.4.0-rc01"
    const val okHttp = "4.9.0"
    const val networkResponseAdapter = "4.0.1"
    const val coroutines = "1.5.0"
    const val room = "2.3.0"
    const val hilt = "2.38.1"
    const val navigation = "2.3.5"
    const val fragmentKtx = "1.3.6"
    const val activityKtx = "1.4.0"
    const val compose = "1.0.1"
    const val accompanist = "0.11.0"
}

object AppClasspath {
    const val gradle = "com.android.tools.build:gradle:7.1.0-beta01"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${AppConfig.kotlinVersion}"
    const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${AppConfig.kotlinVersion}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${DependencyVersion.hilt}"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${DependencyVersion.navigation}"
}
object MainDependency {
    private const val coreKtx = "androidx.core:core-ktx:${DependencyVersion.coreKtx}"
    private const val androidXAppCompat = "androidx.appcompat:appcompat:${DependencyVersion.androidXAppCompat}"
    private const val googleMaterial = "com.google.android.material:material:${DependencyVersion.googleMaterial}"
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:${DependencyVersion.constraintLayout}"
    private const val glide = "com.github.bumptech.glide:glide:${DependencyVersion.glide}"
    private const val kaptGlide = "com.github.bumptech.glide:compiler:${DependencyVersion.glide}"
    private const val moshi = "com.squareup.moshi:moshi-kotlin:${DependencyVersion.moshi}"
    private const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersion.coroutines}"
    private const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${DependencyVersion.lifecycleKtx}"
    private const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${DependencyVersion.lifecycleKtx}"
    private const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${DependencyVersion.lifecycleKtx}"
    private const val activityKtx = "androidx.activity:activity-ktx:${DependencyVersion.activityKtx}"
    private const val fragmentKtx = "androidx.fragment:fragment-ktx:${DependencyVersion.fragmentKtx}"

    //jetpack compose
    private const val composeActivity = "androidx.activity:activity-compose:1.3.1"
    private const val composeMaterial = "androidx.compose.material:material:1.0.1"
    private const val composeAnimation = "androidx.compose.animation:animation:1.0.1"
    private const val composeUi =  "androidx.compose.ui:ui:1.0.1"
    private const val composeFoundation = "androidx.compose.foundation:foundation:1.0.1"
    private const val composeTooling = "androidx.compose.ui:ui-tooling:1.0.1"
    private const val composeViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
    private const val composeLivedata = "androidx.compose.runtime:runtime-livedata:1.0.1"
    private const val coilCompose = "io.coil-kt:coil-compose:1.4.0" //ImageLoading
    private const val composeNavigation = "androidx.navigation:navigation-compose:2.4.0-beta02"
    private const val hiltJetpackNavigation = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha03"
    private const val accopanistInset = "com.google.accompanist:accompanist-insets:${DependencyVersion.accompanist}"

    private const val retrofit = "com.squareup.retrofit2:retrofit:${DependencyVersion.retrofit}"
    private const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${DependencyVersion.retrofit}"
    private const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${DependencyVersion.okHttp}"
    private const val okHttp = "com.squareup.okhttp3:okhttp:${DependencyVersion.okHttp}"
    private const val networkResponseAdapter = "com.github.haroldadmin:NetworkResponseAdapter:${DependencyVersion.networkResponseAdapter}"

    private const val room = "androidx.room:room-runtime:${DependencyVersion.room}"
    private const val roomKtx = "androidx.room:room-ktx:${DependencyVersion.room}"
    private const val kaptRoom = "androidx.room:room-compiler:${DependencyVersion.room}"
    //DI
    private const val hilt = "com.google.dagger:hilt-android:${DependencyVersion.hilt}"
    private const val kaptHilt = "com.google.dagger:hilt-compiler:${DependencyVersion.hilt}"
    //for apple silicon macs
    private const val kaptJDBC = "org.xerial:sqlite-jdbc:3.34.0"

    val mainLibs = listOf(
        coreKtx,
        androidXAppCompat,
        googleMaterial,
        constraintLayout,
        glide,
        moshi,
        coroutinesCore,
        viewModelKtx,
        lifecycleRuntimeKtx,
        liveDataKtx,
        retrofit,
        retrofitMoshiConverter,
        okHttpInterceptor,
        okHttp,
        networkResponseAdapter,
        room,
        roomKtx,
        hilt,
        activityKtx,
        fragmentKtx,
        //compose
        composeActivity,
        composeAnimation,
        composeFoundation,
        composeLivedata,
        composeMaterial,
        composeTooling,
        composeUi,
        composeViewModel,
        coilCompose,
        composeNavigation,
        accopanistInset,
        hiltJetpackNavigation
    )

    val kaptLibs = listOf(
        kaptGlide,
        kaptRoom,
        kaptJDBC,
        kaptHilt
    )
}

object TestDependency {
    private const val jUnit = "junit:junit:${DependencyVersion.jUnit}"
    private const val androidXJunit = "androidx.test.ext:junit:${DependencyVersion.androidXJunit}"
    private const val espresso = "androidx.test.espresso:espresso-core:${DependencyVersion.espresso}"
    private const val room = "androidx.room:room-testing:${DependencyVersion.room}"

    val testLibs = listOf(
        jUnit,
        room
    )

    val androidTestLibs = listOf(
        androidXJunit,
        espresso
    )
}

fun DependencyHandler.configureMainDeps(mainLibs: List<String>) {
    mainLibs.forEach { lib -> add("implementation", lib) }
}

fun DependencyHandler.configureKaptDeps(kaptLibs: List<String>) {
    kaptLibs.forEach { lib -> add("kapt", lib) }
}

fun DependencyHandler.configureTestDeps(testLibs: List<String>) {
    testLibs.forEach { lib -> add("testImplementation", lib) }
}

fun DependencyHandler.configureAndroidTestDeps(androidTestLibs: List<String>) {
    androidTestLibs.forEach { lib -> add("androidTestImplementation", lib) }
}