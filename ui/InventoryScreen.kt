import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InventoryScreen(viewModel: InventoryViewModel) {
    var textInput by remember { mutableStateOf("") }
    val inventory by viewModel.inventory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Drug Inventory FDA Checker", style = MaterialTheme.typography.headlineMedium)
        
        OutlinedTextField(
            value = textInput,
            onValueChange = { textInput = it },
            label = { Text("Enter Drugs (e.g. Advil, Penicillin)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.runFdaCheck(textInput) },
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Analyzing..." else "Verify via Gemini")
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn {
            items(inventory) { drug ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when(drug.isApproved) {
                            true -> Color(0xFFC8E6C9)
                            false -> Color(0xFFFFCDD2)
                            else -> Color.White
                        }
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(drug.name, style = MaterialTheme.typography.titleLarge)
                        Text(drug.details, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
