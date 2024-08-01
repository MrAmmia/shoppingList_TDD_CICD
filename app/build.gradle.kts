import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "net.thebookofcode.shoppinglistitemswithtdd"
    compileSdk = 34

    defaultConfig {
        applicationId = "net.thebookofcode.shoppinglistitemswithtdd"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "net.thebookofcode.shoppinglistitemswithtdd.HiltTestRunner"

        // Load the API key from local.properties
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            FileInputStream(localPropertiesFile).use { stream ->
                localProperties.load(stream)
            }
        }
        val apiKey: String = localProperties.getProperty("apiKey") ?: ""

        // Add the API key as a build config field
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }

//    testOptions {
//        packaging {
//            resources.excludes.add("META-INF/*")
//            jniLibs {
//                useLegacyPackaging = true
//            }
//        }
//    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.navigation.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)

    debugImplementation(libs.androidx.fragment.testing.manifest)

    androidTestImplementation(libs.androidx.fragment.testing)
    
    
    // Architectural Components
    implementation (libs.androidx.lifecycle.viewmodel.ktx.v220)

    // Lifecycle
    implementation (libs.androidx.lifecycle.extensions)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.runtime)

    // Room
    implementation(libs.androidx.room.runtime)
    // To use Kotlin annotation processing tool (kapt)
    kapt(libs.androidx.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    // optional - Test helpers
    testImplementation(libs.androidx.room.testing)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    // Coroutine Lifecycle Scopes
    implementation (libs.lifecycle.viewmodel.ktx)
    implementation (libs.lifecycle.runtime.ktx)

    // Navigation Components
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    // Glide
    implementation (libs.glide)
    kapt (libs.compiler)

    // Activity KTX for viewModels()
    // implementation ("androidx.activity:activity-ktx:1.1.0")

    //Dagger - Hilt
    implementation (libs.hilt.android)
    kapt (libs.hilt.android.compiler)

    // implementation (libs.androidx.hilt.lifecycle.viewmodel)
    kapt (libs.androidx.hilt.compiler)

    // Timber
    implementation (libs.timber)

    // Local Unit Tests
    implementation (libs.androidx.core)
    testImplementation (libs.hamcrest.all)
    testImplementation (libs.androidx.core.testing.v210)
    testImplementation (libs.robolectric)
    testImplementation (libs.jetbrains.kotlinx.coroutines.test)
    testImplementation (libs.google.truth)
    testImplementation (libs.mockito.mockito.core)
    androidTestImplementation (libs.androidx.core)

    // Instrumented Unit Tests
    // androidTestImplementation (libs.dexmaker.mockito)
    androidTestImplementation (libs.jetbrains.kotlinx.coroutines.test)
    androidTestImplementation (libs.core.testing)
    androidTestImplementation (libs.google.truth)

    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.3.0")

    androidTestImplementation (libs.mockito.mockito.core)
    implementation ("androidx.activity:activity-ktx:1.9.0")
    implementation ("androidx.fragment:fragment-ktx:1.8.1")

    // Add mockk for unit tests
    testImplementation ("io.mockk:mockk:1.13.3")
    // Add mockk for Android instrumentation tests
    androidTestImplementation ("io.mockk:mockk-android:1.13.3")

}

kapt {
    correctErrorTypes = true
}