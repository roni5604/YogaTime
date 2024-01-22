package com.example.yogatime

class Manager {
    fun createTrain(name: String, date: String, capacity: Int): Train {
        val newTrain = Train(name, date, capacity)
        return newTrain
    }
}

class Train(val name: String, val date: String, val capacity: Int) {
    // Add methods for the Train class here
}