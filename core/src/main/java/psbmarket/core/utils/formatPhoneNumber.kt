package psbmarket.core.utils

public fun formatPhoneNumber(phoneNumber: String): String {
    val digits = phoneNumber.filter {
        it.isDigit()
    }.let {
        if (it.startsWith("8")) "7" + it.substring(1) else it
    }

    return if (digits.length == 11) {
        "+${digits[0]} ${digits.substring(1, 4)} ${digits.substring(4, 7)}-${digits.substring(7, 9)}-${digits.substring(9, 11)}"
    } else ""
}
