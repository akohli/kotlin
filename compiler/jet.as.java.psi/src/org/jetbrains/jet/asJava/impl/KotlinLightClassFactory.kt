/*
 * Copyright 2010-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.asJava.impl

import com.intellij.psi.PsiManager
import org.jetbrains.jet.lang.psi.JetClassOrObject
import org.jetbrains.jet.lang.psi.JetPsiUtil
import org.jetbrains.jet.lang.psi.JetObjectDeclaration
import org.jetbrains.jet.lang.resolve.java.JvmClassName
import org.jetbrains.jet.lang.psi.JetFile
import org.jetbrains.jet.asJava.LightClassUtil
import org.jetbrains.jet.codegen.binding.PsiCodegenPredictor
import org.jetbrains.jet.lang.resolve.name.FqName
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.PsiClass
import com.intellij.psi.impl.java.stubs.PsiClassStub
import com.intellij.openapi.util.Comparing
import com.intellij.psi.stubs.PsiFileStub
import org.jetbrains.jet.asJava.LightClassDataForKotlinClass
import org.jetbrains.jet.asJava.OutermostKotlinClassLightClassData
import com.intellij.psi.util.CachedValuesManager
import org.jetbrains.jet.asJava.KotlinJavaFileStubProvider
import com.intellij.openapi.util.Key
import com.intellij.psi.util.CachedValue
import com.intellij.psi.PsiTypeParameterListOwner
import com.intellij.psi.PsiTypeParameter
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.jet.asJava.KotlinLightClass
import com.intellij.psi.PsiParameter
import org.jetbrains.jet.lang.resolve.java.jetAsJava.KotlinLightMethod
import com.intellij.psi.PsiPackage
import org.jetbrains.jet.lang.psi.JetTypeParameterListOwner
import com.intellij.psi.impl.light.LightTypeParameterListBuilder
import com.intellij.psi.PsiTypeParameterList
import org.jetbrains.jet.lang.psi.JetDeclaration

public object KotlinLightClassFactory {
    private val JAVA_API_STUB: Key<CachedValue<OutermostKotlinClassLightClassData>> = Key.create("JAVA_API_STUB")

    public fun create(classOrObject: JetClassOrObject): KotlinLightClassForExplicitDeclaration? {
        if (LightClassUtil.belongsToKotlinBuiltIns((classOrObject.getContainingFile() as JetFile))) return null

        val jvmInternalName = getJvmInternalName(classOrObject)
        if (jvmInternalName == null) return null

        val fqName = JvmClassName.byInternalName(jvmInternalName).getFqNameForClassNameWithoutDollars()

        val manager = classOrObject.getManager()!!

        if (classOrObject is JetObjectDeclaration && ((classOrObject as JetObjectDeclaration)).isObjectLiteral()) {
            return KotlinLightClassForAnonymousDeclaration(manager, fqName, classOrObject)
        }

        return KotlinLightClassForExplicitDeclaration(manager, fqName, classOrObject)
    }

    public fun createForPackage(
            manager: PsiManager,
            qualifiedName: FqName,
            searchScope: GlobalSearchScope,
            files: Collection<JetFile>): KotlinLightClass? {
        for (file in files) {
            if (LightClassUtil.belongsToKotlinBuiltIns(file)) return null
        }

        return KotlinLightClassForPackage(manager, qualifiedName, searchScope, files)
    }

    public fun createPackage(psiManager: PsiManager, fqName: FqName, allScope: GlobalSearchScope): PsiPackage {
        return JetLightPackage(psiManager, fqName, allScope)
    }

    public fun createFakeForPackage(manager: PsiManager, delegate: KotlinLightClass, file: JetFile): KotlinLightClass {
        return FakeLightClassForFileOfPackage(manager, delegate, file)
    }

    public fun createTypeParameter(owner: PsiTypeParameterListOwner, index: Int, name: String): PsiTypeParameter {
        return KotlinLightTypeParameter(owner, index, name)
    }

    public fun createLightParameter(delegate: PsiParameter, index: Int, method: KotlinLightMethod): PsiParameter {
        return KotlinLightParameter(delegate, index, method)
    }

    public fun createLightTypeParameterList(owner: PsiTypeParameterListOwner, declaration: JetDeclaration): PsiTypeParameterList {
        val builder = LightTypeParameterListBuilder(owner.getManager(), owner.getLanguage())
        if (declaration is JetTypeParameterListOwner) {
            val parameters = declaration.getTypeParameters()
            for (i in 0..parameters.size() - 1) {
                val jetTypeParameter = parameters.get(i)
                val name = jetTypeParameter.getName()
                val safeName = if (name == null) "__no_name__" else name

                builder.addParameter(createTypeParameter(owner, i, safeName))
            }
        }
        return builder
    }

    fun findClass(fqn: FqName, stub: StubElement<*>): PsiClass? {
        if (stub is PsiClassStub && Comparing.equal(fqn.asString(), ((stub as PsiClassStub<PsiClass>)).getQualifiedName())) {
            return (stub.getPsi() as PsiClass)
        }

        if (stub is PsiClassStub || stub is PsiFileStub) {
            for (child in stub.getChildrenStubs()) {
                val answer = findClass(fqn, child as StubElement<*>)
                if (answer != null)
                    return answer
            }
        }

        return null
    }

    fun getOutermostClassOrObject(classOrObject: JetClassOrObject): JetClassOrObject {
        return JetPsiUtil.getOutermostClassOrObject(classOrObject) ?:
        throw IllegalStateException("Attempt to build a light class for a local class: " + classOrObject.getText())
    }

    fun getLightClassDataExactly(classOrObject: JetClassOrObject): LightClassDataForKotlinClass? {
        val data = getLightClassData(classOrObject)
        return if (data.classOrObject == classOrObject) data else data.allInnerClasses[classOrObject]
    }

    fun getLightClassData(classOrObject: JetClassOrObject): OutermostKotlinClassLightClassData {
        val outermostClassOrObject = getOutermostClassOrObject(classOrObject)
        return CachedValuesManager.getManager(classOrObject.getProject()).getCachedValue(
                outermostClassOrObject,
                JAVA_API_STUB,
                KotlinJavaFileStubProvider.createForDeclaredClass(outermostClassOrObject),
                false)!!
    }

    private fun getJvmInternalName(classOrObject: JetClassOrObject): String? {
        if (JetPsiUtil.isLocal(classOrObject)) {
            val data = getLightClassDataExactly(classOrObject)
            return data?.jvmInternalName
        }

        return PsiCodegenPredictor.getPredefinedJvmInternalName(classOrObject)
    }
}
