package com.example.yogatime.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.data.Manager.ManagerHomeUIEvent
import com.example.yogatime.data.Manager.ManagerHomeViewModel
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import kotlinx.coroutines.launch
import androidx.compose.material.Surface


@Composable
fun ManagerHomeScreen (managerHomeViewModel: ManagerHomeViewModel = viewModel()) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    managerHomeViewModel.getUserData()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar ={
            AppToolbar(toolbarTitle = stringResource(R.string.home),
                navigationIconClicked = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavigationDrawerHeader(managerHomeViewModel.fullNameId.value)
            NavigationDrawerBody(navigationDrawerItems = managerHomeViewModel.navigationItemsList,
                onNavigationItemClicked = {
                    if (it.itemId == "LogoutButton"){
                        managerHomeViewModel.onEvent(ManagerHomeUIEvent.LogoutButtonClicked)
                    }else if (it.itemId == "profileScreen"){
                        managerHomeViewModel.onEvent(ManagerHomeUIEvent.ProfileButtonClicked)
                    }
                }
            )
        }
    ) {paddingValues ->
        Surface(color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                HeadingTextComponent(value = "Home page")
                Button(onClick = {
                    YogaTimeAppRouter.navigateTo(Screen.AddNewEventScreen)
                }) {
                    Text("Go to Add New Event")
                }

                Button(onClick = {
                    YogaTimeAppRouter.navigateTo(Screen.GalleryScreen)
                }) {
                    Text("Go to Gallery")
                }
            }
        }
    }
}