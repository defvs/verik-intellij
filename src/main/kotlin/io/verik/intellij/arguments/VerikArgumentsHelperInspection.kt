/*
 * Copyright (c) 2022 Francis Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.verik.intellij.arguments

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.KtValueArgumentList
import org.jetbrains.kotlin.psi.KtVisitorVoid

class VerikArgumentsHelperInspection : AbstractKotlinInspection() {

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return VerikArgumentsHelperVisitor(holder)
    }

    override fun getStaticDescription(): String {
        return "Add missing call arguments"
    }


    private class VerikArgumentsHelperVisitor(
        private val holder: ProblemsHolder
    ): KtVisitorVoid() {

        override fun visitValueArgumentList(list: KtValueArgumentList) {
            val descriptor = VerikArgumentsHelperUtil.getDescriptor(list) ?: return
            if (descriptor.valueParameters.size > list.arguments.size) {
                val quickFix = VerikArgumentsHelperQuickFix()
                holder.registerProblem(list, quickFix.name, quickFix)
            }
        }
    }
}
