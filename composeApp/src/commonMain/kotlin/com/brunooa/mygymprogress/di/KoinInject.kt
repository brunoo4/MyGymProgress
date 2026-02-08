package com.brunooa.mygymprogress.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

/**
 * Multiplatform-compatible Koin injection function
 * Uses koin-core instead of koin-compose for better multiplatform support
 */
@Composable
inline fun <reified T> koinInject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T {
    return remember {
        val koin = GlobalContext.get()
        koin.get(qualifier, parameters)
    }
}
