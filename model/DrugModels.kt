data class DrugItem(
    val name: String,
    val status: String = "Pending",
    val isApproved: Boolean? = null,
    val details: String = ""
)

data class GeminiDrugResponse(
    val results: List<DrugResult>
)

data class DrugResult(
    val name: String,
    val approved: Boolean,
    val summary: String
)
