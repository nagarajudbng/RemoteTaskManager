package com.pesto.authentication.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pesto.core.presentation.UiEvent
import com.pesto.core.presentation.Validations
import kotlinx.coroutines.flow.collectLatest


// Created by Nagaraju on 21/05/24.

@Composable
fun SignUpScreen(
    onNavigation:(String) -> Unit
) {
    val viewModel = hiltViewModel<SignUpViewModel>()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateUp -> {
                    onNavigation("SignIn")
                }
                is UiEvent.ShowSnackBar -> {

                }
                is UiEvent.Message -> {
                }
                else -> {}
            }

        }
    }
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = viewModel.userName.value.text,
                onValueChange = {
                    viewModel.onEvent(AuthEvent.EnteredUserName(it))
                },
                label = { Text("User Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    viewModel.userName.value.statusText?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.email.value.text,
                onValueChange = {
                                viewModel.onEvent(AuthEvent.EnteredEmail(it))
                },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    viewModel.email.value.statusText?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.password.value.text,
                onValueChange = { newPassword ->
                    viewModel.onEvent(AuthEvent.EnteredPassword(newPassword))
                },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    viewModel.password.value.statusText?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.confirmPassword.value.text,
                onValueChange = {
                    viewModel.onEvent(AuthEvent.EnteredConfirmPassword(it))
                },
                label = { Text("Confirm Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    viewModel.confirmPassword.value.statusText?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    viewModel.onEvent(AuthEvent.SignUP)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF396803)
                )
            ) {
                Text("Sign Up")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen( onNavigation = {})
}
