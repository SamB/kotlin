/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.scopes

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.declarations.FirFile
import org.jetbrains.kotlin.fir.resolve.ScopeSession
import org.jetbrains.kotlin.fir.resolve.buildUseSiteScope
import org.jetbrains.kotlin.fir.resolve.toSymbol
import org.jetbrains.kotlin.fir.scopes.impl.*
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeSymbol
import org.jetbrains.kotlin.fir.symbols.ConeFunctionSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol
import org.jetbrains.kotlin.fir.types.ConeAbbreviatedType
import org.jetbrains.kotlin.fir.types.ConeClassLikeType
import org.jetbrains.kotlin.fir.types.coneTypeUnsafe
import org.jetbrains.kotlin.fir.names.FirName
import org.jetbrains.kotlin.utils.addToStdlib.safeAs

fun MutableList<FirScope>.addImportingScopes(file: FirFile, session: FirSession) {
    this += listOf(
        // from low priority to high priority
        FirDefaultStarImportingScope(session),
        FirExplicitStarImportingScope(file.imports, session),
        FirDefaultSimpleImportingScope(session),
        FirSelfImportingScope(file.packageFqName, session),
        // TODO: explicit simple importing scope should have highest priority (higher than inner scopes added in process)
        FirExplicitSimpleImportingScope(file.imports, session)
    )
}

fun FirCompositeScope.addImportingScopes(file: FirFile, session: FirSession) {
    scopes.addImportingScopes(file, session)
}

private fun finalExpansionName(symbol: FirTypeAliasSymbol, session: FirSession): FirName? {
    val expandedType = symbol.fir.expandedTypeRef.coneTypeUnsafe<ConeClassLikeType>()
    return when (expandedType) {
        is ConeAbbreviatedType ->
            expandedType.abbreviationLookupTag.toSymbol(session)?.safeAs<FirTypeAliasSymbol>()?.let {
                finalExpansionName(it, session)
            }
        else -> expandedType.lookupTag.classId.shortClassName
    }

}

fun processConstructors(
    matchedSymbol: ConeClassLikeSymbol?,
    processor: (ConeFunctionSymbol) -> ProcessorAction,
    session: FirSession,
    scopeSession: ScopeSession,
    name: FirName
): ProcessorAction {
    try {
        if (matchedSymbol != null) {
            val scope = when (matchedSymbol) {
                is FirTypeAliasSymbol -> matchedSymbol.fir.buildUseSiteScope(session, scopeSession)
                is FirClassSymbol -> matchedSymbol.fir.buildUseSiteScope(session, scopeSession)
                else -> null
            }


            val constructorName = when (matchedSymbol) {
                is FirTypeAliasSymbol -> finalExpansionName(matchedSymbol, session) ?: return ProcessorAction.NEXT
                else -> name
            }

            //TODO: why don't we use declared member scope at this point?
            if (scope != null && scope.processFunctionsByName(
                    constructorName,
                    processor
                ) == ProcessorAction.STOP
            ) {
                return ProcessorAction.STOP
            }
        }
        return ProcessorAction.NEXT
    } catch (e: Throwable) {
        throw RuntimeException("While processing constructors", e)
    }
}