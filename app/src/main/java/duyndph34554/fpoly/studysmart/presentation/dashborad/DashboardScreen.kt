package duyndph34554.fpoly.studysmart.presentation.dashborad

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import duyndph34554.fpoly.studysmart.R
import duyndph34554.fpoly.studysmart.domain.model.Session
import duyndph34554.fpoly.studysmart.domain.model.Subject
import duyndph34554.fpoly.studysmart.domain.model.Task
import duyndph34554.fpoly.studysmart.presentation.components.AddSubjectDialog
import duyndph34554.fpoly.studysmart.presentation.components.CountCard
import duyndph34554.fpoly.studysmart.presentation.components.DeleteDialog
import duyndph34554.fpoly.studysmart.presentation.components.SubjectCard
import duyndph34554.fpoly.studysmart.presentation.components.studySessionsList
import duyndph34554.fpoly.studysmart.presentation.components.taskList
import duyndph34554.fpoly.studysmart.presentation.destinations.SessionScreenRouteDestination
import duyndph34554.fpoly.studysmart.presentation.destinations.SubjectScreenRouteDestination
import duyndph34554.fpoly.studysmart.presentation.destinations.TaskScreenRouteDestination
import duyndph34554.fpoly.studysmart.presentation.subject.SubjectScreenNavArgs
import duyndph34554.fpoly.studysmart.presentation.task.TaskScreenNavArgs
import duyndph34554.fpoly.studysmart.util.SnackbarEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

data class SubjectScreenNavArgs(
    val subjectId: Int
)

@RootNavGraph(start = true)
@Destination()
@Composable
fun DashboardScreenRoute(
    navigator: DestinationsNavigator
) {

    val viewModel: DashboardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val recentSessions by viewModel.recentSessions.collectAsStateWithLifecycle()

    DashboardScreen(
        state = state,
        tasks = tasks,
        recentSessions = recentSessions,
        onEvent = viewModel::onEvent,
        snackbarEvent = viewModel.snackbarEventFlow,
        onSubjectCardClick = {subjectId ->
            subjectId?.let {
                val navArg = SubjectScreenNavArgs(subjectId = subjectId)
                navigator.navigate(SubjectScreenRouteDestination(navArgs = navArg))
            }
        },
        onTaskCardClick = {taskId ->
            val navArg = TaskScreenNavArgs(taskId = taskId ,subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
        },
        onStartSessionButtonClick = {
            navigator.navigate(SessionScreenRouteDestination())
        }
    )
}

@Composable
private fun DashboardScreen(
    state: DashboardState, //Trang thai hien tai cua man hinh
    tasks: List<Task>, //Danh sách cong viec
    recentSessions: List<Session>,
    onEvent: (DashboardEvent) -> Unit, //Ham xu ly su kien khi co 1 su kien xay ra
    snackbarEvent: SharedFlow<SnackbarEvent>,
    onSubjectCardClick: (Int?) -> Unit, //Ham xu ly su kien khi nguoi
    onTaskCardClick: (Int?) -> Unit,    // dung nha vao cac thanh phan tuong ung
    onStartSessionButtonClick: () -> Unit
) {

//   Bien trang thai duoc lưu tru bao toan qua viec luu lại man hinh
    var isAddSubjectDialog by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialog by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() } //Quan ly trang thai Snackbar

//    Lang nghe su kien tu Snackbar

    LaunchedEffect(key1 = true) {
        snackbarEvent.collectLatest { event ->
            when(event) {
//                Hien thi Snackbar khi có sư kien
                is SnackbarEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }

                SnackbarEvent.NavigateUp -> {}
            }
        }
    }

//    Hop thoai Xu ly su kien them subject moi
    AddSubjectDialog(
        isOpen = isAddSubjectDialog,
        subjectName = state.subjectName,
        goalHours = state.goalStudyHours,
        onSubjectNameChange = { onEvent(DashboardEvent.OnSubjectNameChange(it)) },
        onGoalHoursChange = { onEvent(DashboardEvent.OnGoalStudyHoursChange(it)) },
        selectedColors = state.subjectCardColors,
        onColorChange = { onEvent(DashboardEvent.OnSubjectCardColorChange(it)) },
        onDismissRequest = { isAddSubjectDialog = false },
        onConfirmButtonClick = {
            onEvent(DashboardEvent.SaveSubject)
            isAddSubjectDialog = false
        }
    )

//    Hop thoai xac nhan khi nguoi dung xoa subject
    DeleteDialog(
        isOpen = isDeleteSessionDialog,
        title = "Delete Session?",
        bodyText = "Are you sure, you want to delete this session? Your studied hours will be reduced" +
                "by this session time. This action can not be undone.",
        onDismissRequest = { isDeleteSessionDialog = false },
        onConfirmButtonClick = {
            onEvent(DashboardEvent.DeleteSession)
            isDeleteSessionDialog = false
        }
    )

    Scaffold (
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { DashboardScreenTopBar() }
    ) { paddingValues ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
//            Hien thi tong so mon hoc va tong so gio hoc
            item {
                CountCardsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    subjectCount = state.totalSubjectCount,
                    studiedHours = state.totalStudiedHours.toString(),
                    goalHours = state.totalGoalStudyHours.toString()
                )
            }

//          Hien thi danh sach cac the mon hoc va nut them mon hoc moi
            item {
                SubjectCardsSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = state.subjects,
                    onAddIconClicked = {
                        isAddSubjectDialog = true
                    },
                    onSubjectCardClick = onSubjectCardClick
                )
            }
//            Nut de bat dau phien hoc moi
            item {
                Button(
                    onClick = onStartSessionButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp, vertical = 20.dp)
                ) {
                    Text(text = "Start Study Session")
                }
            }
//            Hien thi danh sach cong viec sap toi
            taskList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks.\n" +
                        "Click the + button in subject screen to add new task",
                tasks = tasks,
                onCheckBoxClick = {onEvent(DashboardEvent.OnTaskIsCompleteChange(it))},
                onTaskCardClick = onTaskCardClick
            )
            item { 
                Spacer(modifier = Modifier.height(20.dp))
            }
//            Hien thi danh sach cac phien hoc gan day
            studySessionsList(
                sectionTitle = "RECENT STUDY SESSION",
                emptyListText = "You don't have any recent study sessions.\n" +
                        "Start a study session to begin recording your progress",
                sessions = recentSessions,
                onDeleteIconClick = {
                    onEvent(DashboardEvent.OnDeleteSessionButtonClick(it))
                    isDeleteSessionDialog = true
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "StudySmart",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )
}

// Hien thi tong so mon hoc va tong so gio hoc
@Composable
private fun CountCardsSection(
    modifier: Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalHours: String
) {
    Row (
        modifier = modifier
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Subject Count",
            count = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Studied Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalHours
        )
    }
}

@Composable
private fun SubjectCardsSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any subjects.\n Click the + button to add new subject.",
    onAddIconClicked: () -> Unit,
    onSubjectCardClick: (Int?) -> Unit
) {
    Column (
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = onAddIconClicked) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Subject"
                )
            }
        }

        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.img_books),
                contentDescription = emptyListText
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        LazyRow (
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) { subject ->
                SubjectCard(
                    subjectName = subject.name,
                    gradientColors = subject.colors.map { Color(it) },
                    onClick = {onSubjectCardClick(subject.subjectId)}
                )
            }
        }
    }
}