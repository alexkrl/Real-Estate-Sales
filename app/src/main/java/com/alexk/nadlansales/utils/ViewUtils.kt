package com.alexk.nadlansales.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * Created by alexkorolov on 27/02/2020.
 */

fun Context.shortToast(toastMessage : String){
    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(toastMessage : String){
    Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun EditText.asFlow() = callbackFlow {

    val textWatcher = doAfterTextChanged {
        offer(it.toString())
    }

    awaitClose{
        removeTextChangedListener(textWatcher)
    }
}