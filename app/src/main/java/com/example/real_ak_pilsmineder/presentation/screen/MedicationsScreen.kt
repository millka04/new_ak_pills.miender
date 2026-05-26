package com.example.real_ak_pilsmineder.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.real_ak_pilsmineder.R
import com.example.real_ak_pilsmineder.domain.model.Medication
import com.example.real_ak_pilsmineder.presentation.screen.components.AddEditMedicationDialog
import com.example.real_ak_pilsmineder.presentation.screen.components.MedicationItem
import com.example.real_ak_pilsmineder.presentation.theme.PilulaColor
import com.example.real_ak_pilsmineder.presentation.theme.StatusBarColor
import com.example.real_ak_pilsmineder.presentation.viewmodel.MedicationViewModel

import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationsScreen(viewModel: MedicationViewModel) {
    val medications by viewModel.medications.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var editingMedication by remember { mutableStateOf<Medication?>(null) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.screen_medications)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = StatusBarColor,
                    titleContentColor = colorResource(R.color.black)
                )
            )
        }
    ) { paddingValues ->
        Log.d("LLL","$paddingValues")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 0.dp, top = 93.dp)
                .background(colorResource(R.color.white))
        ) {
            if (medications.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Нет добавленных препаратов", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(medications) { medication ->
                        MedicationItem(
                            medication = medication,
                            onEdit = {
                                editingMedication = medication
                                showAddDialog = true
                            },
                            onDelete = {
                                scope.launch {
                                    viewModel.deleteMedication(medication)
                                }
                            }
                        )
                    }
                }
            }

            FloatingActionButton(
                onClick = {
                    editingMedication = null
                    showAddDialog = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = PilulaColor
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить препарат")
            }
        }
        if (showAddDialog) {
            AddEditMedicationDialog(
                medication = editingMedication,
                onDismiss = { showAddDialog = false },
                onSave = { name ->
                    if (editingMedication != null) {
                        //viewModel.updateMedication(editingMedication!!.copy(name = name))
                    } else {
                        viewModel.addMedication(name)
                    }
                    showAddDialog = false
                }
            )
        }

    }



}