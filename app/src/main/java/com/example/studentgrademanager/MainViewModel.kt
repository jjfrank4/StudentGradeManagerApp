package com.example.studentgrademanager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var students by mutableStateOf<List<Student>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var newStudentName by mutableStateOf("")
        private set

    var newStudentGrade by mutableStateOf("")
        private set

    fun addStudent(name: String, grade: String) {
        val gradeValue = grade.toFloatOrNull() ?: 0f
        students = students + Student(name, gradeValue)
        newStudentName = ""
        newStudentGrade = ""
    }

    fun removeStudent(student: Student) {
        students = students - student
    }

    fun calculateGPA(): Float {
        return if (students.isEmpty()) 0f else students.map { it.grade }.average().toFloat()
    }

    fun loadSampleData() {
        viewModelScope.launch {
            isLoading = true
            delay(1500)
            students = listOf(
                Student("Alice Johnson", 95f),
                Student("Bob Smith", 87f),
                Student("Carol Davis", 92f)
            )
            isLoading = false
        }
    }

    fun updateNewStudentName(name: String) {
        newStudentName = name
    }

    fun updateNewStudentGrade(grade: String) {
        newStudentGrade = grade
    }
}

data class Student(
    val name: String,
    val grade: Float
)