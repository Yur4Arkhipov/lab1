package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by remember { mutableStateOf("") }
        var selectedIndex by remember { mutableStateOf(0) }

        Dropdown(selectedIndex) { index -> selectedIndex = index }
        Spacer(modifier = Modifier.height(16.dp))

        SimpleFilledTextFieldSample(text) { newText -> text = newText }

        Spacer(modifier = Modifier.height(16.dp))

        OutputField(text = text, selectedIndex = selectedIndex)
    }
}

@Composable
fun SimpleFilledTextFieldSample(text: String, onTextChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text("Enter number: ") },
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@Composable
fun Dropdown(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Binary", "Ternary", "Decimal", "Octal", "Hexadecimal")

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(Color.Gray)
            .clickable(onClick = { expanded = true })
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "More"
            )
        }
        Text(
            "${items[selectedIndex]} input",
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Center)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    onItemSelected(index)
                    expanded = false
                }) {
                    Text(text = s)
                }
            }
        }
    }
}

@Composable
fun OutputField(text: String, selectedIndex: Int) {
    val number = try {
        when (selectedIndex) {
            0 -> text.toIntOrNull(2)
            1 -> text.toIntOrNull(3)
            2 -> text.toIntOrNull(10)
            3 -> text.toIntOrNull(8)
            4 -> text.toIntOrNull(16)
            else -> null
        }
    } catch (e: NumberFormatException) {
        null
    }

    val output = if (number != null) {
        listOf(
            "Binary: ${Integer.toBinaryString(number)}",
            "Ternary: ${number.toString(3)}",
            "Decimal: $number",
            "Octal: ${Integer.toOctalString(number)}",
            "Hexadecimal: ${Integer.toHexString(number).uppercase()}"
        ).filterIndexed { index, _ -> index != selectedIndex }
    } else {
        listOf("Incorrect input")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        output.forEach { line ->
            Text(
                text = line,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}