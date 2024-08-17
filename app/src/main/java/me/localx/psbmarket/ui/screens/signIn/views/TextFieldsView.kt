package me.localx.psbmarket.ui.screens.signIn.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import me.localx.icons.rounded.Icons
import me.localx.icons.rounded.outline.CirclePhone
import me.localx.icons.rounded.outline.Eye
import me.localx.icons.rounded.outline.EyeCrossed
import me.localx.icons.rounded.outline.Lock
import me.localx.psbmarket.R
import me.localx.psbmarket.ui.screens.signIn.SignInScreenModel
import me.localx.psbmarket.utils.MaskVisualTransformation
import psbmarket.uikit.theme.Theme
import psbmarket.uikit.theme.defaultTextFieldColors

@Composable
fun TextFieldsView(screenModel: SignInScreenModel) {
    var isPasswordHidden by remember { mutableStateOf(false) }
    val phoneMask = "+7 ### ###-##-##"

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TextField(
            value = screenModel.phoneNumber,
            placeholder = {
                Text(
                    text = stringResource(R.string.screen_signIn_numberFieldPlaceholder),
                    color = LocalContentColor.current
                )
            },
            onValueChange = { value ->
                if (value.length <= phoneMask.count { it == '#' }) {
                    screenModel.updatePhoneNumber(value)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            shape = Theme.shapes.medium,
            singleLine = true,
            visualTransformation = MaskVisualTransformation(phoneMask),
            colors = defaultTextFieldColors,
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(Icons.Outline.CirclePhone),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        )

        TextField(
            value = screenModel.password,
            placeholder = {
                Text(
                    text = stringResource(R.string.screen_signIn_passwordFieldPlaceholder),
                    color = LocalContentColor.current
                )
            },
            onValueChange = screenModel::updatePassword,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = Theme.shapes.medium,
            colors = defaultTextFieldColors,
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(Icons.Outline.Lock),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable(
                            indication = rememberRipple(color = Theme.colors.text),
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { isPasswordHidden = !isPasswordHidden }
                        )
                ) {
                    Icon(
                        painter = rememberVectorPainter(if (isPasswordHidden) Icons.Outline.EyeCrossed else Icons.Outline.Eye),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )
    }
}

