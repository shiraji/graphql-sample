package com.github.shiraji.graphqlsample

data class Employee(val id: Int, val name: String)

data class Task(val id: Int, val name: String, val desc: String, val employeeId: Int)