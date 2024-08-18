package psbmarket.core.utils

public fun declOfNum(
    number: Int,
    vararg titles: String,
    withNumber: Boolean = true
): String = declOfNum(number, titles.asList(), withNumber)

public fun <T : Collection<String>> declOfNum(
    number: Int,
    titles: T,
    withNumber: Boolean = true
): String {
    val cases = listOf(2, 0, 1, 1, 1, 2)
    val title = titles.elementAt(
        if (number % 100 in 5..19) 2 else cases[
            if(number % 10 < 5 ) number % 10 else 5
        ]
    )

    return if (withNumber) "$number $title" else title
}