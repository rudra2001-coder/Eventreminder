import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rudra.eventreminder.R
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.ui.theme.Theme
import com.rudra.eventreminder.util.DateUtils
import com.rudra.eventreminder.viewmodel.EventViewModel
import com.rudra.eventreminder.viewmodel.ThemeViewModel
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: EventViewModel,
    themeViewModel: ThemeViewModel,
    onNavigateToAddMemory: () -> Unit,
    onNavigateToEmojiExplorer: () -> Unit,
    onNavigateToUpcomingEvents: () -> Unit,
    onNavigateToPastEvents: () -> Unit,
    onNavigateToTimeline: () -> Unit
) {
    val upcomingEvents by viewModel.events.collectAsState(initial = null)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(label = { Text("Upcoming Events") }, selected = false, onClick = { onNavigateToUpcomingEvents() })
                NavigationDrawerItem(label = { Text("Past Events") }, selected = false, onClick = { onNavigateToPastEvents() })
                NavigationDrawerItem(label = { Text("Timeline") }, selected = false, onClick = { onNavigateToTimeline() })
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "All Memories", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = onNavigateToEmojiExplorer) {
                            Icon(Icons.Default.Face, contentDescription = "Emoji Explorer")
                        }
                        ThemeSelector(themeViewModel = themeViewModel)
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onNavigateToAddMemory,
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Memory", tint = MaterialTheme.colorScheme.onSecondary)
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 8.dp)
            ) {
                if (upcomingEvents == null) {
                    items(5) { ShimmerEventCard() }
                } else {
                    item {
                        InsightsDashboard(events = upcomingEvents!!)
                    }
                    item {
                        Text(
                            text = "Upcoming Moments",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                        )
                    }
                    items(upcomingEvents!!) { event ->
                        val daysLeft = DateUtils.daysLeft(event.date, event.isRecurring)
                        if (daysLeft >= 0) {
                            EventCountdownCard(
                                event = event,
                                daysLeft = daysLeft,
                                onDelete = { viewModel.deleteEvent(event.id.toLong()) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventCountdownCard(event: Event, daysLeft: Long, onDelete: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed) 0.96f else 1f
    val elevation = if (isPressed) 8.dp else 4.dp

    var showCelebration by remember { mutableStateOf(false) }

    LaunchedEffect(daysLeft) {
        if (daysLeft == 0L) {
            showCelebration = true
            delay(3000) // Show for 3 seconds
            showCelebration = false
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .graphicsLayer { this.scaleX = scale; this.scaleY = scale }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { /* Handle card click if needed */ }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = event.eventType,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.secondary)
                    )
                    Text(
                        text = if (daysLeft == 0L) "Today is the day!" else "$daysLeft days left",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                    )
                }
                Text(text = event.emoji, fontSize = 32.sp)
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Event")
                }
            }
            if (showCelebration) {
                CelebrationAnimation()
            }
        }
    }
}

@Composable
private fun InsightsDashboard(events: List<Event>) {
    val eventsThisWeek = events.count {
        val daysLeft = DateUtils.daysLeft(it.date, it.isRecurring)
        daysLeft in 0..7
    }
    val overdueEvents = events.count { DateUtils.daysLeft(it.date, it.isRecurring) < 0 }

    if (eventsThisWeek > 0 || overdueEvents > 0) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (eventsThisWeek > 0) {
                    Text(text = "You have $eventsThisWeek ${if (eventsThisWeek == 1) "event" else "events"} this week.", style = MaterialTheme.typography.bodyLarge)
                }
                if (overdueEvents > 0) {
                    Text(text = "You have $overdueEvents overdue ${if (overdueEvents == 1) "event" else "events"}.", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.error))
                }
            }
        }
    }
}

@Composable
fun ShimmerEventCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .shimmer(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(24.dp)
                        .clip(MaterialTheme.shapes.small)
                )
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(16.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
fun CelebrationAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fireworks))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ThemeSelector(themeViewModel: ThemeViewModel) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Theme")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Theme.entries.forEach { theme ->
                DropdownMenuItem(onClick = {
                    themeViewModel.setTheme(theme)
                    expanded = false
                }, text = {Text(theme.name)})
            }
        }
    }
}
