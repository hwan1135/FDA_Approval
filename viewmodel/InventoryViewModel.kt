import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InventoryViewModel(private val repository: GeminiRepository) : ViewModel() {
    private val _inventory = MutableStateFlow<List<DrugItem>>(emptyList())
    val inventory = _inventory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun runFdaCheck(names: String) {
        val drugList = names.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        _inventory.value = drugList.map { DrugItem(name = it) }

        viewModelScope.launch {
            _isLoading.value = true
            val results = repository.checkFdaStatus(drugList)
            
            _inventory.value = _inventory.value.map { item ->
                val match = results.find { it.name.equals(item.name, ignoreCase = true) }
                if (match != null) {
                    item.copy(status = "Checked", isApproved = match.approved, details = match.summary)
                } else {
                    item.copy(status = "Error", details = "Could not verify.")
                }
            }
            _isLoading.value = false
        }
    }
}
