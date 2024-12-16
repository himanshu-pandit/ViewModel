package com.bluepearl.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmployeeVModel : ViewModel() {

    private val _empName = MutableLiveData<String>("")
    val empName : LiveData<String> get() = _empName

    private val _empList = MutableLiveData<List<String>>(emptyList())
    val empList : LiveData<List<String>> get() = _empList


    fun updateEmp(name:String){
        _empName.value = name
    }

    fun addEmployee(name: String){
        val currentList = _empList.value.orEmpty()
        _empList.value = currentList + name
    }

    fun deleteEmployee(name: String){
        val currentList = _empList.value.orEmpty()
        _empList.value = currentList.filter { it != name }
    }

}