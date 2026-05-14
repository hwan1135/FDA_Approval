class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Use a secure method to retrieve your API Key
        val repo = GeminiRepository(apiKey = "YOUR_SECURE_API_KEY")
        val viewModel = InventoryViewModel(repo)

        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    InventoryScreen(viewModel)
                }
            }
        }
    }
}
