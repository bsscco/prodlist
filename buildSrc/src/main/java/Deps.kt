object Deps {

    object Module {
        const val DOMAIN = ":shared:domain"
        const val DATA = ":shared:data"
        const val LOCAL = ":shared:data:local"
        const val REMOTE = ":shared:data:remote"
        const val KT_UTIL = ":shared:util:ktutil"
        const val AOS_UTIL = ":shared:util:aosutil"
        const val MVI = ":shared:mvi"
        const val THIRD_PARTY = ":shared:thirdparty"
        const val DESIGN_SYS = ":shared:designsys"
        const val NAVIGATION = ":shared:navigation"
        const val HOME = ":feature:home"
        const val PROD_LIST = ":feature:prodlist"
        const val PROD_DETAIL = ":feature:proddetail"
        const val FAVORITE = ":feature:favorite"
    }

    const val AGP_GRADLE_PLUGIN = "com.android.tools.build:gradle:${Versions.AGP}"

    object Kotlin {
        const val GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.KOTLIN}"
        const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.KOTLIN_COROUTINE}"
        const val COROUTINE_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.KOTLIN_COROUTINE}"
    }

    object JavaX {
        const val INJECT = "javax.inject:javax.inject:${Versions.JavaX.INJECT}"
    }

    object Jetpack {
        const val CORE_KTX = "androidx.core:core-ktx:${Versions.Jetpack.CORE}"
        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.Jetpack.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.Jetpack.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.Jetpack.ROOM}"
        const val NAVIGATION_GRADLE_PLUGIN = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Jetpack.NAVIGATION_PLUGIN}"
        const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:${Versions.Jetpack.NAVIGATION}"
        const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Jetpack.LIFECYCLE}"
        const val LIFECYCLE_VIEWMODEL_COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Jetpack.LIFECYCLE}"
        const val LIFECYCLE_PROCESS = "androidx.lifecycle:lifecycle-process:${Versions.Jetpack.LIFECYCLE}"
        const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Jetpack.LIFECYCLE}"
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.Jetpack.APP_COMPAT}"
        const val HILT_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Jetpack.HILT}"
        const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.Jetpack.HILT}"
        const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.Jetpack.HILT}"
        const val HILT_NAVIGATION_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Versions.Jetpack.HILT_NAVIGATION_COMPOSE}"
        const val COMPOSE_COMPILER = "androidx.compose.compiler:compiler:${Versions.Jetpack.COMPOSE}"
        const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.Jetpack.COMPOSE}"
        const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.Jetpack.COMPOSE}"
        const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.Jetpack.COMPOSE}"
        const val COMPOSE_FOUNDATION = "androidx.compose.foundation:foundation:${Versions.Jetpack.COMPOSE}"
        const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.Jetpack.COMPOSE}"
        const val COMPOSE_ANIMATION = "androidx.compose.animation:animation:${Versions.Jetpack.COMPOSE}"
    }

    object GMS {
        const val GOOGLE_SERVICES = "com.google.gms:google-services:${Versions.GMS.GOOGLE_SERVICES}"
    }

    object Firebase {
        const val CRASHLYTICS_PLUGIN = "com.google.firebase:firebase-crashlytics-gradle:${Versions.Firebase.CRASHLYTICS_PLUGIN}"
        const val BOM = "com.google.firebase:firebase-bom:${Versions.Firebase.BOM}"
        const val CRASHLYTICS = "com.google.firebase:firebase-crashlytics"
        const val CRASHLYTICS_NDK = "com.google.firebase:firebase-crashlytics-ndk"
        const val ANALYTICS = "com.google.firebase:firebase-analytics"
    }

    object Retrofit {
        const val CORE = "com.squareup.retrofit2:retrofit:${Versions.Retrofit.RETROFIT}"
        const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.Retrofit.CONVERTER_GSON}"
    }

    object Okhttp {
        const val CORE = "com.squareup.okhttp3:okhttp:${Versions.Okhttp.OKHTTP}"
        const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.Okhttp.LOGGING_INTERCEPTOR}"
    }

    object Accompanist {
        const val NAVIGATION_COMPOSE_ANIMATION = "com.google.accompanist:accompanist-navigation-animation:${Versions.ACCOMPANIST}"
    }
}