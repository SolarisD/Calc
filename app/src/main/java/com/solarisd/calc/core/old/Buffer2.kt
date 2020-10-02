/*
package com.solarisd.calc.core.old

import androidx.lifecycle.MutableLiveData
import com.solarisd.calc.core.toDisplayString
import kotlin.math.pow
import kotlin.text.StringBuilder

class Buffer2(value: String? = null) {
    companion object{
        const val length = 10
    }

    val out: MutableLiveData<String> = MutableLiveData()

    private var sign = false
        set(value) {
            field = value
            out.postValue(getDisplay())
        }
    private var significant: Long = 0
        set(value) {
            field = value
            out.postValue(getDisplay())
        }
    private var exponent: Int? = null
        set(value) {
            field = value
            out.postValue(getDisplay())
        }
    private val base = 10.0

    fun clear() {
        sign = false
        significant = 0
        exponent = null
    }

    private fun getDisplay(): String =
        if(exponent == null){
            significant.toDisplayString()
        } else if (exponent == 0){
            significant.toDisplayString() + "."
        } else if (exponent!! + getLength() > length || exponent!! < -length ){
            getScf()
        } else if (significant == 0L && exponent!! < 0){
            val stb = StringBuilder("0.")
            val end = -exponent!!
            for (i in 1..end){
                stb.append('0')
            }
            stb.toString()
        } else {
            getDouble().toDisplayString()
        }


    fun getDouble(): Double{
        if (sign) return -significant.toDouble() * base.pow(exponent ?: 0)
        return significant.toDouble() * base.pow(exponent ?: 0)
    }

    private fun getScf(): String{
        val ins1 = if (significant.toString().length == 1) ".0"
        else "."
        val ss = StringBuilder(significant.toString()).insert(1, ins1).toString()
        val eAdd = significant.toString().length - 1
        val e = if (exponent!=null) exponent!! + eAdd
        else eAdd
        val ins2 = if (e > 0) "E+${e}"
        else "E${e}"
        return  ss + ins2
    }

    fun setDouble(value: Double){
        if (value.isFinite()){
            val fmt = "%.${length}E"
            var scf = String.format(fmt, value)
            sign = scf[0] == '-'
            if (sign) scf = scf.drop(1)
            var strSignificant = scf.substringBefore('E').replace(".", "")
            //cleanup zeroes
            var tmp = strSignificant.length
            for (i in (strSignificant.length - 1) downTo 0){
                if (strSignificant[i] == '0') tmp = i
                else break
            }
            if(tmp < strSignificant.length) strSignificant = strSignificant.substring(0 until tmp)
            significant = strSignificant.toLong()
            val strExponent= scf.substringAfter('E')
            val expSign = strExponent[0] == '-'
            exponent = if(expSign) -strExponent.drop(1).toInt()
            else strExponent.drop(1).toInt()
            exponent = exponent!! - strSignificant.length + 1
            refactor()
        } else{
            clear()
            out.postValue(value.toDisplayString())
        }
    }
    private fun refactor(){
        if (exponent == 0) exponent = null
        ////TODO FOR MINUS
        exponent?.let {
            if (it > 0 && (significant.toString().length + it) <= length){
                significant = significant * base.pow(it).toLong()
                exponent = null
            }
        }
    }
    private fun getLength(): Int{
        return significant.toString().length
    }

    fun symbol(symbol: Char){
        if (getLength() >= length) return
        when(symbol){
            '0' -> addNumber(0)
            '1' -> addNumber(1)
            '2' -> addNumber(2)
            '3' -> addNumber(3)
            '4' -> addNumber(4)
            '5' -> addNumber(5)
            '6' -> addNumber(6)
            '7' -> addNumber(7)
            '8' -> addNumber(8)
            '9' -> addNumber(9)
            '.' -> addDot()
            'π' -> setPi()
        }
    }

    private fun addDot(){
        if (exponent == null) exponent = 0
    }

    private fun addNumber(num: Int){
        significant = 10 * significant + num
        if (exponent != null) exponent = exponent!! - 1
    }

    fun negative(){
        sign = !sign
    }

    fun backspace(){
        exponent?.let {
            if (it > 0) exponent = it - 1
            if (it < 0) exponent = it + 1
            refactor()
            if (it + significant.toString().length >= length) return
        }
        significant /= 10
    }

    private fun setPi(){
        setDouble(Math.PI)
    }
}*/
