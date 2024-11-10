plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("app.cash.sqldelight")
}

android {
    namespace = "co.uk.purpleeagle.bloodpressure2"
    compileSdk = 35

    defaultConfig {
        applicationId = "co.uk.purpleeagle.bloodpressure2"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2024.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0-RC.2")

    implementation("androidx.navigation:navigation-compose:2.8.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.3")

    implementation ("androidx.documentfile:documentfile:1.0.1")

    implementation("app.cash.sqldelight:android-driver:2.0.2")
    implementation("app.cash.sqldelight:runtime:2.0.2")
    implementation("app.cash.sqldelight:coroutines-extensions:2.0.2")

    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation("androidx.compose.material:material-icons-extended:1.7.5")
}

sqldelight{
    databases{
        create("RecordsDB"){
            packageName.set("records")
        }
    }
}