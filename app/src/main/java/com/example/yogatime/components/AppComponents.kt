package com.example.yogatime.components

import android.util.Log
import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yogatime.ui.theme.Primary
import com.example.yogatime.ui.theme.TextColor
import com.example.yogatime.ui.theme.componentShapes
import com.example.yogatime.R
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import com.example.yogatime.ui.theme.Secondary


import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.DateRange

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.example.yogatime.data.gallery.GalleryScreenViewModel
import java.util.*


@Composable
fun NormalTextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 24.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        )
        , color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        )
        , color = TextColor,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(labelValue: String, painterResource: Painter,
                onTextSelected: (String) -> Unit,
                errorStatus:Boolean = false) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = labelValue)},
        value = textValue.value,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,

        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription ="" )
        },
        isError = !errorStatus
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPhoneField(labelValue: String, painterResource: Painter,
                onTextSelected: (String) -> Unit,
                errorStatus:Boolean = false) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = labelValue)},
        value = textValue.value,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,

            ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription ="" )
        },
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(labelValue: String, painterResource: Painter,
                      onTextSelected: (String) -> Unit,
                      errorStatus: Boolean = false) {

    val localFocusManager = LocalFocusManager.current

    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = labelValue) },
        value = password.value,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
        singleLine = true,
        keyboardActions = KeyboardActions{
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription ="" )
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }

            val description = if(passwordVisible.value){
                "Hide password"
            }else{
                "Show password"
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else
        PasswordVisualTransformation(),
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MyEmailField(labelValue: String,
                 painterResource: Painter,
                 onTextSelected: (String) -> Unit,
                 errorStatus:Boolean = false) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = labelValue)},
        value = textValue.value,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,

            ), keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription ="" )
        },
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFromTodayPicker(
    labelValue: String,
    painterResource: Painter,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        // Note: month is 0 based
        selectedDate = "$dayOfMonth/${month + 1}/$year"
        onDateSelected(selectedDate) // Pass the selected date back to the caller
        // Optionally display a message or handle the date selection as required
    }

    OutlinedTextField(
        value = selectedDate,
        onValueChange = { /* Read-only field */ },
        readOnly = true,
        modifier = modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        textStyle = TextStyle(fontSize = 18.sp),
        label = { Text(text = labelValue) },

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ), singleLine = true,
        maxLines = 1,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select Date",
                modifier = Modifier.clickable {
                    DatePickerDialog(
                        context,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).apply {
                        datePicker.minDate = System.currentTimeMillis() - 1000 // Restrict to today or future dates
                        show()
                    }
                }
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayDatePicker(
    labelValue: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    // Adjust the calendar to start 100 years back for a birthday picker
    val YearsBack = calendar.apply {
        add(Calendar.YEAR, -100)
    }.timeInMillis

    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        // Note: month is 0 based
        selectedDate = "$dayOfMonth/${month + 1}/$year"
        onDateSelected(selectedDate) // Pass the selected date back to the caller
    }

    OutlinedTextField(
        value = selectedDate,
        onValueChange = { /* Read-only field */ },
        readOnly = true,
        modifier = modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        textStyle = TextStyle(fontSize = 18.sp),
        label = { Text(text = labelValue) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select Date",
                modifier = Modifier.clickable {
                    DatePickerDialog(
                        context,
                        dateSetListener,
                        calendar.get(Calendar.YEAR) - 70, // Start 70 years back
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).apply {
                        datePicker.maxDate = System.currentTimeMillis() // Ensure future dates cannot be picked
                        datePicker.minDate = YearsBack // Optional: Set a minimum date if needed
                                show()
                    }
                }
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        )
    )
}




@Composable
fun ButtonComponent(value:String, onButtonClicked : () -> Unit, isEnabled : Boolean = false){
    Button(onClick = {
       onButtonClicked.invoke()
    },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnabled
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                    shape = RoundedCornerShape(50.dp)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
            }
    }
}


@Composable
fun DateFromTodayCompose(
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()

    // Prepare the listener for date set
    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        // Note: month is 0 based
        selectedDate = "$dayOfMonth/${month + 1}/$year"
        // Use the selected date as needed
        Toast.makeText(context, "Selected date: $selectedDate", Toast.LENGTH_LONG).show()
    }

    Button(onClick = {
        DatePickerDialog(
            context,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis() - 1000 // Restrict to today or future
            show()
        }
    }, modifier = modifier) {
        Text(if (selectedDate.isEmpty()) "Select Date" else selectedDate)
    }
}


@Composable
fun ClickableTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {
    val initialText =
        if (tryingToLogin) "Already have an account? " else "Don’t have an account yet? "
    val loginText = if (tryingToLogin) "Login" else "Register"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 21.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString,
        onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if (span.item == loginText) {
                        onTextSelected(span.item)
                    }
                }

        },
    )
}

@Composable
fun PickImageFromGallery(viewModel: GalleryScreenViewModel) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(400.dp)
                        .padding(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Pick Image")
        }

        // Conditionally display the Upload button only if an image has been picked
        if (imageUri != null) {
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                imageUri?.let { uri ->
                    val inputStream = context.contentResolver.openInputStream(uri)
                    inputStream?.let {
                        // Assuming you have a fileName strategy, for example using a timestamp
                        val fileName = "upload_${System.currentTimeMillis()}.jpg"
                        viewModel.uploadImageToFirebaseStorage(it, fileName)
                    }
                }
            }) {
                Text(text = "Upload")
            }
        }
    }

}