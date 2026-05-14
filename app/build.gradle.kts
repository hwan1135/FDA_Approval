dependencies {
    // UI and Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)

    // Gemini API SDK
    implementation("com.google.ai.client.generativeai:generativeai:0.7.0")
    
    // Serialization for JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")
}
