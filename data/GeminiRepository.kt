import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.google.gson.Gson

class GeminiRepository(apiKey: String) {
    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey,
        generationConfig = generationConfig {
            responseMimeType = "application/json"
        }
    )

    private val gson = Gson()

    suspend fun checkFdaStatus(drugNames: List<String>): List<DrugResult> {
        val prompt = """
            Analyze the following drug names for FDA approval status: ${drugNames.joinToString(", ")}.
            Return a JSON object with a 'results' array. Each object in the array must contain:
            'name' (string), 'approved' (boolean), and 'summary' (brief description of FDA status).
        """.trimIndent()

        return try {
            val response = model.generateContent(prompt)
            val jsonResponse = response.text ?: ""
            val parsed = gson.fromJson(jsonResponse, GeminiDrugResponse::class.java)
            parsed.results
        } catch (e: Exception) {
            emptyList()
        }
    }
}
