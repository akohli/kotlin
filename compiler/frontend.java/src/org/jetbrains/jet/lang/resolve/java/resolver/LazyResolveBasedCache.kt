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

package org.jetbrains.jet.lang.resolve.java.resolver

import org.jetbrains.jet.lang.descriptors.*
import org.jetbrains.jet.lang.resolve.java.structure.JavaClass
import org.jetbrains.jet.lang.resolve.java.structure.JavaElement
import org.jetbrains.jet.lang.resolve.java.structure.JavaField
import org.jetbrains.jet.lang.resolve.java.structure.JavaMethod
import org.jetbrains.jet.lang.resolve.lazy.ResolveSession
import org.jetbrains.jet.lang.resolve.lazy.ResolveSessionUtils
import org.jetbrains.jet.lang.resolve.name.FqName
import org.jetbrains.jet.lang.types.TypeProjection
import javax.inject.Inject
import java.util.Collections
import kotlin.properties.Delegates
import org.jetbrains.jet.lang.types.lang.KotlinBuiltIns
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import org.jetbrains.jet.lang.resolve.java.structure.impl.JavaClassImpl
import com.google.common.base.Predicates
import org.jetbrains.jet.lang.descriptors.impl.PackageViewDescriptorImpl
import org.jetbrains.jet.lang.resolve.name.tail
import org.jetbrains.jet.lang.resolve.name.each

public class LazyResolveBasedCache() : JavaResolverCache {
    class object {
        private val LOG = Logger.getInstance(javaClass<TraceBasedJavaResolverCache>())
    }

    private var resolveSession by Delegates.notNull<ResolveSession>()
    private val traceBasedCache = TraceBasedJavaResolverCache()

    Inject
    public fun setSession(resolveSession: ResolveSession) {
        this.resolveSession = resolveSession
        traceBasedCache.setTrace(this.resolveSession.getTrace())
    }

    override fun getClassResolvedFromSource(fqName: FqName): ClassDescriptor? {
        val descriptor = traceBasedCache.getClassResolvedFromSource(fqName)
        if (descriptor != null) return descriptor

        var foundClass: ClassDescriptor? = null

        (if (fqName.isRoot()) fqName else fqName.parent()).each {
            (parentFqName: FqName) : Boolean ->
            val parentPackageFragmentDescriptor = resolveSession.getPackageFragment(parentFqName)

            if (parentPackageFragmentDescriptor != null) {
                val classInPackagePath = fqName.tail(parentFqName)
                val classDescriptors = ResolveSessionUtils.getClassOrObjectFromPackage(
                        PackageViewDescriptorImpl(resolveSession.getModuleDescriptor(), parentFqName, listOf(parentPackageFragmentDescriptor)),
                        classInPackagePath,
                        { true })

                if (!classDescriptors.isEmpty()) {
                    foundClass = classDescriptors.first()
                    return@each false
                }
            }

            true
        }

        return foundClass
    }

    override fun getMethod(method: JavaMethod): SimpleFunctionDescriptor? {
        val descriptor = traceBasedCache.getMethod(method)
        if (descriptor != null) return descriptor

        val classFqName = method.getContainingClass().getFqName()
        if (classFqName == null) return null

        val containingClass = getClassResolvedFromSource(classFqName)
        if (containingClass == null) return null

        val memberScope = containingClass.getMemberScope(Collections.emptyList<TypeProjection>())
        val functions = memberScope.getFunctions(method.getName())

        return if (functions.isNotEmpty()) (functions.first() as SimpleFunctionDescriptor) else null
    }

    override fun getConstructor(constructor: JavaElement): ConstructorDescriptor? = traceBasedCache.getConstructor(constructor)

    override fun getClass(javaClass: JavaClass): ClassDescriptor? {
        return traceBasedCache.getClass(javaClass) ?: null
    }

    override fun recordMethod(method: JavaMethod, descriptor: SimpleFunctionDescriptor) {
        traceBasedCache.recordMethod(method, descriptor)
    }

    override fun recordConstructor(element: JavaElement, descriptor: ConstructorDescriptor) {
        traceBasedCache.recordConstructor(element, descriptor)
    }

    override fun recordField(field: JavaField, descriptor: PropertyDescriptor) {
        traceBasedCache.recordField(field, descriptor)
    }
    override fun recordClass(javaClass: JavaClass, descriptor: ClassDescriptor) {
        traceBasedCache.recordClass(javaClass, descriptor)
    }
}
