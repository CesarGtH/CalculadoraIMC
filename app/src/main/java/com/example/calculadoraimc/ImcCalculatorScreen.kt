package com.example.calculadoraimc


import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment

@Composable

fun ImcCalculatorScreen() {
    var pesoText by remember { mutableStateOf("") }
    var alturaText by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("Masculino") }
    var actividad by remember { mutableStateOf("Moderada") }
    var resultado by remember { mutableStateOf("") }

    val actividades = listOf("Baja", "Moderada", "Alta")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.
                    fillMaxSize().
                    padding(26.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    )
    {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Calculadora de IMC", style = MaterialTheme.typography.titleLarge)
        TextField(
            value = pesoText,
            onValueChange = { pesoText = it },
            label = { Text("Peso (kg)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = alturaText,
            onValueChange = { alturaText = it },
            label = { Text("Altura (cm)") },
            modifier = Modifier.fillMaxWidth()
        )
        Text("Género:")

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            RadioButton(
                selected = genero == "Masculino",
                onClick = { genero = "Masculino" }
            )
            Text("Masculino")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = genero == "Femenino",
                onClick = { genero = "Femenino" }
            )
            Text("Femenino")
        }
        Text("Actividad física:")

        Box{
            OutlinedButton (
                onClick = { expanded = true },
            ){
                Text(actividad)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false })
            {
                actividades.forEach { actividadOption ->
                    DropdownMenuItem(
                        text = { Text(text = actividadOption) },
                        onClick = {
                            actividad = actividadOption
                            expanded = false
                        }
                    )
                }
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val peso = pesoText.toDoubleOrNull() ?: 0.0
                val altura = alturaText.toDoubleOrNull() ?: 0.0
                val alturaMetros = altura / 100
                val imc = peso / (alturaMetros * alturaMetros)
                resultado = "Tu IMC es: %.2f".format(imc)
                //evalue IMC y defina la categoria
                val categoria = when {
                    imc < 18.5 -> "Bajo peso"
                    imc < 25 -> "Peso normal"
                    imc < 30 -> "Sobrepeso"
                    else -> "Obesidad"
                }
                resultado = "Tu IMC es: %.2f $categoria".format(imc)

            }
        )
        {
            Text("Calcular IMC")
        }
        if (resultado.isNotEmpty())
            Text(resultado, style = MaterialTheme.typography.titleMedium)
    }
}



