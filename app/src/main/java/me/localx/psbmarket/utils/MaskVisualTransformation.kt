package me.localx.psbmarket.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class MaskVisualTransformation(private val mask: String): VisualTransformation {
    private val specialSymbolsIndices = mask.indices.filter { mask[it] != '#' }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0

        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }

            out += char
            maskIndex++
        }

        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset == 0) return 0

            return mask
                .withIndex()
                .filter { it.value == '#' }
                .getOrNull(offset - 1)?.index?.plus(1) ?: (mask.count { it != '#' } + offset)
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset).count { it == '#' }
        }
    }
}