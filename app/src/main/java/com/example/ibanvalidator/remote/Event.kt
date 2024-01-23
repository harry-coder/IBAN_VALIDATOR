package com.example.ibanvalidator.remote

/**Event class for the live data be be observed again and again in components*/
class Event<T>(private var data: T) {

    var isAlreadyHandled = false
        private set

    fun getData(): T? {
        if (isAlreadyHandled) {
            return null
        }
        isAlreadyHandled = true
        return data
    }

    fun peekContent(): T {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }



}