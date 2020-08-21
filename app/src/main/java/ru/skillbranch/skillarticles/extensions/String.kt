package ru.skillbranch.skillarticles.extensions

fun String?.indexesOf(substr: String, ignoreCase: Boolean = true):List<Int>{
    var result = mutableListOf<Int>()
    var repeatCycle = true
    var str = this
    var startIndex = 0
    return if (!substr.isNullOrEmpty()) {
        while (repeatCycle && !str.isNullOrEmpty()) {
            val index = str.indexOf(substr, startIndex, ignoreCase)

            if (index == -1) {
                repeatCycle = false
            } else {
                result.add(index)
                startIndex = index + substr.length
            }
        }

        result
    }else listOf()

}
