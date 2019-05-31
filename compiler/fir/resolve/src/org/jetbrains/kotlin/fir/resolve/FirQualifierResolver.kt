/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.resolve

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.service
import org.jetbrains.kotlin.fir.symbols.ConeClassifierSymbol
import org.jetbrains.kotlin.fir.types.FirQualifierPart
import org.jetbrains.kotlin.fir.names.FirClassId

interface FirQualifierResolver {
    fun resolveSymbolWithPrefix(parts: List<FirQualifierPart>, prefix: FirClassId): ConeClassifierSymbol?

    fun resolveSymbol(parts: List<FirQualifierPart>): ConeClassifierSymbol?

    companion object {
        fun getInstance(session: FirSession): FirQualifierResolver = session.service()
    }
}

