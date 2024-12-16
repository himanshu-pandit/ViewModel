package com.bluepearl.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bluepearl.viewmodel.ui.theme.ViewModelTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewModelTheme {

                val employeeVModel: EmployeeVModel = viewModel()

                val name by employeeVModel.empName.observeAsState("")
                val empList by employeeVModel.empList.observeAsState(emptyList())


                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("ViewModel")
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary
                            ),
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->

                    Column (
                        modifier = Modifier.padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                        OutlinedTextField(
                            value = name,
                            onValueChange = { employeeVModel.updateEmp(it) },
                            label = { Text("Enter employee name") },
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (name.isNotBlank()){
                                        employeeVModel.addEmployee(name)
                                    }
                                    employeeVModel.updateEmp("")
                                }
                            ),
                            leadingIcon = {
                                Icon(Icons.Default.Person, null)
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        employeeVModel.updateEmp("")
                                    }
                                ) {
                                    Icon(Icons.Default.Clear, null)
                                }

                            },
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (name.isNotBlank()){
                                    employeeVModel.addEmployee(name)
                                }
                                employeeVModel.updateEmp("")
                            }
                        ) {
                            Text("Add Employee")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if(empList.isEmpty()){
                            Text("No employee added")
                        }else{
                            LazyColumn (
                                modifier = Modifier.fillMaxSize().padding(8.dp)
                            ){
                                items(empList){
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(it)
                                        IconButton(
                                            onClick = {
                                                employeeVModel.deleteEmployee(it)
                                            }
                                        ) {
                                            Icon(Icons.Default.Delete, contentDescription = null)
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ViewModelTheme {

    }
}