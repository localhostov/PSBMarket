package me.localx.psbmarket.ui.screens.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import me.localx.psbmarket.ui.screens.catalog.CatalogScreen
import me.localx.psbmarket.ui.screens.overview.views.CategoryAccordion

class OverviewScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: OverviewScreenModel = getScreenModel()
        val categories by screenModel.categories.collectAsState()

        LaunchedEffect(Unit) {
            screenModel.getCategories()
        }

        if (categories != null) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 8.dp))

                categories!!.fastForEach { category ->
                    key(category.id) {
                        CategoryAccordion(
                            text = category.title,
                            level = 0,
                            onClick = { navigator.push(CatalogScreen(category.id)) },
                            content = if (category.children.isEmpty()) null else {{
                                Column {
                                    category.children.fastForEach { category1 ->
                                        key(category1.id) {
                                            CategoryAccordion(
                                                text = category1.title,
                                                level = 1,
                                                onClick = { navigator.push(CatalogScreen(category1.id)) },
                                                content = if (category1.children.isEmpty()) null else {{
                                                    Column {
                                                        category1.children.fastForEach { category2 ->
                                                            key(category2.id) {
                                                                CategoryAccordion(
                                                                    text = category2.title,
                                                                    level = 2,
                                                                    onClick = { navigator.push(CatalogScreen(category2.id)) },
                                                                    content = if (category2.children.isEmpty()) null else {{
                                                                        Column {
                                                                            category2.children.fastForEach { category3 ->
                                                                                key(category3.id) {
                                                                                    CategoryAccordion(
                                                                                        text = category3.title,
                                                                                        level = 3,
                                                                                        onClick = { navigator.push(CatalogScreen(category3.id)) }
                                                                                    )
                                                                                }
                                                                            }
                                                                        }
                                                                    }}
                                                                )
                                                            }
                                                        }
                                                    }
                                                }}
                                            )
                                        }
                                    }
                                }
                            }}
                        )
                    }
                }

                Box(modifier = Modifier.height(8.dp))
            }
        }
    }
}