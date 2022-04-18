plugins {
    id(Plugins.KOTLIN)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(Deps.Module.KT_UTIL))

    // Kotlin
    implementation(Deps.Kotlin.COROUTINE_CORE)
    implementation(Deps.Kotlin.COROUTINE_ANDROID)

    // Java
    implementation(Deps.JavaX.INJECT)
}