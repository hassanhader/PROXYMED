plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.proxymed"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.proxymed"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    viewBinding {
        enable = true
    }
    dataBinding {
        enable = true
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.ui)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    
    // Mockito pour les tests unitaires
    testImplementation("org.mockito:mockito-core:5.1.1")
    testImplementation("org.mockito:mockito-inline:5.1.1")
    
    // Robolectric pour tester les composants Android sans émulateur
    testImplementation("org.robolectric:robolectric:4.11.1")
    
    // Room testing
    testImplementation("androidx.room:room-testing:2.6.1")
    androidTestImplementation("androidx.room:room-testing:2.6.1")
    
    // Architecture Components testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    
    // Espresso contrib pour RecyclerView et autres composants
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    
    // Fragment testing
    debugImplementation("androidx.fragment:fragment-testing:1.6.2")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.8")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.8")

    // Dépendances ROOM pour la base de données locale
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")


    // Google Play Services pour la localisation
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Google Maps (déjà présent, mais vérifie)
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    //AppCompat
    implementation("androidx.appcompat:appcompat:1.3.0")

    // Glide pour le chargement d'images
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")// Si tu utilises Java

}




