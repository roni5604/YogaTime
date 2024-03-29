package com.example.yogatime.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AddEventTextField
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.ButtonComponent
import com.example.yogatime.components.DisplayHomeBackgroundImage
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.components.NumberOfParticipants
import com.example.yogatime.components.PickDateFromToday
import com.example.yogatime.components.PickTime
import com.example.yogatime.components.SmallButtonComponent
import com.example.yogatime.data.AddEvent.AddNewEventScreenViewModel
import com.example.yogatime.data.AddEvent.AddNewEvent_UIEvent
import com.example.yogatime.data.ToolBar
import kotlinx.coroutines.launch


/***************************** Add New Event Screen *******************************/
/**
 *  AddNewEventScreen is a composable function which is used by the manager to add new train to the app for the users.
 *  In this screen, the manager can add new train to the app.
 *  The manager can also logout from the app or go to the home screen.
 *
 *  @param addEventViewModel is the view model for the add new event screen.
 */
@Composable
fun AddNewEventScreen(addEventViewModel: AddNewEventScreenViewModel = viewModel()) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    ToolBar.getUserData()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar ={
            AppToolbar(toolbarTitle = stringResource(R.string.train),
                navigationIconClicked = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavigationDrawerHeader(ToolBar.fullNameId.value)
            NavigationDrawerBody(navigationDrawerItems = ToolBar.navigationItemsList,
                onNavigationItemClicked = {
                    when (it.itemId) {
                        "LogoutButton" -> {
                            addEventViewModel.onEvent(AddNewEvent_UIEvent.LogoutButtonClicked)
                        }
                        "homeScreen" -> {
                            addEventViewModel.onEvent(AddNewEvent_UIEvent.HomeButtonClicked)
                        }
                        "profileScreen" -> {
                            addEventViewModel.onEvent(AddNewEvent_UIEvent.ProfileButtonClicked)
                        }
                    }
                }
            )
        }
    ) {paddingValues ->
        Box(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
            DisplayHomeBackgroundImage(painterResource = painterResource(id = R.drawable.homescreenbackground)
            )
            androidx.compose.material.Surface(
                color = Color.Black.copy(alpha = 0.0f), // Adjust opacity and color
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    NormalTextComponent(value = "Event")
                    HeadingTextComponent(value = "Add New Event")
                    Spacer(modifier = Modifier.height(40.dp))
                    AddEventTextField(
                        labelValue = "Event Name",
                        onTextSelected = {
                            addEventViewModel.onEvent(AddNewEvent_UIEvent.eventNameChanged(it))
                        },
                        errorStatus = addEventViewModel.AddNewEventState.value.EventNameError
                    )
                    PickDateFromToday(
                        "Pick Date",
                        onDateSelected = {
                            addEventViewModel.onEvent(
                                AddNewEvent_UIEvent.dateChanged(it)
                            )
                        },
                        errorStatus = addEventViewModel.AddNewEventState.value.EventDateError
                    )
                    PickTime(
                        labelValue = "Pick time ",
                        onTimeSelected = {
                            addEventViewModel.onEvent(
                                AddNewEvent_UIEvent.timeChanged(it)
                            )
                        },
                        errorStatus = addEventViewModel.AddNewEventState.value.EventTimeError
                    )
                    NumberOfParticipants(
                        labelValue = "number of participants",
                        painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            addEventViewModel.onEvent(
                                AddNewEvent_UIEvent.NumberOfParticipantChanged(it)
                            )
                        },
                        errorStatus = addEventViewModel.AddNewEventState.value.NumberOfParticipantsError
                    )
                    Spacer(modifier = Modifier.height(80.dp))

                    ButtonComponent(value = "Add Event",
                        onButtonClicked = { addEventViewModel.onEvent(AddNewEvent_UIEvent.AddNewEventButtonClicked) },
                        isEnabled = addEventViewModel.allValidationsPassed.value
                    )
                    SmallButtonComponent(value = "Back",
                        onButtonClicked = {
                            addEventViewModel.onEvent(AddNewEvent_UIEvent.BackButtonClicked)
                        })
                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
        }
    }
    if (addEventViewModel.popupMessage.value != null) {
        AlertDialog(
            onDismissRequest = { addEventViewModel.popupMessage.value = null },
            title = { Text("Error") },
            text = { Text(addEventViewModel.popupMessage.value!!) },
            confirmButton = { TextButton(onClick = { addEventViewModel.popupMessage.value = null }) { Text("OK") } }
        )
    }
}