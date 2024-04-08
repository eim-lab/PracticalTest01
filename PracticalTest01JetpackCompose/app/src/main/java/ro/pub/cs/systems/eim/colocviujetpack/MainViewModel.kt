package ro.pub.cs.systems.eim.colocviujetpack

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val input1 = mutableIntStateOf(0)
    val input2 = mutableIntStateOf(0)

    fun incrementInput1() {
        input1.intValue += 1
    }

    fun incrementInput2() {
        input2.intValue += 1
    }
}