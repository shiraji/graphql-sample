package com.github.shiraji.graphqlsample

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class GraphqlSampleApplication

fun main(args: Array<String>) {
    runApplication<GraphqlSampleApplication>(*args)
}

@Component
class EmployeeResolver(private val employeeDao: EmployeeDao) : GraphQLQueryResolver {
    fun getEmployee(id: Int) = employeeDao.getEmployee(id)
}

@Component
class TaskResolver(private val taskDao : TaskDao) : GraphQLResolver<Employee> {
    fun tasks(author: Employee) = taskDao.getTasksByEmployee(author.id)
}

@Component
class EmployeeDao {
    private val data = mutableListOf(
            Employee(id = 1, name = "foo"),
            Employee(id = 2, name = "bar")
    )

    fun getEmployee(id: Int) = data.firstOrNull { it.id == id }
}

@Component
class TaskDao {
    private val data = mutableListOf(
            Task(id = 1, name = "foo task", desc = "do foo!", employeeId = 1),
            Task(id = 2, name = "bar task", desc = "do bar!", employeeId = 1),
            Task(id = 3, name = "bar task2", desc = "do bar!!", employeeId = 2)
    )

    fun getTask(id: Int) = data.firstOrNull { it.id == id }
    fun getTasksByEmployee(employeeId: Int) = data.filter { it.employeeId == employeeId }
}

@Component
class Query : GraphQLQueryResolver {
    fun version() = "1.0.0"
}